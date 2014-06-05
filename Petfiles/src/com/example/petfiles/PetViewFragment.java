package com.example.petfiles;

import com.example.petfiles.model.Pet;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PetViewFragment extends Fragment{
	
	final static String ARG_POSITION = "position";
	int currentPosition = -1;
	
	public PetViewFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		getActivity().setTitle("Pet Profile");
		
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
		
        View rootView = inflater.inflate(R.layout.fragment_pet_view, container, false);
        
        return rootView;
    }
	
	
    public void updatePetView(int position) {
		Activity now = getActivity();
		DatabaseHandler db = new DatabaseHandler(now);
		// NEED TO UPDATE NUMBER ACCORDING TO WHICH PET IS CLICKED
		Pet pet = db.getPet(position);
		String name = "Name: " + pet.getName();
		String species = "Species: " + pet.getSpecies();
		String breed = "Breed: " + pet.getBreed();
		String birth = "Birthday: " + pet.getBirthday();
		String gender = "Gender: " + pet.getGender();
		String notes = "Notes: " + pet.getNotes();
		((TextView) now.findViewById(R.id.name)).setText(name);
		((TextView) now.findViewById(R.id.species)).setText(species);
		((TextView) now.findViewById(R.id.breed)).setText(breed);
		((TextView) now.findViewById(R.id.birthday)).setText(birth);
		((TextView) now.findViewById(R.id.gender)).setText(gender);
		((TextView) now.findViewById(R.id.notes)).setText(notes);
		ImageView image = (ImageView) now.findViewById(R.id.pic);
		if (pet.getImage() == null) {
			image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		} else {
			image.setImageBitmap(BitmapFactory.decodeFile(pet.getImage()));
		}
    }
    
    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
        	updatePetView(args.getInt(ARG_POSITION));
        } else if (currentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
        	updatePetView(currentPosition);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, currentPosition);
    }
    

}
