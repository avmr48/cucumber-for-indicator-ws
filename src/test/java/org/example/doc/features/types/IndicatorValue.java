package org.example.doc.features.types;

import lombok.Value;

import java.util.List;

@Value
public class IndicatorValue implements HasParentIndicator<String> {
    String indicatorId;
    String time;
    String place;
    String value;
    String goal;

    @Value
    public static class Catalog {
        List<IndicatorValue> list;
    }
}
