package com.realapps.wakeup.scene.main.alarm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.realapps.wakeup.R;
import com.realapps.wakeup.core.db.AlarmDB;
import com.realapps.wakeup.scene.main.alarm.activity.DefaultAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.DumbbellAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.GogiAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.KissAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.MathAlarm;

public class SetupAlarm extends Activity implements OnTouchListener, OnClickListener, OnTimeChangedListener {
	/*
	 *  Request ID Defines
	 */
	public static final int 		REQUEST_SELECT_ALARM 		= 1;
	public static final int 		REQUEST_SELECT_DAY			= 2;
	
	/*
	 *  Intent Data Defines
	 */
	public static final String 	INTENT_ALARM 		= "alarm_style";
	
	public static final int		ALARM_DEFAULT		= 0;
	public static final int		ALARM_DUMBBELL	= 1;
	public static final int		ALARM_GOGI			= 2;
	public static final int		ALARM_MATH			= 3;
	public static final int		ALARM_KISS				= 4;
	
	public static final String	INTENT_ALARM_DIFFICUALT = "alarm_difficulty";
	
	public static final int 		DIFFICULT_HARD		= 0;
	public static final int 		DIFFICULT_NORMAL	= 1;
	public static final int 		DIFFICULT_EASY		= 2;
	
	/*
	 * 2011/08/01 - BetaS
	 * SetupAlarm Activity
	 */
	private int	alarm_difficulty		= DIFFICULT_EASY;
	private int 	alarm_style 			= ALARM_DEFAULT;
	private int 	alarm_repeat 		= SetAlarmDay.REPEAT_ONCE;
	private int[] alarm_repeat_day 	= null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_setup);
		
		getInfoOfViews();
		setAlarmDifficult(DIFFICULT_EASY);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent result) {
		super.onActivityResult(requestCode, resultCode, result);
		
		if(resultCode == RESULT_OK) {
			if(requestCode == REQUEST_SELECT_ALARM) {
				setAlarmStyle(result.getIntExtra(INTENT_ALARM, alarm_style));
			} else if(requestCode == REQUEST_SELECT_DAY) {
				setAlarmRepeat(result.getIntExtra(SetAlarmDay.INTENT_REPEAT, SetAlarmDay.REPEAT_ONCE), result.getIntArrayExtra(SetAlarmDay.INTENT_DAY));
			}
		}
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		String str = String.format("%02d : %02d %s", (hourOfDay%12), minute, ((hourOfDay < 12)?"오전":"오후"));
		txt_time.setText(str);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			v.setBackgroundColor(0xff0066aa);
		} else {
			v.setBackgroundColor(0xff000000);
			if(event.getAction() == MotionEvent.ACTION_UP) {
				if(v.getId() == R.id.layout01) {
					Intent intent = new Intent(this, SetAlarmDay.class);
					intent.putExtra(SetAlarmDay.INTENT_REPEAT, alarm_repeat);
					intent.putExtra(SetAlarmDay.INTENT_DAY, alarm_repeat_day);
					
					startActivityForResult(intent, REQUEST_SELECT_DAY);
				} else if(v.getId() == R.id.layout02) {
					AlertDialog.Builder ab = new AlertDialog.Builder(this);
					ab.setItems(new String[]{"상", "중", "하"}, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							setAlarmDifficult(which);
						}
					});
					ab.create().show();
				}
			}
		}

		return false;
	}

	@Override
	public void onClick(View view) {
		if(view == btn_alarm) {
			Intent intent = new Intent(this, SelectAlarm.class);
			intent.putExtra(INTENT_ALARM, alarm_style);
			startActivityForResult(intent, REQUEST_SELECT_ALARM);
		} else if(view == btn_preview) {
			Intent intent = null;
			if(alarm_style == ALARM_DEFAULT)
				intent = new Intent(this, DefaultAlarm.class);
			else if(alarm_style == ALARM_KISS)
				intent = new Intent(this, KissAlarm.class);
			else if(alarm_style == ALARM_GOGI)
				intent = new Intent(this, GogiAlarm.class);
			else if(alarm_style == ALARM_MATH)
				intent = new Intent(this, MathAlarm.class)
			;else if(alarm_style == ALARM_DUMBBELL)
					intent = new Intent(this, DumbbellAlarm.class);
			
			startActivity(intent);
		} else if(view == btn_ok) {
			if(alarm_repeat == SetAlarmDay.REPEAT_DAY) {
				alarm_repeat_day = new int[]{0, 1, 2, 3, 4, 5, 6};
			}
			AlarmDB.write(this, pck_time.getCurrentHour(), pck_time.getCurrentMinute(), alarm_repeat, alarm_repeat_day, alarm_style, alarm_difficulty);
			finish();
		}
	}
	
	/*
	 * 2011/07/30 - BetaS
	 * View 관련 객체들
	 */
	private ImageButton btn_alarm 		= null;
	private Button btn_preview 			= null;
	private Button btn_ok 				= null;
	
	private TextView txt_time 			= null;
	private TextView txt_repeat 			= null;
	private TextView txt_diff_hard 		= null;
	private TextView txt_diff_normal 	= null;
	private TextView txt_diff_easy 		= null;
	private TextView[] txt_day 			= new TextView[7];
	
	private TimePicker pck_time = null;
	
	private void getInfoOfViews() {
		// Get View Objects
		btn_alarm 		= (ImageButton)findViewById(R.id.btn_alarm);
		btn_preview		= (Button)findViewById(R.id.btn_preview);
		btn_ok			= (Button)findViewById(R.id.btn_ok);
		
		txt_time 			= (TextView)findViewById(R.id.txt_time);
		txt_repeat 		= (TextView)findViewById(R.id.txt_repeat);
		
		txt_diff_hard 		= (TextView)findViewById(R.id.txt_diff_hard);
		txt_diff_normal 	= (TextView)findViewById(R.id.txt_diff_normal);
		txt_diff_easy 		= (TextView)findViewById(R.id.txt_diff_easy);
		
		txt_day[0] 		= (TextView)findViewById(R.id.txt_sunday);
		txt_day[1] 		= (TextView)findViewById(R.id.txt_monday);
		txt_day[2] 		= (TextView)findViewById(R.id.txt_tuesday);
		txt_day[3] 		= (TextView)findViewById(R.id.txt_wednesday);
		txt_day[4] 		= (TextView)findViewById(R.id.txt_thursday);
		txt_day[5] 		= (TextView)findViewById(R.id.txt_friday);
		txt_day[6] 		= (TextView)findViewById(R.id.txt_saturday);
		
		pck_time 			= (TimePicker)findViewById(R.id.pck_date);
		
		// Init Views
		btn_ok.getBackground().setColorFilter(0xFF39AACC, PorterDuff.Mode.MULTIPLY);
		btn_ok.setTextColor(0xFFFFFFFF);
		
		String str = String.format("%02d : %02d %s", (pck_time.getCurrentHour()%12), pck_time.getCurrentMinute(), ((pck_time.getCurrentHour() < 12)?"오전":"오후"));
		txt_time.setText(str);

		setAlarmStyle(alarm_style);
		setAlarmRepeat(SetAlarmDay.REPEAT_ONCE, new int[]{(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1)});
		
		// Attach Listeners
		btn_alarm.setOnClickListener(this);
		btn_preview.setOnClickListener(this);
		btn_ok.setOnClickListener(this);
		
		pck_time.setOnTimeChangedListener(this);
		
		findViewById(R.id.layout01).setOnTouchListener(this);
		findViewById(R.id.layout02).setOnTouchListener(this);
	}
	
	/*
	 * Alarm Style Set
	 */
	private void setAlarmStyle(int style) {
		this.alarm_style = style;
		
		switch(alarm_style) {
		case ALARM_DEFAULT: {
			btn_alarm.setBackgroundResource(R.drawable.default_icon_12);
		} break;
		case ALARM_DUMBBELL: {
			btn_alarm.setBackgroundResource(R.drawable.dumbbell_icon_12);
		} break;
		case ALARM_GOGI: {
			btn_alarm.setBackgroundResource(R.drawable.gogi_icon_12);
		} break;
		case ALARM_MATH: {
			btn_alarm.setBackgroundResource(R.drawable.math_icon_12);
		} break;
		case ALARM_KISS: {
			btn_alarm.setBackgroundResource(R.drawable.morning_kiss_icon_12);
		} break;
		default: {
			btn_alarm.setBackgroundResource(R.drawable.rock_icon_12);
		} break;
		}
	}
	
	/*
	 * Alarm Repeat Set
	 */
	private void setAlarmRepeat(int repeat, int[] days) {
		this.alarm_repeat 		= repeat;
		this.alarm_repeat_day 	= days;

		if(repeat == SetAlarmDay.REPEAT_ONCE) {
			txt_repeat.setText("(한번)");
		} else if(repeat == SetAlarmDay.REPEAT_DAY) {
			txt_repeat.setText("(매일)");
		} else if(repeat == SetAlarmDay.REPEAT_WEEK) {
			txt_repeat.setText("(매주)");
		}
		
		for(TextView text: txt_day) {
			text.setTextColor(0xFF666666);
			text.postInvalidate();
			text.setVisibility(View.VISIBLE);
		}
		
		if(repeat == SetAlarmDay.REPEAT_DAY) {
			for(TextView text: txt_day) {
				text.setVisibility(View.GONE);
			}
		} else {
			if(days != null) {
				for(int day: days) {
					txt_day[day].setTextColor(0xFF0066AA);
					txt_day[day].postInvalidate();
				}
			}
		}
	}
	/*
	 * Alarm Difficulty Set
	 */
	private void setAlarmDifficult(int difficulty) {
		this.alarm_difficulty = difficulty;
		
		txt_diff_easy.setTextColor(0xFF666666);
		txt_diff_normal.setTextColor(0xFF666666);
		txt_diff_hard.setTextColor(0xFF666666);
		
		if(alarm_difficulty == DIFFICULT_EASY) {
			txt_diff_easy.setTextColor(0xFF0066AA);
		} else if(alarm_difficulty == DIFFICULT_NORMAL) {
			txt_diff_normal.setTextColor(0xFF0066AA);
		} else if(alarm_difficulty == DIFFICULT_HARD) {
			txt_diff_hard.setTextColor(0xFF0066AA);
		}
	}
}
