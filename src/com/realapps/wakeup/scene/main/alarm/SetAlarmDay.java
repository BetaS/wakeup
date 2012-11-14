package com.realapps.wakeup.scene.main.alarm;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.realapps.wakeup.R;

public class SetAlarmDay extends Activity implements OnTouchListener, OnClickListener, OnCheckedChangeListener {
	public static final String 	INTENT_REPEAT			= "repeat";
	public static final int		REPEAT_ONCE				= R.id.chk_once;
	public static final int		REPEAT_DAY				= R.id.chk_day;
	public static final int		REPEAT_WEEK				= R.id.chk_week;
	
	public static final String 	INTENT_DAY					= "day";
	public static final int 		INTENT_DAY_SUNDAY		= 0;
	public static final int 		INTENT_DAY_MONDAY		= 1;
	public static final int 		INTENT_DAY_TUESDAY		= 2;
	public static final int 		INTENT_DAY_WEDNESDAY	= 3;
	public static final int 		INTENT_DAY_THURSDAY		= 4;
	public static final int 		INTENT_DAY_FRIDAY			= 5;
	public static final int 		INTENT_DAY_SATURDAY		= 6;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_day_setup);
		
		getInfoOfViews();
		
		Intent data = getIntent();
		
		if(data != null) {
			int type = data.getIntExtra(INTENT_REPEAT, REPEAT_ONCE);
			chk_repeat.check(type);
			
			int[] days = data.getIntArrayExtra(INTENT_DAY);
			if(days != null) {
				for(int day: days) {
					checkDay(day);
				}
			}
		}
		
		setupViews();
	}


	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP) {
			if(isCheckDay(view)) {
				uncheckDay(view);
			} else {
				checkDay(view);
			}
		}
		return false;
	}
	
	@Override
	public void onClick(View view) {
		if(view == btn_ok) {
			Intent result = new Intent();
			
			int repeat = chk_repeat.getCheckedRadioButtonId();
			result.putExtra(INTENT_REPEAT, repeat);
			
			if(repeat != REPEAT_DAY) {
				ArrayList<Integer> list = new ArrayList<Integer>();
				for(int i=0; i<btn_day.length; i++) {
					if(isCheckDay(i)) {
						list.add(i);
					}
				}
				int[] days = new int[list.size()];
				for(int i=0; i<days.length; i++) {
					days[i] = list.get(i);
				}
				
				result.putExtra(INTENT_DAY, days);
			}
			
			setResult(RESULT_OK, result);
			finish();
		} else if(view == btn_cancle) {
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		setupViews();
	}
	
	private RadioGroup chk_repeat	= null;
	
	private Button[] btn_day 			= new Button[7];
	private Button btn_ok				= null;
	private Button btn_cancle			= null;
	
	private void getInfoOfViews() {
		// Get View Objects
		chk_repeat		= (RadioGroup)findViewById(R.id.chk_repeat);

		btn_day[0] 		= (Button)findViewById(R.id.btn_sunday);
		btn_day[1] 		= (Button)findViewById(R.id.btn_monday);
		btn_day[2] 		= (Button)findViewById(R.id.btn_tuesday);
		btn_day[3] 		= (Button)findViewById(R.id.btn_wednesday);
		btn_day[4] 		= (Button)findViewById(R.id.btn_thursday);
		btn_day[5] 		= (Button)findViewById(R.id.btn_friday);
		btn_day[6] 		= (Button)findViewById(R.id.btn_saturday);
		
		btn_ok			= (Button)findViewById(R.id.btn_ok);
		btn_cancle		= (Button)findViewById(R.id.btn_cancle);
		
		// Attach Listeners
		chk_repeat		.setOnCheckedChangeListener(this);
		
		for(int i=0; i<btn_day.length; i++) {
			btn_day[i].setOnTouchListener(this);
			btn_day[i].setContentDescription("");
		}

		btn_ok		.setOnClickListener(this);
		btn_cancle	.setOnClickListener(this);
	}
	
	private void setupViews() {
		switch(chk_repeat.getCheckedRadioButtonId()) {
		case REPEAT_DAY: {
			for(int i=0; i<btn_day.length; i++) {
				btn_day[i].setEnabled(false);
			}
		} break;
		default: {
			for(int i=0; i<btn_day.length; i++) {
				btn_day[i].setEnabled(true);
			}
		} break;
		}
	}

	private boolean isCheckDay(int day) {
		return isCheckDay(btn_day[day]);
	}
	private boolean isCheckDay(View view) {
		if(view.getContentDescription().length() > 0) {
			return true;
		}
		return false;
	}

	private void checkDay(int day) {
		checkDay(btn_day[day]);
	}
	private void checkDay(View view) {
		view.setContentDescription("select");
		view.getBackground().setColorFilter(0xff00ccff, PorterDuff.Mode.MULTIPLY);
		view.postInvalidate();
	}
	
	@SuppressWarnings("unused")
	private void uncheckDay(int day) {
		uncheckDay(btn_day[day]);
	}
	private void uncheckDay(View view) {
		view.setContentDescription("");
		view.getBackground().clearColorFilter();
		view.postInvalidate();
	}
}
