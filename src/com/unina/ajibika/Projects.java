package com.unina.ajibika;

public class Projects {
   String title,summary,contractor,year_funded,image,absolute_url,loacation,status,estimate_cost;
 
int county_id,project_id;
   
public Projects() {
	super();
	// TODO Auto-generated constructor stub
}
public Projects(String title, String summary, String contractor,
		String year_funded,String estimate_cost, String image, String absolute_url,
		String loacation, String status, int county_id, int project_id) {
	super();
	this.title = title;
	this.summary = summary;
	this.contractor = contractor;
	this.year_funded = year_funded;
	this.estimate_cost = estimate_cost;
	this.image = image;
	this.absolute_url = absolute_url;
	this.loacation = loacation;
	this.status = status;
	this.county_id = county_id;
	this.project_id = project_id;
}
public String getEstimate_cost() {
	return estimate_cost;
}
public void setEstimate_cost(String estimate_cost) {
	this.estimate_cost = estimate_cost;
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
public String getContractor() {
	return contractor;
}
public void setContractor(String contractor) {
	this.contractor = contractor;
}
public String getYear_funded() {
	return year_funded;
}
public void setYear_funded(String year_funded) {
	this.year_funded = year_funded;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public String getAbsolute_url() {
	return absolute_url;
}
public void setAbsolute_url(String absolute_url) {
	this.absolute_url = absolute_url;
}
public String getLoacation() {
	return loacation;
}
public void setLoacation(String loacation) {
	this.loacation = loacation;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public int getCounty_id() {
	return county_id;
}
public void setCounty_id(int county_id) {
	this.county_id = county_id;
}
public int getProject_id() {
	return project_id;
}
public void setProject_id(int project_id) {
	this.project_id = project_id;
}
}
