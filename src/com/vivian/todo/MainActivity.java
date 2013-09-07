package com.vivian.todo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
	ArrayList<Task> mTasksList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mContext = getApplicationContext();
        mListView = (ListView)findViewById(R.id.listview);
        mAddTask = (Button)findViewById(R.id.create_task_btn);
        mTasksDB = Database.getInstance(mContext);
        mTasksList = mTasksDB.getAllTasks();
        // mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, mTasksList);
        mAdapter = new CustomAdapter(this, mTasksList);
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
    		String task_name = taskIntent.getStringExtra("TASK");
    		mTasksDB.addTask(task_name);
        	updateTasks();
    	}
    }
    
    private void updateTasks() {
    	mTasksList = mTasksDB.getAllTasks();
    	// mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, mTasksList);
        mAdapter = new CustomAdapter(this, mTasksList);
    	mListView.setAdapter(mAdapter);
    }
    
    public void deleteTask(View v) {
    	ViewGroup parent = (ViewGroup) v.getParent();
    	mTasksDB.deleteTask(parent.getId());
    	updateTasks();
    }
    
    private class CustomAdapter extends ArrayAdapter<Task> {
    	
    	Activity mActivity;
    	int mLayoutResId;
    	
    	public CustomAdapter(Activity activity, ArrayList<Task> tasks) {
    		super(activity, R.layout.list_item, tasks);
    		mActivity = activity;
    		mLayoutResId = R.layout.list_item;
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		View v = convertView;
    		if (v == null) {
    			LayoutInflater inflater = mActivity.getLayoutInflater();
    			v = inflater.inflate(mLayoutResId, parent, false);
    			TextView task = (TextView)v.findViewById(R.id.task_item);
    			ImageView deleteButton = (ImageView)v.findViewById(R.id.delete_button);
    			deleteButton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					deleteTask(v);
    				}
    			});
    			// task.setText(mTasksList.get(position));
    			task.setText(mTasksList.get(position).getName());
    			v.setId(mTasksList.get(position).getId());
    		}
    		return v;
    	}
    }
    
}
