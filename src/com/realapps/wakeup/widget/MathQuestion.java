package com.realapps.wakeup.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.realapps.wakeup.R;

public class MathQuestion extends LinearLayout {
	private View inflateView = null;
	
	private TextView txt_question = null;
	private EditText txt_answer = null;
	private double answer = 0.0;
	
	public MathQuestion(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflateView = inflater.inflate(R.layout.math_question, this);
		
		txt_question = (TextView)inflateView.findViewById(R.id.txt_question);
		txt_answer = (EditText)inflateView.findViewById(R.id.txt_answer);
	}
	
	public void setQuestion(String question, double answer) {
		this.answer = answer;
		txt_question.setText(question);
		txt_answer.setText("");
	}
	public void clearAnswer() {
		txt_answer.setText("");
	}
	public boolean checkAnswer() {
		try {
			if(Double.parseDouble(txt_answer.getText().toString()) == answer) {
				txt_answer.setEnabled(false);
				return true;
			}
		} catch(NumberFormatException e) {
			
		}
		
		Toast.makeText(getContext(), "Æ²·È½À´Ï´Ù!", Toast.LENGTH_SHORT).show();
		return false;
	}
	public boolean isClear() {
		if(!txt_answer.isEnabled()) return true;
		return false;
	}
}
