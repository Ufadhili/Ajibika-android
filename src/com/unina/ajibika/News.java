package com.unina.ajibika;

public class News {
	int county_id;
	 int news_id;
	 String title;
	 String message;
	 String publication_date;
	 String image_url;
	 String absolute_url;
	
	 public News(int county_id, int news_id, String title, String message,
			String publication_date, String image_url, String absolute_url) {
		super();
		this.county_id = county_id;
		this.news_id = news_id;
		this.title = title;
		this.message = message;
		this.publication_date = publication_date;
		this.image_url = image_url;
		this.absolute_url = absolute_url;
	}


	
	 public News() {
	    	super();
	}
	  
  
	public int getNews_id() {
		return news_id;
	}


	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public String getAbsolute_url() {
		return absolute_url;
	}


	public void setAbsolute_url(String absolute_url) {
		this.absolute_url = absolute_url;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPublication_date() {
		return publication_date;
	}
	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	 public int getCounty_id() {
			return county_id;
	}

	public void setCounty_id(int county_id) {
			this.county_id = county_id;
	}

}
