package com.realapps.wakeup.scene.main.mail;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.realapps.wakeup.R;

public class ViewMail extends Activity {
	private static final String[] txt_list = new String[]{
		"�氡�� 100���̳� �ֿ���",
		"�� ���� ��¥ ���°ǰ�?",
		"asdfwefgsafeafeafaf",
		"�� �������� ��Դµ� ������ ���ϴ�",
		"������ ����� ������",
		"�� �������!"
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mail);
        
        getInfoOfViews();
    }
    
    /*
	 * 2011/07/30 - BetaS
	 * View ���� ��ü��
	 */
	private TextView txt_content 	= null;
	
    private void getInfoOfViews() {
    	// Get View Objects
    	txt_content 	= (TextView)findViewById(R.id.txt_content);
    	txt_content.setText(txt_list[new Random().nextInt(5)]);
    }
}