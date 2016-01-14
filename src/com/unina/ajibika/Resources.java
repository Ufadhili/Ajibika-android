package com.unina.ajibika;

public class Resources {
	String title,summary,type,file_link;
	int resource_id,county_id;
	
	 
	public Resources(String title, String summary, String file_link,
			int resource_id) {
		super();
		this.title = title;
		this.summary = summary;
		this.file_link = file_link;
		this.resource_id = resource_id;
	}

	public Resources(String title, String summary, String type,
			String file_link, int resource_id, int county_id) {
		super();
		this.title = title;
		this.summary = summary;
		this.type = type;
		this.file_link = file_link;
		this.resource_id = resource_id;
		this.county_id = county_id;
	}

	public Resources() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFile_link() {
		return file_link;
	}
	public void setFile_link(String file_link) {
		this.file_link = file_link;
	}
	public int getResource_id() {
		return resource_id;
	}
	public void setResource_id(int resource_id) {
		this.resource_id = resource_id;
	}
	public int getCounty_id() {
		return county_id;
	}
	public void setCounty_id(int county_id) {
		this.county_id = county_id;
	}
	
    
}
