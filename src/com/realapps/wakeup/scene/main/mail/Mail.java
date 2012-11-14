package com.realapps.wakeup.scene.main.mail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.realapps.wakeup.R;

public class Mail extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail);
        
        getInfoOfViews();
    }

	@Override
	public void onClick(View v) {
		if(v == btn_send_mail) {
			Intent intent = new Intent(this, SendMail.class);
			startActivity(intent);
		} else if(v == btn_view_mail) {
			Intent intent = new Intent(this, ViewMail.class);
			startActivity(intent);
		}
	}
    
    /*
	 * 2011/07/30 - BetaS
	 * View °ü·Ã °´Ã¼µé
	 */
	private Button btn_send_mail 	= null;
	private Button btn_view_mail 	= null;
	
    private void getInfoOfViews() {
    	// Get View Objects
    	btn_send_mail 	= (Button)findViewById(R.id.btn_send_mail);
    	btn_view_mail 	= (Button)findViewById(R.id.btn_view_mail);
        
        // Attach Click Listeners
        btn_send_mail	.setOnClickListener(this);
        btn_view_mail	.setOnClickListener(this);
    }
}