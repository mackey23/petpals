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
		String info = " Name: " + pet.getName() + "\n Species: " + pet.getSpecies() 
				+ "\n Breed: " + pet.getBreed() + "\n Birthday: " + pet.getBirthday()
				+ "\n Gender: " + pet.getGender() +  "\n Notes: " + pet.getNotes();
		((TextView) now.findViewById(R.id.textView1)).setText(info);
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
