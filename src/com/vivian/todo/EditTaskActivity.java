package com.vivian.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTaskActivity extends Activity {
	
	private static final String EXTRA_TASK = "com.vivian.todo.extras.EXTRA_TASK";
	private EditText mTaskName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_task);
		setTitle("Write a task!");
		mTaskName = (EditText)findViewById(R.id.task_name);
	}
	
	public void addTask(View v) {
		Intent returnIntent = new Intent();
		returnIntent.putExtra(EXTRA_TASK, mTaskName.getText().toString());
		setResult(RESULT_OK, returnIntent);
		finish();
	}
	
	public void cancelAddTask(View v) {
		finish();
	}
}
