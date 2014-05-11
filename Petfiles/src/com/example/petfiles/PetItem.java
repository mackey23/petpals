package com.example.petfiles;

public class PetItem {
	
	private String name;
	private String image;
	private String breed;
	private String birthday;
	
	public PetItem(String name, String image, String breed, String birthday){
		
		this.name = name;
		this.image= image;
		this.breed = breed;
		this.birthday = birthday;
		
	}
	
	public String getName(){
		return name; 
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getImage(){
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
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
