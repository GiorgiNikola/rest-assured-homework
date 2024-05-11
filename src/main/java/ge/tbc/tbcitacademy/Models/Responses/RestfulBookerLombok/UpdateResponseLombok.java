package ge.tbc.tbcitacademy.Models.Responses.RestfulBookerLombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"firstname", "lastname", "totalprice", "depositpaid", "bookingdates", "additionalneeds"})
public class UpdateResponseLombok{
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
}
