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
import android.widget.TextView;

public class CountyPerson extends Fragment {
	
	private SharedPreferences sharedpref;
	private static View view;
	
	private int county_id,position_id;
	private TextView person_name,person_summary_title,person_summary;
	 SmartImageView person_image ;
	private TextView person_info_title;
	private TextView person_info;
	private WebView webDisqus;
	private String htmlComments;
	private ImageView personmoreinfoicon;
	
	public CountyPerson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CountyPerson(int position_id) {
		super();
        this.position_id=position_id;
		// TODO Auto-generated constructor stub
	}
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
			
			 view = paramLayoutInflater.inflate(R.layout.county_person, paramViewGroup, false);
			   
	   
	    
	            if(this.county_id>0){
	             Person person= MainActivity.UfadhiliDataSource.getPerson( this.county_id,position_id);
	            if(person!=null){
	    	 
	    	    
			    person_name=(TextView)view.findViewById(R.id.PersonName);
			    person_summary_title=(TextView)view.findViewById(R.id.personSummarytitle);
			    person_summary=(TextView)view.findViewById(R.id.personSummary);
			    person_info_title=(TextView)view.findViewById(R.id.personmoreinfoSummarytitle);
			    person_info=(TextView)view.findViewById(R.id.personMoreinfoSummary);
			    person_image = (SmartImageView)view.findViewById(R.id.personImage);
			   
	    	 
	    	 
	    	 
		     person_name.setText(person.getTitle()+" "+person.getLegal_name());  
		     person_summary_title.setText("About "+person.getLegal_name());
		     person_summary.setText(Html.fromHtml(person.getSummary()));
		     person_info_title.setText("More about "+person.getLegal_name());
		     person_info.setText(Html.fromHtml(person.getBiography()));
		     person_image.setImageUrl(person.getImage_url());
		     
		     
		     RelativeLayout aboutheader = (RelativeLayout)view.findViewById(R.id.aboutheader);
		     RelativeLayout contentrelativelayout = (RelativeLayout)view.findViewById(R.id.contentrelativelayout);
		     RelativeLayout moreinformationheader = (RelativeLayout)view.findViewById(R.id.moreinfoheader);
		  
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
		    
		        webDisqus = (WebView) view.findViewById(R.id.countypersoncomments);
				// set up disqus
				WebSettings websettings = webDisqus.getSettings();
				websettings.setJavaScriptEnabled(true);
				websettings.setBuiltInZoomControls(true);
				//webDisqus.requestFocusFromTouch();
				webDisqus.setWebViewClient(new WebViewClient());
				webDisqus.setWebChromeClient(new WebChromeClient());
				webDisqus.loadUrl("http://ajibika.org/disqus/?identifier="+MainActivity.base_url+"/person/"+person.getSlug()+"/");
		    
		  }else{
				    try {
				        view = paramLayoutInflater.inflate(R.layout.county_no_person, paramViewGroup, false);
				    } catch (InflateException e) {
				        /* map is already there, just return view as it is */
				    }
		  }
	       
	    }
		return view;
	    
		
     
}

}

	
