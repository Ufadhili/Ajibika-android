package com.unina.ajibika;



import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CountyAssembly extends Fragment {
	private static View view;
	private SharedPreferences sharedpref;
	
	private int county_id;
	public List<Person> personlist;
	@Override
	public void onActivityCreated(Bundle paramBundle)
	  {
	    super.onActivityCreated(paramBundle);
	  
	  }

	@Override
	public void onCreate(Bundle paramBundle)
	{
	   
	    super.onCreate(paramBundle);
	    sharedpref = getActivity().getSharedPreferences("UserSession", 0); // 0 - for private mode
		this.county_id=sharedpref.getInt("selected_county_id", 0);
		
	    
	     
	}
	@Override
	public void onResume()
	{
	
	 super.onResume();
	
	 
	}
	 @Override
	public void onPause() {
	    super.onPause();
	   
	 }
	 @Override
		public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
		{
			 super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
			 
			 if (view != null) {
			        ViewGroup parent = (ViewGroup) view.getParent();
			        if (parent != null)
			            parent.removeView(view);
			    }
			    try {
			        view = paramLayoutInflater.inflate(R.layout.county_people, paramViewGroup, false);
			    } catch (InflateException e) {
			        /* map is already there, just return view as it is */
			    }
			    // Log.i("ID",""+this.county_id);
			    
			    personlist=MainActivity.UfadhiliDataSource.getGroupPersons( this.county_id,4);
				View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview_nocontent, null, false);

			    final ListView listview = (ListView) view.findViewById(R.id.peoplelistview);
			    final PeopleListViewArrayAdapter adapter = new PeopleListViewArrayAdapter(getActivity(),personlist);
			    listview.setAdapter(adapter);
			    if(personlist.isEmpty()){
				     listview.addFooterView(footerView);
				}
			    
			    listview.setOnItemClickListener(new OnItemClickListener() {
			    	 
	                  @Override
	                  public void onItemClick(AdapterView<?> parent, View view,
	                     int position, long id) {
	                    
	                   // ListView Clicked item index
	                   int itemPosition     = position;
	                   Intent myIntent = new Intent(getActivity(), CountyPersonDetail.class);
	                   myIntent.putExtra("personID",personlist.get(itemPosition).person_id);
	                   myIntent.putExtra("positionID",personlist.get(itemPosition).position_id);
	                   startActivity(myIntent);
	                  }
	    
	             }); 
			  return view;
	    }

}
