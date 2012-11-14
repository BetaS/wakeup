package com.realapps.wakeup.scene.main.alarm.activity.receiver;

import com.realapps.wakeup.core.db.AlarmDB;
import com.realapps.wakeup.scene.main.alarm.SetAlarmDay;
import com.realapps.wakeup.scene.main.alarm.SetupAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.DefaultAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.DumbbellAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.GogiAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.KissAlarm;
import com.realapps.wakeup.scene.main.alarm.activity.MathAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm!!!", Toast.LENGTH_SHORT).show();
		
		int alarm_type = intent.getIntExtra("alarm_type", SetupAlarm.ALARM_DEFAULT);
		int difficulty = intent.getIntExtra("difficulty", SetupAlarm.DIFFICULT_EASY);
		
		int repeat = intent.getIntExtra("repeat", SetAlarmDay.REPEAT_ONCE);
		if(repeat == SetAlarmDay.REPEAT_ONCE) {
			AlarmDB.remove(context, intent.getIntExtra("id", 0));
		} else {
			ResetAlarm.setNextAlarm(context, intent);
		}
		
		
		Intent alarm = null;
		if(alarm_type == SetupAlarm.ALARM_DEFAULT) {
			alarm = new Intent(context, DefaultAlarm.class);
		} else if(alarm_type == SetupAlarm.ALARM_DUMBBELL) {
			alarm = new Intent(context, DumbbellAlarm.class);
		} else if(alarm_type == SetupAlarm.ALARM_GOGI) {
			alarm = new Intent(context, GogiAlarm.class);
		} else if(alarm_type == SetupAlarm.ALARM_KISS) {
			alarm = new Intent(context, KissAlarm.class);
		} else if(alarm_type == SetupAlarm.ALARM_MATH) {
			alarm = new Intent(context, MathAlarm.class);
		}
		alarm.putExtra(SetupAlarm.INTENT_ALARM_DIFFICUALT, difficulty);
		alarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(alarm);
	}
}
