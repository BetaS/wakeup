package com.realapps.wakeup.scene.main.alarm.activity;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.realapps.wakeup.R;
import com.realapps.wakeup.scene.main.alarm.SetupAlarm;
import com.realapps.wakeup.widget.ClearSwitch;

public class GogiAlarm extends Activity {
	private static final int HARD_MIN	= 8;
	private static final int HARD_MAX	= 10;
	private static final int NORMAL_MIN	= 5;
	private static final int NORMAL_MAX	= 8;
	private static final int EASY_MIN	= 3;
	private static final int EASY_MAX	= 5;
	
	private RelativeLayout alarm_layout = null;
	private ClearSwitch clearSwitch = null;
	private TextView txt_mission = null;
	
	private int mission_count = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_gogi);
		
		alarm_layout = (RelativeLayout)findViewById(R.id.layout_alarm);
		clearSwitch = (ClearSwitch)findViewById(R.id.onoff_switch);
		txt_mission = (TextView)findViewById(R.id.txt_mission);

		int difficult = getIntent().getIntExtra(SetupAlarm.INTENT_ALARM_DIFFICUALT, SetupAlarm.DIFFICULT_EASY);
		
		if(difficult == SetupAlarm.DIFFICULT_EASY)
			mission_count = new Random().nextInt(EASY_MAX-EASY_MIN)+EASY_MIN;
		else if(difficult == SetupAlarm.DIFFICULT_NORMAL)
			mission_count = new Random().nextInt(NORMAL_MAX-NORMAL_MIN)+NORMAL_MIN;
		else if(difficult == SetupAlarm.DIFFICULT_HARD)
			mission_count = new Random().nextInt(HARD_MAX-HARD_MIN)+HARD_MIN;
		
		for(int i=0; i<mission_count; i++) {
			ImageView gogi = new ImageView(this);
			gogi.setContentDescription("");
			
			Random random = new Random();
			final int gogi_cnt = random.nextInt(3);
			
			if(gogi_cnt == 0) {
				gogi.setBackgroundResource(R.drawable.gogi1_off);
				gogi.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						v.setOnClickListener(null);
						
						v.setBackgroundResource(R.drawable.gogi1_on);
						v.postDelayed(new Runnable() {
							@Override
							public void run() {
								v.setVisibility(View.GONE);
							}
						}, 3000);
						mission_count--;
						
						if(mission_count <= 0) alarmOff();
					}
				});
			} else if(gogi_cnt == 1) {
				gogi.setBackgroundResource(R.drawable.gogi2_off);
				gogi.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						v.setOnClickListener(null);
						
						v.setBackgroundResource(R.drawable.gogi2_on);
						v.postDelayed(new Runnable() {
							@Override
							public void run() {
								v.setVisibility(View.GONE);
							}
						}, 3000);
						mission_count--;
						
						if(mission_count <= 0) alarmOff();
					}
				});
			} else if(gogi_cnt == 2) {
				gogi.setBackgroundResource(R.drawable.gogi3_off);
				gogi.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						v.setOnClickListener(null);
						
						v.setBackgroundResource(R.drawable.gogi3_on);
						v.postDelayed(new Runnable() {
							@Override
							public void run() {
								v.setVisibility(View.GONE);
							}
						}, 3000);
						mission_count--;
						
						if(mission_count <= 0) alarmOff();
					}
				});
			} else if(gogi_cnt == 3) {
				gogi.setBackgroundResource(R.drawable.gogi4_off);
				gogi.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(final View v) {
						v.setOnClickListener(null);
						
						v.setBackgroundResource(R.drawable.gogi4_on);
						v.postDelayed(new Runnable() {
							@Override
							public void run() {
								v.setVisibility(View.GONE);
							}
						}, 3000);
						mission_count--;
						
						if(mission_count <= 0) alarmOff();
					}
				});
			}
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(random.nextInt(400)+100, random.nextInt(100)+100, 0, 0);

			alarm_layout.addView(gogi, params);
		}
		
		
		txt_mission.setText("고기를 전부 뒤집어주세요");
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
	
	private void alarmOff() {
		txt_mission.setText("뒤로가기 버튼을 누르면 종료됩니다.");
		clearSwitch.setOff();
	}
}
