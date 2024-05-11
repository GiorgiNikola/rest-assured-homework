package ge.tbc.tbcitacademy.Models.Responses.Swapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;


public record Planet (
	@JsonProperty("films") List<String> films,

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("edited") LocalDateTime edited,

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("created") LocalDateTime created,

	@JsonProperty("climate") String climate,
	@JsonProperty("rotation_period") int rotationPeriod,
	@JsonProperty("url") String url,
	@JsonProperty("population") String population,
	@JsonProperty("orbital_period") int orbitalPeriod,
	@JsonProperty("surface_water") String surfaceWater,
	@JsonProperty("diameter") int diameter,
	@JsonProperty("gravity") String gravity,
	@JsonProperty("name") String name,
	@JsonProperty("residents") List<String> residents,
	@JsonProperty("terrain") String terrain
){

		}