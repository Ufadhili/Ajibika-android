package com.unina.ajibika;


import com.unina.ajibika.R.drawable;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutCounty extends Fragment {
	private static View view;
	private TextView countysummary;
	private int county_id;
	private UfadhiliData  countydatasource;
	private SharedPreferences sharedpref;
	
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
			        view = paramLayoutInflater.inflate(R.layout.about_county, paramViewGroup, false);
			    } catch (InflateException e) {
			        /* map is already there, just return view as it is */
			    }
			   // Log.i("ID",""+this.county_id);
			    if(this.county_id>0){
			     County county= MainActivity.UfadhiliDataSource.getCounty( this.county_id);
			     countysummary=(TextView) view.findViewById(R.id.county_summary);
			     countysummary.setText(Html.fromHtml(county.getDesc()));
			    }
			    
			    ExpandablePanel panel = (ExpandablePanel) view.findViewById(R.id.expandabledescription);

				panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
					public void onCollapse(View handle, View content) {
						ImageView imgview = (ImageView) handle;
						imgview.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_expand));
					}

					public void onExpand(View handle, View content) {
						ImageView imgview = (ImageView) handle;
						imgview.setImageDrawable(getResources().getDrawable(drawable.ic_action_collapse));
					}
				}); 
			    
			    
			    
			    
			    
			  return view;
	    }

}
