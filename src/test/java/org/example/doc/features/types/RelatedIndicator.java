package org.example.doc.features.types;

import lombok.Value;

import java.util.List;

@Value
public class RelatedIndicator implements HasParentIndicator<String> {
    String indicatorId;
    String id;
    String name;
    String description;

    @Value
    public static class Catalog {
        List<RelatedIndicator> list;
    }
}
