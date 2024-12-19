package com.example.PHONGTROSPRING.request;

public class RequestTime {
	private String bientime;
	private int time;
	
	
	public RequestTime(String bientime, int time) {
		this.bientime = bientime;
		this.time = time;
	}
	public String getBientime() {
		return bientime;
	}
	public void setBientime(String bientime) {
		this.bientime = bientime;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
}
