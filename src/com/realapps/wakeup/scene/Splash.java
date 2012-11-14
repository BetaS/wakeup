package com.realapps.wakeup.scene;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.realapps.wakeup.R;
import com.realapps.wakeup.scene.main.Main;

public class Splash extends Activity implements Runnable {
	private Handler handler = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		handler = new Handler();
		handler.postDelayed(this, 3000); // Auto Transition Scene
	}

	@Override
	public void run() {
		finish();
		
		Intent intent = new Intent(this, Main.class);
		startActivity(intent);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP) {
			handler.removeCallbacks(this);
			
			this.run();
		}
		return false;
	}
}