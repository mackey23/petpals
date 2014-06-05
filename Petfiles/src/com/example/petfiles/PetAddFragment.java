package com.example.petfiles;

import java.util.List;

import com.example.petfiles.DatabaseHandler;
import com.example.petfiles.model.Pet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

public class PetAddFragment extends Fragment {
	
	private static int RESULT_LOAD_IMAGE = 1;
	private String selectedImagePath;
	
	public PetAddFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		getActivity().setTitle("Edit Pet Profile");
        View rootView = inflater.inflate(R.layout.fragment_pet_add, container, false);
        setSpinnerContent(rootView);
        getImage(rootView);
        submit(rootView);
         
        return rootView;
    }
		
	private void setSpinnerContent(View view){
		Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.gender_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
	
	private void submit(View view){
		Button submit = (Button) view.findViewById(R.id.confirm);
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Activity now = getActivity();
				EditText editName = (EditText) now.findViewById(R.id.editName);
				String name = editName.getText().toString();
				EditText editSpecies = (EditText) now.findViewById(R.id.editSpecies);
				String species = editSpecies.getText().toString();
				EditText editBreed = (EditText) now.findViewById(R.id.editBreed);
				String breed = editBreed.getText().toString();
				EditText editBirth = (EditText) now.findViewById(R.id.editBirthday);
				String birthday = editBirth.getText().toString();
				Spinner spinner = (Spinner) now.findViewById(R.id.spinner1);
				String gender = spinner.getSelectedItem().toString();
				String image = selectedImagePath;
				EditText editNotes = (EditText) now.findViewById(R.id.editNotes);
				String notes = editNotes.getText().toString();
				if(name.length() == 0){
					editName.setError("Name is required");
				} 
				if(species.length() == 0){
					editSpecies.setError("Species is required");
				} 
				if(birthday.length() == 0){
					editBirth.setError("Birthday is required");
				} // what id if someone doesn't know their pet's birthday?
				if(name.length() != 0 && breed.length() != 0 && birthday.length() != 0 ){
					DatabaseHandler db = new DatabaseHandler(now);
					Pet pet = new Pet(name, birthday, species, breed, gender, image, notes);
					db.addPet(pet);
					
//	 					TEST code
//				        Log.d("Reading: ", "Reading all contacts..");
//				        List<Pet> pets = db.getAllPets();       
//				 
//				        for (Pet p : pets) {
//				            String log = "Id: "+p.getID()+" ,Name: " + p.getName() + " ,Breed: " + p.getBreed();
//				                // Writing Contacts to log
//				            Log.d("Name: ", log);
//				        }
			        
			    	Fragment newFragment = new MyPetsFragment();
			    	FragmentTransaction transaction = getFragmentManager().beginTransaction();

			    	// Replace whatever is in the fragment_container view with this fragment,
			    	// and add the transaction to the back stack
			    	transaction.replace(R.id.frame_container, newFragment);
			    	transaction.addToBackStack(null);

			    	// Commit the transaction
			    	transaction.commit();
				}		

			}
			
		});
	}
	
	private void getImage(View view){
		ImageButton button = (ImageButton) view.findViewById(R.id.imageButton1);
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
			
			ImageButton button = (ImageButton) getView().findViewById(R.id.imageButton1);
			button.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
		
		}
    
    
    }
}
