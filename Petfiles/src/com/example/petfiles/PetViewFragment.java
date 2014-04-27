package com.example.petfiles;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PetViewFragment extends Fragment{
	
	public PetViewFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_pet_view, container, false);
        getPetInfo(rootView);
         
        return rootView;
    }
	
	private void getPetInfo(View view){
		Activity now = getActivity();
		DatabaseHandler db = new DatabaseHandler(now);
		// NEED TO UPDATE NUMBER ACCORDING TO WHICH PET IS CLICKED
		Pet pet = db.getPet(18);
		String info = " Name: " + pet.getName() + "\n Species:" + pet.getSpecies() 
				+ "\n Breed: " + pet.getBreed() + "\n Birthday" + pet.getBirthday()
				+ "\n Gender:" + pet.getGender();
		((TextView) view.findViewById(R.id.textView1)).setText(info);
	}

}
