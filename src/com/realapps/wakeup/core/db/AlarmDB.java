package com.realapps.wakeup.core.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.realapps.wakeup.scene.main.alarm.activity.receiver.AlarmReceiver;
import com.realapps.wakeup.scene.main.alarm.activity.receiver.ResetAlarm;

public class AlarmDB {
	public static class DBOpener extends SQLiteOpenHelper {
		private static final String create_query = 
			"CREATE TABLE alarm(" +
				"id integer primary key autoincrement, " +
				"hour integer, " +
				"minute integer, " +
				"day_flag integer, " +
				"difficulty integer, " +
				"repeat integer, " +
				"alarm_type integer" +
			");";
		public DBOpener(Context context) {
			super(context, "wakeup", null, 1);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(create_query);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS alarm");
			onCreate(db);
		}
	}
	
	public static void write(Context context, int dayOfHour, int minute, int repeat, int[] days, int alarm_type, int difficulty) {
		SQLiteDatabase db = new DBOpener(context).getWritableDatabase();
		int day_flag = 0;
		for(int day: days) {
			day_flag += Math.pow(10, day);
		}
		
		ContentValues values = new ContentValues();
		
		values.put("hour", dayOfHour);
		values.put("minute", minute);
		values.put("repeat", repeat);
		values.put("day_flag", day_flag);
		values.put("alarm_type", alarm_type);
		values.put("difficulty", difficulty);
		
		int result = (int)db.insert("alarm", null, values);
		db.close();
		
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("id", result);
		intent.putExtra("hour", dayOfHour);
		intent.putExtra("minute", minute);
		intent.putExtra("repeat", repeat);
		intent.putExtra("days", days);
		intent.putExtra("alarm_type", alarm_type);
		intent.putExtra("difficulty", difficulty);

		ResetAlarm.setAlarm(context, intent);
	}
	
	public static void remove(Context context, int row) {
		SQLiteDatabase db = new DBOpener(context).getWritableDatabase();
		Intent intent = new Intent(context, AlarmReceiver.class);
		ResetAlarm.removeAlarm(context, intent, row);
		
		db.delete("alarm", "id="+row, null);
		db.close();
	}
	
	public static Cursor select(Context context) {
		SQLiteDatabase db = new DBOpener(context).getWritableDatabase();
		Cursor result = db.query("alarm", new String[]{"id","hour","minute","repeat","day_flag","alarm_type","difficulty"}, null, null, null, null, null);
		
		return result;
	}
}
