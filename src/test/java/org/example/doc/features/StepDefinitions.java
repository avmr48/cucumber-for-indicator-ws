package org.example.doc.features;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.doc.features.types.HasParentIndicator;
import org.example.doc.features.types.*;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

public class StepDefinitions {

    private static final String WS_URL = "http://my-json-server.typicode.com/avmr48/json-placeholder/db";

    private static final String PATH_INDICATORS = "indicators";
    private static final String PATH_INDICATOR_ID = "indicators[%d].id";
    private static final String PATH_INDICATOR_NAME = "indicators[%d].name";
    private static final String PATH_INDICATOR_DESCRIPTION = "indicators[%d].description";

    private static final String PATH_INDICATORS_VALUES = "indicators.find{it.id == '%s'}.values";
    private static final String PATH_INDICATOR_VALUE_TIME = "indicators.find{it.id == '%s'}.values[%d].time.value";
    private static final String PATH_INDICATORS_VALUE_PLACE = "indicators.find{it.id == '%s'}.values[%d].place";
    private static final String PATH_INDICATORS_VALUE_VALUE = "indicators.find{it.id == '%s'}.values[%d].value";
    private static final String PATH_INDICATORS_VALUE_GOAL = "indicators.find{it.id == '%s'}.values[%d].goal";

    private static final String PATH_RELATED_INDICATORS = "indicators.find { it.id == '%s' }.related";
    private static final String PATH_RELATED_INDICATOR_ID = "indicators.find { it.id == '%s' }.related[%d].id";
    private static final String PATH_RELATED_INDICATOR_NAME = "indicators.find { it.id == '%s' }.related[%d].name";
    private static final String PATH_RELATED_INDICATOR_DESCRIPTION = "indicators.find { it.id == '%s' }.related[%d].description";

    public static final String PATH_RELATED_INDICATOR_VALUES = "indicators.related.flatten().find { it.id == '%s' }.values";
    private static final String PATH_RELATED_INDICATOR_VALUE_TIME = "indicators.related.flatten().find { it.id == '%s' }.values[%d].time.value";
    private static final String PATH_RELATED_INDICATOR_VALUE_PLACE = "indicators.related.flatten().find { it.id == '%s' }.values[%d].place";
    private static final String PATH_RELATED_INDICATOR_VALUE_VAL = "indicators.related.flatten().find { it.id == '%s' }.values[%d].value";
    private static final String PATH_RELATED_INDICATOR_VALUE_GOAL = "indicators.related.flatten().find { it.id == '%s' }.values[%d].goal";

    GetIndicatorRequest request;
    String requestRaw;

    Response response;
    JsonPath jsonPath;

    private <ID, E extends HasParentIndicator<ID>> Map<ID, List<E>> indexByParentIndicatorId(List<E> list) {
        return list.stream()
                .collect(Collectors.groupingBy(
                        E::getIndicatorId,
                        Collectors.toList()
                ));
    }

    // =================================================================================================================

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

    // =================================================================================================================
    // Indicator

    @Then("I should get list of indicators:")
    public void i_should_get_indicator(Indicator.Catalog expectedList) {
        Assert.assertThat(jsonPath.getList(PATH_INDICATORS).size(), equalTo(expectedList.getList().size()));

        Common.forEach(expectedList.getList(), (expected, i) -> {
            Assert.assertThat(jsonPath.getString(String.format(PATH_INDICATOR_ID, i)), equalTo(expected.getId()));
            Assert.assertThat(jsonPath.getString(String.format(PATH_INDICATOR_NAME, i)), equalTo(expected.getName()));
            if (expected.getDescription() != null) {
                Assert.assertThat(jsonPath.getString(String.format(PATH_INDICATOR_DESCRIPTION, i)), equalTo(expected.getDescription()));
            }
        });
    }

    // =================================================================================================================
    // Indicator values

    @Then("indicator {string} should have values:")
    public void indicator_should_have_values(String indicatorId, IndicatorValue.Catalog expectedList) {
        assertAllIndicatorValues(indicatorId, expectedList.getList());
    }

    @Then("I should get indicator values:")
    public void i_should_get_indicator_values(IndicatorValue.Catalog expectedList) {
        Map<String, List<IndicatorValue>> expectedValuesByIndicatorId = indexByParentIndicatorId(expectedList.getList());

        expectedValuesByIndicatorId.forEach(this::assertAllIndicatorValues);
    }

    private void assertAllIndicatorValues(String indicatorId, List<IndicatorValue> expectedIndicatorValues) {
        assertIndicatorValuesCount(indicatorId, expectedIndicatorValues);
        Common.forEach(expectedIndicatorValues, (expectedValue, i) -> {
            assertIndicatorValue(indicatorId, i, expectedValue);
        });
    }

    private void assertIndicatorValuesCount(String indicatorId, List<IndicatorValue> expectedIndicatorValues) {
        int count = jsonPath.getList(String.format(PATH_INDICATORS_VALUES, indicatorId)).size();
        Assert.assertThat("Indicator values count is wrong", count, equalTo(expectedIndicatorValues.size()));
    }

    private void assertIndicatorValue(String indicatorId, Integer valueIndex, IndicatorValue expectedValue) {
        Assert.assertThat(jsonPath.getString(String.format(PATH_INDICATOR_VALUE_TIME, indicatorId, valueIndex)), equalTo(expectedValue.getTime()));
        Assert.assertThat(jsonPath.getString(String.format(PATH_INDICATORS_VALUE_PLACE, indicatorId, valueIndex)), equalTo(expectedValue.getPlace()));
        Assert.assertThat(jsonPath.getString(String.format(PATH_INDICATORS_VALUE_VALUE, indicatorId, valueIndex)), equalTo(expectedValue.getValue()));
        Assert.assertThat(jsonPath.getString(String.format(PATH_INDICATORS_VALUE_GOAL, indicatorId, valueIndex)), equalTo(expectedValue.getGoal()));
    }

    // =================================================================================================================
    // related indicators

    @Then("indicator {string} should have related:")
    public void indicator_should_have_related(String indicatorId, RelatedIndicator.Catalog expectedList) {
        assertAllRelated(indicatorId, expectedList.getList());
    }

    @Then("I should get related indicators:")
    public void i_should_get_related_indicators(RelatedIndicator.Catalog expectedList) {
        Map<String, List<RelatedIndicator>> relatedListByIndicatorId = indexByParentIndicatorId(expectedList.getList());
        relatedListByIndicatorId.forEach(this::assertAllRelated);
    }

    private void assertAllRelated(String indicatorId, List<RelatedIndicator> relatedIndicators) {
        assertRelatedCount(relatedIndicators, indicatorId);
        Common.forEach(relatedIndicators, (expectedRelatedIndicator, i) -> {
            assertRelatedIndicator(indicatorId, i, expectedRelatedIndicator);
        });
    }

    private void assertRelatedCount(List<RelatedIndicator> relatedIndicators, String indicatorId) {
        int count = jsonPath.getList(String.format(PATH_RELATED_INDICATORS, indicatorId)).size();
        Assert.assertThat("Related indicators count is wrong", count, equalTo(relatedIndicators.size()));
    }

    private void assertRelatedIndicator(String indicatorId, Integer i, RelatedIndicator expected) {
        Assert.assertThat(jsonPath.getString(String.format(PATH_RELATED_INDICATOR_ID, indicatorId, i)), equalTo(expected.getId()));
        Assert.assertThat(jsonPath.getString(String.format(PATH_RELATED_INDICATOR_NAME, indicatorId, i)), equalTo(expected.getName()));
        if (expected.getDescription() != null) {
            Assert.assertThat(jsonPath.getString(String.format(PATH_RELATED_INDICATOR_DESCRIPTION, indicatorId, i)), equalTo(expected.getDescription()));
        }
    }

    // =================================================================================================================
    // Related indicators values

    @Then("related indicator {string} should have values:")
    public void related_indicator_x_should_have_values(String relatedIndicatorId, RelatedIndicatorValue.Catalog expectedList) {
        assertAllRelatedIndicatorValues(relatedIndicatorId, expectedList.getList());
    }

    @Then("I should get related indicators values:")
    public void i_should_get_related_indicators_values(RelatedIndicatorValue.Catalog expectedList) {
        Map<String, List<RelatedIndicatorValue>> valuesByRelatedIndicatorId =
                indexByParentIndicatorId(expectedList.getList());

        valuesByRelatedIndicatorId.forEach(this::assertAllRelatedIndicatorValues);
    }

    private void assertAllRelatedIndicatorValues(String relatedIndicatorId, List<RelatedIndicatorValue> list) {
        assertRelatedIndicatorValuesCount(relatedIndicatorId, list);
        Common.forEach(list, (expected, i) -> {
            assertRelatedIndicatorValue(relatedIndicatorId, i, expected);
        });
    }

    private void assertRelatedIndicatorValuesCount(String relatedIndicatorId, List<RelatedIndicatorValue> relatedIndicatorValues) {
        List<Object> list = jsonPath.getList(String.format(PATH_RELATED_INDICATOR_VALUES, relatedIndicatorId));
        Assert.assertThat(list.size(), equalTo(relatedIndicatorValues.size()));
    }

    private void assertRelatedIndicatorValue(String relatedIndicatorId, Integer i, RelatedIndicatorValue expected) {
        Assert.assertThat(jsonPath.getString(String.format(PATH_RELATED_INDICATOR_VALUE_TIME, relatedIndicatorId, i)), equalTo(expected.getTime()));
        Assert.assertThat(jsonPath.getString(String.format(PATH_RELATED_INDICATOR_VALUE_PLACE, relatedIndicatorId, i)), equalTo(expected.getPlace()));
        Assert.assertThat(jsonPath.getString(String.format(PATH_RELATED_INDICATOR_VALUE_VAL, relatedIndicatorId, i)), equalTo(expected.getValue()));
        Assert.assertThat(jsonPath.getString(String.format(PATH_RELATED_INDICATOR_VALUE_GOAL, relatedIndicatorId, i)), equalTo(expected.getGoal()));
    }

    // =================================================================================================================
    // Raw

    @Then("I should have following response:")
    public void i_should_have_following_response(String responseRaw) throws IOException {
        String responseBody = response.body().print();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode a = objectMapper.readTree(responseBody);
        JsonNode b = objectMapper.readTree(responseRaw);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        Assert.assertThat(objectWriter.writeValueAsString(a), equalTo(objectWriter.writeValueAsString(b)));
    }

    // =================================================================================================================
}
