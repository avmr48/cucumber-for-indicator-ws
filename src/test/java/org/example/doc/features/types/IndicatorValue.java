package org.example.doc.features.types;

import lombok.Value;

import java.util.List;

@Value
public class IndicatorValue {
    String id;
    String time;
    String place;
    Double value;
    Double goal;

    @Value
    public static class Catalog {
        List<IndicatorValue> list;
    }
}
