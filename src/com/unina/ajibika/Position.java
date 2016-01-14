package com.unina.ajibika;

public class Position {

	//private variables
    int position_id,county_id,organisation_id,_id;
    String position_name;
    String position_created;
    String position_updated;
     
    // Empty constructor
    public Position(){
         
    }
    // constructor
    public Position(
    		int id,
    		int county_id,
    		int org_id,
    		String position_name,
    		String position_created,
    		String position_updated
    		){
        this.position_id = id;
        this.county_id = county_id;
        this.organisation_id = org_id;
        this.position_name = position_name;
        this.position_created = position_created;
        this.position_updated = position_updated;
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
        return this.position_id;
    }
     
    // setting id
    public void setID(int id){
        this.position_id = id;
    }
 // getting ID
    public int getOrgID(){
        return this.organisation_id;
    }
     
    // setting id
    public void setOrgID(int id){
        this.organisation_id = id;
    }
 // getting ID
    public int getCountyID(){
        return this.county_id;
    }
     
    // setting id
    public void setCountyID(int id){
        this.county_id = id;
    }
     
    // getting name
    public String getName(){
        return this.position_name;
    }
   public void setName(String name){
        this.position_name = name;
    }
    
   
    // getting created
    public String getCreated(){
        return this.position_created;
    }
     
    // setting created
    public void setCreated(String created){
        this.position_created = created;
    }
    // getting updated
    public String getUpdated(){
        return this.position_updated;
    }
     
    // setting updated
    public void setUpdated(String updated){
        this.position_updated = updated;
    }
}