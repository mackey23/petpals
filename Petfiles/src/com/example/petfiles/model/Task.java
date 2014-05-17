package com.example.petfiles.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// A Model Class to reflect a Task
public class Task implements Serializable {
	
	// USER DEFINED STUFFS
	
	// Static variables to indicate priority level of the task
	public static final int HIGH_PRIORITY = 0;
	public static final int MEDIUM_PRIORITY = 1;
	public static final int LOW_PRIORITY = 2;
	
	// Static variables to indicate completion status
	public static final int TASK_NOT_COMPLETED = 0;
	public static final int TASK_COMPLETED = 1;
	
	// A static variable to use when put it into bundle
	public static final String TASK_BUNDLE_KEY = "task_bundle_key";  
	
	// The ID of this task, which is a randomly generated combination of numbers and letters
	private String id;
	
	// The title of this task
	private String title;
	
	// The due date of this task
	private Calendar dueDate;
	
	// The task note
	private String note;
	
	// The priority level of this task
	// Retrieve from those static variables in this class
	// HIGH_PRIORITY, MEDIUM_PRIORITY, LOW_PRIORITY
	private int priorityLevel;
	
	// The pet this task belongs to
	private Pet pet;
	
	// The completion status of this task
	// Retrieve from those static variables in this class
	private int status;
	
	
	// COMPUTER GENERATED STUFFS
	
	public Task(){
		this.id = "";
		this.title = "";
		this.dueDate = Calendar.getInstance();
		this.note = "";
		this.priorityLevel = LOW_PRIORITY;
		this.pet = new Pet();
		this.status = TASK_NOT_COMPLETED;
	}
	
	public Task(String id, String title, Calendar dueDate, String note,
			int priorityLevel, Pet pet, int status) {
		super();
		this.id = id;
		this.title = title;
		this.dueDate = dueDate;
		this.note = note;
		this.priorityLevel = priorityLevel;
		this.pet = pet;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Calendar getDueDate() {
		return dueDate;
	}

	public void setDueDate(Calendar dueDate) {
		this.dueDate = dueDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
