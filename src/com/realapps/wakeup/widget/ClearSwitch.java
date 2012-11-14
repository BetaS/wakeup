package com.realapps.wakeup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.realapps.wakeup.R;

public class ClearSwitch extends LinearLayout {
	private View inflateView = null;
	private ImageView onoff_switch = null;
	private boolean off = false;
	
	public ClearSwitch(Context context, AttributeSet attr) {
		super(context, attr);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflateView = inflater.inflate(R.layout.clear_switch, null);
		this.addView(inflateView);
		
		onoff_switch = (ImageView)inflateView.findViewById(R.id.switch1);
	}
	

	private Runnable off_run = new Runnable() {
		@Override
		public void run() {
			MarginLayoutParams params = (MarginLayoutParams)onoff_switch.getLayoutParams();
			params.leftMargin += 10;
			
			if(params.leftMargin <= 150) {
				onoff_switch.setLayoutParams(params);
				onoff_switch.postDelayed(off_run, 10);
			} else {
				off = true;
			}
		}
	};
	
	public void setOff() {
		onoff_switch.post(off_run);
	}
	public boolean isOff() {
		return off;
	}
}
