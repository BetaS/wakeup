package com.realapps.wakeup.scene.main.alarm;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.realapps.wakeup.R;
import com.realapps.wakeup.core.db.AlarmDB;

public class AlarmList extends ListActivity {
	public static class Alarm {
		public int id = 0;
		
		public int hour = 0;
		public int minute = 0;
		
		public int alarm_style = 0;
		public int alarm_repeat = 0;
		
		public int alarm_difficulty = 0;
		public int alarm_day_flag = 0;
	}
	
	public static class AlarmAdapter extends ArrayAdapter<Alarm> {
		private class ViewHolder {
			public ImageView icon;
			
			public TextView time;
			public TextView repeat;
			public TextView difficulty;
			public TextView[] days = new TextView[7];
		}
		private Context context;

		public AlarmAdapter(Context ctx, int itemLayout, ArrayList<Alarm> list){
			super(ctx, itemLayout, list);
			
			this.context = ctx;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final Alarm result = getItem(position);

			ViewHolder viewHolder = null;
			if(convertView == null) {
				LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        convertView = vi.inflate(R.layout.alarm_list_item, parent, false);
		   
		        viewHolder = new ViewHolder();
		        viewHolder.icon = (ImageView)convertView.findViewById(R.id.icon);
				
		        viewHolder.time = (TextView)convertView.findViewById(R.id.time);
		        viewHolder.repeat = (TextView)convertView.findViewById(R.id.repeat);
		        viewHolder.difficulty = (TextView)convertView.findViewById(R.id.difficulty);
		        
		        viewHolder.days[0] = (TextView)convertView.findViewById(R.id.txt_sunday);
		        viewHolder.days[1] = (TextView)convertView.findViewById(R.id.txt_monday);
		        viewHolder.days[2] = (TextView)convertView.findViewById(R.id.txt_tuesday);
		        viewHolder.days[3] = (TextView)convertView.findViewById(R.id.txt_wednesday);
		        viewHolder.days[4] = (TextView)convertView.findViewById(R.id.txt_thursday);
		        viewHolder.days[5] = (TextView)convertView.findViewById(R.id.txt_friday);
		        viewHolder.days[6] = (TextView)convertView.findViewById(R.id.txt_saturday);
				
		        convertView.setTag(viewHolder);
		    } else {
		        viewHolder = (ViewHolder)convertView.getTag();
		    }
		  
			if(result.alarm_style == SetupAlarm.ALARM_DEFAULT) {
				viewHolder.icon.setBackgroundResource(R.drawable.default_icon_12);
			} else if(result.alarm_style == SetupAlarm.ALARM_DUMBBELL) {
				viewHolder.icon.setBackgroundResource(R.drawable.dumbbell_icon_12);
			} else if(result.alarm_style == SetupAlarm.ALARM_GOGI) {
				viewHolder.icon.setBackgroundResource(R.drawable.gogi_icon_12);
			} else if(result.alarm_style == SetupAlarm.ALARM_KISS) {
				viewHolder.icon.setBackgroundResource(R.drawable.morning_kiss_icon_12);
			} else if(result.alarm_style == SetupAlarm.ALARM_MATH) {
				viewHolder.icon.setBackgroundResource(R.drawable.math_icon_12);
			}
			
			viewHolder.time.setText(String.format("%02d : %02d", result.hour, result.minute));
			
			if(result.alarm_repeat == SetAlarmDay.REPEAT_ONCE) {
				viewHolder.repeat.setText("반복: 한번");
			} else if(result.alarm_repeat == SetAlarmDay.REPEAT_DAY) {
				viewHolder.repeat.setText("반복: 매일");
			} else if(result.alarm_repeat == SetAlarmDay.REPEAT_WEEK) {
				viewHolder.repeat.setText("반복: 매주");
			}
			
			if(result.alarm_difficulty == SetupAlarm.DIFFICULT_EASY) {
				viewHolder.difficulty.setText("난이도: 쉬움");
			} else if(result.alarm_difficulty == SetupAlarm.DIFFICULT_NORMAL) {
				viewHolder.difficulty.setText("난이도: 보통");
			} else if(result.alarm_difficulty == SetupAlarm.DIFFICULT_HARD) {
				viewHolder.difficulty.setText("난이도: 어려움");
			}
			
			for(int i=0; i<7; i++) {
				if((result.alarm_day_flag/(int)Math.pow(10, i)) % 10 == 1) {
					viewHolder.days[i].setTextColor(0xFF0066AA);
				} else {
					viewHolder.days[i].setTextColor(0xFF666666);
				}
			}
			
			return convertView;
		}
	}
	
	private ArrayList<Alarm> alarms = new ArrayList<Alarm>();
	private AlarmAdapter adapter = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list);
        
        Cursor result = AlarmDB.select(this);
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
        
        adapter = new AlarmAdapter(this, R.layout.alarm_list_item, alarms);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				final Alarm alarm = AlarmList.this.adapter.getItem(position);
				AlertDialog.Builder ab = new AlertDialog.Builder(AlarmList.this);
				ab.setMessage("정말 삭제하시겠습니까?");
				ab.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						AlarmDB.remove(AlarmList.this, alarm.id);
						AlarmList.this.adapter.remove(alarm);
					}
				});
				ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				ab.create().show();
			}
		});
    }
}