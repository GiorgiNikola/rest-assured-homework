package ge.tbc.tbcitacademy.Models.Requests.RestfulBookerLombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@JsonPropertyOrder({"firstname", "lastname", "totalprice", "depositpaid", "bookingdates", "additionalneeds"})
@JsonIgnoreProperties({"salesprice"})
public class UpdateRequestLombok{
	@JsonProperty("firstname")
	private String firstName;
	@JsonProperty("additionalneeds")
	private String additionalNeeds;
	@JsonProperty("bookingdates")
	private BookingDatesLombok bookingDates;
	@JsonProperty("totalprice")
	private int totalPrice;
	@JsonProperty("depositpaid")
	private boolean depositPaid;
	@JsonProperty("lastname")
	private String lastName;
	private int salesprice;
	private String passportNo;
}