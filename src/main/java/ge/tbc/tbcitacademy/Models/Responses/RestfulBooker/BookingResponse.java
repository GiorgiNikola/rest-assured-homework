package ge.tbc.tbcitacademy.Models.Responses.RestfulBooker;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingResponse{

	@JsonProperty("booking")
	private Booking booking;

	@JsonProperty("bookingid")
	private int bookingid;

	public void setBooking(Booking booking){
		this.booking = booking;
	}

	public Booking getBooking(){
		return booking;
	}

	public void setBookingid(int bookingid){
		this.bookingid = bookingid;
	}

	public int getBookingid(){
		return bookingid;
	}
}