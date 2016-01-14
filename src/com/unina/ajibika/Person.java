package com.unina.ajibika;

public class Person {
	 int person_id;
	 int person_main_id;
	 int county_id;
	 int organisation_id;
	 int position_id;
	
	 String person_name;
	 String additional_name;
	 String biography;
	 String created;
	 String date_of_birth;
	 String date_of_death;
	 String email;
	 String gender;
	 String legal_name;
	 String national_identity;
	 String slug;
	 String sort_name;
	 String summary;
	
	String title;
	 String subtitle;
	 String image_url;
	 public int getPerson_main_id() {
			return person_main_id;
		}
		public void setPerson_main_id(int person_main_id) {
			this.person_main_id = person_main_id;
		}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	 public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	String updated;
	private String position_name;
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}
	public int getCounty_id() {
		return county_id;
	}
	public void setCounty_id(int county_id) {
		this.county_id = county_id;
	}
	public int getOrganisation_id() {
		return organisation_id;
	}
	public void setOrganisation_id(int organisation_id) {
		this.organisation_id = organisation_id;
	}
	public int getPosition_id() {
		return position_id;
	}
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	public String getPerson_name() {
		return person_name;
	}
	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}
	public String getAdditional_name() {
		return additional_name;
	}
	public void setAdditional_name(String additional_name) {
		this.additional_name = additional_name;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getDate_of_death() {
		return date_of_death;
	}
	public void setDate_of_death(String date_of_death) {
		this.date_of_death = date_of_death;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLegal_name() {
		return legal_name;
	}
	public void setLegal_name(String legal_name) {
		this.legal_name = legal_name;
	}
	public String getNational_identity() {
		return national_identity;
	}
	public void setNational_identity(String national_identity) {
		this.national_identity = national_identity;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getSort_name() {
		return sort_name;
	}
	public void setSort_name(String sort_name) {
		this.sort_name = sort_name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	/**
	 * @return the position_name
	 */
	public String getPosition_name() {
		return position_name;
	}
	/**
	 * @param position_name the position_name to set
	 */
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public Person(int person_id,int person_main_id, int county_id, int organisation_id,
			int position_id, String person_name, String additional_name,
			String biography, String created, String date_of_birth,
			String date_of_death, String email, String gender,
			String legal_name, String national_identity, String slug,
			String sort_name, String summary, String title, String subtitle,String image_url, String updated) {
		super();
		this.person_id = person_id;
		this.person_main_id = person_main_id;
		this.county_id = county_id;
		this.organisation_id = organisation_id;
		this.position_id = position_id;
		this.person_name = person_name;
		this.additional_name = additional_name;
		this.biography = biography;
		this.created = created;
		this.date_of_birth = date_of_birth;
		this.date_of_death = date_of_death;
		this.email = email;
		this.gender = gender;
		this.legal_name = legal_name;
		this.national_identity = national_identity;
		this.slug = slug;
		this.sort_name = sort_name;
		this.summary = summary;
		this.title = title;
		this.subtitle = subtitle;
		this.image_url=image_url;
		this.updated = updated;
	}
	public Person(int person_id,int person_main_id, int county_id,
			int position_id, String person_name, String additional_name,
			String biography, String created, String date_of_birth,
			String date_of_death, String email, String gender,
			String legal_name, String national_identity, String slug,
			String sort_name, String summary, String title, String subtitle,String image_url, String updated) {
		super();
		this.person_id = person_id;
		this.person_main_id = person_main_id;
		this.county_id = county_id;
		this.position_id = position_id;
		this.person_name = person_name;
		this.additional_name = additional_name;
		this.biography = biography;
		this.created = created;
		this.date_of_birth = date_of_birth;
		this.date_of_death = date_of_death;
		this.email = email;
		this.gender = gender;
		this.legal_name = legal_name;
		this.national_identity = national_identity;
		this.slug = slug;
		this.sort_name = sort_name;
		this.summary = summary;
		this.title = title;
		this.subtitle = subtitle;
		this.image_url=image_url;
		this.updated = updated;
	}
	public Person(int person_id,int position_id,
			String position_name, String person_name, String additional_name, String email,
			String legal_name,String slug, String title,String subtitle,String image_url) {
		super();
		this.person_id = person_id;
		
		this.position_id = position_id;
		this.position_name=position_name;
		this.person_name = person_name;
		this.additional_name = additional_name;
		this.email = email;
		this.legal_name = legal_name;
		this.slug = slug;
		this.title = title;
		this.subtitle = subtitle;
		this.image_url = image_url;
		
	}
	
	
	
}
