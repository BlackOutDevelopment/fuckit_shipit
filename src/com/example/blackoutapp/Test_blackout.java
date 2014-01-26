package com.example.blackoutapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Test_blackout extends DialogFragment {

	private String stringIn;
	
	public Test_blackout() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.article_view, container, false);
		getDialog().setTitle("Are you blacked out?");
		
		return view;
	}
}
