package com.unina.ajibika;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

public class DevolutionResources extends ActionBarActivity {
    public DevolutioDocsListViewArrayAdapter devolutionresouresadapter ;
    private SwipeRefreshLayout  swiperefreshlayout;
	private boolean    isresourceslistEmpty;
  
	public List<Resources> resourcedatalist;
	public ArrayList<DevolutionDocs>   devolutionresourcesarraylist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devolutionresources);
		getSupportActionBar().setTitle("Devolution Resources");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	
		// Log.i("ID",""+this.county_id);
	     
	     resourcedatalist= MainActivity.UfadhiliDataSource.getAllResources();
		
		
		
		swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.devolutioncontainer);
		 swiperefreshlayout.setColorScheme(R.color.refreshcolorA,
			     R.color.refreshcolorB,R.color.refreshcolorA,R.color.refreshcolorB);
				swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
			        @Override
			        public void onRefresh() {
			           
			        	isresourceslistEmpty=false;
			        	RequestdevolutionResourcesData task = new RequestdevolutionResourcesData();
						task.execute();
			        }
			    });	
		 if(resourcedatalist.isEmpty()){
			    isresourceslistEmpty=true;
			    swiperefreshlayout.setRefreshing(true);
			    RequestdevolutionResourcesData task = new RequestdevolutionResourcesData();
				task.execute();
		 }else{
			 isresourceslistEmpty=false;
		 }
		
		 devolutionresourcesarraylist= new ArrayList<DevolutionDocs>();
		 final ListView listview = (ListView) findViewById(R.id.devolutionresourceslistview);	
		 for(int i = 0;i<resourcedatalist.size();i++){
		 Resources Resourcesfromdb=resourcedatalist.get(i);	
		 DevolutionDocs devolutiondocs=new DevolutionDocs(
				 Resourcesfromdb.getTitle(),
				 Resourcesfromdb.getSummary(),
				 Resourcesfromdb.getFile_link()
				 );
				 
		 devolutionresourcesarraylist.add(devolutiondocs);
		 
	     }
	    
		 devolutionresouresadapter = new DevolutioDocsListViewArrayAdapter(this,devolutionresourcesarraylist);
	     listview.setAdapter(devolutionresouresadapter); 
		 
		 
		 
		 
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        finish();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	 private class RequestdevolutionResourcesData extends AsyncTask<String, Void, String> {
			 private JSONParser jsonParser; 
			 JSONObject Resourceslist;
			
			
			 @Override
			    protected String doInBackground(String... urls) {
			      String response = "";
			    
	            jsonParser=new JSONParser();

	   	     

	  	      // Building Parameters ( you can pass as many parameters as you want)
	  	      List<NameValuePair> params = new ArrayList<NameValuePair>();

	  	     /* params.add(new BasicNameValuePair("name", name));*/
	  	      
	  		      // Getting JSON Object
	  		      try {
	  		    	  
	  		    	  
	  		    	  
	  		    	  Resourceslist = jsonParser.makeHttpRequest(MainActivity.base_url+"/api/v1/ajibika/?format=json", "GET", params);
	  		    	
	  				      try {
		    				       if(Resourceslist!=null){
		    				    	   
		    				    	    JSONArray ajibikadata = Resourceslist.getJSONArray("objects");
		  					    		
 					    			    JSONObject obj = ajibikadata.getJSONObject(0);
 					    			   
 					    			    JSONArray ajibikadocuments = obj.getJSONArray("documents");
		    				    	 
			    				    	 for(int k=0;k<ajibikadocuments.length();k++){
			    				    		 JSONObject Resourcesdata = ajibikadocuments.getJSONObject(k);
			    				    		 Log.i("Resources item retrieved",Resourcesdata.getString("title"));
			    				    		 Resources resourcesItem=new Resources(
			    				    				Resourcesdata.getString("title"),
			    				    				Resourcesdata.getString("summary"),
			    				    				"general",
			    				    				Resourcesdata.getString("file"),
			    				    				Integer.parseInt(Resourcesdata.getString("id")),
			    				    				0
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
			    
			    	   
					     resourcedatalist= MainActivity.UfadhiliDataSource.getAllResources();
						 
					
							
						     devolutionresourcesarraylist.clear();
							 for(int i = 0;i<resourcedatalist.size();i++){
							 Resources Resourcesfromdb=resourcedatalist.get(i);	
							 DevolutionDocs devolutiondocs=new DevolutionDocs(
									 Resourcesfromdb.getTitle(),
									 Resourcesfromdb.getSummary(),
									 Resourcesfromdb.getFile_link()
									 );
									 
							 devolutionresourcesarraylist.add(devolutiondocs);
							
							 if(isresourceslistEmpty){
								 devolutionresouresadapter.notifyDataSetChanged(); 
							 }
							}
							 swiperefreshlayout.setRefreshing(false);
			    }
		 }
	 

}
