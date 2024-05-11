package ge.tbc.tbcitacademy.Models.Responses.RestfulBookerLombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"checkin, checkout"})
public class BookingDatesLombok {
    @JsonProperty("checkin")
    private String checkIn;
    @JsonProperty("checkout")
    private String checkOut;
}
