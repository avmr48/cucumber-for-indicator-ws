package org.example.doc.features.types;

import lombok.Value;

import java.util.List;

@Value
public class RelatedIndicator {
    String idIndicator;
    String id;
    String name;
    String description;

    @Value
    public static class Catalog {
        List<RelatedIndicator> list;
    }
}
