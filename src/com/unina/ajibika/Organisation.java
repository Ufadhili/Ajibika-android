package com.unina.ajibika;

public class Organisation {

	//private variables
    int organisation_id,_id;
    String organisation_name;
    String organisation_created;
    String organisation_updated;
     
    // Empty constructor
    public Organisation(){
         
    }
    // constructor
    public Organisation(
    		int id,
    		String organisation_name,
    		String organisation_created,
    		String organisation_updated
    		){
        this.organisation_id = id;
        this.organisation_name = organisation_name;
        this.organisation_created = organisation_created;
        this.organisation_updated = organisation_updated;
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
        return this.organisation_id;
    }
     
    // setting id
    public void setID(int id){
        this.organisation_id = id;
    }
     
    // getting name
    public String getName(){
        return this.organisation_name;
    }
   public void setName(String name){
        this.organisation_name = name;
    }
    
   
    // getting created
    public String getCreated(){
        return this.organisation_created;
    }
     
    // setting created
    public void setCreated(String created){
        this.organisation_created = created;
    }
    // getting updated
    public String getUpdated(){
        return this.organisation_updated;
    }
     
    // setting updated
    public void setUpdated(String updated){
        this.organisation_updated = updated;
    }
}