package com.unina.ajibika;



import com.loopj.android.image.SmartImageView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CountyProjectDetail extends ActionBarActivity {
	
	   private SharedPreferences sharedpref;
		
		private int county_id,project_id,position_id;
		private TextView project_title,project_summary;
		 SmartImageView  project_image ;
		private RelativeLayout relativeMain;
		private WebView webDisqus;
		private String htmlComments;
		private TextView project_year;
		private TextView project_status;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			
			super.onCreate(savedInstanceState);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setTitle("Projects");
		    sharedpref = this.getSharedPreferences("UserSession", 0); // 0 - for private mode
			this.county_id=sharedpref.getInt("selected_county_id", 0);
			
			Intent myIntent = getIntent(); // gets the previously created intent
			this.project_id=myIntent.getIntExtra("projectID", 0); 
			
			
			    
			    if(this.county_id>0){
			    	
			     final Projects project= MainActivity.UfadhiliDataSource.getProjectsItem(this.project_id);
			     Log.i("The ids sent for query",project.getImage());
			     
			     
			     
			     if(project!=null){
			    	 if(project.getImage().equalsIgnoreCase("null")){
			    		 setContentView(R.layout.county_project_withoutimage);
			    	 }else{
				    	 setContentView(R.layout.county_project);
			    	 }
			       webDisqus = (WebView) findViewById(R.id.countyprojectscomments);	
			       project_title=(TextView)findViewById(R.id.projectTitle);
				   project_summary=(TextView)findViewById(R.id.projectSummary);
				   project_year=(TextView)findViewById(R.id.project_year_funded);
				   project_status=(TextView)findViewById(R.id.project_status);
			     if(project.getImage().equalsIgnoreCase("null")){
			    	 
					    project_title.setText(project.getTitle());  
					    project_summary.setText(Html.fromHtml(project.getSummary()));
					    project_year.setText(project.getYear_funded());  
					    project_status.setText(project.getStatus());
						
			    	
			     }else{
			    	
			    	   //Log.i("ID",""+this.county_id);
					   project_image = (SmartImageView)findViewById(R.id.projectImage);
					   project_title.setText(project.getTitle());  
					   project_summary.setText(Html.fromHtml(project.getSummary()));
					   project_year.setText(project.getYear_funded());  
					   project_status.setText(project.getStatus());
			           project_image.setImageUrl(project.getImage());
			         
			     }
			        //set up disqus
					WebSettings websettings = webDisqus.getSettings();
					websettings.setJavaScriptEnabled(true);
					websettings.setBuiltInZoomControls(true);
					//webDisqus.requestFocusFromTouch();
					webDisqus.setWebViewClient(new WebViewClient());
					webDisqus.setWebChromeClient(new WebChromeClient());
					webDisqus.loadUrl("http://ajibika.org/disqus/?identifier="+cleanAbsoluteUrl(MainActivity.base_url+project.getAbsolute_url()));
			        ImageButton projectitemShareButton = (ImageButton)findViewById(R.id.projectitemsharebutton);
			        projectitemShareButton.setOnClickListener(new OnClickListener() {					 
						@Override
						public void onClick(View arg0) {
			 
							Intent share=new Intent(android.content.Intent.ACTION_SEND);

						    share.setType("text/plain");
						    share.putExtra(android.content.Intent.EXTRA_SUBJECT,project.getTitle());
						    share.putExtra(android.content.Intent.EXTRA_TEXT,"Follow the link : "+MainActivity.base_url+project.getAbsolute_url()+" to read more");
						    startActivity(Intent.createChooser(share,"Share via"));
			 			}
			 		});
			     }
			   }
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
		public String getHtmlComment(String idPost,String url, String shortName) {
				
				return "<div id='disqus_thread'></div>"
						+ "<script type='text/javascript'>"
						+ "var disqus_url = '"
						+ url
						+ "';"
						+ "var disqus_shortname = '"
						+ shortName
						+ "';"
						+ " (function() { var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;"
						+ "dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';"
						+ "(document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq); })();"
						+ "</script>";
		 }
		 public String cleanAbsoluteUrl(String str) {
     	    if (str.length() > 0 && str.charAt(str.length()-1)=='/'){
     	      str = str.substring(0, str.length()-1);
     	    }
     	    return str;
     	}
	         
}

	
