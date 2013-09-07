package com.vivian.todo;

public class Task {

	private int mId;
	private String mName;
	
	public Task(int id, String name) {
		mId = id;
		mName = name;
	}
	
	public String getName() {
		return mName;
	}
	
	public int getId() {
		return mId;
	}
	
}
