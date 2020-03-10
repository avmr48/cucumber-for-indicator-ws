package org.example;

import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;

public class JsonPathTest {
    @Test
    public void json() {
        JsonPath from = JsonPath.from(getClass().getResource("/db.json"));
        assertJsonPath(from.getString("indicators.id"), "[i_1, i_2]");
        assertJsonPath(from.getString("indicators[0].id"), "i_1");
        assertJsonPath(from.getString("indicators.related.id"), "[[i_50], [i_51]]");
        assertJsonPath(from.getString("indicators.related.flatten().find { it.id == 'i_50' }.id"), "i_50");
        assertJsonPath(from.getString("indicators.components.id"), "[[], []]");
        assertJsonPath(from.getString("indicators.values.value"), "[[1.2, null], [2.5]]");
        assertJsonPath(from.getString("indicators.values[0].value"), "[1.2, null]");
        assertJsonPath(from.getString("indicators.values[1].value"), "[2.5]");
    }

    private void assertJsonPath(Object queryResult, Object expected) {
        Assert.assertThat(queryResult, equalTo(expected));
    }
}
