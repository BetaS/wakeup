package com.realapps.wakeup.scene.main.alarm.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import com.realapps.wakeup.R;

public class DefaultAlarm extends Activity {
	private TextView txt_mission = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_default);
		
		txt_mission = (TextView)findViewById(R.id.txt_mission);

		txt_mission.setText("뒤로가기 버튼을 누르면 종료됩니다.");
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
		finish();
	}
}
