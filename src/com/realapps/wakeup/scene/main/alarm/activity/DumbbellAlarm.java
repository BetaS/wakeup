package com.realapps.wakeup.scene.main.alarm.activity;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.realapps.wakeup.R;
import com.realapps.wakeup.scene.main.alarm.SetupAlarm;
import com.realapps.wakeup.widget.ClearSwitch;

public class DumbbellAlarm extends Activity implements SensorEventListener {
	private static final int HARD_MIN	= 40;
	private static final int HARD_MAX	= 60;
	private static final int NORMAL_MIN	= 25;
	private static final int NORMAL_MAX	= 40;
	private static final int EASY_MIN	= 10;
	private static final int EASY_MAX	= 25;
	
	private ClearSwitch clearSwitch = null;
	private TextView txt_mission = null;
	
	private int mission_count = 0;
	
	private SensorManager sensorManager = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_dumbbell);
		
		clearSwitch = (ClearSwitch)findViewById(R.id.onoff_switch);
		txt_mission = (TextView)findViewById(R.id.txt_mission);
		
		int difficult = getIntent().getIntExtra(SetupAlarm.INTENT_ALARM_DIFFICUALT, SetupAlarm.DIFFICULT_EASY);
		
		if(difficult == SetupAlarm.DIFFICULT_EASY)
			mission_count = new Random().nextInt(EASY_MAX-EASY_MIN)+EASY_MIN;
		else if(difficult == SetupAlarm.DIFFICULT_NORMAL)
			mission_count = new Random().nextInt(NORMAL_MAX-NORMAL_MIN)+NORMAL_MIN;
		else if(difficult == SetupAlarm.DIFFICULT_HARD)
			mission_count = new Random().nextInt(HARD_MAX-HARD_MIN)+HARD_MIN;
		
		txt_mission.setText("아령운동 "+mission_count+"번!!!");
	}
	
	private MediaPlayer player = null;
	private AudioManager am = null;
	private int volume = 0;
	
	@Override
	public void onResume() {
		super.onResume();
		
		am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		volume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_SHOW_UI);
		player = MediaPlayer.create(this, R.raw.alarm);
		player.setLooping(true);
		player.start();
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager
		.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		sensorManager.unregisterListener(this);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
		player.stop();
	}
	@Override
	public void onBackPressed() {
		if(clearSwitch.isOff()) {
			finish();
		}
	}
	
	private void alarmOff() {
		txt_mission.setText("뒤로가기 버튼을 누르면 종료됩니다.");
		clearSwitch.setOff();
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	private boolean upping = true;
	private double dist = 0.0;
	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		
		switch (sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER: {
			event.values[2] -= 9.8;
			Log.e("Sensor", "dist:"+dist+", Z:"+event.values[2]);
			if(upping && event.values[2] <= -1.0)
				dist += event.values[2];
			else if(!upping && event.values[2] >= 1.0)
				dist += event.values[2];
			
			if(dist >= 25.0 && !upping) {
				Log.e("Dumbbell", "Down: "+dist);
				
				upping = true;
			} else if(dist <= -25.0 && upping) {
				Log.e("Dumbbell", "Up: "+dist);
				
				upping = false;
				mission_count--;
				txt_mission.setText("아령운동 "+mission_count+"번!!!");
				
				if(mission_count <= 0) {
					alarmOff();
				}
			}
		} break;
		}
	}
}
