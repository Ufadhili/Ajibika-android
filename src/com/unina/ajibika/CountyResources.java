package com.unina.ajibika;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CountyResources extends Fragment {
	private static View view;
	private SharedPreferences sharedpref;
	private int county_id;
	private static String type;
	
	private SwipeRefreshLayout  swiperefreshlayout;
	public ArrayList<Plans> plansresourcesarraylist;
	public ArrayList<Budgets> budgetsresourcesarraylist;
	public ArrayList<Bills>   billsresourcesarraylist;
	public ArrayList<Transcripts>   transcriptsresourcesarraylist;
	public ArrayList<OtherDocs>   otherdocsresourcesarraylist;
	public PlansListViewArrayAdapter plansadapter ;
	public BillsListViewArrayAdapter billsadapter ;
	public BudgetsListViewArrayAdapter budgetsadapter ;
	public TranscriptsListViewArrayAdapter transcriptsadapter ;
	public OtherDocsListViewArrayAdapter otherdocsadapter ;
 
	
	public List<Resources> resourcedatalist;
	private boolean    isresourceslistEmpty;
	
	
	public CountyResources() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CountyResources(String type) {
		super();
		CountyResources.type=type;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onActivityCreated(Bundle paramBundle)
	  {
	    super.onActivityCreated(paramBundle);
	  
	  }
	@Override
	public void onSaveInstanceState(Bundle outState) {
	   super.onSaveInstanceState(outState);
	   outState.putString("type",CountyResources.type);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
	    super.onCreate(savedInstanceState);
	    sharedpref = getActivity().getSharedPreferences("UserSession", 0); // 0 - for private mode
		this.county_id=sharedpref.getInt("selected_county_id", 0);
		if( savedInstanceState != null ) {
			CountyResources.type= savedInstanceState .getString("type");
		  }
		
		RequestCountyResourcesData task = new RequestCountyResourcesData();
		task.execute(new String[] {});	
	     
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
			        view = paramLayoutInflater.inflate(R.layout.county_resources, paramViewGroup, false);
			    } catch (InflateException e) {
			        /* map is already there, just return view as it is */
			    }
			  
			    // Log.i("ID",""+this.county_id);
			    
			     resourcedatalist=MainActivity.UfadhiliDataSource.getAllResources(county_id,type);
				
				 swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.newscontainer);
				 swiperefreshlayout.setColorScheme(R.color.refreshcolorA,
					     R.color.refreshcolorB,R.color.refreshcolorA,R.color.refreshcolorB);
						swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
					        @Override
					        public void onRefresh() {
					           
					        	isresourceslistEmpty=true;
					        	RequestCountyResourcesData task = new RequestCountyResourcesData();
								task.execute();
					        }
					    });	
				 if(resourcedatalist.isEmpty()){
					    isresourceslistEmpty=true;
					    swiperefreshlayout.setRefreshing(true);
					    RequestCountyResourcesData task = new RequestCountyResourcesData();
						task.execute();
				 }else{
					 isresourceslistEmpty=false;
				 }
				 View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview_nocontent, null, false);

				 final ListView listview = (ListView) view.findViewById(R.id.countyresourceslistview);
				 
				 
				if(type.equalsIgnoreCase("plan")){ 
				 plansresourcesarraylist= new ArrayList<Plans>();
				
				 for(int i = 0;i<resourcedatalist.size();i++){
				 Resources Resourcesfromdb=resourcedatalist.get(i);	
				 Plans plan=new Plans(
						 Resourcesfromdb.getTitle(),
						 Resourcesfromdb.getSummary(),
						 Resourcesfromdb.getFile_link()
						 );
				 plansresourcesarraylist.add(plan);
				 
			     }
			     
			     plansadapter = new PlansListViewArrayAdapter(getActivity(),plansresourcesarraylist);
			     if(plansresourcesarraylist.isEmpty()){
				     listview.addFooterView(footerView);
				 }
			     listview.setAdapter(plansadapter);
			     
				}else if(type.equalsIgnoreCase("budget")){
					 budgetsresourcesarraylist= new ArrayList<Budgets>();
					
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 Budgets budget=new Budgets(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
							 
					 budgetsresourcesarraylist.add(budget);
					 
				     }
				    
				     budgetsadapter = new BudgetsListViewArrayAdapter(getActivity(),budgetsresourcesarraylist);
				     if(budgetsresourcesarraylist.isEmpty()){
					     listview.addFooterView(footerView);
					 }
				     listview.setAdapter(budgetsadapter);
				     
				}else if(type.equalsIgnoreCase("bill")){
					 billsresourcesarraylist= new ArrayList<Bills>();
					
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 Bills bill=new Bills(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
							 
					 billsresourcesarraylist.add(bill);
					 
				     }
				    
				     billsadapter = new BillsListViewArrayAdapter(getActivity(),billsresourcesarraylist);
				     if(billsresourcesarraylist.isEmpty()){
					     listview.addFooterView(footerView);
					 }
				     listview.setAdapter(billsadapter);
				 }else if(type.equalsIgnoreCase("transcript")){
					 transcriptsresourcesarraylist= new ArrayList<Transcripts >();
					
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 Transcripts transcript=new Transcripts(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
							 
					 transcriptsresourcesarraylist.add(transcript);
					 
				     }
				    
				     transcriptsadapter = new TranscriptsListViewArrayAdapter(getActivity(),transcriptsresourcesarraylist);
				     if(transcriptsresourcesarraylist.isEmpty()){
					     listview.addFooterView(footerView);
					 }
				     listview.setAdapter(transcriptsadapter);
				}else if(type.equalsIgnoreCase("other")){
					otherdocsresourcesarraylist= new ArrayList<OtherDocs >();
						
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 OtherDocs otherdocs=new OtherDocs(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
							 
					 otherdocsresourcesarraylist.add(otherdocs);
					 
				     }
				    
					 otherdocsadapter = new OtherDocsListViewArrayAdapter(getActivity(),otherdocsresourcesarraylist);
				     if(otherdocsresourcesarraylist.isEmpty()){
					     listview.addFooterView(footerView);
					 }
				     listview.setAdapter(otherdocsadapter);
					}
			  return view;
	    }
	 private class RequestCountyResourcesData extends AsyncTask<String, Void, String> {
			private JSONParser jsonParser; 
			 JSONArray Resourceslist;
			
			 @Override
			    protected String doInBackground(String... urls) {
			      String response = "";
			    
	            jsonParser=new JSONParser();

	   	     

	  	      // Building Parameters ( you can pass as many parameters as you want)
	  	      List<NameValuePair> params = new ArrayList<NameValuePair>();

	  	     /* params.add(new BasicNameValuePair("name", name));*/
	  	      
	  		      // Getting JSON Object
	  		      try {
	  		    	  
	  		    	  
	  		    	  
	  		    	  Resourceslist = jsonParser.makeHttpRequest2(MainActivity.base_url+"/api/v1/county/"+county_id+"/documents/?type="+type+"&format=json", "GET", params);
	  		    	
	  				      try {
		    				       if(Resourceslist!=null){
		    				    	  
			    				    	 for(int k=0;k<Resourceslist.length();k++){
			    				    		 JSONObject Resourcesdata = Resourceslist.getJSONObject(k);
			    				    		 Log.i("Resources item retrieved",Resourcesdata.getString("title"));
			    				    		 String[] fileulr=Resourcesdata.getString("file").split(Pattern.quote("?"));
			    				    		Resources resourcesItem=new Resources(
			    				    				Resourcesdata.getString("title"),
			    				    				Resourcesdata.getString("summary"),
			    				    				type,
			    				    				fileulr[0],
			    				    				Integer.parseInt(Resourcesdata.getString("id")),
			    				    				Integer.parseInt(Resourcesdata.getString("county_id"))
			    				    				);
			    				    		
			    				    		//add position to database
			    				    		MainActivity.UfadhiliDataSource.addResources(resourcesItem);
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
			    
			     resourcedatalist= MainActivity.UfadhiliDataSource.getAllResources(county_id,type);
				
				
				
				 if(type.equalsIgnoreCase("plan")){
					 plansresourcesarraylist.clear(); 
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 Plans plan=new Plans(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
					 plansresourcesarraylist.add(plan);
					 
				     }
					 if(isresourceslistEmpty){
					 plansadapter.notifyDataSetChanged();
					 }
				 }else if(type.equalsIgnoreCase("budget")){
					 
					 budgetsresourcesarraylist.clear();
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 Budgets budget=new Budgets(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
							 
					 budgetsresourcesarraylist.add(budget);
					 
				     }
					 if(isresourceslistEmpty){
				     budgetsadapter.notifyDataSetChanged();
					 }
				    
				     
				}else if(type.equalsIgnoreCase("bill")){
					
					billsresourcesarraylist.clear();
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 Bills bill=new Bills(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
							 
					 billsresourcesarraylist.add(bill);
					 
				     }
					 if(isresourceslistEmpty){
				     billsadapter.notifyDataSetChanged();
					 }
				     
				 }else if(type.equalsIgnoreCase("transcript")){
					
					 transcriptsresourcesarraylist.clear();
					 for(int i = 0;i<resourcedatalist.size();i++){
					 Resources Resourcesfromdb=resourcedatalist.get(i);	
					 Transcripts transcript=new Transcripts(
							 Resourcesfromdb.getTitle(),
							 Resourcesfromdb.getSummary(),
							 Resourcesfromdb.getFile_link()
							 );
							 
					 transcriptsresourcesarraylist.add(transcript);
					 
				     }
					 if(isresourceslistEmpty){
				     transcriptsadapter.notifyDataSetChanged(); 
					 }
				 }else if(type.equalsIgnoreCase("other")){
						
						 otherdocsresourcesarraylist.clear();
						 for(int i = 0;i<resourcedatalist.size();i++){
						 Resources Resourcesfromdb=resourcedatalist.get(i);	
						 OtherDocs otherdocs=new OtherDocs(
								 Resourcesfromdb.getTitle(),
								 Resourcesfromdb.getSummary(),
								 Resourcesfromdb.getFile_link()
								 );
								 
						 otherdocsresourcesarraylist.add(otherdocs);
						 
					     }
						 if(isresourceslistEmpty){
							 otherdocsadapter.notifyDataSetChanged(); 
						 }
						}
				// Notify swipeRefreshLayout that the refresh has finished
				 swiperefreshlayout.setRefreshing(false);
			    }
		 }
	 

}
