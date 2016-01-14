package com.unina.ajibika;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.loopj.android.image.SmartImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CountyNewsDetail extends ActionBarActivity {
	
	   private SharedPreferences sharedpref;
		
		private int county_id,news_id,position_id;
		private TextView news_title,news_summary;
		 SmartImageView  news_image ;
		private RelativeLayout relativeMain;
		private WebView webDisqus;
		private ImageButton newsitemShareButton;
		private TextView published;
		
		@SuppressLint("SetJavaScriptEnabled")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setTitle("News");
		    sharedpref = this.getSharedPreferences("UserSession", 0); // 0 - for private mode
			this.county_id=sharedpref.getInt("selected_county_id", 0);
			
			Intent myIntent = getIntent(); // gets the previously created intent
			this.news_id=myIntent.getIntExtra("newsID", 0); 
			
			
			    
			    String htmlComments = null;
			    
			    if(this.county_id>0){
			    	
			     final News news= MainActivity.UfadhiliDataSource.getNewsItem(this.news_id);
			     Log.i("news image url",news.getImage_url());
			     if(news!=null){
			    	 Date date =new Date();
			 	    // it comes out like this 2013-08-31 15:55:22 so adjust the date format
			 		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss",Locale.US);
			 		
			 	    try {
			 			date = df.parse(news.getPublication_date());
			 		} catch (ParseException e) {
			 			// TODO Auto-generated catch block
			 			e.printStackTrace();
			 		}
			 	  
			 	    long publishedtime = date.getTime();
			 	    CharSequence timePassedString = DateUtils.getRelativeTimeSpanString(publishedtime, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);	 
			 	    
			     if(news.getImage_url().equalsIgnoreCase("null")){
			    	    setContentView(R.layout.county_news_item_withoutimage);
			    	  // Log.i("ID",""+this.county_id);
			    	    news_title=(TextView)findViewById(R.id.newsTitle);
					    published=(TextView)findViewById(R.id.newsitempublished);
					    news_summary=(TextView)findViewById(R.id.newsSummary);
					    newsitemShareButton=(ImageButton)findViewById(R.id.newsitemsharebutton);
					    news_title.setText(news.getTitle());
					    published.setText(timePassedString);  
					    news_summary.setText(Html.fromHtml(news.getMessage()));
					    
			     }else{
			    	    setContentView(R.layout.county_news_item);
			    	 // Log.i("ID",""+this.county_id);
			    	    news_title=(TextView)findViewById(R.id.newsTitle);
					    published=(TextView)findViewById(R.id.newsitempublished);
					    news_summary=(TextView)findViewById(R.id.newsSummary);
					    newsitemShareButton=(ImageButton)findViewById(R.id.newsitemsharebutton);
					    news_image = (SmartImageView)findViewById(R.id.newsImage);
					    news_title.setText(news.getTitle()); 
					    published.setText(timePassedString); 
					    news_summary.setText(Html.fromHtml(news.getMessage()));
			            news_image.setImageUrl(news.getImage_url());
			           
			           
			      }
			     Log.i("URL passed to disqus is",MainActivity.base_url+news.getAbsolute_url());
			        //
			        webDisqus = (WebView) findViewById(R.id.newscomments);
					// set up disqus
					WebSettings websettings = webDisqus.getSettings();
					websettings.setJavaScriptEnabled(true);
					websettings.setBuiltInZoomControls(true);
					//webDisqus.requestFocusFromTouch();
					webDisqus.setWebViewClient(new WebViewClient());
					webDisqus.setWebChromeClient(new WebChromeClient());
					webDisqus.loadUrl("http://ajibika.org/disqus/?identifier="+cleanAbsoluteUrl(MainActivity.base_url+news.getAbsolute_url()));
					
					
					newsitemShareButton.setOnClickListener(new OnClickListener() {
						 
						@Override
						public void onClick(View arg0) {
			 
                              //Toast.makeText(CountyNewsDetail.this, Html.fromHtml(news.getMessage().substring(0, 200)+" Link: "+news.getAbsolute_url()),Toast.LENGTH_SHORT).show();
                              Intent share=new Intent(android.content.Intent.ACTION_SEND);

                              share.setType("text/plain");
                              share.putExtra(android.content.Intent.EXTRA_SUBJECT,news.getTitle());
                              share.putExtra(android.content.Intent.EXTRA_TEXT,"Follow the link : "+MainActivity.base_url+news.getAbsolute_url()+" to read more");
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
          
          public String cleanAbsoluteUrl(String str) {
        	    if (str.length() > 0 && str.charAt(str.length()-1)=='/') {
        	      str = str.substring(0, str.length()-1);
        	    }
        	    return str;
        	}
	
}

	
