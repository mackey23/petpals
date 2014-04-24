package com.example.petfiles;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PetEditFragment extends Fragment {
	
	public PetEditFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		getActivity().setTitle("Edit Pet Profile");
        View rootView = inflater.inflate(R.layout.fragment_pet_edit, container, false);
        setSpinnerContent(rootView);
        setButton(rootView);
         
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
		        R.array.planets_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}
}
