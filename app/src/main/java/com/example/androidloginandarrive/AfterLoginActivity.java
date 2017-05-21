package com.example.androidloginandarrive;

import com.example.androidloginandarrive.adapter.MyAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class AfterLoginActivity extends Activity {
	private ListView listView;
	private MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);
		initView();
	}
	private void initView() {
		listView = (ListView) findViewById(R.id.listView_user);
		adapter = new MyAdapter(this);
		listView.setAdapter(adapter);
	}
	
	
}
