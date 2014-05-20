package com.example.petfiles;

import java.util.ArrayList;
import java.util.List;

import com.example.petfiles.adapter.PetListAdapter;
import com.example.petfiles.model.Pet;
import com.example.petfiles.model.PetItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class MyPetsFragment extends Fragment {
	
	private ArrayList<String> names = new ArrayList<String>();;
	private ArrayList<String> images = new ArrayList<String>();
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
		// Clean out table
//		db.deleteAll();
		
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
		}
         
        return rootView;
    }
	
	@Override
	public void onResume(){
		super.onResume();
		petItems = new ArrayList<PetItem>();
		
		DatabaseHandler db = new DatabaseHandler(getActivity());
		
        List<Pet> pets = db.getAllPets(); 
        
   	 	if (names.size() == 0) {
	        for (Pet p : pets) {
	        	String name = p.getName();
	        	String breed = p.getBreed();
	        	String birthday = p.getBirthday();
	        	String image = p.getImage();
	        	names.add(name);
	        	breeds.add(breed);
	        	birthdays.add(birthday);
	        	images.add(image);
	        }
   	 	}
        
        if (petItems.size() == 0){	        
	        for(int i=0; i<names.size(); i++){
	        	PetItem item = new PetItem(names.get(i), images.get(i), breeds.get(i), birthdays.get(i));
	        	petItems.add(item);
	        }
	        
	        mylistview = (ListView) getView().findViewById(R.id.list);
	        PetListAdapter adapter = new PetListAdapter(getActivity(), petItems);
	        mylistview.setAdapter(adapter);
	        mylistview.setOnItemClickListener(new petListClickListener());
	        mylistview.setOnItemLongClickListener(new OnItemLongClickListener() {

	            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long id) {
	            	ImageButton delete = (ImageButton) getView().findViewById(pos+1000);
	            	delete.setVisibility(View.VISIBLE);
	            	delete.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View arg0) {
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
							 
							// set title
							alertDialogBuilder.setTitle("Delete Pet");
							 
							// set dialog message
							alertDialogBuilder
							.setMessage("Do you wish to delete this pet?")
							.setCancelable(false)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									DatabaseHandler db = new DatabaseHandler(getActivity());
									Pet pet = db.getPet(pos+1);
									db.deletePet(pet);
							        List<Pet> pets = db.getAllPets();   
							        db.deleteAllPets();
									 
							        for (Pet p : pets) {
							        	db.addPet(p);
							        }
							        
							        db.close();
							        
							    	Fragment newFragment = new MyPetsFragment();
							    	FragmentTransaction transaction = getFragmentManager().beginTransaction();

							    	// Replace whatever is in the fragment_container view with this fragment,
							    	// and add the transaction to the back stack
							    	transaction.replace(R.id.frame_container, newFragment);

							    	// Commit the transaction
							    	transaction.commit();
								}
							})
							.setNegativeButton("No",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
									dialog.cancel();
								}
							});
							 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
				 
							// show it
							alertDialog.show();
						}
	            		
	            	});
	            	getView().findViewById(pos+100).setVisibility(View.VISIBLE);
	            	hide(pos);
	                return true;
	            }
	            
	        });
        }
		
	}
	
	public void hide(final int pos){
		final View listen = getView().findViewById(pos+100);
    	listen.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
            	ImageButton delete = (ImageButton) mylistview.findViewById(pos+1000);
            	delete.setVisibility(View.GONE);
            	listen.setVisibility(View.GONE);
			}
    		
    	});
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
	
}
