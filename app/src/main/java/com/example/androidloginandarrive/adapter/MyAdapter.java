package com.example.androidloginandarrive.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.androidloginandarrive.R;
import com.example.androidloginandarrive.entry.User;
import com.example.androidloginandarrive.sql.MySqlHelper;

public class MyAdapter extends BaseAdapter {
	//�ж���ļ���
	private List<User> mlist;
	//��������
	private Context mcontext;
	private Cursor cursor;
	//��LayoutInflater
	private LayoutInflater inflate;
	private MySqlHelper mysql;
	//���й��췽��
	public MyAdapter(Context context){
		this.mcontext = context;
		mlist=getUser();
		mysql = new MySqlHelper(context);
		inflate = LayoutInflater.from(context);
	}
//	private News getNews(){
//		SQLiteDatabase db = mysql.getReadableDatabase();
//		String sql = "select news_name,news_author,news_iconurl from "+NewsSQL.NEWS_TB+" where news_id = ?";
//		News news  =new News();
//		Cursor cursor = db.rawQuery(sql,new String[]{news.getId()});
//		while(cursor.moveToNext()){
//			news.setName(cursor.getString(cursor.getColumnIndex("news_name")));
//			news.setAutorImage(cursor.getString(cursor.getColumnIndex("news_author")));
//			news.setIconurl(cursor.getString(cursor.getColumnIndex("news_iconurl")));
//		}
//		cursor.close();
//		db.close();
//		return news;
		
//	}
	@Override
	public int getCount() {
	
		return null==mlist?0:mlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return mlist.get(position).getId();
	}
	private List<User> getUser(){
		mlist = new ArrayList<User>();
		mysql = new MySqlHelper(mcontext);
		SQLiteDatabase db = mysql.getWritableDatabase();
		cursor = getCursor();
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("user_name"));
			String pass = cursor.getString(cursor.getColumnIndex("user_pass"));
			User user = new User(id, name, pass);
			mlist.add(user);
			Log.e("==",user.toString());
		}
		cursor.close();
		db.close();
		return mlist;
		
	}
	private void delteUser(int id){
		mysql = new MySqlHelper(mcontext);
		SQLiteDatabase db = mysql.getWritableDatabase();
		String sql = "delete from "+MySqlHelper.USER_TB+" where _id=?";
		db.execSQL(sql,new Object[]{id});
	}

	private Cursor getCursor(){
		SQLiteDatabase db = mysql.getWritableDatabase();
		return db.rawQuery("select * from "+MySqlHelper.USER_TB, null);
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder viewHolder=null;
		if(null==view){
			view = inflate.inflate(R.layout.list_item,parent,false);
			viewHolder = new ViewHolder(view);
		}else{
			viewHolder = (ViewHolder)view.getTag();
		}
		final User user = mlist.get(position);
		viewHolder.id_tv.setText("编号:"+user.getId());
		viewHolder.name_tv.setText("姓名:"+user.getName());
		viewHolder.pass_tv.setText("密码:"+user.getPass());
		viewHolder.imagebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mlist.remove(position);
				delteUser(user.getId());
				notifyDataSetChanged();
			}
		});
		return view;
	}
	
	class ViewHolder{
		TextView id_tv;
		TextView name_tv;
		TextView pass_tv;
		ImageButton imagebtn;
		public ViewHolder(View view){
			id_tv = (TextView) view.findViewById(R.id.user_id);
			name_tv = (TextView) view.findViewById(R.id.user_name_item);
			pass_tv = (TextView) view.findViewById(R.id.user_pass_item);
			imagebtn = (ImageButton) view.findViewById(R.id.delte_user_item);
			view.setTag(this);
		}
	}
}
