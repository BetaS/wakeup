package com.realapps.wakeup.scene.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.realapps.wakeup.R;
import com.realapps.wakeup.scene.main.alarm.AlarmList;
import com.realapps.wakeup.scene.main.alarm.SetupAlarm;
import com.realapps.wakeup.scene.main.mail.Mail;

public class Main extends Activity {
	private Button btn_setAlarm = null;
	private Button btn_getAlarm = null;
	private Button btn_viewMail = null;
	//private Button btn_viewCal = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btn_setAlarm = (Button)findViewById(R.id.btn_set_alarm);
        btn_setAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, SetupAlarm.class);
				startActivity(intent);
			}
		});
        
        btn_getAlarm = (Button)findViewById(R.id.btn_view_alarm);
        btn_getAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, AlarmList.class);
				startActivity(intent);
			}
		});
        
        btn_viewMail = (Button)findViewById(R.id.btn_view_mail);
        btn_viewMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, Mail.class);
				startActivity(intent);
			}
		});
        
       /* btn_viewCal = (Button)findViewById(R.id.btn_view_cal);
        btn_viewCal.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(Main.this, CalendarView.class);
        		startActivity(intent);
        	}
        });*/
    }
}