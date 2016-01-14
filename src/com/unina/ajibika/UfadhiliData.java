package com.unina.ajibika;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UfadhiliData extends SQLiteOpenHelper {

	
	
	
    private static final String DEBUG_TAG = "Ufadhili Datastore";
    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "ufadhili_data";
 
    
    
    public static final String TABLE_COUNTIES = "counties";
    public static final String _ID = "id";
    public static final String COUNTY_ID = "county_id";
    public static final String COUNTY_NAME = "county_name";
    public static final String COUNTY_DESC = "county_summary";
    public static final String COUNTY_CREATED = "county_created";
    public static final String COUNTY_LOCATION = "county_location";
    public static final String COUNTY_UPDATED = "county_updated";
    public static final String COUNTY_SLUG = "county_slug";
  
    public static final String TABLE_POSITIONS = "positions";
    public static final String POSITION_ID = "position_id";
    public static final String POSITION_NAME = "position_name";
    public static final String POSITION_CREATED = "position_created";
    public static final String POSITION_UPDATED = "position_updated";


    public static final String TABLE_NEWS = "news";
    public static final String NEWS_ID="news_id";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_MESSAGE= "message";
    public static final String NEWS_PUBLICATION = "publication_date";
    public static final String NEWS_ABSOLUTE_URL="absolute_url";
    public static final String NEWS_IMAGEURL= "image_url";
    
    public static final String TABLE_RESOURCES = "resources";
    public static final String RESOURCES_ID="resource_id";
    public static final String RESOURCES_TITLE = "title";
    public static final String RESOURCES_SUMMARY= "summary";
    public static final String RESOURCES_TYPE = "type";
    public static final String RESOURCES_DOWNLOAD_LINK="download_link";
   
    
    public static final String TABLE_PROJECTS = "projects";
    public static final String PROJECT_ID="news_id";
    public static final String PROJECT_TITLE = "title";
    public static final String PROJECT_SUMMARY= "summary";
    public static final String PROJECT_COST="estimate_cost";
    public static final String PROJECT_LOCATION= "location";
    public static final String PROJECT_ABSOLUTE_URL="absolute_url";
    public static final String PROJECT_CONTRACTOR="contractor";
    public static final String PROJECT_FUNDING="year_funded";
    public static final String PROJECT_STATUS="status";
    public static final String PROJECT_IMAGEURL= "image_url";
    
    public static final String TABLE_ORGANISATIONS = "organisations";
    public static final String ORGANISATION_ID = "organisation_id";
    public static final String ORGANISATION_NAME = "organisation_name";
    public static final String ORGANISATION_CREATED = "organisation_created";
    public static final String ORGANISATION_UPDATED = "organisation_updated";
    
    
    public static final String TABLE_PERSON_ORGANIZATION = "person_organization"; 
    public static final String TABLE_PERSON_POSITION = "person_position"; 
    
    //persons table
     public static final String TABLE_PEOPLE = "people";
     public static final String PERSON_ID="person_id";
     public static final String PERSON_MAIN_ID="person_main_id";
     
     public static final String PERSON_COUNTY="county_id";
     public static final String PERSON_ORGANISATION="organisation_id";
     public static final String PERSON_POSITION="position_id";
	
	
	 public static final String PERSON_ADDITIONAL_NAME="additional_name";
	 public static final String PERSON_BIOGRAPHY="biography";
	 public static final String PERSON_CREATED="created";
	 public static final String PERSON_DOB="date_of_birth";
	 public static final String PERSON_DOD="date_of_death";
	 public static final String PERSON_EMAIL="email";
	 public static final String PERSON_NAME="person_name";
	 public static final String PERSON_GENDER="gender";
	 public static final String PERSON_LEGAL_NAME="legal_name";
	 public static final String PERSON_NID="national_identity";
	 public static final String PERSON_SLUG="slug";
	 public static final String PERSON_SORT_NAME="sort_name";
	 public static final String PERSON_SUMMARY="summary";
	 public static final String PERSON_TITLE="title";
	 public static final String PERSON_SUBTITLE="subtitle";
	 public static final String PERSON_IMAGE="profile_photo";
	 public static final String PERSON_UPDATED="updated";
	 
	 
	 private static final String CREATE_TABLE_PERSON_ORGANIZATION = "create table " + TABLE_PERSON_ORGANIZATION
			    + " (" + _ID + " integer primary key autoincrement, "
			    		+ PERSON_ID + " integer not null , "
			            + PERSON_ORGANISATION + " integer  null ,"
			            + COUNTY_ID + " integer not null);";
	 private static final String CREATE_TABLE_PERSON_POSITION = "create table " + TABLE_PERSON_POSITION
			    + " (" + _ID + " integer primary key autoincrement, "
			    		+ PERSON_ID + " integer not null , "
			            + PERSON_POSITION + " integer  null ,"
			            + COUNTY_ID + " integer not null);";
     
    private static final String CREATE_TABLE_COUNTIES = "create table " + TABLE_COUNTIES
    + " (" + _ID + " integer primary key autoincrement, "
    		+ COUNTY_ID + " integer not null unique , "
            + COUNTY_NAME + " text not null," 
            + COUNTY_SLUG + " text not null,"
            + COUNTY_DESC + " text not null," 
            + COUNTY_LOCATION + " text not null,"
            + COUNTY_CREATED + " text not null," 
            + COUNTY_UPDATED + " text not null);";
    
    private static final String CREATE_TABLE_POSITIONS = "create table " + TABLE_POSITIONS
    	    + " (" + _ID + " integer primary key autoincrement, "
    	            + COUNTY_ID + " integer not null, "
    	    		+ POSITION_ID + " integer not null unique , "
    	    		+ ORGANISATION_ID + " integer not null , "
    	            + POSITION_NAME + " text not null," 
    	            + POSITION_CREATED + " text not null," 
    	            + POSITION_UPDATED + " text not null);";
    
    private static final String CREATE_TABLE_NEWS = "create table " +TABLE_NEWS
    	    + " (" + _ID + " integer primary key autoincrement, "
    	            + COUNTY_ID + " integer not null, "
    	            + NEWS_ID + " integer not null unique , "
    	    		+ NEWS_TITLE + " text not null," 
    	            + NEWS_MESSAGE + " text not null," 
    	            + NEWS_PUBLICATION + " text not null," 
    	            + NEWS_ABSOLUTE_URL + " text not null," 
    	            + NEWS_IMAGEURL + " text not null);";
    private static final String CREATE_TABLE_RESOURCES = "create table " +TABLE_RESOURCES
    	    + " (" + _ID + " integer primary key autoincrement, "
    	            + COUNTY_ID + " integer not null, "
    	            + RESOURCES_ID + " integer not null unique , "
    	    		+ RESOURCES_TITLE + " text not null," 
    	            + RESOURCES_SUMMARY+ " text not null," 
    	            + RESOURCES_TYPE + " text not null," 
    	            + RESOURCES_DOWNLOAD_LINK + " text not null);";
    
    private static final String CREATE_TABLE_PROJECTS = "create table " +TABLE_PROJECTS 
    	    + " (" + _ID + " integer primary key autoincrement, "
    	            + COUNTY_ID + " integer not null, "
    	            + PROJECT_ID + " integer not null unique , "
    	    		+ PROJECT_TITLE + " text not null," 
    	            + PROJECT_SUMMARY + " text not null," 
    	            + PROJECT_LOCATION + " text not null," 
    	            + PROJECT_STATUS + " text not null," 
    	            + PROJECT_CONTRACTOR + " text not null," 
    	             + PROJECT_ABSOLUTE_URL + " text not null," 
    	            + PROJECT_FUNDING + " text not null," 
    	            + PROJECT_COST + " text not null," 
    	            + PROJECT_IMAGEURL + " text not null);";
    
    private static final String CREATE_TABLE_ORGANISATIONS = "create table " + TABLE_ORGANISATIONS
    	    + " ("  + _ID + " integer primary key autoincrement, "
    	            + ORGANISATION_ID + " integer not null unique , "
    	            + ORGANISATION_NAME + " text not null," 
    	            + ORGANISATION_CREATED + " text not null," 
    	            + ORGANISATION_UPDATED + " text not null);";
    
    private static final String CREATE_TABLE_PEOPLE = "create table " + TABLE_PEOPLE
		    	     + " (" + _ID + " integer primary key autoincrement, "
		    	     + PERSON_ID + " text not null," 
		    	     + PERSON_MAIN_ID + " text not null unique,"
		    	     + PERSON_COUNTY + " text not null," 
				     + PERSON_NAME + " text not null," 
					 + PERSON_ADDITIONAL_NAME + " text not null," 
					 + PERSON_BIOGRAPHY + " text not null," 
					 + PERSON_CREATED + " text not null," 
					 + PERSON_DOB + " text not null," 
					 + PERSON_DOD + " text not null," 
					 + PERSON_EMAIL + " text not null," 
				     + PERSON_GENDER + " text not null," 
					 + PERSON_LEGAL_NAME + " text not null," 
					 + PERSON_NID + " text not null," 
					 + PERSON_SLUG + " text not null," 
					 + PERSON_SORT_NAME + " text not null," 
					 + PERSON_SUMMARY + " text not null," 
					 + PERSON_TITLE + " text not null,"
					 + PERSON_SUBTITLE + " text not null,"
					 + PERSON_IMAGE + " text not null," 
					 + PERSON_UPDATED + " text not null);";
    
    
     
    private static final String DB_SCHEMA_COUNTIES = CREATE_TABLE_COUNTIES;
    private static final String DB_SCHEMA_POSITIONS =		CREATE_TABLE_POSITIONS;
    private static final String DB_SCHEMA_ORGANISATIONS =CREATE_TABLE_ORGANISATIONS;
    private static final String DB_SCHEMA_PEOPLE =CREATE_TABLE_PEOPLE;
    private static final String DB_SCHEMA_NEWS =CREATE_TABLE_NEWS;
    private static final String DB_SCHEMA_PROJECTS =CREATE_TABLE_PROJECTS;
    private static final String DB_SCHEMA_RESOURCES=CREATE_TABLE_RESOURCES;
    private static final String DB_SCHEMA_PERSON_ORGANIZATION=CREATE_TABLE_PERSON_ORGANIZATION;
    private static final String DB_SCHEMA_PERSON_POSITION=CREATE_TABLE_PERSON_POSITION;
    public UfadhiliData(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	db.execSQL(DB_SCHEMA_COUNTIES);
    	db.execSQL(DB_SCHEMA_POSITIONS);
    	db.execSQL(DB_SCHEMA_ORGANISATIONS);
    	db.execSQL(DB_SCHEMA_PEOPLE);
    	db.execSQL(DB_SCHEMA_NEWS);
    	db.execSQL(DB_SCHEMA_PROJECTS);
    	db.execSQL(DB_SCHEMA_RESOURCES);
    	db.execSQL(DB_SCHEMA_PERSON_ORGANIZATION);
    	db.execSQL(DB_SCHEMA_PERSON_POSITION);
    	
    	
    	
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	    Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
    	            + oldVersion + "]->[" + newVersion + "]");
    	  
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTIES);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITIONS);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORGANISATIONS);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESOURCES);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON_ORGANIZATION);
    	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON_POSITION);
    	    onCreate(db);
    }
    
}
