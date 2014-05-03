package com.example.petfiles;

public class PetItem {
	
	private String name;
	private int profile_pic_id;
	private String breed;
	private String birthday;
	
	public PetItem(String name, int profile_pic_id, String breed, String birthday){
		
		this.name = name;
		this.profile_pic_id= profile_pic_id;
		this.breed = breed;
		this.birthday = birthday;
		
	}
	
	public String getName(){
		return name; 
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getProfile_pic_id(){
		return profile_pic_id;
	}
	
	public void setProfile_pic_id(int profile_pic_id) {
		this.profile_pic_id = profile_pic_id;
	}
	
	public String getBreed(){
		return breed; 
	}
	
	public void setBreed(String breed){
		this.breed = breed;
	}
	
	public String getBirthday(){
		return birthday; 
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	
	
}
