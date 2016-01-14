package com.unina.ajibika;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

@SuppressLint("CommitPrefEdits")
public class MainActivity  extends ActionBarActivity  {
	Spinner spinner ;
	public ConnectionDetector connectiondetector ;
	public static String base_url="http://ajibika.org";
	public static String base_file_url="https://ajibika.s3.amazonaws.com/aji-media/";
	public static String county_url=base_url+"/api/v1/county/?format=json";
	public static final String DEVELOPER_KEY = "AIzaSyB2mlk5n6wS33p3oy6cWed9SE9CAALhEl0";
    public List<County> countylist;
    public List<Position> positionlist;
    public List<Organisation> organisationlist;
    public SharedPreferences sharedpref;
    public Context context;
    public static  ArrayAdapter<String> adapter;
    public static UfadhiliDatabaseHelper  UfadhiliDataSource;
    public RequestCountyListData RequestCountyListDatatask;
    public List<String> spinnerArray ;
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		context=this;
		sharedpref = context.getSharedPreferences("UserSession", 0); // 0 - for private mode
		connectiondetector = new ConnectionDetector(getApplicationContext());
		UfadhiliDataSource=new UfadhiliDatabaseHelper(this);
		
		//check if a preference is set and go to the home screen of the county.
		Log.i("Current County",""+sharedpref.getInt("selected_county_id", 0));
		if(sharedpref.getInt("selected_county_id", 0)>0){
			Intent mIntent=new Intent(MainActivity.this,HomeActivity.class);
            startActivity(mIntent);
		}else{
			if(connectiondetector.isConnectingToInternet()){// true or false
			    
				RequestCountyListDatatask = new RequestCountyListData();
				RequestCountyListDatatask.execute(new String[] {});	
			}
		}
		
		
		populatespinnerarray();
		adapter = new CountySpinnerAdapter(this,R.layout.county_spinner_layout, spinnerArray);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner = (Spinner) findViewById(R.id.countyspinner);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>0){
                	County county=countylist.get(position-1);
                	
            	    Editor editor = sharedpref.edit();	
            	    Log.i("County id",""+county.getID());
            	    editor.putInt("selected_county_id", county.getID());
            	    editor.putInt("selected_county_index", position-1);
            	    editor.commit(); // commit changes
            	   
            	// your code here
                Intent mIntent=new Intent(MainActivity.this,HomeActivity.class);
                mIntent.putExtra("lang", parentView.getItemIdAtPosition(position));
                startActivity(mIntent);
                spinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            	Log.i("Name for spinner",""+parentView.getItemIdAtPosition(0));
            }

        });
	}
	@SuppressLint("NewApi")
	@Override
	protected void onResume()
	{
	    super.onResume();

	    if (Build.VERSION.SDK_INT < 16)
	    {
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        // Hide the action bar
	        getSupportActionBar().hide();
	    }
	    else
	    {
	        // Hide the status bar
	        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
	        //Hide the action bar
	        getSupportActionBar().hide();
	    }
	}
	
	@Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

            if (RequestCountyListDatatask != null) {
            	RequestCountyListDatatask.cancel(true);
            }


    }
	public void populatespinnerarray(){
		
		 countylist= UfadhiliDataSource.getAllCounties();
		
		 spinnerArray =  new ArrayList<String>();
		 spinnerArray.add("--Choose County--");
		 for(int i = 0;i<countylist.size();i++){
		 County countyfromdb=countylist.get(i);	 
		Log.i("Name for spinner",countyfromdb.getName());
		
	     spinnerArray.add(countyfromdb.getName());
	     }
		
	}
	
	public void updatespinnerarray(){
		
		 countylist= UfadhiliDataSource.getAllCounties();
		 
		 spinnerArray.clear();
		 spinnerArray.add("--Choose County--");
		 for(int i = 0;i<countylist.size();i++){
		 County countyfromdb=countylist.get(i);	 
		 Log.i("Name for spinner",countyfromdb.getName());
		 
	     spinnerArray.add(countyfromdb.getName());
	     }
		 adapter.notifyDataSetChanged();
	}
	private class RequestCountyListData extends AsyncTask<String, Void, String> {
		private JSONParser jsonParser; 
		 JSONArray counties;
		 County county;
		 private Dialog dialog ; 
		 @Override
	     protected void onPreExecute() {
			    
			    dialog = new Dialog(context,R.style.NewDialog);
	         	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	         	dialog.addContentView(new ProgressBar(context), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	         	int height = getResources().getDisplayMetrics().heightPixels;

	         	dialog.getWindow().setGravity(Gravity.CENTER);
	         	WindowManager.LayoutParams w = dialog.getWindow().getAttributes();
	         	w.y = height / 2;
	         	dialog.getWindow().setAttributes(w);
	         	dialog.setCancelable(true);
	            dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
	                 @Override
	    				public void onCancel(DialogInterface dialog) {
	                        
	                 }
	           });
	             
	           dialog.show();
	             super.onPreExecute();
	             // do stuff before posting data
	     }

		 @Override
	    protected String doInBackground(String... urls) {
	      String response = "";
	      jsonParser=new JSONParser();

	     

	      // Building Parameters ( you can pass as many parameters as you want)
	      List<NameValuePair> params = new ArrayList<NameValuePair>();

	     /* params.add(new BasicNameValuePair("name", name));*/
	      
		      // Getting JSON Object
		      try {
		    	  Log.i("COUNTY URL",county_url);
		          JSONObject jsonobject = jsonParser.makeHttpRequest(county_url, "GET", params);

				      try {
				    	 
				    	 counties=jsonobject.getJSONArray("objects");
				           
				    	 if(counties!=null){
				    		 
					    	 for(int k=0;k<counties.length();k++){
					    		 JSONObject countydata = counties.getJSONObject(k);
						    	 //Log.i("Summary"," "+county.getString("name"));
					    		 county=new County(
					    				 Integer.parseInt(countydata.getString("id")),
					    				 countydata.getString("name"),
					    				 countydata.getString("slug"),
					    				 countydata.getString("_summary_rendered"),
					    				 countydata.getString("location"),
					    				 countydata.getString("created"),
					    				 countydata.getString("updated")
					    				 );
					    		 
					    		//add county to database
					    		 UfadhiliDataSource.addCounty(county);
					    		
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
	    	dialog.dismiss();
	    	updatespinnerarray();
	    	
	    }
	  }
	 
	 
	 
}
