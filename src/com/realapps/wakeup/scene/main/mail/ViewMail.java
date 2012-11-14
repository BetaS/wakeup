package com.realapps.wakeup.scene.main.mail;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.realapps.wakeup.R;

public class ViewMail extends Activity {
	private static final String[] txt_list = new String[]{
		"길가다 100원이나 주우라긔",
		"이 편지 진짜 가는건가?",
		"asdfwefgsafeafeafaf",
		"왜 설렁탕을 사왔는데 먹지를 못하니",
		"빱빱빱 굳모닝 빱빱빱",
		"야 기분좋다!"
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mail);
        
        getInfoOfViews();
    }
    
    /*
	 * 2011/07/30 - BetaS
	 * View 관련 객체들
	 */
	private TextView txt_content 	= null;
	
    private void getInfoOfViews() {
    	// Get View Objects
    	txt_content 	= (TextView)findViewById(R.id.txt_content);
    	txt_content.setText(txt_list[new Random().nextInt(5)]);
    }
}