package com.example.petfiles;

import java.util.ArrayList;
import java.util.List;

import com.example.petfiles.controller.ApplicationNavigationHandler;
import com.example.petfiles.R;
import com.example.petfiles.DatabaseHandler;
import com.example.petfiles.MyPetsFragment.OnHeadlineSelectedListener;
import com.example.petfiles.controller.MessageDialogHandler;
import com.example.petfiles.model.Pet;
import com.example.petfiles.model.Task;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ToDoFragment extends Fragment {
	
	// The List View that shows all Tasks
	private ListView allTasksListView;

	// DatabaseHandler for interacting with database
	private DatabaseHandler databaseHandler;

	// The cursor for query all pets from database
	private Cursor allTasksCursor;

	// Adapter for All Tasks List View
	private SimpleCursorAdapter allTasksListViewAdapter;

	// The Add New Task request code
	public static final int ADD_NEW_TASK_REQUEST_CODE = 1;
	
//	OnHeadlineSelectedListener mListener;
	
	public ToDoFragment(){}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_do, container, false);
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// to add menu buttons/items
		setHasOptionsMenu(true);
		
		// set action listener for allTasksListView
		allTasksListView = (ListView) getActivity().findViewById(R.id.view_all_tasks);
		allTasksListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				allTaskListViewItemClickHandler(arg0, arg1, arg2);
			}
		});

		// Open the connection to database
		databaseHandler = new DatabaseHandler(getActivity());
		databaseHandler.open();
		
		// Init all tasks and add them to list view
		initAllTasksListView();
	}

	// Init the Tasks List View
	// Load all Tasks from database and put them to list view
	public void initAllTasksListView(){
		// Check if the databaseAdapter is not null
		if(this.databaseHandler != null){
			// Get all Tasks
			allTasksCursor = databaseHandler.getAllTasks();
			// TODO replace the deprecated startManagingCursor method with an alternative one
			getActivity().startManagingCursor(allTasksCursor);
			// Get data from which column
			String[] from = new String[]{DatabaseHandler.TASK_TITLE};
			// Put data to which components in layout
			int[] to = new int[]{R.id.activity_view_all_pets_listview_all_pets_layout_textview_pet_title};
			// Init the adapter for list view
			// TODO replace the deprecated SimpleCursorAdapter with an alternative one
			allTasksListViewAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.activity_view_all_pets_listview_all_pets_layout, allTasksCursor, from, to);
			// Set the adapter for the list view
			this.allTasksListView.setAdapter(allTasksListViewAdapter);
		}
	}

	// Handle the item clicked event of allTasksListView
	private void allTaskListViewItemClickHandler(AdapterView<?> adapterView, View listView, int selectedItemId){
		// Create a new Task object and init the data
		// After that pass it to the next activity to display detail
		Task selectedTask = new Task();
		// move the cursor to the right position
		allTasksCursor.moveToFirst();
		allTasksCursor.move(selectedItemId);
		// set the data for selectedTask
		// set id
		selectedTask.setId(allTasksCursor.getString(allTasksCursor.getColumnIndex(DatabaseHandler.TASK_ID)));
		// set title
		selectedTask.setTitle(allTasksCursor.getString(allTasksCursor.getColumnIndex(DatabaseHandler.TASK_TITLE)));
		// set due date
		selectedTask.getDueDate().setTimeInMillis(allTasksCursor.getLong(allTasksCursor.getColumnIndex(DatabaseHandler.TASK_DUE_DATE)));
		// set note
		selectedTask.setNote(allTasksCursor.getString(allTasksCursor.getColumnIndex(DatabaseHandler.TASK_NOTE)));
		// set priority level
		selectedTask.setPriorityLevel(allTasksCursor.getInt(allTasksCursor.getColumnIndex(DatabaseHandler.TASK_PRIORITY)));
		// set completion status
		selectedTask.setStatus(allTasksCursor.getInt(allTasksCursor.getColumnIndex(DatabaseHandler.TASK_STATUS)));
		// set the group
		selectedTask.setPet(this.getPetByTask(allTasksCursor.getString(allTasksCursor.getColumnIndex(DatabaseHandler.TASK_PET))));
		
		// start the activity
		ApplicationNavigationHandler.viewTaskDetail(getActivity(), selectedTask);
	}
	
	// Get the group by the input Id
	private Pet getPetByTask(String petId){
		Pet pet = new Pet();
		
		// query from database
		Cursor petCursor = this.databaseHandler.getPetById(petId);
		petCursor.moveToFirst();
		pet.setID(petCursor.getInt(petCursor.getColumnIndex(DatabaseHandler.PET_ID)));
		pet.setName(petCursor.getString(petCursor.getColumnIndex(DatabaseHandler.PET_NAME)));
		
		return pet;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		// references the item inside res/menu/view_all_tasks.xml
		case R.id.view_all_tasks_item_add_task:
			List<Pet> allPetsList = databaseHandler.getAllPets();
			if (allPetsList.size() == 0) {
				MessageDialogHandler.showMessageDialog(getActivity(),
						"Opps.. No pets added yet!\nPlease go back and add\nyour pets first");
			} else {
				ApplicationNavigationHandler.addNewTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	// adds the menu item
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.view_all_tasks, menu);
		super.onCreateOptionsMenu(menu,inflater);
	}
}
