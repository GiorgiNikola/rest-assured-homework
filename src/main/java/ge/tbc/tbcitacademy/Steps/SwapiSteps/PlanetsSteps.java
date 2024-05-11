package ge.tbc.tbcitacademy.Steps.SwapiSteps;

import ge.tbc.tbcitacademy.Data.Constants;
import ge.tbc.tbcitacademy.Models.Responses.Swapi.Planet;
import ge.tbc.tbcitacademy.Models.Responses.Swapi.PlanetsResponse;
import ge.tbc.tbcitacademy.Models.Responses.Swapi.Resident;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlanetsSteps {
    Response getPlanets;
    PlanetsResponse planetsResponse;
    List<Planet> planets;
    Response residentResponse;
    Resident resident;

    @Step("Sending request to get planets")
    public PlanetsSteps getResponse(RequestSpecification requestSpec){
        getPlanets = given(requestSpec)
                .when()
                .get("/planets/?format=json");
        return this;
    }

    @Step("Deserialize planets response into lombok pojo class")
    public PlanetsSteps deserializeResponse(){
        planetsResponse = getPlanets
                .then()
                .extract()
                .as(PlanetsResponse.class);
        planets = planetsResponse.results();
        return this;
    }

    @Step("We identify three most newly created planets")
    public PlanetsSteps identifyThreeMostRecentPlanets(){
        List<Planet> threeMostRecent = planets.stream()
                .sorted(Comparator.comparing(Planet::created).reversed())
                .limit(3)
                .toList();
        return this;
    }

    @Step("Five validations about planets")
    public PlanetsSteps fiveValidation(){
        for (Planet planet : planets){
            assertThat(planet.name(), notNullValue());
            assertThat(planet.rotationPeriod(), greaterThan(10));
            assertThat(planet.orbitalPeriod(),lessThan(6000));
            assertThat(planet.diameter(), greaterThanOrEqualTo(4500));
            assertThat(planet.climate(), notNullValue());
        }
        return this;
    }

    @Step("Get top 1 planet with rotation period and redirect to first resident url")
    public PlanetsSteps getPlanetWithBiggestRotationPeriod(){
        Optional<Planet> topPlanet = planets.stream()
                .min(Comparator.comparingInt(Planet::rotationPeriod));

        if (topPlanet.isPresent() && !topPlanet.get().residents().isEmpty()) {
            String residentUrl = topPlanet.get().residents().get(0);
            residentResponse = given()
                    .get(residentUrl);
        }
        return this;
    }

    @Step("Deserialize resident response into lombok pojo class")
    public PlanetsSteps deserializeResident(){
        resident = residentResponse
                .then()
                .extract()
                .as(Resident.class);
        return this;
    }

    @Step("Resident validations")
    public PlanetsSteps residentValidation(){
        assertThat(resident.getName(), equalTo(Constants.residentName));
        assertThat(resident.getHeight(), greaterThan(150));
        assertThat(resident.getBirthYear(), notNullValue());
        return this;
    }
}
