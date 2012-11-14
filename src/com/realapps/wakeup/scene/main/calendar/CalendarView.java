package com.realapps.wakeup.scene.main.calendar;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.realapps.wakeup.R;

public class CalendarView extends Activity {
	Calendar Cal, Now;
	GridView Grid;
	TextView CalTitle;
	TextView GridText;
	ArrayAdapter<String> GridAdapter;
	ArrayList<String> GridData= new ArrayList<String>();
	int DoW, Max, DoM, WoM, FoM, Year, Month;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		CalendarDataSet();
		CalendarSet();
		DrawCalendar();
	}

	public void CalendarDataSet(){
		Now=Calendar.getInstance();
		Year = Now.get(Calendar.YEAR);
		Month = Now.get(Calendar.MONTH)+1;
		DoM = Now.get(Calendar.DAY_OF_MONTH);
		DoW = Now.get(Calendar.DAY_OF_WEEK);
		Max = Now.getActualMaximum(Calendar.DAY_OF_MONTH);
		WoM = Now.get(Calendar.WEEK_OF_MONTH);
		Now.set(Year, Month-1, Now.getMinimum(Calendar.DAY_OF_MONTH));
		FoM = Now.get(Calendar.DAY_OF_WEEK) + 6;
		//FoM = DoM - (7 * (WoM-1)) - DoW + 7;
	}

	public void CalendarSet(){
		String[] Day = {"일", "월", "화", "수", "목", "금", "토"};
		int i, j;
		int DiffMonthDay;
		Cal=Calendar.getInstance();
		GridData.ensureCapacity(49);
		for(i=0;i<7;i++){
			GridData.add(i,Day[i]);
		}
		Cal.set(Year, Month-2,1);
		DiffMonthDay = Cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		j= FoM%7 -1;

		for(i=7;i<FoM;i++, j--){
			GridData.add(i, Integer.toString(DiffMonthDay - j));
		}
		//GridText.setTextColor(Color.WHITE);
		for(i=1;i<=Max;i++, FoM++){
			GridData.add(FoM,Integer.toString(i));
		}

		Cal.set(Year, Month,1);
		//GridText.setTextColor(Color.DKGRAY);
		for(i=FoM+1;i<=49;i++){
			GridData.add(Integer.toString(i-FoM));
		}
	}
	
	public static class CalendarAdaptor extends ArrayAdapter<String> {
		private class ViewHolder {
			public ImageView check;
			public TextView number;
		}
		private Context context;

		public CalendarAdaptor(Context ctx, int itemLayout, ArrayList<String> list){
			super(ctx, itemLayout, list);
			
			this.context = ctx;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final String result = getItem(position);

			ViewHolder viewHolder = null;
			if(convertView == null) {
				LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        convertView = vi.inflate(R.layout.monthview_gridtable, parent, false);
		   
		        viewHolder = new ViewHolder();
		        viewHolder.check = (ImageView)convertView.findViewById(R.id.img_check);
		        viewHolder.number = (TextView)convertView.findViewById(R.id.txt_number);
				
		        convertView.setTag(viewHolder);
		    } else {
		        viewHolder = (ViewHolder)convertView.getTag();
		    }
			
			if(position < 7) viewHolder.check.setVisibility(View.GONE);
			
			viewHolder.number.setText(result);
			if(position % 7 == 0)
				viewHolder.number.setTextColor(0xFFFF0000);
			else if(position % 7 == 6)
				viewHolder.number.setTextColor(0xFF0066ff);
			
			return convertView;
		}
	}
	
	public void DrawCalendar(){
		GridAdapter = new CalendarAdaptor(this, R.layout.monthview_gridtable, GridData);
		Grid = (GridView)findViewById(R.id.cal_grid);
		Grid.setAdapter(GridAdapter);
		
		TextView month = (TextView)findViewById(R.id.txt_month);
		month.setText((Calendar.getInstance().get(Calendar.MONTH)+1)+"월");
	}
}