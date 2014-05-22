package com.example.petfiles;

import com.example.petfiles.model.Pet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class EditPetFragment  extends Fragment{
	
	final static String ARG_POSITION = "position";
	int currentPosition = -1;
	
	private static int RESULT_LOAD_IMAGE = 1;
	private String selectedImagePath;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
    	
    	View rootView = inflater.inflate(R.layout.fragment_edit_pet, container, false);
    	updateImage(rootView);
    	updatePet(rootView);
    	
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
    	currentPosition = position;
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
		((EditText) now.findViewById(R.id.editNotes2)).setText(pet.getNotes());
		ImageButton button = (ImageButton) now.findViewById(R.id.updatePic);
		if (selectedImagePath != null) {
			button.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
		} else if (pet.getImage() != null){
			button.setImageBitmap(BitmapFactory.decodeFile(pet.getImage()));
			selectedImagePath = pet.getImage();
		} else {
			button.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		}
		db.close();
    }
    
    @Override
    public void onStart() {
        super.onStart();
    	setSpinnerContent(getView());


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
    
	private void updateImage(View view){
		ImageButton button = (ImageButton) view.findViewById(R.id.updatePic);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
			
		});
	}
	
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			selectedImagePath = cursor.getString(columnIndex);
			cursor.close();
			
			ImageButton button = (ImageButton) getView().findViewById(R.id.updatePic);
			button.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
		
		}
    
    
    }
    
	public void updatePet(View view){
		Button button = (Button) view.findViewById(R.id.update);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText editName = (EditText) getActivity().findViewById(R.id.editName2);
				String name = editName.getText().toString();
				EditText editSpecies = (EditText) getActivity().findViewById(R.id.editSpecies2);
				String species = editSpecies.getText().toString();
				EditText editBreed = (EditText) getActivity().findViewById(R.id.editBreed2);
				String breed = editBreed.getText().toString();
				EditText editBirth = (EditText) getActivity().findViewById(R.id.editBirthday2);
				String birthday = editBirth.getText().toString();
				Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner2);
				String gender = spinner.getSelectedItem().toString();
				EditText editNotes = (EditText) getActivity().findViewById(R.id.editNotes2);
				String notes = editNotes.getText().toString();
		
				DatabaseHandler db = new DatabaseHandler(getActivity());
				Pet pet = new Pet(currentPosition, name, birthday, species, breed, gender, selectedImagePath, notes);
				db.updatePet(pet);
				
				db.close();
				
		    	MyPetsFragment newFragment = new MyPetsFragment();
		    	FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		    	// Replace whatever is in the fragment_container view with this fragment,
		    	// and add the transaction to the back stack
		    	transaction.replace(R.id.frame_container, newFragment);
		    	transaction.addToBackStack(null);
		
		    	// Commit the transaction
		    	transaction.commit();
			}
		});
	}
	
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, currentPosition);
    }
}
