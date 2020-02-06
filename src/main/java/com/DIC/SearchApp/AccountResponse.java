package com.DIC.SearchApp;

import java.util.List;

public class AccountResponse {

	private int totalSize;
	private boolean done;
	private List<Account> records;
	 
	public int getTotalSize() {
	return totalSize;
	}
	public void setTotalSize(int totalSize) {
	this.totalSize = totalSize;
	}
	public boolean isDone() {
	return done;
	}
	public void setDone(boolean done) {
	this.done = done;
	}
	public List<Account> getRecords() {
	return records;
	}
	public void setRecords(List<Account> records) {
	this.records = records;
	}
	 
	}
	