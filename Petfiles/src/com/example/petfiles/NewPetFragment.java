package com.example.petfiles;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class NewPetFragment extends Fragment{
	
	public NewPetFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_new_pet, container, false);
        addbutton(rootView);
         
        return rootView;
    }
	
	private void addbutton(View view){
		 ImageButton add = (ImageButton) view.findViewById(R.id.imageButton1);
         add.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
             	Fragment newFragment = new PetEditFragment();
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

}
