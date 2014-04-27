package com.example.petfiles;

public class Pet {
	
	//private variables
	int _id;
	String _name;
	String _birthday;
	String _species;
	String _breed;
	String _gender;
	
	// Empty constructor
	public Pet(){
		
	}
	// constructor
	public Pet(int id, String name, String _birthday, String _species, String _breed, String _gender){
		this._id = id;
		this._name = name;
		this._birthday = _birthday;
		this._species = _species;
		this._breed = _breed;
		this._gender = _gender;
	}
	
	// constructor
	public Pet(String name, String _birthday, String _species, String _breed, String _gender){
		this._name = name;
		this._birthday = _birthday;
		this._species = _species;
		this._breed = _breed;
		this._gender = _gender;
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
}
