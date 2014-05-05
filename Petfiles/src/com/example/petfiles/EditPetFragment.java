package com.example.petfiles;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditPetFragment  extends Fragment{
	
	final static String ARG_POSITION = "position";
	int currentPosition = -1;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	View rootView = inflater.inflate(R.layout.fragment_edit_pet, container, false);
    	
    	return rootView;
    	
    }
    
	private void setSpinnerContent(View view){
		Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner2);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.gender_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	
    public void updatePet(int position) {
		Activity now = getActivity();
		DatabaseHandler db = new DatabaseHandler(now);
		// NEED TO UPDATE NUMBER ACCORDING TO WHICH PET IS CLICKED
		Pet pet = db.getPet(position);
		((EditText) now.findViewById(R.id.editName2)).setText(pet.getName());
		((EditText) now.findViewById(R.id.editSpecies2)).setText(pet.getSpecies());
		((EditText) now.findViewById(R.id.editBreed2)).setText(pet.getBreed());
		((EditText) now.findViewById(R.id.editBirthday2)).setText(pet.getBirthday());
		int pos = -1;
		if (pet.getGender().equalsIgnoreCase("male")){
			pos = 0;
		} else {
			pos = 1; 
		}
		((Spinner) now.findViewById(R.id.spinner2)).setSelection(pos);
    }
    
    @Override
    public void onStart() {
        super.onStart();
   	 	Log.d("3","blue");
    	setSpinnerContent(getView());
    	Log.d("4","blue");

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
        	updatePet(args.getInt(ARG_POSITION));
        	
        } else if (currentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
        	updatePet(currentPosition);
        }
        
    }
}
