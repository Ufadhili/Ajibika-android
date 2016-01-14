package com.unina.ajibika;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.array;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class HomeActivity extends ActionBarActivity {

	List<DrawerItem> groupList;
	List<DrawerItem> childList;
	Map<DrawerItem, List<DrawerItem>> menuCollection;
	ExpandableListView expListView;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	SharedPreferences sharedpref;
	public Context context;
	public List<Position> positionlist;
	public List<Person> personlist;
	public List<Organisation> organisationlist;
	Fragment fr;
	FragmentManager fm;
	SpinnerAdapter mSpinnerAdapter;
	public List<String> spinnerArray;
	public List<County> countylist;
	private OnNavigationListener mOnNavigationListener;
	public UfadhiliDatabaseHelper UfadhiliDataSource;
	RequestCountyPeopleInformation RequestCountyPeopleInformationTask;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		createGroupList();
		context = this;
		fm = getSupportFragmentManager();
		createCollection();
		sharedpref = context.getSharedPreferences("UserSession", 0); // 0 - for
																		// private
																		// mode
		UfadhiliDataSource=new UfadhiliDatabaseHelper(this);
		RequestCountyPeopleInformation task = new RequestCountyPeopleInformation();
		task.execute(new String[] {});

		populatespinnerarray();

		mSpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerArray);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		expListView = (ExpandableListView) findViewById(R.id.left_drawer);
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
				this, groupList, menuCollection);
		expListView.setAdapter(expListAdapter);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				// getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu(); // creates call to
												// onPrepareOptionsMenu()
			}
		};

		mOnNavigationListener = new OnNavigationListener() {
			// Get the same strings provided for the drop-down's ArrayAdapter

			@Override
			public boolean onNavigationItemSelected(int position, long itemId) {

				countylist = MainActivity.UfadhiliDataSource.getAllCounties();
				County county = countylist.get(position);

				Editor editor = sharedpref.edit();

				editor.putInt("selected_county_id", county.getID());
				editor.putInt("selected_county_index", position);
				editor.commit(); // commit changes
				
				RequestCountyPeopleInformationTask = new RequestCountyPeopleInformation();
				RequestCountyPeopleInformationTask.execute(new String[] {});
				fr = new CountyNews();
				setTitle("News");
				 new Handler().post(new Runnable() {
			            public void run() {
							FragmentTransaction fragmentTransaction = fm.beginTransaction();
							fragmentTransaction.replace(R.id.home_fragment_space, fr);
							fragmentTransaction.commit();
			            }
			        });
				return true;
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter,
				mOnNavigationListener);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getSupportActionBar().setSelectedNavigationItem(
				sharedpref.getInt("selected_county_index", 0));

		expListView.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				final DrawerItem selected = (DrawerItem) expListAdapter
						.getChild(groupPosition, childPosition);

				// get information about the county
				if (selected.getItemID() == 4) {// county governor
					fr = new CountyPerson(6);
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 5) {// Deputy county Governor
					fr = new CountyPerson(11);
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 7) {// county speaker
					fr = new CountyPerson(15);
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 8) {// deputy county speaker
					fr = new CountyPerson(22);
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 9) {// county clerk
					fr = new CountyPerson(18);
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 10) {// county members of
														// assembly
					fr = new CountyAssembly();
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 6) {// county executive
					fr = new CountyExecutive();
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 21) {// county other
					fr = new CountyOthers();
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 11) {// county plans
					fr = new CountyResources("plan");
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 12) {// county budgets
					fr = new CountyResources("budget");
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 13) {// county bills
					fr = new CountyResources("bill");
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 17) {// county transcripts
					fr = new CountyResources("transcript");
					setTitle(selected.ItemName);
				
				} else if (selected.getItemID() == 14) {// county documents
					fr = new CountyResources("other");
					setTitle(selected.ItemName);
				}else if (selected.getItemID() == 19) {// women's rep
					fr = new CountyPerson(19);
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 20) {// members of parliament
					fr = new CountyParliament();
					setTitle(selected.ItemName);
				} else {

				}

				FragmentTransaction fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.home_fragment_space, fr);
				fragmentTransaction.commit();
				mDrawerLayout.closeDrawer(expListView);
				return true;
			}
		});
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				final DrawerItem selected = (DrawerItem) expListAdapter
						.getGroup(groupPosition);
				Log.i("COUNTY ID",
						"" + sharedpref.getInt("selected_county_id", 0));
				// get information about the county
				if (selected.getItemID() == 1) { // description
					fr = new AboutCounty();
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 2) {// county senator
					fr = new CountyPerson(8);
					setTitle(selected.ItemName);
				}

				else if (selected.getItemID() == 16) {// load projects fragment
					fr = new CountyProjects();
					setTitle(selected.ItemName);
				} else if (selected.getItemID() == 3) {// load projects fragment
					fr = new CountyNews();
					setTitle(selected.ItemName);
				} else {

				}

				FragmentTransaction fragmentTransaction = fm.beginTransaction();
				fragmentTransaction.replace(R.id.home_fragment_space, fr);
				fragmentTransaction.commit();
				if (selected.getItemID() > 0) {
					mDrawerLayout.closeDrawer(expListView);
				}
				return false;
			}

		});
		mDrawerLayout.openDrawer(expListView);
	}
	@Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

            if (RequestCountyPeopleInformationTask != null) {
            	RequestCountyPeopleInformationTask.cancel(true);
            }


    }
	
	
	
	
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// SearchManager searchManager =
			// (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			// SearchView searchView =
			// (SearchView) menu.findItem(R.id.action_search).getActionView();
			// searchView.setSearchableInfo(
			// searchManager.getSearchableInfo(getComponentName()));
			// searchView.setIconifiedByDefault(false);
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_about:
			Intent aboutajibika = new Intent(this, AboutAjibika.class);
			startActivity(aboutajibika);
			return true;
		case R.id.action_resources:
			Intent devolutionresources = new Intent(this,
					DevolutionResources.class);
			startActivity(devolutionresources);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void createGroupList() {
		groupList = new ArrayList<DrawerItem>();
		groupList.add(new DrawerItem("News", 3, R.drawable.news));
		groupList.add(new DrawerItem("About", 1, R.drawable.about));
		groupList.add(new DrawerItem("County Executive", 0,
				R.drawable.county_executive));
		groupList.add(new DrawerItem("Senator", 2, R.drawable.senator));
		groupList.add(new DrawerItem("County Assembly", 0,
				R.drawable.countyassembly));
		groupList.add(new DrawerItem("National Assembly", 0,
				R.drawable.nationalassembly));
		groupList.add(new DrawerItem("Projects", 16, R.drawable.projects));
		groupList.add(new DrawerItem("Resources", 0, R.drawable.resources));

	}

	private void createCollection() {
		// preparing navmenus collection(child)
		ArrayList<DrawerItem> countyexecutivechildren = new ArrayList<DrawerItem>();
		countyexecutivechildren.add(new DrawerItem("Governor", 4,
				R.drawable.ic_launcher));
		countyexecutivechildren.add(new DrawerItem("Deputy Governor", 5,
				R.drawable.ic_launcher));
		countyexecutivechildren.add(new DrawerItem("County Executive", 6,
				R.drawable.ic_launcher));
		countyexecutivechildren.add(new DrawerItem("Other Officials", 21,
				R.drawable.ic_launcher));

		ArrayList<DrawerItem> countyassemblychildren = new ArrayList<DrawerItem>();
		countyassemblychildren.add(new DrawerItem("County Speaker", 7,
				R.drawable.ic_launcher));
		countyassemblychildren.add(new DrawerItem("Deputy County Speaker", 8,
				R.drawable.ic_launcher));
		countyassemblychildren.add(new DrawerItem("County Clerk", 9,
				R.drawable.ic_launcher));
		countyassemblychildren.add(new DrawerItem("Members of County Assembly",
				10, R.drawable.ic_launcher));

		ArrayList<DrawerItem> nationalassemblychildren = new ArrayList<DrawerItem>();
		nationalassemblychildren.add(new DrawerItem("Women's Representative",
				19, R.drawable.ic_launcher));
		nationalassemblychildren.add(new DrawerItem("Members of Parliament",
				20, R.drawable.ic_launcher));

		ArrayList<DrawerItem> newschildren = new ArrayList<DrawerItem>();
		newschildren.add(new DrawerItem("Plans", 11, R.drawable.ic_launcher));
		newschildren.add(new DrawerItem("Budgets", 12, R.drawable.ic_launcher));
		newschildren.add(new DrawerItem("Bills", 13, R.drawable.ic_launcher));
		newschildren.add(new DrawerItem("Transcripts", 17,
				R.drawable.ic_launcher));
		newschildren.add(new DrawerItem("Other Docs", 14,
				R.drawable.ic_launcher));

		ArrayList<DrawerItem> emptychildren = new ArrayList<DrawerItem>();

		menuCollection = new LinkedHashMap<DrawerItem, List<DrawerItem>>();

		for (DrawerItem navmenu : groupList) {

			if (navmenu.getItemName().equals("County Executive")) {
				loadChild(countyexecutivechildren);
			} else if (navmenu.getItemName().equals("County Assembly")) {
				loadChild(countyassemblychildren);
			} else if (navmenu.getItemName().equals("National Assembly")) {
				loadChild(nationalassemblychildren);
			} else if (navmenu.getItemName().equals("Resources")) {
				loadChild(newschildren);
			} else {
				loadChild(emptychildren);
			}
			Log.i("DrawerItem ", navmenu.getItemName());
			menuCollection.put(navmenu, childList);
		}
	}

	private void loadChild(ArrayList<DrawerItem> navmenuModels) {
		childList = new ArrayList<DrawerItem>();
		for (DrawerItem model : navmenuModels)
			childList.add(model);
	}


	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public void populatespinnerarray() {
		
		countylist =UfadhiliDataSource.getAllCounties();
		spinnerArray = new ArrayList<String>();
		for (int i = 0; i < countylist.size(); i++) {
			County countyfromdb = countylist.get(i);
			// Log.i("Name for spinner",countyfromdb.getName());

			spinnerArray.add(countyfromdb.getName());
		}

		Log.i("SELECTED COUNTY INDEX",
				"" + sharedpref.getInt("selected_county_index", 0));
	}

	private class RequestCountyPeopleInformation extends
			AsyncTask<String, Void, String> {
		private JSONParser jsonParser;

		JSONArray organisations;
		Organisation organisation;

		JSONArray positions;
		Position position;

		JSONArray people;
		JSONObject persons_details;
		Person person;

		
		 JSONArray counties;
		 County county;
		@Override
		protected String doInBackground(String... data) {

			jsonParser = new JSONParser();
			String[] imageulr = null;
			String person_id, place_id, org_id;
			// Building Parameters ( you can pass as many parameters as you
			// want)
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			
			 // Getting JSON Object
		      try {
		    	  Log.i("COUNTY URL",MainActivity.county_url);
		          JSONObject jsonobject = jsonParser.makeHttpRequest(MainActivity.county_url, "GET", params);

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
			
			
			
			
			// Getting JSON Object
			try {
				Log.i("Organizations url",
						MainActivity.base_url + "/api/v1/county/"
								+ sharedpref.getInt("selected_county_id", 0)
								+ "/organisations/?format=json");
				
				
			/*	organisations = jsonParser.makeHttpRequest2(
						MainActivity.base_url + "/api/v1/county/"
								+ sharedpref.getInt("selected_county_id", 0)
								+ "/organisations/?format=json", "GET", params);*/
				try {
					if (organisations != null) {
						
						for (int k = 0; k < organisations.length(); k++) {
							JSONObject organisationdata = organisations
									.getJSONObject(k);

							organisation = new Organisation(
									Integer.parseInt(organisationdata
											.getString("id")),// id
									organisationdata.getString("name"),// position
																		// name
									organisationdata.getString("started"),// created
																			// name
									organisationdata.getString("updated")// updated
																			// name
							);

							// add position to database
							MainActivity.UfadhiliDataSource.addOrganisation(organisation);
						}
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.i("Position url",MainActivity.base_url+"/api/v1/county/"+sharedpref.getInt("selected_county_id", 0)+"/positions/?format=json");
		    	positions =  jsonParser.makeHttpRequest2(MainActivity.base_url+"/api/v1/county/"+sharedpref.getInt("selected_county_id", 0)+"/positions/?format=json", "GET", params);
		    	//MainActivity.UfadhiliDataSource.deletePersonPosition(sharedpref.getInt("selected_county_id", 0));
				      try {
	    				       if(positions!=null){
	    				    	 
	    				    	 for(int k=0;k<positions.length();k++){
	    				    		 
	    				    		 JSONObject positiondata = positions.getJSONObject(k);
	    				    		 Log.i("Position item to be inserted",positiondata.getString("title"));
	    				    		 position=new Position(
	    				    				 Integer.parseInt((positiondata.getString("title_id")=="null")?"0":positiondata.getString("title_id")),//id
	    				    				 Integer.parseInt((positiondata.getString("place_id")=="null")?"0":positiondata.getString("place_id")),//county id
	    				    				 
	    				    				 Integer.parseInt((positiondata.getString("organisation_id")=="null")?"0":positiondata.getString("organisation_id")),//organisation id
	    				    				 positiondata.getString("title"),//position name
	    				    				 positiondata.getString("created"),//created name
	    				    				 positiondata.getString("updated")//updated name
	    				    				 );
	    				    		 
	    				    		//add position to database
	    				    		 MainActivity.UfadhiliDataSource.addPosition(position);
	    				    	 }
	    				    	
	    				      }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    Log.i("people url",MainActivity.base_url+"/api/v1/county/"+sharedpref.getInt("selected_county_id", 0)+"/people/?format=json");
		    	    people = jsonParser.makeHttpRequest2(MainActivity.base_url+"/api/v1/county/"+sharedpref.getInt("selected_county_id", 0)+"/people/?format=json", "GET", params);
		    	    MainActivity.UfadhiliDataSource.deletePersonOrganization(sharedpref.getInt("selected_county_id", 0));
				      try {
				    	  if(people!=null){
				    		  //delete person organizations for the county members
				  	        	
						    	 for(int k=0;k<people.length();k++){
						    		 JSONObject persondata = people.getJSONObject(k);
						    		 persons_details=persondata.getJSONObject("personal_details");
						    		 Log.i("Name of person ", persons_details.getString("legal_name")
						    				+" ID "+persondata.getString("person_id")
						    				+" County "+persondata.getString("place_id")
						    				+" Organisation "+persondata.getString("organisation_id")
						    				+" Position "+persondata.getString("title_id")
						    				);
						    		if(persondata.getString("person_id")!="null"){
						    	        person_id = persondata.getString("person_id");
						    		}else{
						    		    person_id = "0";	
						    		}
						    		if(persondata.getString("place_id")!="null"){
    						    	  place_id = persondata.getString("place_id");
    						    	}else{
    						    		place_id = "0";	
    						    		}
						    		if(persondata.getString("organisation_id")!="null"){
    						    	     org_id = persondata.getString("organisation_id");
    						    		}else{
    						    		org_id = "0";	
    						    		}
						    		if(persondata.getString("image")!="null"){
						    			imageulr=persondata.getString("image").split(Pattern.quote("?"));
						    		}
						    		 Person personobject=new Person(
						    				 Integer.parseInt(person_id),//person id
						    				 Integer.parseInt(persondata.getString("id")),//person id
						    				 Integer.parseInt(place_id),//county id
						    				 Integer.parseInt(org_id),//organisation id
						    				 persondata.getString("title_id")!="null"?Integer.parseInt(persondata.getString("title_id")):0,//position id
						    				 persons_details.getString("legal_name"),
						    				 persons_details.getString("additional_name"),
						    				 persons_details.getString("biography"),
						    				 persons_details.getString("created"),
						    				 persons_details.getString("date_of_birth"),
						    				 persons_details.getString("date_of_death"),
						    				 persons_details.getString("email"),
						    				 persons_details.getString("gender"),
						    				 persons_details.getString("legal_name"),
						    				 persons_details.getString("national_identity"),
						    				 persons_details.getString("slug"),
						    				 persons_details.getString("sort_name"),
						    				 persons_details.getString("summary"),
						    				 persons_details.getString("title"),
						    				 persondata.getString("subtitle"),
						    				 imageulr[0],
						    				 persons_details.getString("updated")
						    				 );
						    		 MainActivity.UfadhiliDataSource.addPerson(personobject);
						    	 }
						    	
				    	  }
				    	
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
			} catch (IOException e) {
				e.printStackTrace();

			}

			//Log.i("MESSAGE", "I am here");

			// TODO Auto-generated method stub
			return null;
		}

	}
}
