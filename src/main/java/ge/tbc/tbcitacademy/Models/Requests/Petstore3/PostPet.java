package ge.tbc.tbcitacademy.Models.Requests.Petstore3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonPropertyOrder({"petId", "id", "status", "complete", "quantity", "shipDate"})
public class PostPet{

	@JsonProperty("petId")
	private int petId;

	@JsonProperty("quantity")
	private int quantity;

	@JsonProperty("id")
	private int id;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonProperty("shipDate")
	private String shipDate;

	@JsonProperty("complete")
	private boolean complete;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonProperty("status")
	private String status;
}