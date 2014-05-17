package com.example.petfiles.controller;

import com.example.petfiles.DatabaseHandler;
import com.example.petfiles.model.Task;
import com.example.petfiles.ModifyTaskActivity;
import com.example.petfiles.ToDoFragment;
import com.example.petfiles.ViewTaskDetailActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;

// A class consists of static functions that help the application navigation between activity
// The idea of the class is that many events can call to the same activity,
// so that every time they need to start one particular activity, just call the static function here
// and it will do the rest of the work
// This is for reusable purpose
public class ApplicationNavigationHandler {
	
	// Go to ViewTaskDetailActivity
	public static void viewTaskDetail(Activity sourceActivity, Task task){
		// create new intent
		Intent viewTaskDetailIntent = new Intent(sourceActivity, ViewTaskDetailActivity.class);
		// put the Task object into the bundle
		Bundle viewTaskDetailBundle = new Bundle();
		viewTaskDetailBundle.putSerializable(Task.TASK_BUNDLE_KEY, task);
		// put the bundle into the intent
		viewTaskDetailIntent.putExtras(viewTaskDetailBundle);
		// start the activity
		sourceActivity.startActivity(viewTaskDetailIntent);
	}
	
//	// Go to ViewAllTasksActivity
//	public static void showAllTasks(Activity sourceActivity){
//		Intent showAllTasksIntent = new Intent(sourceActivity, ViewAllTasksActivity.class);
//		sourceActivity.startActivity(showAllTasksIntent);
//	}
	
	// Go to ModifyTaskActivity to edit and existing Task
	public static void editExistingTask(Activity sourceActivity, Task existingTask){
		Intent editExistingTaskIntent = new Intent(sourceActivity, ModifyTaskActivity.class);
		// put the task to edit into the bundle
		Bundle editExistingTaskBundle = new Bundle();
		// editExistingTaskBundle.putSerializable(Task.TASK_BUNDLE_KEY, existingTask);
		editExistingTaskBundle.putSerializable(Task.TASK_BUNDLE_KEY, existingTask);
		// put the bundle into intent
		editExistingTaskIntent.putExtras(editExistingTaskBundle);
		// start the activity
		sourceActivity.startActivityForResult(editExistingTaskIntent, ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE);
	}
	
	// Go to ModifyTaskActivity to add new task
	public static void addNewTask(Activity sourceActivity) {
		// Start the activity for user to add task
		Intent addNewTaskIntent = new Intent(sourceActivity, ModifyTaskActivity.class);
		sourceActivity.startActivityForResult(addNewTaskIntent, ToDoFragment.ADD_NEW_TASK_REQUEST_CODE);
	}
}
