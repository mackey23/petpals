package com.example.petfiles;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.petfiles.model.Task;
import com.example.petfiles.model.Pet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "petpals";
	
	// Pet table
	// Pet table name
	public static final String TABLE_PETS = "pets";
	// Pet table columns names
	public static final String PET_ID = "_id";
	public static final String PET_NAME = "name";
	public static final String PET_BIRTH = "birthday";
	public static final String PET_SPECIES = "species";
	public static final String PET_BREED = "breed";
	public static final String PET_GENDER = "gender";
	public static final String PET_IMAGE = "imagePath";
	public static final String PET_NOTES = "notes";
	
	// Pet table create statement
	public static final String CREATE_PETS_TABLE 
			= "CREATE TABLE " + TABLE_PETS
			+ "("
			+ PET_ID + " INTEGER PRIMARY KEY," 
			+ PET_NAME + " TEXT,"
			+ PET_BIRTH + " TEXT," 
			+ PET_SPECIES + " TEXT," 
			+ PET_BREED + " TEXT,"
			+ PET_GENDER + " TEXT,"
			+ PET_IMAGE + " TEXT,"
			+ PET_NOTES + " TEXT" 
			+ ")";
	
	// Task table
	// Task table name
	public static final String TABLE_TASK = "task";
	// Task table column names
	public static final String TASK_ID = "_id";
	public static final String TASK_TITLE = "_title";
	public static final String TASK_DUE_DATE = "_due_date";
	public static final String TASK_NOTE = "_note";
	public static final String TASK_PRIORITY = "_priority";
	public static final String TASK_PET = "_pet";
	public static final String TASK_STATUS = "_status";
	// Task table create statement
	public static final String CREATE_TASK_TABLE
			= "create table " + TABLE_TASK
			+ " ( "
			+ TASK_ID + " text primary key, "
			+ TASK_TITLE + " text not null, "
			+ TASK_DUE_DATE + " integer not null, "
			+ TASK_NOTE + " text,"
			+ TASK_PRIORITY + " integer not null, "
			+ TASK_PET + " text not null, "
			+ TASK_STATUS + " integer not null, "
			// create the foreign key from column pet -> pet(id)
			+ "foreign key ( " + TASK_PET + " ) references " + TABLE_PETS + " ( " + PET_ID + " ) "
			+ " );";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create the Pet table
		db.execSQL(CREATE_PETS_TABLE);
		// Create the Task table
		db.execSQL(CREATE_TASK_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
		// Drop Task table if exists
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

		// Create tables again
		onCreate(db);
	}
	
	// Open connection to database, should be called right after constructor
	public DatabaseHandler open(){
		SQLiteDatabase db = this.getWritableDatabase();
		return this;
	}
	
	// Close connection to database, should be called at the end when everything is finished
	public void close(){
		this.close();
	}
	
	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	// FUNCTIONS FOR PETS
	
	// Adding new pet
	public void addPet(Pet pet) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PET_NAME, pet.getName()); // Pet Name
		values.put(PET_BIRTH, pet.getBirthday()); // Pet Birthday
		values.put(PET_SPECIES, pet.getSpecies()); // Pet Species
		values.put(PET_BREED, pet.getBreed()); // Pet Breed
		values.put(PET_GENDER, pet.getGender()); // Pet Gender
		values.put(PET_IMAGE, pet.getImage()); 
		values.put(PET_NOTES, pet.getNotes());

		// Inserting Row
		db.insert(TABLE_PETS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single pet
	Pet getPet(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PETS, new String[] { PET_ID,
				PET_NAME, PET_BIRTH, PET_SPECIES, PET_BREED, PET_GENDER, PET_IMAGE, PET_NOTES }, PET_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Pet pet = new Pet(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
		// return contact
		return pet;
	}
	
	// Getting All Pets
	public List<Pet> getAllPets() {
		List<Pet> petList = new ArrayList<Pet>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_PETS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Pet pet = new Pet();
				pet.setID(Integer.parseInt(cursor.getString(0)));
				pet.setName(cursor.getString(1));
				pet.setBirthday(cursor.getString(2));
				pet.setSpecies(cursor.getString(3));
				pet.setBreed(cursor.getString(4));
				pet.setGender(cursor.getString(5));
				pet.setImage(cursor.getString(6));
				pet.setNotes(cursor.getString(7));
				// Adding contact to list
				petList.add(pet);
			} while (cursor.moveToNext());
		}

		// return pet list
		return petList;
	}
	
	// Return all groups currently in database
	public Cursor getAllPetsCursor(){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.query(TABLE_PETS,
				new String[] {PET_ID, PET_NAME}, null, null, null, null, null);
	}

	// Updating single pet
	public int updatePet(Pet pet) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PET_NAME, pet.getName());
		values.put(PET_BIRTH, pet.getBirthday());
		values.put(PET_SPECIES, pet.getSpecies());
		values.put(PET_BREED, pet.getBreed());
		values.put(PET_GENDER, pet.getGender());
		values.put(PET_IMAGE, pet.getImage()); 
		values.put(PET_NOTES, pet.getNotes()); 
		
		// updating row
		return db.update(TABLE_PETS, values, PET_ID + " = ?",
				new String[] { String.valueOf(pet.getID()) });
	}

	// Deleting single pet
	public void deletePet(Pet pet) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PETS, PET_ID + " = ?",
				new String[] { String.valueOf(pet.getID()) });
		db.close();
	}

	// Getting Pets Count
	public int getPetsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PETS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}
	
	// Find task by its id
	public Cursor getPetById(String petId){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.query(TABLE_PETS,
				new String[] {PET_ID, PET_NAME, PET_BIRTH, PET_SPECIES, PET_BREED, PET_GENDER, PET_IMAGE, PET_NOTES},
				PET_ID + " = '" + petId + "'", null, null, null, null);
	}
	
	public void deleteAll(){
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_PETS,null,null);
	    db.close();
	}
	
	// FUNCTIONS FOR TASKS
	
	// Insert a new task into Task table
	public void insertTask(Task task){
		ContentValues initialValues = new ContentValues();
		initialValues.put(TASK_ID, task.getId());
		initialValues.put(TASK_TITLE, task.getTitle());
		initialValues.put(TASK_DUE_DATE, task.getDueDate().getTimeInMillis());
		initialValues.put(TASK_NOTE, task.getNote());
		initialValues.put(TASK_PRIORITY, task.getPriorityLevel());
		initialValues.put(TASK_PET, task.getPet().getID());
		initialValues.put(TASK_STATUS, task.getStatus());
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(TABLE_TASK, null, initialValues);
	}
	
	// Return all task currently in database
	public Cursor getAllTasks(){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.query(TABLE_TASK,
				new String[] {TASK_ID, TASK_TITLE, TASK_DUE_DATE, TASK_NOTE, TASK_PRIORITY, TASK_PET, TASK_STATUS},
				null, null, null, null, null);
	}
	
	// Find task by its id
	public Cursor getTaskById(String taskId){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.query(TABLE_TASK,
				new String[] {TASK_ID, TASK_TITLE, TASK_DUE_DATE, TASK_NOTE, TASK_PRIORITY, TASK_PET, TASK_STATUS},
				TASK_ID + " = '" + taskId + "'", null, null, null, null);
	}
	
	// Edit an existing task
	public void editExistingTask(Task task){
		ContentValues updateValues;
		SQLiteDatabase db = this.getWritableDatabase();
		
		// Update Task table first
		updateValues = new ContentValues();
		updateValues.put(TASK_TITLE, task.getTitle());
		updateValues.put(TASK_NOTE, task.getNote());
		updateValues.put(TASK_DUE_DATE, task.getDueDate().getTimeInMillis());
		updateValues.put(TASK_PRIORITY, task.getPriorityLevel());
		updateValues.put(TASK_PET, task.getPet().getID());
		updateValues.put(TASK_STATUS, task.getStatus());
		db.update(TABLE_TASK, updateValues, TASK_ID + " = '" + task.getId() + "'", null);
	}
	
	// delete the selected Task
	public void deleteTask(Task task){
		deleteTask(task.getId());
	}
	
	// helper for delete task
	public void deleteTask(String taskId){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TASK, TASK_ID + " = '" + taskId + "'", null);
	}
	
	// Return a new randomly generated task id
	public String getNewTaskId(){
		String uuid = null;
		Cursor cursor = null;
		
		// Create a random uuid and then check if it's exist
		do {
			uuid = UUID.randomUUID().toString();
			cursor = getTaskById(uuid);
		} while (cursor.getCount() > 0);
		
		return uuid;
	}
	
}