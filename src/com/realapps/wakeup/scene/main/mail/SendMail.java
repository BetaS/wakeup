package com.realapps.wakeup.scene.main.mail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.realapps.wakeup.R;

public class SendMail extends Activity implements OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mail);
        
        getInfoOfViews();
    }

	@Override
	public void onClick(View v) {
		Toast.makeText(this, "이 기능은 준비중입니다.", Toast.LENGTH_SHORT).show();
	}
    
    /*
	 * 2011/07/30 - BetaS
	 * View 관련 객체들
	 */
	private Button btn_send_mail 	= null;
	
    private void getInfoOfViews() {
    	// Get View Objects
    	btn_send_mail 	= (Button)findViewById(R.id.btn_send_mail);
        
        // Attach Click Listeners
        btn_send_mail	.setOnClickListener(this);
    }
}