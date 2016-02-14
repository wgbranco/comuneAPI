package com.comune.model.android;

import java.sql.Time;

public class WorkingDay 
{
	private int dayOfTheWeek;	
	private long openingTime;
	private long closingTime;
	
	public WorkingDay()
	{}

	public int getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(int dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public long getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Time openingTime) {
		this.openingTime = openingTime.getTime();
	}

	public long getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Time closingTime) {
		this.closingTime = closingTime.getTime();
	}
}
