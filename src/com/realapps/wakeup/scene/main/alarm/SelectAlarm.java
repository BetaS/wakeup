package com.realapps.wakeup.scene.main.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.realapps.wakeup.R;

public class SelectAlarm extends Activity implements OnClickListener {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_select);
        
        getInfoOfViews();
    }

	@Override
	public void onClick(View view) {
		Intent result = new Intent();
		if(view == btn_default) {
			result.putExtra(SetupAlarm.INTENT_ALARM, SetupAlarm.ALARM_DEFAULT);
		} else if(view == btn_dumbbell) {
			result.putExtra(SetupAlarm.INTENT_ALARM, SetupAlarm.ALARM_DUMBBELL);
		} else if(view == btn_gogi) {
			result.putExtra(SetupAlarm.INTENT_ALARM, SetupAlarm.ALARM_GOGI);
		} else if(view == btn_math) {
			result.putExtra(SetupAlarm.INTENT_ALARM, SetupAlarm.ALARM_MATH);
		} else if(view == btn_kiss) {
			result.putExtra(SetupAlarm.INTENT_ALARM, SetupAlarm.ALARM_KISS);
		}
		
		// End Activity
		setResult(RESULT_OK, result);
		finish();
	}

	/*
	 * 2011/07/30 - BetaS
	 * View °ü·Ã °´Ã¼µé
	 */
	private ImageButton btn_default	= null;
	private ImageButton btn_dumbbell = null;
	private ImageButton btn_gogi 		= null;
	private ImageButton btn_math 		= null;
	private ImageButton btn_kiss 		= null;
	
    private void getInfoOfViews() {
    	// Get View Objects
    	btn_default		= (ImageButton)findViewById(R.id.btn_default);
        btn_dumbbell 	= (ImageButton)findViewById(R.id.btn_dumbbell);
        btn_gogi 			= (ImageButton)findViewById(R.id.btn_gogi);
        btn_math 		= (ImageButton)findViewById(R.id.btn_math);
        btn_kiss 			= (ImageButton)findViewById(R.id.btn_kiss);
        
        // Attach Click Listeners
        btn_default		.setOnClickListener(this);
        btn_dumbbell	.setOnClickListener(this);
        btn_gogi			.setOnClickListener(this);
        btn_math			.setOnClickListener(this);
        btn_kiss			.setOnClickListener(this);
    }
}