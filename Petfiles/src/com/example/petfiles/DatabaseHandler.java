package com.example.petfiles;
import java.util.ArrayList;
import java.util.List;

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

	// Contacts table name
	private static final String TABLE_PETS = "pets";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_BIRTH = "birthday";
	private static final String KEY_SPECIES = "species";
	private static final String KEY_BREED = "breed";
	private static final String KEY_GENDER = "gender";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PETS_TABLE = "CREATE TABLE " + TABLE_PETS  + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_BIRTH + " TEXT," + KEY_SPECIES + " TEXT," + KEY_BREED + " TEXT,"
				+ KEY_GENDER + " TEXT" + ")";
		db.execSQL(CREATE_PETS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new pet
	void addPet(Pet pet) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, pet.getName()); // Pet Name
		values.put(KEY_BIRTH, pet.getBirthday()); // Pet Birthday
		values.put(KEY_SPECIES, pet.getSpecies()); // Pet Species
		values.put(KEY_BREED, pet.getBreed()); // Pet Breed
		values.put(KEY_GENDER, pet.getGender()); // Pet Gender

		// Inserting Row
		db.insert(TABLE_PETS, null, values);
		db.close(); // Closing database connection
	}

	// Getting single pet
	Pet getPet(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_PETS, new String[] { KEY_ID,
				KEY_NAME, KEY_BIRTH, KEY_SPECIES, KEY_BREED, KEY_GENDER }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Pet pet = new Pet(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
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
				// Adding contact to list
				petList.add(pet);
			} while (cursor.moveToNext());
		}

		// return pet list
		return petList;
	}

	// Updating single pet
	public int updatePet(Pet pet) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, pet.getName());
		values.put(KEY_BIRTH, pet.getBirthday());
		values.put(KEY_SPECIES, pet.getSpecies());
		values.put(KEY_BREED, pet.getBreed());
		values.put(KEY_GENDER, pet.getGender());

		// updating row
		return db.update(TABLE_PETS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(pet.getID()) });
	}

	// Deleting single pet
	public void deletePet(Pet pet) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PETS, KEY_ID + " = ?",
				new String[] { String.valueOf(pet.getID()) });
		db.close();
	}


	// Getting Pets Count
	public int getPetsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PETS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}