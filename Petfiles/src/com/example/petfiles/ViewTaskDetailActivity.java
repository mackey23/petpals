package com.example.petfiles;

import java.util.Calendar;
import java.util.Locale;

import com.example.petfiles.R;
import com.example.petfiles.R.layout;
import com.example.petfiles.R.menu;
import com.example.petfiles.controller.ApplicationNavigationHandler;
import com.example.petfiles.controller.ConfirmDeletionDialog;
import com.example.petfiles.DatabaseHandler;
import com.example.petfiles.model.Task;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewTaskDetailActivity extends Activity {

	// The task object that this activity is going to display
	// It's retrieved from bundle in onCreate()
	private Task task;

	// Request code when user select edit
	public static final int EDIT_TASK_REQUEST_CODE = 1;

	// Database Adapter for interacting with database
	private DatabaseHandler databaseHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_task_detail);

		// Open the connection to database
		databaseHandler = new DatabaseHandler(this);
		databaseHandler.open();

		// Retrieve the task object from the bundle
		Bundle taskDetailBundle = this.getIntent().getExtras();
		try{
			this.task = (Task) taskDetailBundle.getSerializable(Task.TASK_BUNDLE_KEY);
		} catch (Exception ex){
			ex.printStackTrace();
		}

		// Next, check if the task object is null or not
		if(this.task == null){
			// If null, close this activity
			this.finish();
		} else {
			// If not null, get all data from the task object and then put it to the view
			this.putDataToView();
		}

	}

	// Extract data members in this.task object and then put the information into view
	private void putDataToView(){
		// First, check if the task object is null or not
		if(this.task == null){
			// If null, close this activity
			this.finish();
		} else {
			// If not null, get all data from the task object and then put it to the view
			// set the title
			TextView taskTitleTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_task_title_content);
			taskTitleTextView.setText(this.task.getTitle());
			// set the due date
			TextView taskDueDateTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_due_date_content);
			Calendar dueDate = this.task.getDueDate();
			String dueDateString = dueDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US) + " "
					+ dueDate.get(Calendar.DATE) + " "
					+ dueDate.get(Calendar.YEAR);
			taskDueDateTextView.setText(dueDateString);
			// set the note
			TextView taskNoteTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_note_content);
			taskNoteTextView.setText(this.task.getNote());
			// set the priority level
			TextView priorityTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_prority_level_content);
			String priorityString;
			switch (this.task.getPriorityLevel()){
			case Task.HIGH_PRIORITY:
				priorityString = this.getString(R.string.activity_modify_task_Spinner_priority_level_Item_high_String_title);
				break;
			case Task.MEDIUM_PRIORITY:
				priorityString = this.getString(R.string.activity_modify_task_Spinner_priority_level_Item_medium_String_title);
				break;
			default:
				priorityString = this.getString(R.string.activity_modify_task_Spinner_priority_level_Item_low_String_title);
				break;
			}
			priorityTextView.setText(priorityString);
			// set the completion status
			TextView completionTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_status_content);
			String completionString;
			if(this.task.getStatus() == Task.TASK_COMPLETED){
				completionString = getString(R.string.activity_modify_task_Spinner_completion_status_Item_yes_String_title);
			} else {
				completionString = getString(R.string.activity_modify_task_Spinner_completion_status_Item_no_String_title);
			}
			completionTextView.setText(completionString);
			// set the pet
			TextView petTextView = (TextView) findViewById(R.id.activity_view_task_detail_TextView_pet_content);
			petTextView.setText(this.task.getPet().getName());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_task_detail, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == EDIT_TASK_REQUEST_CODE){
			this.task = (Task) data.getExtras().getSerializable(Task.TASK_BUNDLE_KEY);
			this.putDataToView();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.activity_view_task_detail_Menu_actionbar_Item_edit:
			ApplicationNavigationHandler.editExistingTask(this, this.task);
			return true;
		case R.id.activity_view_task_detail_Menu_actionbar_Item_delete:
			ConfirmDeletionDialog.showConfirmDeleteDialogForTask(this, this.task, this.databaseHandler);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
