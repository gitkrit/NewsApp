package com.example.kriti.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class NewsActivity extends FragmentActivity {

	private String url ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		Intent intent= getIntent();
		Bundle b = intent.getExtras();
		if(b!=null) {
			url = b.getString("url");
			//		url = "http://www.thehindu.com/news/?service=rss";
		}

		if (savedInstanceState == null) {
			addRssFragment();
		}

	}

	private void addRssFragment() {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		RssFragment fragment = new RssFragment();
		transaction.add(R.id.fragment_container, fragment);
		fragment.setURL(url);

		transaction.commit();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("fragment_added", true);
	}
}