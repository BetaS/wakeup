package com.realapps.wakeup.scene.main.alarm.activity;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.realapps.wakeup.R;
import com.realapps.wakeup.scene.main.alarm.SetupAlarm;
import com.realapps.wakeup.widget.ClearSwitch;
import com.realapps.wakeup.widget.MathQuestion;

public class MathAlarm extends Activity implements OnClickListener{
	private static final int HARD_QUESTION_MIN		= 5;
	private static final int HARD_QUESTION_MAX		= 7;
	private static final int NORMAL_QUESTION_MIN	= 3;
	private static final int NORMAL_QUESTION_MAX	= 5;
	private static final int EASY_QUESTION_MIN			= 2;
	private static final int EASY_QUESTION_MAX		= 3;
	
	private ArrayList<MathQuestion> questions = new ArrayList<MathQuestion>();
	
	private LinearLayout layout = null;
	private ClearSwitch clearSwitch = null;
	private TextView txt_mission = null;
	private Button btn_ok = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_math);
		
		layout = (LinearLayout)findViewById(R.id.layout01);
		clearSwitch = (ClearSwitch)findViewById(R.id.onoff_switch);
		txt_mission = (TextView)findViewById(R.id.txt_mission);
		btn_ok = (Button)findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
		
		Random random = new Random();
		int difficult = getIntent().getIntExtra(SetupAlarm.INTENT_ALARM_DIFFICUALT, SetupAlarm.DIFFICULT_EASY);
		if(difficult == SetupAlarm.DIFFICULT_EASY) {
			int size = random.nextInt(EASY_QUESTION_MAX-EASY_QUESTION_MIN)+EASY_QUESTION_MIN;
			for(int i=0; i<size; i++) {
				int number1 = 0;
				int number2 = 0;
				int answer = 0;
				char op = '+';

				int oper = random.nextInt(2);
				if(oper == 0) { // 더하기
					op = '+';
					number1 = random.nextInt(99);
					number2 = random.nextInt(99);
					answer = number1+number2;
				} else if(oper == 1) { // 빼기
					op = '-';
					number1 = random.nextInt(69)+30;
					number2 = random.nextInt(number1);
					answer = number1-number2;
				} else if(oper == 2) { // 곱하기
					op = '*';
					number1 = random.nextInt(9);
					number2 = random.nextInt(9);
					answer = number1*number2;
				}
				
				MathQuestion question = new MathQuestion(this);
				question.setQuestion(number1+" "+op+" "+number2+" = ", answer);
				questions.add(question);
				
				layout.addView(question);
			}
		} else if(difficult == SetupAlarm.DIFFICULT_NORMAL) {
			int size = random.nextInt(NORMAL_QUESTION_MAX-NORMAL_QUESTION_MIN)+NORMAL_QUESTION_MIN;
			for(int i=0; i<size; i++) {
				String str = "";
				double answer = 0.0;
				int oper = random.nextInt(4);
				if(oper == 0) { // 더하기
					int number1 = random.nextInt(899)+100;
					int number2 = random.nextInt(989)+10;
					
					str = number1+" + "+number2; 
					answer = number1+number2;
					
					oper = random.nextInt();
					if(oper%2 == 0) {
						int number3 = random.nextInt(989)+10;
						
						str = str+" + "+number3;
						answer += number3;
					}
					str = str+" = ";
				} else if(oper == 1) { // 빼기
					int number1 = random.nextInt(969)+30;
					int number2 = random.nextInt(number1);
					
					str = number1+" - "+number2;
					answer = number1-number2;
					
					oper = random.nextInt();
					if(oper%2 == 0 && answer > 10) {
						int number3 = random.nextInt((int)answer);
						
						str = str+" - "+number3;
						answer -= number3;
					}
					str = str+" = ";
				} else if(oper == 2) { // 곱하기
					int number1 = random.nextInt(88)+11;
					int number2 = random.nextInt(88)+11;

					str = number1+" * "+number2+" = ";
					answer = number1*number2;
				} else if(oper == 3) { // 나누기
					int number1 = random.nextInt(16)+3;
					int number2 = random.nextInt(9);
					
					str = (number1*number2)+" / "+number2+" = ";
					answer = (number1*number2)/number2;
				} else if(oper == 4) { // 복합 연산
					int number1 = random.nextInt(969)+30;
					int number2 = random.nextInt(number1);
					int number3 = random.nextInt(899)+100;
					
					str = number1+" - "+number2+" + "+number3+" = ";
					answer = number1-number2+number3;
				} 
				
				MathQuestion question = new MathQuestion(this);
				question.setQuestion(str, answer);
				questions.add(question);
				
				layout.addView(question);
			} 
		} else if(difficult == SetupAlarm.DIFFICULT_HARD) {
			int size = random.nextInt(HARD_QUESTION_MAX-HARD_QUESTION_MIN)+HARD_QUESTION_MIN;
			for(int i=0; i<size; i++) {
				String str = "";
				double answer = 0.0;
				int oper = random.nextInt(4);
				if(oper == 0) { // 더하기
					int number1 = random.nextInt(8999)+1000;
					int number2 = random.nextInt(9899)+100;
					int number3 = random.nextInt(9899)+100;
					
					str = number1+" + "+number2+" + "+number3+" = "; 
					answer = number1+number2+number3;
				} else if(oper == 1) { // 빼기
					int number1 = random.nextInt(9699)+300;
					int number2 = random.nextInt(number1);
					answer = number1-number2;
					int number3 = random.nextInt((int)answer);
					
					str = number1+" - "+number2+" - "+number3+" = ";
					answer = number1-number2-number3;
				} else if(oper == 2) { // 곱하기
					int number1 = random.nextInt(998)+1;
					int number2 = random.nextInt(98)+1;

					str = number1+" * "+number2;
					answer = number1*number2;
					
					oper = random.nextInt();
					if(oper%2 == 0) {
						int number3 = random.nextInt(number2)+1;
						
						str = str+" * "+number3;
						answer *= number3;
					}
					str = str+" = ";
				} else if(oper == 3) { // 나누기
					int number1 = random.nextInt(88)+11;
					int number2 = random.nextInt(88)+11;
					
					str = (number1*number2)+" / "+number2+" = ";
					answer = (number1*number2)/number2;
				} else if(oper == 4) { // 복합 연산
					int number1 = random.nextInt(9699)+300;
					int number2 = random.nextInt(number1);
					int number3 = random.nextInt(9899)+100;
					
					str = number1+" - "+number2+" + "+number3+" = ";
					answer = number1-number2+number3;
				} 
				
				MathQuestion question = new MathQuestion(this);
				question.setQuestion(str, answer);
				questions.add(question);
				
				layout.addView(question);
			}
		}
		
		txt_mission.setText("문제를 풀어라!!");
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
	public void onClick(View v) {
		int clear = 0;
		for(MathQuestion question: questions) {
			question.checkAnswer();
			if(question.isClear()) {
				clear++;
			}
		}
		
		if(clear >= questions.size()) {
			alarmOff();
		}
	}
	
	private void alarmOff() {
		txt_mission.setText("뒤로가기 버튼을 누르면 종료됩니다.");
		clearSwitch.setOff();
	}
}
