package com.example.petfiles;

public class Pet {
	
	//private variables
	int _id;
	String _name;
	String _birthday;
	String _species;
	String _breed;
	String _gender;
	String _image;
	String _notes;
	
	// Empty constructor
	public Pet(){
		
	}
	// constructor
	public Pet(int id, String name, String _birthday, String _species, String _breed, String _gender, String _image, String _notes){
		this._id = id;
		this._name = name;
		this._birthday = _birthday;
		this._species = _species;
		this._breed = _breed;
		this._gender = _gender;
		this._image = _image;
		this._notes = _notes;
	}
	
	// constructor
	public Pet(String name, String _birthday, String _species, String _breed, String _gender, String _image, String _notes){
		this._name = name;
		this._birthday = _birthday;
		this._species = _species;
		this._breed = _breed;
		this._gender = _gender;
		this._image = _image;
		this._notes = _notes;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
	// getting birthday
	public String getBirthday(){
		return this._birthday;
	}
	
	// setting birthday
	public void setBirthday(String birthday){
		this._birthday = birthday;
	}
	
	// getting species
	public String getSpecies(){
		return this._species;
	}
	
	// setting species
	public void setSpecies(String species){
		this._species = species;
	}
	
	// getting breed
	public String getBreed(){
		return this._breed;
	}
	
	// setting breed
	public void setBreed(String breed){
		this._breed = breed;
	}
	
	// getting gender
	public String getGender(){
		return this._gender;
	}
	
	// setting gender
	public void setGender(String gender){
		this._gender = gender;
	}
	
	// getting image
	public String getImage(){
		return this._image;
	}
	
	// setting image
	public void setImage(String image){
		this._image = image;
	}
	
	// getting notes
	public String getNotes(){
		return this._notes;
	}
	
	// setting notes
	public void setNotes(String notes){
		this._notes = notes;
	}
}
