package com.realapps.wakeup.scene.main.alarm.activity.receiver;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.realapps.wakeup.core.db.AlarmDB;
import com.realapps.wakeup.scene.main.alarm.AlarmList.Alarm;

public class ResetAlarm extends BroadcastReceiver {
	public static void setAlarm(Context context, Intent intent) {
		AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0), intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		int hour = intent.getIntExtra("hour", 0);
		int minute = intent.getIntExtra("minute", 0);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		long now = calendar.getTimeInMillis();
		
		int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
		int[] days = intent.getIntArrayExtra("days");
		int max_day = 0;
		for(int day: days) {
			if(max_day < day) { 
				max_day = day;
			}
		}
		
		week = (max_day - week);
		if(week < 0) week+=7;
		
		calendar.add(Calendar.DATE, week);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);

		long dest = calendar.getTimeInMillis();
		if(dest-now < 0) {
			setNextAlarm(context, intent);
		} else {
			manager.set(AlarmManager.RTC_WAKEUP, dest, sender);
		}
	}
	
	public static void setNextAlarm(Context context, Intent intent) {
		AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0), intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		int hour = intent.getIntExtra("hour", 0);
		int minute = intent.getIntExtra("minute", 0);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.DATE, 1);
		
		int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
		int[] days = intent.getIntArrayExtra("days");
		int max_day = 0;
		for(int day: days) {
			if(max_day < day) { 
				max_day = day;
			}
		}
		
		week = (max_day - week);
		if(week < 0) week+=7;
		
		calendar.add(Calendar.DATE, week);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		
		Log.e("Calender", calendar.getTime().toLocaleString());
		
		manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
	}
	
	public static void removeAlarm(Context context, Intent intent, int id) {
		AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		PendingIntent sender = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		manager.cancel(sender);
	}
	
	private ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	@Override
	public void onReceive(Context context, Intent intent) {
		Cursor result = AlarmDB.select(context);
        while(result.moveToNext()) {
        	Alarm alarm = new Alarm();
        	alarm.id = result.getInt(result.getColumnIndex("id"));
        	
        	alarm.hour = result.getInt(result.getColumnIndex("hour"));
        	alarm.minute = result.getInt(result.getColumnIndex("minute"));
        	
        	alarm.alarm_style = result.getInt(result.getColumnIndex("alarm_type"));
        	alarm.alarm_repeat = result.getInt(result.getColumnIndex("repeat"));
        	
        	alarm.alarm_difficulty = result.getInt(result.getColumnIndex("difficulty"));
        	alarm.alarm_day_flag = result.getInt(result.getColumnIndex("day_flag"));
        	
        	alarms.add(alarm);
        }
        result.close();
        
        for(Alarm alarm: alarms) {
        	int[] day = new int[7];
        	int size = 0;
        	for(int i=0; i<7; i++) {
        		if((alarm.alarm_day_flag/(int)Math.pow(10, i)) % 10 == 1) {
        			day[size] = i;
        			size++;
        		}
        	}
        	
        	int[] days = new int[size];
        	for(int i=0; i<size; i++) {
        		days[i] = day[i];
        	}

        	Intent send = new Intent(context, AlarmReceiver.class);
        	send.putExtra("id", alarm.id);
        	send.putExtra("hour", alarm.hour);
        	send.putExtra("minute", alarm.minute);
        	send.putExtra("repeat", alarm.alarm_repeat);
        	send.putExtra("days", days);
        	send.putExtra("alarm_type", alarm.alarm_style);
        	send.putExtra("difficulty", alarm.alarm_difficulty);
        	
        	setAlarm(context, send);
        }
	}
}
