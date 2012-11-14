package com.realapps.wakeup.scene.main.alarm.activity;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.realapps.wakeup.R;
import com.realapps.wakeup.scene.main.alarm.SetupAlarm;
import com.realapps.wakeup.widget.ClearSwitch;

public class KissAlarm extends Activity {
	private static final int HARD_MIN	= 80;
	private static final int HARD_MAX	= 150;
	private static final int NORMAL_MIN	= 50;
	private static final int NORMAL_MAX	= 100;
	private static final int EASY_MIN	= 20;
	private static final int EASY_MAX	= 50;
	
	private ClearSwitch clearSwitch = null;
	private ImageView img_alarm = null;
	private TextView txt_mission = null;
	
	private int mission_count = 0;
	private boolean touched = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_morningkiss);
		
		clearSwitch = (ClearSwitch)findViewById(R.id.onoff_switch);
		img_alarm = (ImageView)findViewById(R.id.img_alarm);
		txt_mission = (TextView)findViewById(R.id.txt_mission);
		
		int difficult = getIntent().getIntExtra(SetupAlarm.INTENT_ALARM_DIFFICUALT, SetupAlarm.DIFFICULT_EASY);
		
		if(difficult == SetupAlarm.DIFFICULT_EASY)
			mission_count = new Random().nextInt(EASY_MAX-EASY_MIN)+EASY_MIN;
		else if(difficult == SetupAlarm.DIFFICULT_NORMAL)
			mission_count = new Random().nextInt(NORMAL_MAX-NORMAL_MIN)+NORMAL_MIN;
		else if(difficult == SetupAlarm.DIFFICULT_HARD)
			mission_count = new Random().nextInt(HARD_MAX-HARD_MIN)+HARD_MIN;
		
		txt_mission.setText("모닝키스 "+mission_count+"번!!!");
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
	}
	
	@Override
	public void onPause() {
		super.onPause();
		am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
		player.stop();
	}
	
	@Override
	public void onBackPressed() {
		if(clearSwitch.isOff()) {
			finish();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.e("Event", event.getAction()+", "+event.getPointerCount());
		if(event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN) {
			img_alarm.setBackgroundResource(R.drawable.morning_kiss_2);
			touched = true;
		} else if(touched && event.getAction() == MotionEvent.ACTION_UP) {
			touched = false;
			img_alarm.setBackgroundResource(R.drawable.morning_kiss_1);
			txt_mission.setText("모닝키스 "+(--mission_count)+"번!!!");
			
			if(mission_count <= 0) {
				alarmOff();
			}
		}
		
		return true;
	}
	
	private void alarmOff() {
		txt_mission.setText("뒤로가기 버튼을 누르면 종료됩니다.");
		clearSwitch.setOff();
	}
}
