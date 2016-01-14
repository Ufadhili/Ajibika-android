package com.unina.ajibika;

import com.unina.ajibika.R.integer;

public class Plans {
	 public Plans() {
		super();
		// TODO Auto-generated constructor stub
	}
	String title;
	String content;
	String file_url;
	 public Plans(String title, String content, String file_url) {
		super();
		this.title = title;
		this.content = content;
		this.file_url = file_url;
	}
	
	   
public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}



}
