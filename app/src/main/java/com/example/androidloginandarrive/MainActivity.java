package com.example.androidloginandarrive;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidloginandarrive.entry.User;
import com.example.androidloginandarrive.shareutils.SharedPreferenceUtils;
import com.example.androidloginandarrive.sql.MySqlHelper;

public class MainActivity extends Activity {
	private EditText username;
	private EditText userpass;
	private CheckBox chkAuto;
	private Button loginBtn;
	private Button registBtn;
	private MySqlHelper mysql;
	private User users;
	private int number = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();
		checkAutoLogin();

	}

	private void initView() {
		username = (EditText) findViewById(R.id.userName);
		userpass = (EditText) findViewById(R.id.userPass);
		chkAuto = (CheckBox) findViewById(R.id.autoLogin);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		registBtn = (Button) findViewById(R.id.registBtn);
	}

	private void initListener() {
		registBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						RegistActivity.class);
				startActivity(intent);
			}
		});
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				login(username.getText().toString(), userpass.getText()
						.toString());

			}
		});
	}

	private void checkAutoLogin() {
		boolean flag = SharedPreferenceUtils.getBoolean(this, "loginstate");
			if(flag==true){
				String userName = SharedPreferenceUtils.getString(this, "username");
				String userPass = SharedPreferenceUtils.getString(this, "userpass");
				chkAuto.setChecked(true);
				username.setText(userName);
				userpass.setText(userPass);
			}else if(flag==false){
				chkAuto.setChecked(false);
			}
	}


	private void login(String userName, String userPass) {
		if (TextUtils.isEmpty(username.getText().toString())) {
			Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(userpass.getText().toString())) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		mysql = new MySqlHelper(this);
		SQLiteDatabase db = mysql.getReadableDatabase();
		String sql = "select * from " + MySqlHelper.USER_TB
				+ " where user_name=?";
		Cursor cursor = db.rawQuery(sql, new String[] {username.getText()
				.toString() });

			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor
						.getColumnIndex("user_name"));
				String pass = cursor.getString(cursor
						.getColumnIndex("user_pass"));
				users = new User(0, name, pass);
			}
		if (null==users){
			Toast.makeText(this,"账号不存在，请先注册",Toast.LENGTH_SHORT).show();
		}else if(users.getName().equals(userName)
				&& users.getPass().equals(userPass)) {
			if (chkAuto.isChecked()) {
				SharedPreferenceUtils.saveString(this, "username", users.getName());
				SharedPreferenceUtils.saveString(this, "userpass", users.getPass());
				SharedPreferenceUtils.saveBoolean(this, "loginstate", true);
			}else if(!chkAuto.isChecked()){
				SharedPreferenceUtils.deleteString(this,"username");
				SharedPreferenceUtils.deleteString(this,"userpass");
				SharedPreferenceUtils.saveBoolean(this, "loginstate",false);
			}
			Intent intent = new Intent(this, AfterLoginActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
		}
		cursor.close();
		db.close();
	}
	}
