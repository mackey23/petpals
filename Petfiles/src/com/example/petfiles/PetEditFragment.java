package com.example.petfiles;

import java.util.List;

import com.example.petfiles.DatabaseHandler;

import android.app.Activity;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.Toast;

public class PetEditFragment extends Fragment {
	
	public PetEditFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		FragmentManager manager = getFragmentManager();
		
        String str1 = "";
        str1 += manager.getBackStackEntryCount();
        Log.d("3", str1);
		
		getActivity().setTitle("Edit Pet Profile");
        View rootView = inflater.inflate(R.layout.fragment_pet_edit, container, false);
        setSpinnerContent(rootView);
        setButton(rootView);
        submit(rootView);
         
        return rootView;
    }
	
//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//	    super.onViewCreated(view, savedInstanceState);
//	    
//	}
	
	private void setButton(View view){
		 Button back = (Button) view.findViewById(R.id.back);
         back.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 getActivity().onBackPressed(); 
             }
         });
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
				if (name.equals(null) || name.equals(" ")){
					 Toast.makeText(null, "Your name field is empty! Please, insert again", Toast.LENGTH_SHORT).show();
				} else {
					DatabaseHandler db = new DatabaseHandler(now);
					Pet pet = new Pet(name, birthday, species, breed, gender);
					db.addPet(pet);
					
// 					TEST code
//			        Log.d("Reading: ", "Reading all contacts..");
//			        List<Pet> pets = db.getAllPets();       
//			 
//			        for (Pet p : pets) {
//			            String log = "Id: "+p.getID()+" ,Name: " + p.getName() + " ,Breed: " + p.getBreed();
//			                // Writing Contacts to log
//			            Log.d("Name: ", log);
//			        }
			        
			    	Fragment newFragment = new PetViewFragment();
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
}
