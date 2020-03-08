package org.example.doc.features.types;

import java.util.List;

public class GetIndicatorRequest {

    private final List<String> listOfIndicatorIds;
    private final String placeId;
    private final String timeId;
    private final boolean shouldGetDescription;
    private final boolean shouldGetRelated;
    private final boolean shouldGetComponents;

    public GetIndicatorRequest(
            List<String> listOfIndicatorIds,
            String placeId,
            String timeId,
            boolean shouldGetDescription,
            boolean shouldGetRelated,
            boolean shouldGetComponents
    ) {
        this.listOfIndicatorIds = listOfIndicatorIds;
        this.placeId = placeId;
        this.timeId = timeId;
        this.shouldGetDescription = shouldGetDescription;
        this.shouldGetRelated = shouldGetRelated;
        this.shouldGetComponents = shouldGetComponents;
    }

    public List<String> getListOfIndicatorIds() {
        return listOfIndicatorIds;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getTimeId() {
        return timeId;
    }

    public boolean isShouldGetDescription() {
        return shouldGetDescription;
    }

    public boolean isShouldGetRelated() {
        return shouldGetRelated;
    }

    public boolean isShouldGetComponents() {
        return shouldGetComponents;
    }

    @Override
    public String toString() {
        return "GetIndicatorRequest{" +
                "listOfIndicatorIds=" + listOfIndicatorIds +
                ", placeId='" + placeId + '\'' +
                ", timeId='" + timeId + '\'' +
                ", shouldGetDescription=" + shouldGetDescription +
                ", shouldGetRelated=" + shouldGetRelated +
                ", shouldGetComponents=" + shouldGetComponents +
                '}';
    }
}
