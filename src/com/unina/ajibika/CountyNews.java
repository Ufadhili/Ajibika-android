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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CountyNews extends Fragment {
	private static View view;
	private SharedPreferences sharedpref;
	private int county_id;
	
	public ArrayList<News> newsarraylist;
	public NewsListViewArrayAdapter newsadapter ;
	public List<News> newsdatalist;
	private boolean isnewslistEmpty;
	private SwipeRefreshLayout  swiperefreshlayout;
	public boolean loadingMore=false;
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
		
		  // /You will setup the action bar with pull to refresh layout
		
	     
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
			    newsarraylist= new ArrayList<News>();
			  	    
			    if (view != null) {
			        ViewGroup parent = (ViewGroup) view.getParent();
			        if (parent != null)
			            parent.removeView(view);
			    }
			    try {
			        view = paramLayoutInflater.inflate(R.layout.county_news, paramViewGroup, false);
			    } catch (InflateException e) {
			        /* map is already there, just return view as it is */
			    }
			    // Log.i("ID",""+this.county_id);
				 
				 newsdatalist= MainActivity.UfadhiliDataSource.getAllNews(county_id);
				
				 swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.newscontainer);
				 swiperefreshlayout.setColorScheme(R.color.refreshcolorB);
						swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
					        @Override
					        public void onRefresh() {
					           
					            isnewslistEmpty=true;
					            RequestCountyNewsData task = new RequestCountyNewsData();
								task.execute();
					        }
					    });	
				 if(newsdatalist.isEmpty()){
					    isnewslistEmpty=true;
					    swiperefreshlayout.setRefreshing(true);
					    RequestCountyNewsData task = new RequestCountyNewsData();
						task.execute();
				 }else{
					 isnewslistEmpty=false;
				 }
				
				 for(int i = 0;i<newsdatalist.size();i++){
				 News newsfromdb=newsdatalist.get(i);	 
				  newsarraylist.add(newsfromdb);
			     }
				View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview_nocontent, null, false);
			    final ListView listview = (ListView) view.findViewById(R.id.countynewslistview);
			     newsadapter = new NewsListViewArrayAdapter(getActivity(),newsarraylist);
			   
			     if(newsarraylist.isEmpty()){
			     listview.addFooterView(footerView);
			     }
			     listview.setAdapter(newsadapter);
			     listview.setOnItemClickListener(new OnItemClickListener() {
			    	 
	                  @Override
	                  public void onItemClick(AdapterView<?> parent, View view,
	                     int position, long id) {
	                 
	                	  Log.i("News item clicked ",""+position);
	                   Intent myIntent = new Intent(getActivity(), CountyNewsDetail.class);
	                   myIntent.putExtra("newsID",newsdatalist.get(position).news_id);
	                
	                   startActivity(myIntent);
	                  }
	    
	             }); 
			    
			  
			
			  return view;
	    }
	/* private class LoadMoreItems extends AsyncTask<String, Void, String> {
		 @Override
	     protected void onPreExecute() {
			 loadingMore = true;
		 }
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			loadingMore = false;
		}
	 }*/
	 
	 private class RequestCountyNewsData extends AsyncTask<String, Void, String> {
			private JSONParser jsonParser; 
			 JSONArray newslist;
			 News newsItem;
			 
			 @Override
			    protected String doInBackground(String... urls) {
			      String response = "";
			    
	            jsonParser=new JSONParser();

	   	     

	  	      // Building Parameters ( you can pass as many parameters as you want)
	  	      List<NameValuePair> params = new ArrayList<NameValuePair>();

	  	     /* params.add(new BasicNameValuePair("name", name));*/
	  	      
	  		      // Getting JSON Object
	  		      try {
	  		    	  
	  		    	   newslist = jsonParser.makeHttpRequest2(MainActivity.base_url+"/api/v1/county/"+county_id+"/news?format=json", "GET", params);
	  		    	
	  				      try {
		    				       if(newslist!=null){
		    				    	   
			    				    	 for(int k=0;k<newslist.length();k++){
			    				    		 JSONObject newsdata = newslist.getJSONObject(k);
			    				    		 Log.i("News item retrieved",newsdata.getString("image"));
			    				    		 String[] imageulr=newsdata.getString("image").split(Pattern.quote("?"));
			    				    		 newsItem=new News(
			    				    				 county_id,
			    				    				 Integer.parseInt(newsdata.getString("id")),//Integer.parseInt(newsdata.getString("news_id")),
			    				    				 newsdata.getString("title"),
			    				    				 newsdata.getString("message"),
			    				    				 newsdata.getString("publication_date"),
			    				    				 imageulr[0],
			    				    				 newsdata.getString("absolute_url")
			    				    			 );
			    				    		 
			    				    		//add position to database
			    				    		 MainActivity.UfadhiliDataSource.addNews(newsItem);
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
			   	 
				 newsdatalist= MainActivity.UfadhiliDataSource.getAllNews(county_id);
				
				 newsarraylist.clear();
				
				 for(int i = 0;i<newsdatalist.size();i++){
				 News newsfromdb=newsdatalist.get(i);	 
				 Log.i("News Item",newsfromdb.getTitle());
				 
				 newsarraylist.add(newsfromdb);
			     }
				 if(isnewslistEmpty){
		    	   newsadapter.notifyDataSetChanged();  
				 }
				 // Notify swipeRefreshLayout that the refresh has finished
				 swiperefreshlayout.setRefreshing(false);
				
			    }
		 }
	 

}
