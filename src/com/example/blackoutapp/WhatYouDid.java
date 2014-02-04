package com.example.blackoutapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class WhatYouDid extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_what_you_did);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.what_you_did, menu);
		return true;
	}

}
