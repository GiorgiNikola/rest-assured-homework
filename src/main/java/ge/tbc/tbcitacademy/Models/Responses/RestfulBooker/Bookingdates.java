package ge.tbc.tbcitacademy.Models.Responses.RestfulBooker;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bookingdates{

	@JsonProperty("checkin")
	private String checkin;

	@JsonProperty("checkout")
	private String checkout;

	public void setCheckin(String checkin){
		this.checkin = checkin;
	}

	public String getCheckin(){
		return checkin;
	}

	public void setCheckout(String checkout){
		this.checkout = checkout;
	}

	public String getCheckout(){
		return checkout;
	}
}