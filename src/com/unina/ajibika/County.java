package com.unina.ajibika;

public class County {
	//private variables
    int county_id,_id;
    String county_name;
    String county_slug;
    String county_desc;
    String county_location;
    String county_created;
    String county_updated;
     
    // Empty constructor
    public County(){
         
    }
    // constructor
    public County(
    		int id,
    		String county_name,
    		String county_slug,
    		String county_desc, 
    		String county_location,
    		String county_created,
    		String county_updated
    		){
        this.county_id = id;
        this.county_name = county_name;
        this.county_slug = county_slug;
        this.county_desc = county_desc;
        this.county_location = county_location;
        this.county_created = county_created;
        this.county_updated = county_updated;
    }
 // getting ID
    public int getDB_ID(){
        return this._id;
    }
    // setting id
    public void setDB_ID(int id){
        this._id = id;
    }
    // getting ID
    public int getID(){
        return this.county_id;
    }
     
    // setting id
    public void setID(int id){
        this.county_id = id;
    }
     
    // getting name
    public String getName(){
        return this.county_name;
    }
   public void setName(String name){
        this.county_name = name;
    }
    
    // getting slug
    public String getSlug(){
        return this.county_slug;
    }
    
    // setting slug
    public void setSlug(String slug){
        this.county_slug = slug;
    }
   
    // getting desc
    public String getDesc(){
        return this.county_desc;
    }
     
    // setting desc
    public void setDesc(String desc){
        this.county_desc = desc;
    }
    // getting location
    public String getLocation(){
        return this.county_location;
    }
     
    // setting location
    public void setLocation(String location){
        this.county_location = location;
    }
    // getting created
    public String getCreated(){
        return this.county_created;
    }
     
    // setting created
    public void setCreated(String created){
        this.county_created = created;
    }
    // getting updated
    public String getUpdated(){
        return this.county_updated;
    }
     
    // setting updated
    public void setUpdated(String updated){
        this.county_updated = updated;
    }
}
