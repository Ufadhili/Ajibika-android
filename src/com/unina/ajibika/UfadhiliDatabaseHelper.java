package com.unina.ajibika;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.unina.ajibika.County;
import com.unina.ajibika.News;
import com.unina.ajibika.Organisation;
import com.unina.ajibika.Person;
import com.unina.ajibika.Position;
import com.unina.ajibika.Projects;
import com.unina.ajibika.Resources;
import com.unina.ajibika.UfadhiliData;


public class UfadhiliDatabaseHelper {
	 // Database fields
	  private SQLiteDatabase database;
	  private UfadhiliData dbHelper;
	
	 public UfadhiliDatabaseHelper(Context context) {
		    dbHelper = new UfadhiliData(context);
	 }

     public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
	}

    public void close() {
		 //   dbHelper.close();
    }
		  
    // ---------------------------------------------------------county table data manipulation------------------------------------------

    @SuppressLint("NewApi")
	public void addCounty(County county) {
    	
    	database = dbHelper.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, county.getID()); // county id
        values.put(UfadhiliData.COUNTY_NAME, county.getName()); // county name
        values.put(UfadhiliData.COUNTY_SLUG, county.getSlug()); // county slug
        values.put(UfadhiliData.COUNTY_DESC, county.getDesc()); // county desc
        values.put(UfadhiliData.COUNTY_LOCATION, county.getLocation()); // county location
        values.put(UfadhiliData.COUNTY_CREATED, county.getCreated()); // county created
        values.put(UfadhiliData.COUNTY_UPDATED, county.getUpdated()); // county updated
 
        // Inserting Row
        
        database.insertWithOnConflict(UfadhiliData.TABLE_COUNTIES, UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
      
    }

    // Getting single county
    public County getCounty(int id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_COUNTIES, new String[] {
        		UfadhiliData.COUNTY_ID,
        		UfadhiliData.COUNTY_NAME, 
        		UfadhiliData.COUNTY_SLUG,
        		UfadhiliData.COUNTY_DESC,
        		UfadhiliData.COUNTY_LOCATION,
        		UfadhiliData.COUNTY_CREATED,
        		UfadhiliData.COUNTY_UPDATED },
        		UfadhiliData.COUNTY_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        County county = new County(
        		Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
                );
        // return county
        cursor.close();
        
        return county;
    }
    
 // Getting All Counties
    public List<County> getAllCounties() {
        List<County> countyList = new ArrayList<County>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + UfadhiliData.TABLE_COUNTIES;
        
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	County county = new County();
            	county.setDB_ID(Integer.parseInt(cursor.getString(0)));
            	county.setID(Integer.parseInt(cursor.getString(1)));
            	county.setName(cursor.getString(2));
            	county.setSlug(cursor.getString(3));
            	county.setDesc(cursor.getString(4));
            	county.setLocation(cursor.getString(5));
            	county.setCreated(cursor.getString(6));
            	county.setUpdated(cursor.getString(7));
                // Adding county to list
            	countyList.add(county);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return countyList;
    }
 // Updating single county
    public int updateCounty(County county) {
    	database = dbHelper.getWritableDatabase();
 
    	  ContentValues values = new ContentValues();
          values.put(UfadhiliData.COUNTY_ID, county.getID()); // county id
          values.put(UfadhiliData.COUNTY_NAME, county.getName()); // county name
          values.put(UfadhiliData.COUNTY_SLUG, county.getSlug()); // county slug
          values.put(UfadhiliData.COUNTY_DESC, county.getDesc()); // county desc
          values.put(UfadhiliData.COUNTY_LOCATION, county.getLocation()); // county location
          values.put(UfadhiliData.COUNTY_CREATED, county.getCreated()); // county created
          values.put(UfadhiliData.COUNTY_UPDATED, county.getUpdated()); // county updated
 
        // updating row
        int res= database.update(UfadhiliData.TABLE_COUNTIES, values, UfadhiliData.COUNTY_ID + " = ?",
                new String[] { String.valueOf(county.getID()) });
        
        return res; 
        
    }
 
    // Deleting single county
    public void deleteCounty(County county) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_COUNTIES, UfadhiliData.COUNTY_ID + " = ?",
                new String[] { String.valueOf(county.getID()) });
        
    }
 
 
    // Getting counties Count
    public int getCountiesCount() {
        String countQuery = "SELECT  * FROM " + UfadhiliData.TABLE_COUNTIES;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }

   //------------------------------------------------resources table data manipulation------------------------------------------------- 
    
   
    // Adding new county
    @SuppressLint("NewApi")
	public void addResources(Resources resourceItem) {
    	
    	database = dbHelper.getWritableDatabase();
 
    	ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, resourceItem.getCounty_id()); // county id
        values.put(UfadhiliData.RESOURCES_ID, resourceItem.getResource_id()); // county id
        values.put(UfadhiliData.RESOURCES_TITLE, resourceItem.getTitle()); // county name
        values.put(UfadhiliData.RESOURCES_SUMMARY, resourceItem.getSummary()); // county slug
        values.put(UfadhiliData.RESOURCES_TYPE, resourceItem.getType()); // county desc
        values.put(UfadhiliData.RESOURCES_DOWNLOAD_LINK, resourceItem.getFile_link()); // county location
      
       
 
        // Inserting Row
        Log.i("Resources item to be inserted",resourceItem.getTitle());
        database.insertWithOnConflict(UfadhiliData.TABLE_RESOURCES, UfadhiliData.RESOURCES_ID, values, SQLiteDatabase.CONFLICT_IGNORE);
      
    }

    // Getting single county
    public Resources getResourcesItem(int id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_RESOURCES, new String[] {
        		UfadhiliData.COUNTY_ID,
        		UfadhiliData.RESOURCES_ID,
        		UfadhiliData.RESOURCES_TITLE, 
        		UfadhiliData.RESOURCES_SUMMARY,
        		UfadhiliData.RESOURCES_TYPE,
        		UfadhiliData.RESOURCES_DOWNLOAD_LINK,
        		},
        		UfadhiliData.COUNTY_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
    	Resources resourceitem = new Resources(
        		cursor.getString(0),
        		cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt( cursor.getString(5))
                );
               
        // return county
        cursor.close();
        
        return resourceitem;
    }
    
 // Getting All Counties
    public List<Resources> getAllResources(int county_id,String type) {
        List<Resources> resourcelist = new ArrayList<Resources>();
    Log.i("","COUNTY "+county_id+" type  "+type);
 
        database = dbHelper.getReadableDatabase();
        
        Cursor cursor = database.query(UfadhiliData.TABLE_RESOURCES, new String[] {
        		UfadhiliData.RESOURCES_TITLE, 
        		UfadhiliData.RESOURCES_SUMMARY,
        		UfadhiliData.RESOURCES_DOWNLOAD_LINK,
        		UfadhiliData.RESOURCES_ID,
        		
        		},
        		UfadhiliData.COUNTY_ID+ "=? AND "+UfadhiliData.RESOURCES_TYPE+ "=?" ,
                new String[] { String.valueOf(county_id),type }, null, null, null, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
            	Resources resourceitem = new Resources(
            			cursor.getString(0),
                		cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3))
            			
            			);
            	
            	
                // Adding county to list
            	resourcelist.add(resourceitem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return resourcelist;
    }
    // Getting All Counties
    public List<Resources> getAllResources() {
        List<Resources> resourcelist = new ArrayList<Resources>();
    
 
        database = dbHelper.getReadableDatabase();
        
        Cursor cursor = database.query(UfadhiliData.TABLE_RESOURCES, new String[] {
        		UfadhiliData.RESOURCES_TITLE, 
        		UfadhiliData.RESOURCES_SUMMARY,
        		UfadhiliData.RESOURCES_DOWNLOAD_LINK,
        		UfadhiliData.RESOURCES_ID,
        		
        		},
        		UfadhiliData.RESOURCES_TYPE+ "=?" ,
                new String[] { String.valueOf("general") }, null, null,  UfadhiliData.RESOURCES_ID+" DESC", "500");
        
       
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	 Log.i("Resources item retrieved",cursor.getString(1));
            	Resources resourceitem = new Resources(
            			cursor.getString(0),
                		cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3))
            			
            			);
            	
            	
                // Adding county to list
            	resourcelist.add(resourceitem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return resourcelist;
    }
 // Updating single county
    public int updateNews(Resources resourceItem) {
    	database = dbHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, resourceItem.getCounty_id()); // county id
        values.put(UfadhiliData.RESOURCES_ID, resourceItem.getResource_id()); // county id
        values.put(UfadhiliData.RESOURCES_TITLE, resourceItem.getTitle()); // county name
        values.put(UfadhiliData.RESOURCES_SUMMARY, resourceItem.getSummary()); // county slug
        values.put(UfadhiliData.RESOURCES_TYPE, resourceItem.getType()); // county desc
        values.put(UfadhiliData.RESOURCES_DOWNLOAD_LINK, resourceItem.getFile_link()); // county location
 
        // updating row
        int res= database.update(UfadhiliData.TABLE_RESOURCES, values, UfadhiliData.RESOURCES_ID + " = ?",
                new String[] { String.valueOf(resourceItem.getResource_id()) });
        
        return res; 
        
    }
 
    // Deleting single county
    public void deleteNews(Resources resourceItem) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_RESOURCES, UfadhiliData.RESOURCES_ID + " = ?",
                new String[] { String.valueOf(resourceItem.getResource_id()) });
        
    }
 
 
    // Getting counties Count
    public int getResourcesCount() {
        String countQuery = "SELECT  * FROM " + UfadhiliData.TABLE_RESOURCES;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
   
    // -------------------------------------------------projects table data maniputalion---------------------------------------
    
    @SuppressLint("NewApi")
	public void addProjects(Projects projectItem) {
    	
    	database = dbHelper.getWritableDatabase();
 
    	ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, projectItem.getCounty_id()); // county id
        values.put(UfadhiliData.PROJECT_ID, projectItem.getProject_id()); // county id
        values.put(UfadhiliData.PROJECT_TITLE, projectItem.getTitle()); // county name
        values.put(UfadhiliData.PROJECT_SUMMARY, projectItem.getSummary()); // county slug
        values.put(UfadhiliData.PROJECT_LOCATION, projectItem.getLoacation()); // county desc
        values.put(UfadhiliData.PROJECT_STATUS, projectItem.getStatus()); // county location
        values.put(UfadhiliData.PROJECT_FUNDING, projectItem.getYear_funded()); // county location
        values.put(UfadhiliData.PROJECT_COST, projectItem.getYear_funded()); // county location
        values.put(UfadhiliData.PROJECT_CONTRACTOR, projectItem.getContractor()); // county location
        values.put(UfadhiliData.PROJECT_IMAGEURL, projectItem.getImage()); // county location
        values.put(UfadhiliData.PROJECT_ABSOLUTE_URL, projectItem.getAbsolute_url()); // county location
       
 
        // Inserting Row
        Log.i("Projects item to be inserted",projectItem.getTitle());
        database.insertWithOnConflict(UfadhiliData.TABLE_PROJECTS,UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
      
    }

    // Getting single county
    public Projects getProjectsItem(int id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_PROJECTS, new String[] {
        		UfadhiliData.PROJECT_TITLE,
        		UfadhiliData.PROJECT_SUMMARY,
        		UfadhiliData.PROJECT_CONTRACTOR, 
        		UfadhiliData.PROJECT_FUNDING,
        		UfadhiliData.PROJECT_COST,
        		UfadhiliData.PROJECT_IMAGEURL,
        		UfadhiliData.PROJECT_ABSOLUTE_URL,
        		UfadhiliData.PROJECT_LOCATION,
        		UfadhiliData.PROJECT_STATUS,
        		UfadhiliData.COUNTY_ID,
        		UfadhiliData.PROJECT_ID,
        		},
        		UfadhiliData.PROJECT_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
    	Projects projectitem = new Projects(
    			cursor.getString(0),
    			cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                Integer.parseInt(cursor.getString(9)),
                Integer.parseInt(cursor.getString(10))
        		
                
                );
               
        // return county
        cursor.close();
        
        return projectitem;
    }
    
 // Getting All Counties
    public List<Projects> getAllProjects(int county_id) {
        List<Projects> projectlist = new ArrayList<Projects>();
    
 
        database = dbHelper.getWritableDatabase();
    	
        Cursor cursor = database.query(UfadhiliData.TABLE_PROJECTS, new String[] {
        		UfadhiliData.PROJECT_TITLE,
        		UfadhiliData.PROJECT_SUMMARY,
        		UfadhiliData.PROJECT_CONTRACTOR, 
        		UfadhiliData.PROJECT_FUNDING,
        		UfadhiliData.PROJECT_COST,
        		UfadhiliData.PROJECT_IMAGEURL,
        		UfadhiliData.PROJECT_ABSOLUTE_URL,
        		UfadhiliData.PROJECT_LOCATION,
        		UfadhiliData.PROJECT_STATUS,
        		UfadhiliData.COUNTY_ID,
        		UfadhiliData.PROJECT_ID,
        		},
        		UfadhiliData.COUNTY_ID+ "=?",
                new String[] { String.valueOf(county_id) }, null, null, UfadhiliData.PROJECT_ID+" DESC", "500");
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Projects projectitem = new Projects(
            			cursor.getString(0),
            			cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        Integer.parseInt(cursor.getString(9)),
                        Integer.parseInt(cursor.getString(10))
                       );
            	
                // Adding county to list
            	projectlist.add(projectitem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return projectlist;
    }
 // Updating single county
    public int updateProjects(Projects projectItem) {
    	database = dbHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, projectItem.getCounty_id()); // county id
        values.put(UfadhiliData.PROJECT_ID, projectItem.getProject_id()); // county id
        values.put(UfadhiliData.PROJECT_TITLE, projectItem.getTitle()); // county name
        values.put(UfadhiliData.PROJECT_SUMMARY, projectItem.getSummary()); // county slug
        values.put(UfadhiliData.PROJECT_LOCATION, projectItem.getLoacation()); // county desc
        values.put(UfadhiliData.PROJECT_STATUS, projectItem.getStatus()); // county location
        values.put(UfadhiliData.PROJECT_FUNDING, projectItem.getYear_funded()); // county location
        values.put(UfadhiliData.PROJECT_COST, projectItem.getYear_funded()); // county location
        values.put(UfadhiliData.PROJECT_CONTRACTOR, projectItem.getContractor()); // county location
        values.put(UfadhiliData.PROJECT_IMAGEURL, projectItem.getImage()); // county location
        values.put(UfadhiliData.PROJECT_ABSOLUTE_URL, projectItem.getAbsolute_url()); // county location
        // updating row
        int res= database.update(UfadhiliData.TABLE_PROJECTS, values, UfadhiliData.PROJECT_ID + " = ?",
                new String[] { String.valueOf(projectItem.getProject_id() ) });
        
        return res; 
        
    }
 
    // Deleting single county
    public void deleteProjects(County county) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_PROJECTS, UfadhiliData.PROJECT_ID + " = ?",
                new String[] { String.valueOf(county.getID()) });
        
    }
 
 
    // Getting counties Count
    public int getProjectsCount() {
        String countQuery = "SELECT  * FROM " + UfadhiliData.TABLE_PROJECTS;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    //------------------------------------------------positions table data manipulation------------------------------------------------- 
    
    // Adding new county
    public void addPosition(Position position) {
    	
        database = dbHelper.getWritableDatabase();
       
    	  
        ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, position.getCountyID()); // county id
        values.put(UfadhiliData.POSITION_ID, position.getID()); 
        values.put(UfadhiliData.ORGANISATION_ID, position.getOrgID());// county id
        values.put(UfadhiliData.POSITION_NAME, position.getName()); // county name
        values.put(UfadhiliData.POSITION_CREATED, position.getCreated()); // county created
        values.put(UfadhiliData.POSITION_UPDATED, position.getUpdated()); // county updated
 
       Log.i("Position name added to the database"," "+position.getCountyID()+" "+position.getID()+"  "+position.getName());
        // Inserting Row
     
        database.insertWithOnConflict(UfadhiliData.TABLE_POSITIONS, UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
        // // Closing database connection
    	
    }

    // Getting single county
    public Position getPosition(int id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_POSITIONS, new String[] {
        		UfadhiliData.POSITION_ID,
        		UfadhiliData.COUNTY_ID,
        		UfadhiliData.ORGANISATION_ID,
        		UfadhiliData.POSITION_NAME, 
        		UfadhiliData.POSITION_CREATED,
        		UfadhiliData.POSITION_UPDATED },
        		UfadhiliData.POSITION_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.getCount()> 0)
            cursor.moveToFirst();
 
        Position org = new Position(
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(2)),
        		Integer.parseInt(cursor.getString(3)),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
                );
        // return county
        cursor.close();
        
        return org;
    }
    
 // Getting All Counties
    public List<Position> getAllPositions() {
        List<Position> positionlist = new ArrayList<Position>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + UfadhiliData.TABLE_POSITIONS;
 
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
            	Position position = new Position();
            	position.setDB_ID(Integer.parseInt(cursor.getString(0)));
            	position.setID(Integer.parseInt(cursor.getString(2)));
            	position.setOrgID(Integer.parseInt(cursor.getString(3)));
            	position.setName(cursor.getString(4));
            	position.setCreated(cursor.getString(5));
            	position.setUpdated(cursor.getString(6));
                // Adding county to list
            	positionlist.add(position);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return positionlist;
    }
 // Updating single county
    public int updatePosition(Position position) {
    	database = dbHelper.getWritableDatabase();
 
    	  ContentValues values = new ContentValues();
    	  values.put(UfadhiliData.COUNTY_ID, position.getID()); // county id
          values.put(UfadhiliData.POSITION_ID, position.getID());
          values.put(UfadhiliData.ORGANISATION_ID, position.getOrgID());// county id
          values.put(UfadhiliData.POSITION_NAME, position.getName()); // county name
          values.put(UfadhiliData.POSITION_CREATED, position.getCreated()); // county created
          values.put(UfadhiliData.POSITION_UPDATED, position.getUpdated()); // county updated
        // updating row
        int res= database.update(UfadhiliData.TABLE_POSITIONS, values, UfadhiliData._ID + " = ?",
                new String[] { String.valueOf(position.getID()) });
        
        return res; 
        
    }
 
    // Deleting single county
    public void deletePosition(Position position) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_POSITIONS, UfadhiliData.POSITION_ID + " = ?",
                new String[] { String.valueOf(position.getID()) });
        
    }
 
 
    // Getting counties Count
    public int getPositionCount() {
        String countQuery = "SELECT  * FROM " + UfadhiliData.TABLE_POSITIONS;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    //------------------------------------------------persons table data manipulation------------------------------------------------- 
    // Deleting single county
    public void deletePersonOrganization(int county_id) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_PERSON_ORGANIZATION, UfadhiliData.COUNTY_ID + " = ?",
                new String[] { String.valueOf(county_id) });
        
    }
    
    public long addPersonOrganization(Person person){
    	long id=0;
    	database = dbHelper.getWritableDatabase();
        
  	  
        ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, person.getCounty_id()); // county id
        values.put(UfadhiliData.PERSON_ID,  person.getPerson_main_id()); 
        values.put(UfadhiliData.ORGANISATION_ID, person.getOrganisation_id());// county id
       
 
       Log.i("InsertPersonOrganization"," "+person.getCounty_id()+" "+person.getPerson_id()+"  "+person.getOrganisation_id());
        // Inserting Row
     
       id=database.insertWithOnConflict(UfadhiliData.TABLE_PERSON_ORGANIZATION, UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
        // // Closing database connection
    	
       return id;
    	
    }
    // Deleting single county
    public void deletePersonPosition(int county_id) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_PERSON_POSITION, UfadhiliData.COUNTY_ID + " = ?",
                new String[] { String.valueOf(county_id) });
        
    }
    
    public long addPersonPosition(Person person){
    	long id=0;
    	database = dbHelper.getWritableDatabase();
        
  	  
        ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, person.getCounty_id()); // county id
        values.put(UfadhiliData.PERSON_ID,  person.getPerson_main_id()); 
        values.put(UfadhiliData.POSITION_ID, person.getPosition_id());// position id
       
 
       Log.i("Insert Person Position"," "+person.getCounty_id()+" "+person.getPerson_id()+"  "+person.getPosition_id());
        // Inserting Row
     
       id=database.insertWithOnConflict(UfadhiliData.TABLE_PERSON_POSITION, UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
        // // Closing database connection
    	
       return id;
    	
    }
    // Adding new county
    public void addPerson(Person person) {
    	long id=0;
        database = dbHelper.getWritableDatabase();
       
       
         ContentValues values = new ContentValues();
         values.put(UfadhiliData.PERSON_ID , person.getPerson_id());
         values.put(UfadhiliData.PERSON_MAIN_ID , person.getPerson_main_id());
	     values.put(UfadhiliData.PERSON_COUNTY, person.getCounty_id());
	     values.put(UfadhiliData.PERSON_NAME , person.getPerson_name());
		 values.put(UfadhiliData.PERSON_ADDITIONAL_NAME, person.getAdditional_name());
		 values.put(UfadhiliData.PERSON_BIOGRAPHY, person.getBiography());
		 values.put(UfadhiliData.PERSON_CREATED, person.getCreated());
		 values.put(UfadhiliData.PERSON_DOB, person.getDate_of_birth());
		 values.put(UfadhiliData.PERSON_DOD, person.getDate_of_death());
		 values.put(UfadhiliData.PERSON_EMAIL, person.getEmail());
		 values.put(UfadhiliData.PERSON_GENDER, person.getGender());
		 values.put(UfadhiliData.PERSON_LEGAL_NAME, person.getLegal_name());
		 values.put(UfadhiliData.PERSON_NID , person.getNational_identity());
		 values.put(UfadhiliData.PERSON_SLUG, person.getSlug());
		 values.put(UfadhiliData.PERSON_SORT_NAME, person.getSort_name());
		 values.put(UfadhiliData.PERSON_SUMMARY, person.getSummary());
		 values.put(UfadhiliData.PERSON_TITLE, person.getTitle());
		 values.put(UfadhiliData.PERSON_SUBTITLE, person.getSubtitle());
		 values.put(UfadhiliData.PERSON_IMAGE, person.getImage_url());
		 values.put(UfadhiliData.PERSON_UPDATED, person.getUpdated());
 
		
        // Inserting Row
     
       id=database.insertWithOnConflict(UfadhiliData.TABLE_PEOPLE, UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
       
       this.addPersonOrganization(person);
       this.addPersonPosition(person);
       //  // Closing database connection
       Log.i("id of person added to the database"," "+id);
       if(id==-1){
    	   this.updatePerson(person);
       }
       
      
    }

    // Getting single county
    public Person getPerson(int id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_PEOPLE, new String[] {
        		 UfadhiliData.PERSON_ID ,
        		 UfadhiliData.PERSON_MAIN_ID,
       	         UfadhiliData.PERSON_COUNTY, 
    		    
    		     UfadhiliData.PERSON_POSITION ,
    		     UfadhiliData.PERSON_NAME ,
    			 UfadhiliData.PERSON_ADDITIONAL_NAME,
    			 UfadhiliData.PERSON_BIOGRAPHY,
    			 UfadhiliData.PERSON_CREATED,
    			 UfadhiliData.PERSON_DOB,
    			 UfadhiliData.PERSON_DOD,
    			 UfadhiliData.PERSON_EMAIL,
    			 UfadhiliData.PERSON_GENDER,
    			 UfadhiliData.PERSON_LEGAL_NAME,
    			 UfadhiliData.PERSON_NID ,
    			 UfadhiliData.PERSON_SLUG,
    			 UfadhiliData.PERSON_SORT_NAME,
    			 UfadhiliData.PERSON_SUMMARY,
    			 UfadhiliData.PERSON_TITLE,
    			 UfadhiliData.PERSON_SUBTITLE,
    			 UfadhiliData.PERSON_UPDATED
    			 
    			 
        		},
        		UfadhiliData.PERSON_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.getCount()> 0)
            cursor.moveToFirst();
 
        Person person = new Person(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(2)),
        		Integer.parseInt(cursor.getString(3)),
        		cursor.getString(4),
        		cursor.getString(5),
        		cursor.getString(6),
        		cursor.getString(7),
        		cursor.getString(8),
        		cursor.getString(9),
        		cursor.getString(10),
        		cursor.getString(11),
        		cursor.getString(12),
        		cursor.getString(13),
        		cursor.getString(14),
        		cursor.getString(15),
        		cursor.getString(16),
        		cursor.getString(17), 
        		cursor.getString(18),
        		cursor.getString(19),
        		cursor.getString(20)
        		
        		);
        // return county
        cursor.close();
        
        return person;
    }
    // Getting single county
    public Person getPerson(int county_id,int position_id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_PEOPLE+ " AS pple LEFT JOIN "
                + UfadhiliData.TABLE_PERSON_POSITION +" AS pos ON "  +"pos."+UfadhiliData.PERSON_ID+ "="+"pple."+UfadhiliData.PERSON_MAIN_ID,
                new String[] {
        		"pple."+UfadhiliData.PERSON_ID ,
        		"pple."+UfadhiliData.PERSON_MAIN_ID ,
        		"pple."+ UfadhiliData.PERSON_COUNTY, 
        		"pos."+UfadhiliData.PERSON_POSITION,
        		"pple."+ UfadhiliData.PERSON_NAME,
        		"pple."+UfadhiliData.PERSON_ADDITIONAL_NAME,
        		"pple."+UfadhiliData.PERSON_BIOGRAPHY,
        		"pple."+UfadhiliData.PERSON_CREATED,
        		"pple."+UfadhiliData.PERSON_DOB,
        		"pple."+UfadhiliData.PERSON_DOD,
        		"pple."+UfadhiliData.PERSON_EMAIL,
        		"pple."+UfadhiliData.PERSON_GENDER,
        		"pple."+ UfadhiliData.PERSON_LEGAL_NAME,
        		"pple."+ UfadhiliData.PERSON_NID ,
        		"pple."+UfadhiliData.PERSON_SLUG,
        		"pple."+UfadhiliData.PERSON_SORT_NAME,
        		"pple."+UfadhiliData.PERSON_SUMMARY,
        		"pple."+UfadhiliData.PERSON_TITLE,
        		"pple."+UfadhiliData.PERSON_SUBTITLE,
        		"pple."+UfadhiliData.PERSON_IMAGE,
        		"pple."+UfadhiliData.PERSON_UPDATED
    			 
    			 
        		},
        		"pple."+UfadhiliData.PERSON_COUNTY + "=? "+ " AND  pos."+UfadhiliData.PERSON_POSITION + "=?", 
                new String[] { String.valueOf(county_id),String.valueOf(position_id) }, null, null, null, null);
        
        
        
        Log.i("The where clause",UfadhiliData.PERSON_COUNTY + "="+county_id+ " AND "  +UfadhiliData.PERSON_POSITION + "="+position_id);
        Person person = null;
        if (cursor.getCount()> 0){
            cursor.moveToFirst();
 
        person = new Person(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(2)),
        		Integer.parseInt(cursor.getString(3)),
        		cursor.getString(4),
        		cursor.getString(5),
        		cursor.getString(6),
        		cursor.getString(7),
        		cursor.getString(8),
        		cursor.getString(9),
        		cursor.getString(10),
        		cursor.getString(11),
        		cursor.getString(12),
        		cursor.getString(13),
        		cursor.getString(14),
        		cursor.getString(15),
        		cursor.getString(16),
        		cursor.getString(17), 
        		cursor.getString(18),
        		cursor.getString(19),
        		cursor.getString(20)
        		
        		);
        // return county
        cursor.close();
        
        }
        return person;
    }
    // Getting single county
    public Person getPerson(int county_id,int position_id,int person_id) {
    	
    	database = dbHelper.getReadableDatabase();
 
    	Cursor cursor = database.query(UfadhiliData.TABLE_PEOPLE+ " AS pple LEFT JOIN "
                + UfadhiliData.TABLE_PERSON_POSITION +" AS pos ON "  +"pos."+UfadhiliData.PERSON_ID+ "="+"pple."+UfadhiliData.PERSON_MAIN_ID,
        		new String[] {
    			"pple."+UfadhiliData.PERSON_ID ,
        		"pple."+UfadhiliData.PERSON_MAIN_ID ,
        		"pple."+ UfadhiliData.PERSON_COUNTY, 
        		"pos."+UfadhiliData.PERSON_POSITION,
        		"pple."+ UfadhiliData.PERSON_NAME,
        		"pple."+UfadhiliData.PERSON_ADDITIONAL_NAME,
        		"pple."+UfadhiliData.PERSON_BIOGRAPHY,
        		"pple."+UfadhiliData.PERSON_CREATED,
        		"pple."+UfadhiliData.PERSON_DOB,
        		"pple."+UfadhiliData.PERSON_DOD,
        		"pple."+UfadhiliData.PERSON_EMAIL,
        		"pple."+UfadhiliData.PERSON_GENDER,
        		"pple."+ UfadhiliData.PERSON_LEGAL_NAME,
        		"pple."+ UfadhiliData.PERSON_NID ,
        		"pple."+UfadhiliData.PERSON_SLUG,
        		"pple."+UfadhiliData.PERSON_SORT_NAME,
        		"pple."+UfadhiliData.PERSON_SUMMARY,
        		"pple."+UfadhiliData.PERSON_TITLE,
        		"pple."+UfadhiliData.PERSON_SUBTITLE,
        		"pple."+UfadhiliData.PERSON_IMAGE,
        		"pple."+UfadhiliData.PERSON_UPDATED
    			 
    			 
        		},
        		"pple."+UfadhiliData.PERSON_COUNTY + "=? AND "+"pple."+UfadhiliData.PERSON_ID + "=?", 
                new String[] { String.valueOf(county_id),String.valueOf(person_id) }, null, null, null, null);
    

    	Log.i("The person detail where clause",UfadhiliData.PERSON_COUNTY + "="+county_id+ " AND "  +UfadhiliData.PERSON_POSITION + "="+position_id+ " AND "  +UfadhiliData.PERSON_MAIN_ID + "="+person_id);
        Person person = null;
        if (cursor.getCount()> 0){
            cursor.moveToFirst();
 
        person =new Person(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
        		Integer.parseInt(cursor.getString(2)),
        		Integer.parseInt(cursor.getString(3)),
        		cursor.getString(4),
        		cursor.getString(5),
        		cursor.getString(6),
        		cursor.getString(7),
        		cursor.getString(8),
        		cursor.getString(9),
        		cursor.getString(10),
        		cursor.getString(11),
        		cursor.getString(12),
        		cursor.getString(13),
        		cursor.getString(14),
        		cursor.getString(15),
        		cursor.getString(16),
        		cursor.getString(17), 
        		cursor.getString(18),
        		cursor.getString(19),
        		cursor.getString(20)
        		
        		);
        // return county
        cursor.close();
        
        }
        return person;
    }
 // Getting All Counties
    public List<Person> getAllPersons() {
        List<Person> personlist = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + UfadhiliData.TABLE_PEOPLE;
 
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	
            	Person person =  new Person(
            			Integer.parseInt(cursor.getString(0)),
                		Integer.parseInt(cursor.getString(1)),
                		Integer.parseInt(cursor.getString(2)),
                		Integer.parseInt(cursor.getString(3)),
                		cursor.getString(4),
                		cursor.getString(5),
                		cursor.getString(6),
                		cursor.getString(7),
                		cursor.getString(8),
                		cursor.getString(9),
                		cursor.getString(10),
                		cursor.getString(11),
                		cursor.getString(12),
                		cursor.getString(13),
                		cursor.getString(14),
                		cursor.getString(15),
                		cursor.getString(16),
                		cursor.getString(17), 
                		cursor.getString(18),
                		cursor.getString(19),
                		cursor.getString(20)
                		
                		);
                // Adding county to list
            	personlist.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return personlist;
    }
    // Getting All Counties
    public List<Person> getGroupPersons(int county_id,int organisation_id) {
        List<Person> personlist = new ArrayList<Person>();
        database = dbHelper.getWritableDatabase();
      
        Cursor cursor = database.query(UfadhiliData.TABLE_PEOPLE+ " AS pple "
        +" LEFT JOIN "
        + UfadhiliData.TABLE_PERSON_ORGANIZATION +" AS po ON "  +"po."+UfadhiliData.PERSON_ID+ "="+"pple."+UfadhiliData.PERSON_MAIN_ID,
        
        new String[] {
        		 "pple."+UfadhiliData.PERSON_MAIN_ID ,
        		 "pple."+UfadhiliData.PERSON_ID ,
        	     "pple."+UfadhiliData.PERSON_NAME,
       	         "pple."+UfadhiliData.PERSON_ADDITIONAL_NAME,
       	         "pple."+UfadhiliData.PERSON_EMAIL,
       	         "pple."+UfadhiliData.PERSON_LEGAL_NAME,
       	         "pple."+UfadhiliData.PERSON_SLUG,
       	         "pple."+UfadhiliData.PERSON_TITLE,
       	         "pple."+UfadhiliData.PERSON_SUBTITLE,
       	         "pple."+UfadhiliData.PERSON_IMAGE,
    			
        		},
        		"pple."+UfadhiliData.PERSON_COUNTY + "=? "+ " AND "  +"po."+UfadhiliData.PERSON_ORGANISATION + "=?" , 
                new String[] { String.valueOf(county_id),String.valueOf(organisation_id) },null, null, null, null);
        Log.i("The where clause",UfadhiliData.PERSON_COUNTY + "="+county_id+ " AND "  +UfadhiliData.PERSON_ORGANISATION + "="+organisation_id);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	String[] position=new String[2];
            	position=getPersonPosition(Integer.parseInt(cursor.getString(0)),county_id);
            	
            	
            	Person person =  new Person(
    	        		Integer.parseInt(cursor.getString(1)),
    	        		(position[0]==null)?0:Integer.parseInt(position[0]),//position id
    	        		position[1],//position name
    	        		cursor.getString(2),
    	        		cursor.getString(3),
    	        		cursor.getString(4),
    	        		cursor.getString(5),
    	        		cursor.getString(6),
    	        		cursor.getString(7),
    	        		cursor.getString(8),
    	        		cursor.getString(9)
    	        		);
                // Adding county to list
            	personlist.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return personlist;
    }
    public String[]  getPersonPosition(int person_main_id,int county_id){
    	 database = dbHelper.getWritableDatabase();
         String[] position=new String[2]; 
         Cursor cursor = database.query(UfadhiliData.TABLE_POSITIONS+ " AS p LEFT JOIN "+UfadhiliData.TABLE_PERSON_POSITION+ " AS pp ON pp."+UfadhiliData.POSITION_ID+"="+"p."+UfadhiliData.POSITION_ID,
        		 new String[] {
        		 "p."+UfadhiliData.POSITION_ID ,
        		 "p."+UfadhiliData.POSITION_NAME,
       	         
        		},
        		"pp."+UfadhiliData.PERSON_ID + "=? AND pp."+UfadhiliData.PERSON_COUNTY + "=? " , 
                new String[] { String.valueOf(person_main_id),String.valueOf(county_id) },null, null, null, null);
        	 //Log.i("POSITION FOR THE PERSON",""+person_main_id);
	         if (cursor.moveToFirst()) {
	        	 do {
	        		 
	        	  Log.i("POSITION FOR THE PERSON",""+cursor.getString(1));
	        	  position[0]=cursor.getString(0);
	        	  position[1]=cursor.getString(1);
	        	 } while (cursor.moveToNext());
         }
         return position;
    }
 // Updating single county
    public int updatePerson(Person person) {
    	database = dbHelper.getWritableDatabase();
 
    	 ContentValues values = new ContentValues();
         values.put(UfadhiliData.PERSON_ID , person.getPerson_id());
	     values.put(UfadhiliData.PERSON_COUNTY, person.getCounty_id());
	     values.put(UfadhiliData.PERSON_NAME , person.getPerson_name());
		 values.put(UfadhiliData.PERSON_ADDITIONAL_NAME, person.getAdditional_name());
		 values.put(UfadhiliData.PERSON_BIOGRAPHY, person.getBiography());
		 values.put(UfadhiliData.PERSON_CREATED, person.getCreated());
		 values.put(UfadhiliData.PERSON_DOB, person.getDate_of_birth());
		 values.put(UfadhiliData.PERSON_DOD, person.getDate_of_death());
		 values.put(UfadhiliData.PERSON_EMAIL, person.getEmail());
		 values.put(UfadhiliData.PERSON_GENDER, person.getGender());
		 values.put(UfadhiliData.PERSON_LEGAL_NAME, person.getLegal_name());
		 values.put(UfadhiliData.PERSON_NID , person.getNational_identity());
		 values.put(UfadhiliData.PERSON_SLUG, person.getSlug());
		 values.put(UfadhiliData.PERSON_SORT_NAME, person.getSort_name());
		 values.put(UfadhiliData.PERSON_SUMMARY, person.getSummary());
		 values.put(UfadhiliData.PERSON_TITLE, person.getTitle());
		 values.put(UfadhiliData.PERSON_SUBTITLE, person.getSubtitle());
		 values.put(UfadhiliData.PERSON_IMAGE, person.getImage_url());
		 values.put(UfadhiliData.PERSON_UPDATED, person.getUpdated());
        // updating row
        int res= database.update(UfadhiliData.TABLE_PEOPLE, values, UfadhiliData.PERSON_ID + " = ?",
                new String[] { String.valueOf(person.getPerson_id()) });
        
        //  // Closing database connection
        Log.i("id of person updated on the database"," "+res);
        return res; 
        
    }
 
    // Deleting single county
    public void deletePerson(Person person) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_PEOPLE, UfadhiliData.PERSON_ID + " = ?",
                new String[] { String.valueOf(person.getPerson_id()) });
        
    }
 
 
    // Getting counties Count
    public int getPersonCount() {
        String countQuery = "SELECT  * FROM " + UfadhiliData.TABLE_PEOPLE;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
  //------------------------------------------------organizations table data manipulation------------------------------------------------- 
    
    // Adding new county
    public void addOrganisation(Organisation organisation) {
    	
    	database = dbHelper.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(UfadhiliData.ORGANISATION_ID, organisation.getID()); // county id
        values.put(UfadhiliData.ORGANISATION_NAME, organisation.getName()); // county name
        values.put(UfadhiliData.ORGANISATION_CREATED, organisation.getCreated()); // county created
        values.put(UfadhiliData.ORGANISATION_UPDATED, organisation.getUpdated()); // county updated
        
        Log.i("Organisation name added to the database"," "+organisation.getID()+"  "+organisation.getName());
        // Inserting Row
        database.insertWithOnConflict(UfadhiliData.TABLE_ORGANISATIONS, UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
       
    	
    }

    // Getting single county
    public Organisation getOrganisation(int id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_ORGANISATIONS, new String[] {
        		UfadhiliData.ORGANISATION_ID,
        		UfadhiliData.ORGANISATION_NAME, 
        		UfadhiliData.ORGANISATION_CREATED,
        		UfadhiliData.ORGANISATION_UPDATED },
        		UfadhiliData.ORGANISATION_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor.getCount()> 0)
            cursor.moveToFirst();
 
        Organisation org = new Organisation(
        		Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
                );
        // return county
        cursor.close();
        
        return org;
    }
    
 // Getting All Counties
    public List<Organisation> getAllOrganisations() {
        List<Organisation> organisationlist = new ArrayList<Organisation>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + UfadhiliData.TABLE_ORGANISATIONS;
 
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Organisation organisation = new Organisation();
            	organisation.setDB_ID(Integer.parseInt(cursor.getString(0)));
            	organisation.setID(Integer.parseInt(cursor.getString(1)));
            	organisation.setName(cursor.getString(2));
            	organisation.setCreated(cursor.getString(3));
            	organisation.setUpdated(cursor.getString(4));
                // Adding county to list
            	organisationlist.add(organisation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return organisationlist;
    }
 // Updating single county
    public int updateOrganisation(Organisation organisation) {
    	database = dbHelper.getWritableDatabase();
 
    	  ContentValues values = new ContentValues();
    	  values.put(UfadhiliData.ORGANISATION_ID, organisation.getID()); // county id
          values.put(UfadhiliData.ORGANISATION_NAME, organisation.getName()); // county name
          values.put(UfadhiliData.ORGANISATION_CREATED, organisation.getCreated()); // county created
          values.put(UfadhiliData.ORGANISATION_UPDATED, organisation.getUpdated()); // county updated
        // updating row
        int res= database.update(UfadhiliData.TABLE_ORGANISATIONS, values, UfadhiliData.ORGANISATION_ID + " = ?",
                new String[] { String.valueOf(organisation.getID()) });
        
        return res; 
        
    }
 
    // Deleting single county
    public void deleteOrganisation(Organisation organisation) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_ORGANISATIONS, UfadhiliData.ORGANISATION_ID + " = ?",
                new String[] { String.valueOf(organisation.getID()) });
        
    }
 
 
    // Getting counties Count
    public int getOrganisationCount() {
        String countQuery = "SELECT  * FROM " + UfadhiliData.TABLE_ORGANISATIONS;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
    
  //------------------------------------------------news table data manipulation------------------------------------------------- 
    // Adding new county
    @SuppressLint("NewApi")
	public void addNews(News newsItem) {
    	
    	database = dbHelper.getWritableDatabase();
 
    	ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, newsItem.getCounty_id()); // county id
        values.put(UfadhiliData.NEWS_ID, newsItem.getNews_id()); // county id
        values.put(UfadhiliData.NEWS_TITLE, newsItem.getTitle()); // county name
        values.put(UfadhiliData.NEWS_MESSAGE, newsItem.getMessage()); // county slug
        values.put(UfadhiliData.NEWS_PUBLICATION, newsItem.getPublication_date()); // county desc
        values.put(UfadhiliData.NEWS_IMAGEURL, newsItem.getImage_url()); // county location
        values.put(UfadhiliData.NEWS_ABSOLUTE_URL, newsItem.getAbsolute_url()); // county location
       
 
        // Inserting Row
        Log.i("News item to be inserted",newsItem.getTitle());
        database.insertWithOnConflict(UfadhiliData.TABLE_NEWS, UfadhiliData._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
      
    }

    // Getting single county
    public News getNewsItem(int id) {
    	
    	database = dbHelper.getReadableDatabase();
 
        Cursor cursor = database.query(UfadhiliData.TABLE_NEWS, new String[] {
        		UfadhiliData.COUNTY_ID,
        		UfadhiliData.NEWS_ID,
        		UfadhiliData.NEWS_TITLE, 
        		UfadhiliData.NEWS_MESSAGE,
        		UfadhiliData.NEWS_PUBLICATION,
        		UfadhiliData.NEWS_IMAGEURL ,
        		UfadhiliData.NEWS_ABSOLUTE_URL,
        		},
        		UfadhiliData.NEWS_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
    	News newsitem = new News(
        		Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
                
                );
               
        // return county
        cursor.close();
        
        return newsitem;
    }
    
 // Getting All Counties
    public List<News> getAllNews(int county_id) {
        List<News> newslist = new ArrayList<News>();
    
 
        database = dbHelper.getWritableDatabase();
        String[] columns= new String[] {
        		UfadhiliData.COUNTY_ID,
        		UfadhiliData.NEWS_ID,
        		UfadhiliData.NEWS_TITLE, 
        		UfadhiliData.NEWS_MESSAGE,
        		UfadhiliData.NEWS_PUBLICATION,
        		UfadhiliData.NEWS_IMAGEURL ,
        		UfadhiliData.NEWS_ABSOLUTE_URL,
        		};
        
        
        Cursor cursor = database.query(
        		true,UfadhiliData.TABLE_NEWS,
        		columns,
        		UfadhiliData.COUNTY_ID+ "=?",
                new String[] { String.valueOf(county_id) },
                null,
                null,
                UfadhiliData.NEWS_ID+" DESC",
                "500"
                );
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	News newsitem = new News(
            			Integer.parseInt(cursor.getString(0)),
                		Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                        
                        );
            	
                // Adding county to list
            	newslist.add(newsitem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        
        // return county list
        return newslist;
    }
 // Updating single county
    public int updateNews(News newsItem) {
    	database = dbHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
        values.put(UfadhiliData.COUNTY_ID, newsItem.getCounty_id()); // county id
        values.put(UfadhiliData.NEWS_ID, newsItem.getNews_id()); // county id
        values.put(UfadhiliData.NEWS_TITLE, newsItem.getTitle()); // county name
        values.put(UfadhiliData.NEWS_MESSAGE, newsItem.getMessage()); // county slug
        values.put(UfadhiliData.NEWS_PUBLICATION, newsItem.getPublication_date()); // county desc
        values.put(UfadhiliData.NEWS_IMAGEURL, newsItem.getImage_url()); // county location
        values.put(UfadhiliData.NEWS_ABSOLUTE_URL, newsItem.getAbsolute_url()); // county location
 
        // updating row
        int res= database.update(UfadhiliData.TABLE_NEWS, values, UfadhiliData.NEWS_ID + " = ?",
                new String[] { String.valueOf(newsItem.getNews_id()) });
        
        return res; 
        
    }
 
    // Deleting single county
    public void deleteNews(News news) {
    	database = dbHelper.getWritableDatabase();
    	database.delete(UfadhiliData.TABLE_NEWS, UfadhiliData.NEWS_ID + " = ?",
                new String[] { String.valueOf(news.getNews_id()) });
        
    }
 
 
    // Getting counties Count
    public int getNewsCount() {
        String countQuery = "SELECT  * FROM " + UfadhiliData.TABLE_NEWS;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }

}
