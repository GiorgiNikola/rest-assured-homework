package ge.tbc.tbcitacademy.Models.Requests.Petstore;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private long id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}
}