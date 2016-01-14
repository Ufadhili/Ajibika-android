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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CountyPersonDetail extends ActionBarActivity {
	
	    private SharedPreferences sharedpref;
		
		private int county_id,person_id,position_id;
		private TextView person_title,person_name,person_summary_title,person_summary;
		 SmartImageView person_image ;
		private TextView person_info_title;
		private TextView person_info;
		private WebView webDisqus;
		private String htmlComments;
		private Person person;
		private ImageView personmoreinfoicon;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			/*ScrollView scrollView = (ScrollView)findViewById(R.id.personscroll);
			scrollView.smoothScrollTo(0,(findViewById(R.id.personrelativescroll)).getTop());*/
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			sharedpref = this.getSharedPreferences("UserSession", 0); // 0 - for private mode
			this.county_id=sharedpref.getInt("selected_county_id", 0);
			
			Intent myIntent = getIntent(); // gets the previously created intent
			this.person_id=myIntent.getIntExtra("personID", 0); 
			this.position_id=myIntent.getIntExtra("positionID", 0); 
			
			 if(this.county_id>0){
			   person= MainActivity.UfadhiliDataSource.getPerson( this.county_id,this.position_id,this.person_id);
			 }
			 if(person!=null){
			   setContentView(R.layout.county_person);
			 }else{
			  setContentView(R.layout.county_no_person); 
			 }
			
			
		    
			
			
			
			
			 
			  // Log.i("ID",""+this.county_id);
			  person_name=(TextView)findViewById(R.id.PersonName);
			    person_summary_title=(TextView)findViewById(R.id.personSummarytitle);
			    person_summary=(TextView)findViewById(R.id.personSummary);
			    person_info_title=(TextView)findViewById(R.id.personmoreinfoSummarytitle);
			    person_info=(TextView)findViewById(R.id.personMoreinfoSummary);
			    person_image = (SmartImageView)findViewById(R.id.personImage);
			    
			    
			    if(this.county_id>0){
			    	
			     if(person!=null){
			     person_name.setText(person.getTitle()+" "+person.getLegal_name());  
			     person_summary_title.setText("About "+person.getLegal_name());
			     person_summary.setText(Html.fromHtml(person.getSummary()));
			     person_info_title.setText("More about "+person.getLegal_name());
			     person_info.setText(Html.fromHtml(person.getBiography()));
			     person_image.setImageUrl(person.getImage_url());
			     
			     
			     RelativeLayout aboutheader = (RelativeLayout)findViewById(R.id.aboutheader);
			     RelativeLayout contentrelativelayout = (RelativeLayout)findViewById(R.id.contentrelativelayout);
			     RelativeLayout moreinformationheader = (RelativeLayout)findViewById(R.id.moreinfoheader);
			     

			     if(person.getSummary().isEmpty() && person.getBiography().isEmpty()){
		        	 moreinformationheader.setVisibility(View.GONE);
		    	     person_info_title.setVisibility(View.GONE);
		    	     person_info.setVisibility(View.GONE);
		    	   
		    	     aboutheader.setVisibility(View.GONE);
		    	     person_summary_title.setVisibility(View.GONE);
		    	     person_summary.setVisibility(View.GONE);
		    	    
			         contentrelativelayout.setVisibility(View.GONE);
			     }
			     if(person.getSummary().isEmpty()){
			     aboutheader.setVisibility(View.GONE);
			     person_summary_title.setVisibility(View.GONE);
			     person_summary.setVisibility(View.GONE);
			    
			     }
			     if( person.getBiography().isEmpty()){
			     moreinformationheader.setVisibility(View.GONE);
			     person_info_title.setVisibility(View.GONE);
			     person_info.setVisibility(View.GONE);
			    
			     }
			     
			     
			     
			     
			     
			     
			     htmlComments = getHtmlComment(MainActivity.base_url+"/person/"+person.getSlug()+"/","ajibika");
			        webDisqus = (WebView) findViewById(R.id.countypersoncomments);
					// set up disqus
					WebSettings websettings = webDisqus.getSettings();
					websettings.setJavaScriptEnabled(true);
					websettings.setBuiltInZoomControls(true);
					//webDisqus.requestFocusFromTouch();
					webDisqus.setWebViewClient(new WebViewClient());
					webDisqus.setWebChromeClient(new WebChromeClient());
					webDisqus.loadData(htmlComments, "text/html", null);
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
		 public String getHtmlComment(String url, String shortName) {
				
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
	        	    if (str.length() > 0 && str.charAt(str.length()-1)=='/') {
	        	      str = str.substring(0, str.length()-1);
	        	    }
	        	    return str;
	        	}
	
}

	
