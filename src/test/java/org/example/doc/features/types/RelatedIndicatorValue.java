package org.example.doc.features.types;

import lombok.Value;

import java.util.List;

@Value
public class RelatedIndicatorValue {
    String id;
    String time;
    String place;
    Double value;
    Double goal;

    @Value
    public static class Catalog {
        List<RelatedIndicatorValue> list;
    }
}
