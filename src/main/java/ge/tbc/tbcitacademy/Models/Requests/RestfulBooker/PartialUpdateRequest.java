package ge.tbc.tbcitacademy.Models.Requests.RestfulBooker;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartialUpdateRequest{

	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("lastname")
	private String lastname;

	public void setFirstname(String firstname){
		this.firstname = firstname;
	}

	public String getFirstname(){
		return firstname;
	}

	public void setLastname(String lastname){
		this.lastname = lastname;
	}

	public String getLastname(){
		return lastname;
	}
}