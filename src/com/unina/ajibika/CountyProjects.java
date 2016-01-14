package com.unina.ajibika;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
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

public class CountyProjects extends Fragment {
	private static View view;
	private SharedPreferences sharedpref;
	
	private int county_id;
	public ArrayList<Projects>projectsarraylist;
	public List<Projects> projectsdatalist;
	private SwipeRefreshLayout  swiperefreshlayout;
	public ProjectsListViewArrayAdapter projectsadapter;
	private boolean isprojectslistEmpty;
	@Override
	public void onActivityCreated(Bundle paramBundle)
	  {
	    super.onActivityCreated(paramBundle);
	  
	  }

	@Override
	public void onCreate(Bundle paramBundle)
	{
		setRetainInstance(true);
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
			 
			   projectsarraylist= new ArrayList<Projects>();
			
			    
			    if (view != null) {
			        ViewGroup parent = (ViewGroup) view.getParent();
			        if (parent != null)
			            parent.removeView(view);
			    }
			    try {
			        view = paramLayoutInflater.inflate(R.layout.county_projects, paramViewGroup, false);
			    } catch (InflateException e) {
			        /* map is already there, just return view as it is */
			    }
			    // Log.i("ID",""+this.county_id);
			    
			    // Log.i("ID",""+this.county_id);
			   
				 projectsdatalist= MainActivity.UfadhiliDataSource.getAllProjects(county_id);
				
				
				 swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.newscontainer);
				 swiperefreshlayout.setColorScheme(R.color.refreshcolorB);
						swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
					        @Override
					        public void onRefresh() {
					           
					        	isprojectslistEmpty=true;
					            RequestCountyProjectsData task = new RequestCountyProjectsData();
								task.execute();
					        }
					    });	
				 if(projectsdatalist.isEmpty()){
					    isprojectslistEmpty=true;
					    swiperefreshlayout.setRefreshing(true);
					    RequestCountyProjectsData task = new RequestCountyProjectsData();
						task.execute();
				 }else{
					 isprojectslistEmpty=false;
				 }
				 for(int i = 0;i<projectsdatalist.size();i++){
				 Projects projectsfromdb=projectsdatalist.get(i);	
				 Log.i("Project Item",projectsfromdb.getStatus());
				 projectsarraylist.add(projectsfromdb);
			     }
				View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview_nocontent, null, false);
			    final ListView listview = (ListView) view.findViewById(R.id.countyprojectslistview);
			    projectsadapter = new ProjectsListViewArrayAdapter(getActivity(),projectsarraylist);
			    
			    if(projectsdatalist.isEmpty()){
				     listview.addFooterView(footerView);
				}
			    listview.setAdapter(projectsadapter);
			    listview.setOnItemClickListener(new OnItemClickListener() {
			    	 
	                  @Override
	                  public void onItemClick(AdapterView<?> parent, View view,
	                     int position, long id) {
	                 
	                	  Log.i("Project item clicked ",""+position);
	                   Intent myIntent = new Intent(getActivity(), CountyProjectDetail.class);
	                   myIntent.putExtra("projectID",projectsdatalist.get(position).project_id);
	                
	                   startActivity(myIntent);
	                  }
	    
	             }); 
			  return view;
	    }
	 private class RequestCountyProjectsData extends AsyncTask<String, Void, String> {
			private JSONParser jsonParser; 
			 JSONArray projectslist;
			 Projects projectsItem;
			 @Override
			    protected String doInBackground(String... urls) {
			      String response = "";
			    
	            jsonParser=new JSONParser();

	   	     

	  	      // Building Parameters ( you can pass as many parameters as you want)
	  	      List<NameValuePair> params = new ArrayList<NameValuePair>();

	  	     /* params.add(new BasicNameValuePair("name", name));*/
	  	      
	  		      // Getting JSON Object
	  		      try {
	  		    	  
	  		    	projectslist = jsonParser.makeHttpRequest2(MainActivity.base_url+"/api/v1/county/"+county_id+"/projects?format=json", "GET", params);
	  		    	
	  				      try {
		    				       if(projectslist!=null){
		    				    	   
			    				    	 for(int k=0;k<projectslist.length();k++){
			    				    		 JSONObject projectsdata = projectslist.getJSONObject(k);
			    				    		 Log.i("projects item retrieved",projectsdata.getString("first_funding_year"));
			    				    		 String[] imageulr=projectsdata.getString("image").split(Pattern.quote("?"));
			    				    		  projectsItem=new Projects (
			    				    				 projectsdata.getString("project_name"),
			    				    				 projectsdata.getString("summary"),
			    				    				 projectsdata.getString("contractor"),
			    				    				 projectsdata.getString("first_funding_year"),
			    				    				 projectsdata.getString("estimated_cost"),
			    				    				 imageulr[0],
			    				    				 projectsdata.getString("absolute_url"),
			    				    				 projectsdata.getString("location_name"),
			    				    				 projectsdata.getString("status"),
			    				    				 county_id,
			    				    				 Integer.parseInt(projectsdata.getString("id"))
			    				    			 );
			    				    		 
			    				    		//add position to database
			    				    		  MainActivity.UfadhiliDataSource.addProjects(projectsItem);
			    				    	 }
		    				    	
		    				      }
	  						} catch (JSONException e) {
	  							// TODO Auto-generated catch block
	  							e.printStackTrace();
	  						}
	  	
	  		      }catch(IOException e) {
	  		        e.printStackTrace();
	  		      }
			     
			      return response;
			    }

			    @Override
			    protected void onPostExecute(String result) {
			   	 
				 projectsdatalist= MainActivity.UfadhiliDataSource.getAllProjects(county_id);
				 
				 projectsarraylist.clear();
				
				 for(int i = 0;i<projectsdatalist.size();i++){
				 Projects projectsfromdb=projectsdatalist.get(i);	 
				 Log.i("Project Item",projectsfromdb.getStatus());
				 
				 projectsarraylist.add(projectsfromdb);
			     }
		    	
				 if(isprojectslistEmpty){
					 projectsadapter.notifyDataSetChanged();   
					 }
					 // Notify swipeRefreshLayout that the refresh has finished
					 swiperefreshlayout.setRefreshing(false);
			    }
		 }
	 
}
