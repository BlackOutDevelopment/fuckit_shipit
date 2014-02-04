package com.example.blackoutapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

public class ActivityTest3 extends Activity {
	static final int SECONDS1 = 5 * 1; // Number of seconds.
	static final int SECONDS2 = 5 * 1; // Number of seconds.
	int i, out;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_test3);
		SetAlarm1(SECONDS1);
		// Show the Up button in the action bar.
		setupActionBar();
//		i = 0;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test3, menu);
		return true;
	}

	public void SetAlarm2(int out) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		BroadcastReceiver r = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent ix)
			{
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(1000);
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("How you doing?");
				final int out = ix.getIntExtra("out", -1);
				assert (out >= 0);
				alert.setMessage("Do you remember the number? ");
				final EditText input = new EditText(context);
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				alert.setView(input);
				alert.setCancelable(false)
					.setPositiveButton("Ok!", 
							new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							int x = 0;
							try {
								x = Integer.parseInt(input.getText().toString());
							} catch(NumberFormatException e) {
								System.out.println("X: " + x);
								System.out.println("tostring: " + input.getText().toString());
							}
							System.out.println("input: " + input);
							if(x != out)
							{
								blacking_out();
							}
							else{
								SetAlarm1(SECONDS1);
							}
							}
					});

				
				alert.show();
				context.unregisterReceiver(this);
				
			}
		};
		this.registerReceiver(r, new IntentFilter("com.com.com.com.com"));
		Intent ix = new Intent("com.com.com.com.com");
		ix.putExtra("out", out);
		
		PendingIntent pintent = PendingIntent.getBroadcast(this, 0, ix, 0);
		AlarmManager manager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
		
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000 * SECONDS2, pintent);
	}
	
	public void blacking_out() {
		Intent i = new Intent(this, Blacked_out.class);
		startActivity(i);
	}
	
	public void SetAlarm1(int minutes)
	{
		out = (int) ((Math.random() * 99) + 1);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		BroadcastReceiver r = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent ix)
			{
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(1000);
				AlertDialog.Builder alert = new AlertDialog.Builder(context);
				alert.setTitle("How you doing?");
				final int out = ix.getIntExtra("out", -1);
				assert (out >= 0);
				alert.setMessage("Can you remember the number " + out + "?")
					.setCancelable(false)
					.setPositiveButton("Ok!", 
							new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							SetAlarm2(out);
							}
					});

				
				alert.show();
				context.unregisterReceiver(this);
				
			}
		};
		
		
		
		this.registerReceiver(r, new IntentFilter("com.com.com.com"));
		Intent ix = new Intent("com.com.com.com");
		ix.putExtra("out", out);
		
		PendingIntent pintent = PendingIntent.getBroadcast(this, 0, ix, 0);
		AlarmManager manager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
		
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000 * minutes, pintent);
		
	}
//	
//	public void check_blackout(View view) {
//    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
//		alert.setTitle("How you doing?");
//		Random r = new Random();
//		int out = r.nextInt(99) + 1;
//		alert.setMessage("Can you remember the number " + out + "?")
//			.setCancelable(false)
//			.setPositiveButton("Ok!", 
//					new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					SetAlarm1(MINUTES);
//					}
//			});
//
//		
//		alert.show();
//    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
//			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
