package com.example.petfiles;

import java.util.List;

import com.example.petfiles.model.PetItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PetListAdapter extends BaseAdapter{
	
	 Context context;
	 List<PetItem> petItems;
	
	 PetListAdapter(Context context, List<PetItem> petItems) {
		  this.context = context;
		  this.petItems = petItems;
	 }
	
	 @Override
	 public int getCount() {
		 return petItems.size();
	 }
	
	 @Override
	 public Object getItem(int position) {
		 return petItems.get(position);
	 }
	
	 @Override
	 public long getItemId(int position) {
		 return petItems.indexOf(getItem(position));
	 }
	
	 /* private view holder class */
	 private class ViewHolder {
		  ImageView profile_pic;
		  TextView name;
		  TextView breed;
		  TextView birthday;
	 }
	
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	
		  ViewHolder holder = null;
		
		  LayoutInflater mInflater = (LayoutInflater) context
		    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		  if (convertView == null) {
			   convertView = mInflater.inflate(R.layout.pet_item, null);
			   holder = new ViewHolder();
			
			   holder.name = (TextView) convertView
			     .findViewById(R.id.pet_name);
			   holder.profile_pic = (ImageView) convertView
			     .findViewById(R.id.profile_pic);
			   holder.breed = (TextView) convertView.findViewById(R.id.breed);
			   holder.birthday = (TextView) convertView
			     .findViewById(R.id.birthday);
			
			   PetItem row_pos = petItems.get(position);
			   
			   if (row_pos.getImage() == null) {
				   holder.profile_pic.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));
			   } else {
				   holder.profile_pic.setImageBitmap(BitmapFactory.decodeFile(row_pos.getImage()));
			   }
			   holder.name.setText(row_pos.getName());
			   holder.breed.setText(row_pos.getBreed());
			   holder.birthday.setText(row_pos.getBirthday());
			
			   convertView.setTag(holder);
		  } else {
			   holder = (ViewHolder) convertView.getTag();
		  }
		
		  return convertView;
	 }

}
