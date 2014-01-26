package com.example.blackoutapp;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;


public class Test_blackout extends Activity {

	private int out;
	
	
	public Test_blackout() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle("How you doing?");
		alert.setMessage("Can you remember the number " + out + "?");
		alert.setButton("Sure!", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		})
		TextView t = (TextView) findViewById(R.id.blackout_test);
		Random r = new Random();
		out = r.nextInt(99) + 1;
		t.setText("Can you remember the number " + out);
		
		
//		View view = inflater.inflate(R.layout.article_view, container, false);
//		getDialog().setTitle("Just checkin' in");
//		return view;
	}
}
