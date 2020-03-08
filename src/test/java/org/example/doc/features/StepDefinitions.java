package org.example.doc.features;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.doc.features.types.*;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

public class StepDefinitions {

    public static final String WS_URL = "http://my-json-server.typicode.com/avmr48/json-placeholder/db";

    GetIndicatorRequest request;
    String requestRaw;

    Response response;
    JsonPath jsonPath;

    @When("I ask for indicators:")
    public void i_ask_for_indicators(GetIndicatorRequest request) {
        this.request = request;
        this.response = get(WS_URL);
        this.jsonPath = response.jsonPath();
    }

    @When("I call the webservice with:")
    public void i_call_the_webservice_with(String request) {
        this.requestRaw = request;
        this.response = get(WS_URL);
        this.jsonPath = response.jsonPath();
    }

    @Then("I should get list of indicators:")
    public void i_should_get_indicator(Indicator.Catalog expectedList) {
        // assert count
        Assert.assertThat(jsonPath.getList("indicators").size(), equalTo(expectedList.getList().size()));

        // assert table
        Common.forEach(expectedList.getList(), (expected, i) -> {
            String pathIndicator = "indicators[" + i + "]";
            Assert.assertThat(jsonPath.getString(pathIndicator + ".id"), equalTo(expected.getId()));
            Assert.assertThat(jsonPath.getString(pathIndicator + ".name"), equalTo(expected.getName()));
            Assert.assertThat(jsonPath.getString(pathIndicator + ".description"), equalTo(expected.getDescription()));
        });
    }

    @Then("indicator {string} should have values:")
    public void indicator_should_have_values(String indicatorId, IndicatorValue.Catalog expectedList) {
        String pathIndicator = "indicators.find{it.id == '" + indicatorId + "'}";
        String pathIndicatorValues = pathIndicator + ".values";

        int count = jsonPath.getList(pathIndicatorValues).size();
        Assert.assertThat(count, equalTo(expectedList.getList().size()));

        Common.forEach(expectedList.getList(), (expected, i) -> {
            String pathValue = pathIndicatorValues + "[" + i + "]";
            Assert.assertThat(jsonPath.getString(pathValue + ".time.value"), equalTo(expected.getTime()));
            Assert.assertThat(jsonPath.getString(pathValue + ".place"), equalTo(expected.getPlace()));
            Assert.assertThat(jsonPath.getDouble(pathValue + ".value"), equalTo(expected.getValue()));
            Assert.assertThat(jsonPath.getDouble(pathValue + ".goal"), equalTo(expected.getGoal()));
        });
    }

    /**
     * @deprecated
     */
    @Then("I should get indicator values:")
    public void i_should_get_indicator_values(IndicatorValue.Catalog expectedList) {
        Map<String, List<IndicatorValue>> valuesByIndicatorId =
                expectedList.getList().stream()
                        .collect(Collectors.groupingBy(IndicatorValue::getId, Collectors.toList()));

        valuesByIndicatorId.forEach((indicatorId, indicatorValues) -> {
            String pathIndicator = "indicators.find { it.id == '" + indicatorId + "' }";
            String pathIndicatorValues = pathIndicator + ".values";

            // assert indicator values count
            Assert.assertThat(jsonPath.getList(pathIndicatorValues).size(), equalTo(indicatorValues.size()));

            Common.forEach(indicatorValues, (expected, i) -> {
                String pathValue = pathIndicatorValues + "[" + i + "]";
                Assert.assertThat(jsonPath.getString(pathValue + ".time.value"), equalTo(expected.getTime()));
                Assert.assertThat(jsonPath.getString(pathValue + ".place"), equalTo(expected.getPlace()));
                Assert.assertThat(jsonPath.getDouble(pathValue + ".value"), equalTo(expected.getValue()));
                Assert.assertThat(jsonPath.getDouble(pathValue + ".goal"), equalTo(expected.getGoal()));
            });
        });
    }

    /**
     * @deprecated
     */
    @Then("I should get related indicators:")
    public void i_should_get_related_indicators(RelatedIndicator.Catalog expectedList) {
        Map<String, List<RelatedIndicator>> relatedListByIndicatorId =
                expectedList.getList().stream()
                        .collect(Collectors.groupingBy(RelatedIndicator::getIdIndicator, Collectors.toList()));

        relatedListByIndicatorId.forEach((indicatorId, relatedIndicators) -> {
            String pathIndicator = "indicators.find { it.id == '" + indicatorId + "' }";
            String pathIndicatorRelated = pathIndicator + ".related";

            // assert related indicators count
            Assert.assertThat(jsonPath.getList(pathIndicatorRelated).size(), equalTo(relatedIndicators.size()));

            Common.forEach(relatedIndicators, (expected, i) -> {
                String pathValue = pathIndicatorRelated + "[" + i + "]";
                Assert.assertThat(jsonPath.getString(pathValue + ".id"), equalTo(expected.getId()));
                Assert.assertThat(jsonPath.getString(pathValue + ".name"), equalTo(expected.getName()));
                Assert.assertThat(jsonPath.getString(pathValue + ".description"), equalTo(expected.getDescription()));
            });
        });
    }

    /**
     * @deprecated
     */
    @Then("I should get related indicators values:")
    public void i_should_get_related_indicators_values(RelatedIndicatorValue.Catalog expectedList) {
        Map<String, List<RelatedIndicatorValue>> valuesByRelatedIndicatorId =
                expectedList.getList().stream()
                        .collect(Collectors.groupingBy(RelatedIndicatorValue::getId, Collectors.toList()));

        valuesByRelatedIndicatorId.forEach((relatedIndicatorId, relatedIndicatorValues) -> {
            String pathRelatedIndicator = "indicators.related.flatten().find { it.id == '" + relatedIndicatorId + "' }";
            String pathRelatedIndicatorValues = pathRelatedIndicator + ".values";

            // assert related indicators values count
            Assert.assertThat(jsonPath.getList(pathRelatedIndicatorValues).size(), equalTo(relatedIndicatorValues.size()));

            Common.forEach(relatedIndicatorValues, (expected, i) -> {
                String pathValue = pathRelatedIndicatorValues + "[" + i + "]";
                Assert.assertThat(jsonPath.getString(pathValue + ".time.value"), equalTo(expected.getTime()));
                Assert.assertThat(jsonPath.getString(pathValue + ".place"), equalTo(expected.getPlace()));
                Assert.assertThat(jsonPath.getDouble(pathValue + ".value"), equalTo(expected.getValue()));
                Assert.assertThat(jsonPath.getDouble(pathValue + ".goal"), equalTo(expected.getGoal()));
            });
        });
    }

    @Then("I should have following response:")
    public void i_should_have_following_response(String responseRaw) throws IOException {
        String responseBody = response.body().print();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode a = objectMapper.readTree(responseBody);
        JsonNode b = objectMapper.readTree(responseRaw);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        Assert.assertThat(objectWriter.writeValueAsString(a), equalTo(objectWriter.writeValueAsString(b)));
    }
}
