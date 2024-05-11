package ge.tbc.tbcitacademy.Models.Responses.Swapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record PlanetsResponse(
	@JsonProperty("next") String next,
	@JsonProperty("previous") Object previous,
	@JsonProperty("count") int count,
	@JsonProperty("results") List<Planet> results
){
}