package com.unina.ajibika;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.image.SmartImageView;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;



public class AboutAjibika extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener{

	public static final String API_KEY = "AIzaSyDN7NgaPYSe5QrE-19d4udqsXlHGn8CEC0";
	public static final String VIDEO_ID = "in-f4eMfAFQ";
    
	private SmartImageView ajibika_image;
	private SwipeRefreshLayout swiperefreshlayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_ajibika);
		ajibika_image= (SmartImageView) findViewById(R.id.ajibikaImage);
		swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.aboutajibikacontainer);
		swiperefreshlayout.setColorScheme(R.color.refreshcolorB);
				swiperefreshlayout.setOnRefreshListener(new OnRefreshListener() {
			        @Override
			        public void onRefresh() {
			       
			            RequestAjibikaData task = new RequestAjibikaData();
			   		    task.execute();
			        }
			    });
		 RequestAjibikaData task = new RequestAjibikaData();
		 task.execute();
		 
		 YouTubePlayerView youTubePlayerView = (YouTubePlayerView)findViewById(R.id.youtube_view);
	     youTubePlayerView.initialize(API_KEY, this);
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
	 private class RequestAjibikaData extends AsyncTask<String, Void, String> {
			private JSONParser jsonParser; 
		    private String imageurl="";
			private String videourl="";
			
		    @Override
		    protected void onPreExecute() {
		    	swiperefreshlayout.setRefreshing(true);
		    	
		    }
		    
			 @Override
			    protected String doInBackground(String... urls) {
			      String response = "";
			    
	            jsonParser=new JSONParser();

	   	     

	  	      // Building Parameters ( you can pass as many parameters as you want)
	  	      List<NameValuePair> params = new ArrayList<NameValuePair>();

	  	    
	  	      
	  		      // Getting JSON Object
	  		      try {
	  		    	  
	  		    	
	  		      JSONObject aboutajibika = jsonParser.makeHttpRequest(MainActivity.base_url+"/api/v1/ajibika/?format=json", "GET", params);
	  				   try{
		    				       if(aboutajibika!=null){
		    				    	
		  					    		    JSONArray ajibikadata = aboutajibika.getJSONArray("objects");
		  					    		
	  					    			    JSONObject obj = ajibikadata.getJSONObject(0);
	  					    			   
	  					    			    JSONArray aboutimages = obj.getJSONArray("images");
	  					    			    
	  					    			    JSONObject image = aboutimages.getJSONObject(0);
	  					    			    
	  					    			   // JSONArray aboutvideos = obj.getJSONArray("videos");
		  					    		   
	  					    			   // JSONObject video = aboutvideos.getJSONObject(0);
		  					    			    
	  					    			    imageurl=image.getString("file");
	  					    			    
		  					    			//videourl=video.getString("youtube_link");
	  					    			   
		  					    	
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
			    	ajibika_image.setImageUrl(imageurl);
			    	
			    	swiperefreshlayout.setRefreshing(false);
			    }
		 }
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,
	   boolean wasRestored) {
	  if (!wasRestored) {
	        player.cueVideo(VIDEO_ID);
	      }
	}
	

}
