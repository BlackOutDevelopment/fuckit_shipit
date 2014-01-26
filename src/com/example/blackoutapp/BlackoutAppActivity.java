package com.example.blackoutapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.blackoutapp.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
@SuppressLint("InlinedApi")
public class BlackoutAppActivity extends Activity {
	final int INITIAL_HIDE_DELAY = 1;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackout_app);
        final View mDecorView = getWindow().getDecorView();
    	final View controlsView = findViewById(R.id.last_night_controls);
    	final View contentView = findViewById(R.id.blackout_content);
    	mDecorView.setOnSystemUiVisibilityChangeListener(
    			new View.OnSystemUiVisibilityChangeListener() {
					
					@SuppressLint("InlinedApi")
					@Override
					public void onSystemUiVisibilityChange(int flags) {
						boolean visible = (flags & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    					controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
					}
				});
    	contentView.setClickable(true);
    	
    	final GestureDetector clickDetector = new GestureDetector(this, 
    			new GestureDetector.SimpleOnGestureListener() {
    		@SuppressLint("InlinedApi")
			@Override
    		public boolean onSingleTapUp(MotionEvent e) {
    			boolean visible = (mDecorView.getSystemUiVisibility()
    					 & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
    		if (visible) {
    			hideSystemUI();
    		}
    		// Enable this for switch on click.
//    		else {
//    			showSystemUI();
//    		}
    		return true;
    		}
		}); 
    	contentView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return clickDetector.onTouchEvent(event);
			}
		});
    	hideSystemUI();
    }


	@SuppressLint("InlinedApi")
	private void hideSystemUI() {
		final View mDecorView = getWindow().getDecorView();
		mDecorView.setSystemUiVisibility(
				  View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE);
	}
	// Show UI just uses layout flags.
	
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
		delayedHide(INITIAL_HIDE_DELAY);
	}
	
	@SuppressLint("HandlerLeak")
	Handler mHideSystemUiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			hideSystemUI();
		}
	};
	
	private void delayedHide(int delayMillis) {
		mHideSystemUiHandler.removeMessages(0);
		mHideSystemUiHandler.sendEmptyMessageDelayed(0,  delayMillis);
	}
	
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		
		if(hasFocus) {
			delayedHide(INITIAL_HIDE_DELAY);
		}
		else {
			mHideSystemUiHandler.removeMessages(0);
		}
	}
	
    public void get_drunk(View view) {
    	Intent intent = new Intent(this, GetDrunkActivity.class);
    	startActivity(intent);
    }
    
    public void last_night_review(View view) {
    	Intent intent = new Intent(this, ActivityListActivity.class);
    	startActivity(intent);
    }
}
