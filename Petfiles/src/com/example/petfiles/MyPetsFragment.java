package com.example.petfiles;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyPetsFragment extends Fragment {
	
	public MyPetsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		DatabaseHandler db = new DatabaseHandler(getActivity());

		if(db.getPetsCount() == 0){
	    	Fragment newFragment = new NewPetFragment();
	    	FragmentTransaction transaction = getFragmentManager().beginTransaction();

	    	// Replace whatever is in the fragment_container view with this fragment,
	    	// and add the transaction to the back stack
	    	transaction.replace(R.id.frame_container, newFragment);

	    	// Commit the transaction
	    	transaction.commit();
		}
 
        View rootView = inflater.inflate(R.layout.fragment_my_pets, container, false);
        changePetView(rootView);
         
        return rootView;
    }
	
	//Changes View to the Pet view
	private void changePetView(View view){
		 Button change = (Button) view.findViewById(R.id.button1);
         change.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
             	Fragment newFragment = new PetViewFragment();
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
