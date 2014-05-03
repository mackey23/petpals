package com.example.petfiles;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentUris;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MyPetsFragment extends Fragment {
	
	private ArrayList<String> names = new ArrayList<String>();;
	private TypedArray profile_pics;
	private ArrayList<String> breeds = new ArrayList<String>();
	private ArrayList<String> birthdays = new ArrayList<String>();
	
	private ListView mylistview;
	private List<PetItem> petItems;
	
	OnHeadlineSelectedListener mListener;
	
	public MyPetsFragment(){}
	
	public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		DatabaseHandler db = new DatabaseHandler(getActivity());
		
		View rootView = inflater.inflate(R.layout.fragment_my_pets, container, false);

		if(db.getPetsCount() == 0){
	    	Fragment newFragment = new NewPetFragment();
	    	FragmentTransaction transaction = getFragmentManager().beginTransaction();

	    	// Replace whatever is in the fragment_container view with this fragment,
	    	// and add the transaction to the back stack
	    	transaction.replace(R.id.frame_container, newFragment);
	    	transaction.addToBackStack(null);

	    	// Commit the transaction
	    	transaction.commit();
		} else {
			petItems = new ArrayList<PetItem>();
			
	        List<Pet> pets = db.getAllPets(); 
	 
	        for (Pet p : pets) {
//                // Writing Contacts to log
	        	String name = p.getName();
	        	String breed = p.getBreed();
	        	String birthday = p.getBirthday();
	        	names.add(name);
	        	breeds.add(breed);
	        	birthdays.add(birthday);
	        }
	        
	        	        
	        for(int i=0; i<names.size(); i++){
	        	PetItem item = new PetItem(names.get(i), 0, breeds.get(i), birthdays.get(i));
	        	petItems.add(item);
	        }
	        
	        mylistview = (ListView) rootView.findViewById(R.id.list);
	        PetListAdapter adapter = new PetListAdapter(getActivity(), petItems);
	        mylistview.setAdapter(adapter);
	        mylistview.setOnItemClickListener(new petListClickListener());
		}
 
       
//        changePetView(rootView);
         
        return rootView;
    }
	
	private class petListClickListener implements ListView.OnItemClickListener{
		
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
	        mListener.onArticleSelected(position);
	        mylistview.setItemChecked(position, true);
		}
		
	}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }
	
	//Changes View to the Pet view
//	private void changePetView(View view){
//		 Button change = (Button) view.findViewById(R.id.button1);
//         change.setOnClickListener(new View.OnClickListener() {
//             public void onClick(View v) {
//             	Fragment newFragment = new PetViewFragment();
//            	FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//            	// Replace whatever is in the fragment_container view with this fragment,
//            	// and add the transaction to the back stack
//            	transaction.replace(R.id.frame_container, newFragment);
//            	transaction.addToBackStack(null);
//
//            	// Commit the transaction
//            	transaction.commit();
//             }
//         });
//	}
}
