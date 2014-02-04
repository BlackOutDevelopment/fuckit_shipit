package com.example.blackoutapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThingsYouDid extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_things_you_did);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				
				new ArrayAdapter<String>(getActionBarThemedContextCompat(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, parseDays(this.getFilesDir())), this);
	}
	
	private String[] parseDays(File parentDir) {
		Set<String> infiles = new HashSet<String>();
		File[] files = parentDir.listFiles();
		for (File file : files) {
			String name = file.getName();
			if(name.substring(name.length() - 4).equals(".txt"))
			{
				infiles.add(name.substring(0,name.length() - 4));
			}
		}
		String[] out = new String[infiles.size()];
		ArrayList<String> a = new ArrayList<String>();
		for(String s : infiles)
		{
			a.add(s);
		}
		for(int i = 0; i < infiles.size(); i++) 
		{
			out[i] = a.get(i);
		}
		return out;
	}

	/**
	 * Backward-compatible version of {@link ActionBar#getThemedContext()} that
	 * simply returns the {@link android.app.Activity} if
	 * <code>getThemedContext</code> is unavailable.
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Context getActionBarThemedContextCompat() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return getActionBar().getThemedContext();
		} else {
			return this;
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.things_you_did, menu);
		return true;
	}

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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public File[] getFilesByDay(File parentDir, String day) {
		ArrayList<File> l1 = new ArrayList<File>();
		File[] files = parentDir.listFiles();
		for (File file : files) {
			String name = file.getName();
			if(name.substring(0, name.length() - 4).equals(day))
			{
				l1.add(file);
			}
		}
		return (File[])l1.toArray();
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		File[] files = getFilesByDay(this.getFilesDir(), parseDays(this.getFilesDir())[position]);
		String[] strings = new String[files.length];
		String output = "";
		for(int i = 0; i < files.length; i++) {
			try {
				strings[i] = getStringFromFile(files[i]);
				output = output + files[i].getName() + ":\n" + strings[i] +"\n\n";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		args.putString(DummySectionFragment.FISI, output);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    reader.close();
	    return sb.toString();
	}

	public static String getStringFromFile (File fl) throws Exception {
	    FileInputStream fin = new FileInputStream(fl);
	    String ret = convertStreamToString(fin);
	    //Make sure you close all streams.
	    fin.close();        
	    return ret;
	}
	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String FISI = "FUCKITSHIPIt";
		public static final String ARG_SECTION_NUMBER = "section_number";
		public DummySectionFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_things_you_did_dummy, container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(getArguments().getString(FISI));
			return rootView;
		}
	}

}
