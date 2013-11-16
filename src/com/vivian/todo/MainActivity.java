package com.vivian.todo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	ListView mListView;
	Button mAddTask;
	Database mTasksDB;
	// ArrayAdapter<String> mAdapter;
	CustomAdapter mAdapter;
	Context mContext;
	// ArrayList<String> mTasksList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mContext = getApplicationContext();
        mListView = (ListView)findViewById(R.id.listview);
        mAddTask = (Button)findViewById(R.id.create_task_btn);
        mTasksDB = Database.getInstance(mContext);
        // mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, mTasksList);
        mAdapter = new CustomAdapter(this, mTasksDB.getCursor(), true);
        mListView.setAdapter(mAdapter);
    }
    
    public void createTask(View v) {
    	Intent intent = new Intent(mContext, EditTaskActivity.class);
    	startActivityForResult(intent, 0);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, 
    		Intent taskIntent) {
    	if (resultCode == RESULT_OK) {
    		String task_name = taskIntent.getStringExtra(EditTaskActivity.EXTRA_TASK);
    		mTasksDB.addTask(task_name);
        	updateTasks();
    	}
    }
    
    private void updateTasks() {
    	// mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, mTasksList);
        mAdapter = new CustomAdapter(this, mTasksDB.getCursor(), true);
    	mListView.setAdapter(mAdapter);
    }
    
    public void deleteTask(View v) {
    	ViewGroup parent = (ViewGroup) v.getParent();
    	mTasksDB.deleteTask((Integer)parent.getTag());
    	updateTasks();
    }
    
    private class CustomAdapter extends CursorAdapter {

		public CustomAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			int id = cursor.getInt(cursor.getColumnIndex(Database.COLUMN_ID));
			String task = cursor.getString(cursor.getColumnIndex(Database.COLUMN_TASK));
			
			TextView taskView = (TextView) view.findViewById(R.id.task_item);
			ImageView deleteButton = (ImageView) view.findViewById(R.id.delete_button);
			deleteButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteTask(v);
				}
			});
			
			taskView.setText(task);
			view.setTag(id);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup vg) {
			View v = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			
			return v;
		}
    }
    
}
