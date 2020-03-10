package org.example.doc.features.types;

import lombok.Value;

import java.util.List;

@Value
public class GetIndicatorRequest {

     List<String> listOfIndicatorIds;
     List<String> listOfPlaces;
     String timeId;
     boolean shouldGetDescription;
     boolean shouldGetRelated;
     boolean shouldGetComponents;

}
