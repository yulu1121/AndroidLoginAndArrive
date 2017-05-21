package com.example.callbacklistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CalBackListView listView;
    private List<Integer> mlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        listView = (CalBackListView) findViewById(R.id.my_list);
        for (int i = 0; i < 100; i++) {
            mlist.add(i);
        }
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return null==mlist?0:mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            textView.setGravity(Gravity.CENTER);
            textView.setText(""+mlist.get(position));
            return textView;
        }
    }
}
