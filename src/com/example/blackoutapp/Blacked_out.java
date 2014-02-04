package com.example.blackoutapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.blackoutapp.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint("SimpleDateFormat")
public class Blacked_out extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	String filename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blacked_out);
//		try {
//			filename = create_day_file();
//			blackout_time(filename);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});
//		sample_audio(10 * 60);
		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
	}
/**
	public void sample_audio(int rate_seconds) {
		BroadcastReceiver r = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent ix)
			{
				try {
					record_audio();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				context.unregisterReceiver(this);
				
				sample_audio(10 * 60);
			}
		};
		
		
		
		this.registerReceiver(r, new IntentFilter("com.com.com.com"));
		Intent ix = new Intent("com.com.com.com");
		
		PendingIntent pintent = PendingIntent.getBroadcast(this, 0, ix, 0);
		AlarmManager manager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
		
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000 * rate_seconds, pintent);
	}
	
	public void record_audio() throws IllegalStateException, IOException {
		MediaRecorder recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(this.getFilesDir().getPath() + new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime()));
		recorder.setMaxDuration(20 * 1000);
		recorder.prepare();
		recorder.start();
	}
*/	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	






	//Day folder is now created
	// Returns filename for day
	@SuppressLint("SimpleDateFormat")
	public String create_day_file() throws IOException
	{
	    //gets date for folder name
	    String day = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime());
	    //creates day main folder
	    File file = new File(this.getFilesDir(), day + ".txt");
	    boolean success = true;

	    if (!file.exists()) {
	        success = file.createNewFile();
	    }
	    if (success) {
	        FileWriter fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(day);
	        String begin = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	        bw.write("Begin: " + begin);
	        bw.close();
	        // already created file. we all good
	    } else {
	        // File already exists/ fine with that  
	    }
	    return day + ".txt";

	}
	
	public void go_home(View view) {
		Intent i = new Intent(this, BlackoutAppActivity.class);
		startActivity(i);
	}
	// Places blackout time in log
	public void blackout_time (String filename) throws IOException
	{
	        FileWriter fw = new FileWriter(filename, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        String blackout = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	        bw.write("Blackout: " + blackout + "\n\nLogs:");
	        bw.close();

	}
	// DWAI
	public void add_Log(String filename, String logname) throws IOException
	{
	        FileWriter fw = new FileWriter(filename, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(logname);
	        bw.close();
	}

	@SuppressLint("SimpleDateFormat")
	public void audio_file(String filename, String dayfile) throws IOException
	{
	    String fileN = filename.substring(0, filename.length() - 4) + ".txt";
	    File file = new File(this.getFilesDir(), fileN);

	    if (!file.exists()) {
	        file.createNewFile();

	        FileWriter fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write("File: " + filename);
	        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	        bw.write("Time: " + time);
	        String day = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime());
	        bw.write("Day: " + day);
	        //write location
	        bw.close();
	        // already created file. we all good
	        // File already exists/ fine with that  
	    }
	    add_Log(dayfile, fileN);
	    return;

	}

	public void text_file(String body, String dayfile) throws IOException
	{
	    String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	    //creates day main folder
	    File file = new File(this.getFilesDir(), "log_" + time + ".txt");
	    FileWriter fw = new FileWriter(file.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    
	    bw.write("Time: " + time);
	    String day = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime());
	    bw.write("Day: " + day);
	        //write location
	    bw.write("Body: \n" + body);
	    bw.close();    
	    add_Log(dayfile, "log_" + time + ".txt");
	}
	
	public void save_text(View view) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("How you doing?");
		alert.setMessage("Do you remember the number? ");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		alert.setView(input);
		alert.setCancelable(false)
			.setPositiveButton("Ok!", 
					new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						String day = create_day_file();
						text_file(input.getText().toString(), day);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
			});
		alert.show();	
	}
}
