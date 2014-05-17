package com.example.petfiles;

import java.util.Calendar;
import java.util.List;

import com.example.petfiles.R;
import com.example.petfiles.controller.ConfirmCancelDialogHandler;
import com.example.petfiles.ViewTaskDetailActivity;
import com.example.petfiles.DatabaseHandler;
import com.example.petfiles.model.Pet;
import com.example.petfiles.model.Task;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

//This activity is used for 2 purposes: Creating new Task and Editing existing Task
//Every time it's loaded, the program should check whether the Task object in the bundle is exist
//If it's exist, this means the activity is going to edit that task
//Otherwise, the activity is going to create a new task
public class ModifyTaskActivity extends Activity{

	// VARIABLES DEFINED HERE

	// a Task object to hold the data about the current task being processed
	// If the activity is creating a new task, this object will be initialized
	// Otherwise, it will be retrieved from the bundle
	private Task task = null;

	// store the current job of this activity, Edit or Add task
	// value will be set from those 2 static variables below
	private int currentJob;
	private final int CURRENT_JOB_EDIT = 1;
	private final int CURRENT_JOB_ADD = 2;

	// DatabaseHandler for interacting with database
	private DatabaseHandler databaseHandler;

	// The cursor for query all pets from database
	private Cursor allPetsCursor;

	// Adapter for All Pets List View
	private SimpleCursorAdapter allPetsSpinnerAdapter;

	// Pet spinner
	private Spinner allPetsSpinner;


	// OVERIDE FUNCTIONS

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent resultIntent;
		Bundle resultBundle;

		switch (item.getItemId()){

		// When user select Cancel or Home button, show a confirmation dialog
		case R.id.activity_modify_task_Menu_actionbar_Item_cancel:
		case android.R.id.home:
			// set the result for the previous activity
			resultIntent = new Intent();
			resultBundle = new Bundle();
			resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
			resultIntent.putExtras(resultBundle);
			setResult(ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE, resultIntent);

			// Show the confirmation dialog
			// The rest (leave activity or not), the function ApplicationDialogHandler.showConfirmCancelDialog() will handle
			ConfirmCancelDialogHandler.showConfirmCancelDialog(this);
			return true;

			// When user select Save
		case R.id.activity_modify_task_Menu_actionbar_Item_save:
			// validate form

			// check whether the current job is add new or edit
			if(this.currentJob == CURRENT_JOB_ADD){
				// add new task to database
				addNewTask();
			} else {
				// edit the current task
				editExistingTask();

				// set the result for the previous activity
				resultIntent = new Intent();
				resultBundle = new Bundle();
				resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
				resultIntent.putExtras(resultBundle);
				setResult(ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE, resultIntent);

			}

			// close this activity
			finish();
			return true;

			// default case, return the base class function
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// set the result for the previous activity
		Intent resultIntent = new Intent();
		Bundle resultBundle = new Bundle();
		resultBundle.putSerializable(Task.TASK_BUNDLE_KEY, this.task);
		resultIntent.putExtras(resultBundle);
		setResult(ViewTaskDetailActivity.EDIT_TASK_REQUEST_CODE, resultIntent);

		// Show the confirmation dialog
		// The rest (leave activity or not), the function ApplicationDialogHandler.showConfirmCancelDialog() will handle
		ConfirmCancelDialogHandler.showConfirmCancelDialog(this);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_task);

		// Retrieve the pet spinner
		allPetsSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_pet);

		// Open the connection to database
		databaseHandler = new DatabaseHandler(this);
		databaseHandler.open();

		// init the pet
		this.initPet();

		// Check whether this activity is going to create a new Task or edit an existing one
		// First, retrieve the Task object from the bundle
		Bundle modifyTaskBundle = this.getIntent().getExtras();
		try {
			this.task = (Task) modifyTaskBundle.getSerializable(Task.TASK_BUNDLE_KEY);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		// Next, check if it is null
		if (task != null){
			// Change the currentJob to edit task
			this.currentJob = this.CURRENT_JOB_EDIT;
			// If the Task object exist, load data from it and put to the form
			putDataToForm();
			// After that, change the title of the activity to "Edit task " + task name
		} else {
			// Init the new Task object
			this.task = new Task();
			// Change the currentJob to add new task
			this.currentJob = this.CURRENT_JOB_ADD;
			// If the Task object not exist, change the title of the activity to "Create new Task"
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_task, menu);
		return true;
	}


	// UTILITY FUNCTIONS

	// This function is used to edit the current task
	private void editExistingTask(){
		// Load data from form to this.task object
		updateTaskObject();
		// Call the database adapter to update the task
		databaseHandler.editExistingTask(this.task);
	}

	// This function is used to retrieve data from form and update the this.task object
	private void updateTaskObject(){
		// Retrieve data from form and put into this.task object
		// No need to set collaborators since the list view handles it automatically
		// set task title
		String taskTitle = ((EditText)findViewById(R.id.activity_modify_task_Edittext_task_title)).getText().toString();
		this.task.setTitle(taskTitle);
		// set due date
		DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.activity_modify_task_Datepicker_due_date);
		this.task.getDueDate().set(Calendar.DATE, taskDueDatePicker.getDayOfMonth());
		this.task.getDueDate().set(Calendar.MONTH, taskDueDatePicker.getMonth());
		this.task.getDueDate().set(Calendar.YEAR, taskDueDatePicker.getYear());
		// set note
		String taskNote = ((EditText)findViewById(R.id.activity_modify_task_EditText_note)).getText().toString();
		this.task.setNote(taskNote);
		// set priority level
		int priorityLevel = ((Spinner)findViewById(R.id.activity_modify_task_Spinner_priority_level)).getSelectedItemPosition();
		this.task.setPriorityLevel(priorityLevel);
		// set completion status
		int completionStatus = ((Spinner)findViewById(R.id.activity_modify_task_Spinner_completion_status)).getSelectedItemPosition();
		this.task.setStatus(completionStatus);
		// set pet
		Spinner petSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_pet);
		this.task.getPet().setID(Integer.parseInt(getPetIdByPosition(allPetsCursor, petSpinner.getSelectedItemPosition())));
	}

	// This function is used to add new task to database
	private void addNewTask(){
		// Load data from form to this.task object
		updateTaskObject();
		// Since we add new task, need to get a new task id
		// set task id
		String taskId = databaseHandler.getNewTaskId();
		this.task.setId(taskId);

		// Call the database adapter to add new task
		this.databaseHandler.insertTask(this.task);
	}

	// This function is used to init pet spinner
	// it loads all pet from database and then put into pet spinner
	// need to be called in the onCreate() method
	private void initPet(){
		// Check if the databaseHandler is not null
		if(this.databaseHandler != null){
			// Get all pets
			allPetsCursor = databaseHandler.getAllPetsCursor();
			// TODO replace the deprecated startManagingCursor method with an alternative one
			startManagingCursor(allPetsCursor);
			// Get data from which column
			String[] from = new String[]{DatabaseHandler.PET_NAME};
			// Put data to which components in layout
			int[] to = new int[]{R.id.activity_view_all_pets_listview_all_pets_layout_textview_pet_title};
			// Init the adapter for spinner
			// TODO replace the deprecated SimpleCursorAdapter with an alternative one
			this.allPetsSpinnerAdapter = new SimpleCursorAdapter(this,
					R.layout.activity_view_all_pets_listview_all_pets_layout, allPetsCursor, from, to);
			// Set the adapter for the spinner
			allPetsSpinner.setAdapter(allPetsSpinnerAdapter);
		} else {
			finish();
		}
	}

	// This function is used to load data from this.task object and put it to form
	private void putDataToForm(){
		// First check if the current job is edit task
		if (this.currentJob == this.CURRENT_JOB_EDIT){
			// Now retrieve data from this Task object and put it into form components
			// no need to set Collaborator list since it's a special component and there is another function that handles that task

			// set task title
			EditText taskTitleEditText = (EditText) findViewById(R.id.activity_modify_task_Edittext_task_title);
			taskTitleEditText.setText(this.task.getTitle());

			// set task date
			DatePicker taskDueDatePicker = (DatePicker) findViewById(R.id.activity_modify_task_Datepicker_due_date);
			taskDueDatePicker.updateDate(this.task.getDueDate().get(Calendar.YEAR),
					this.task.getDueDate().get(Calendar.MONTH),
					this.task.getDueDate().get(Calendar.DATE));

			// set task note
			EditText taskNoteEditText = (EditText) findViewById(R.id.activity_modify_task_EditText_note);
			taskNoteEditText.setText(this.task.getNote());

			// set priority level
			Spinner taskPriorityLevelSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_priority_level);
			taskPriorityLevelSpinner.setSelection(this.task.getPriorityLevel());

			// set pet
			allPetsSpinner.setSelection(this.getPetPositionInCursor(allPetsCursor, Integer.toString(this.task.getPet().getID())));

			// set completion status
			Spinner completionStatusSpinner = (Spinner) findViewById(R.id.activity_modify_task_Spinner_completion_status);
			completionStatusSpinner.setSelection(this.task.getStatus());
		}
	}

	// get the id of the pet with the input position
	private String getPetIdByPosition(Cursor cursor, int position){
		String petId = null;
		cursor.moveToFirst();
		cursor.move(position);
		petId = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PET_ID));
		return petId;
	}

	// get the position of the pet with id String petId in the cursor
	private int getPetPositionInCursor(Cursor cursor, String petId){
		int position = -1;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			String currentPetId = cursor.getString(cursor.getColumnIndex(DatabaseHandler.PET_ID));
			if(currentPetId.equals(petId)){
				position = cursor.getPosition();
			}
			cursor.moveToNext();
		}
		return position;
	}

}
