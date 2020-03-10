package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.GetIndicatorRequest;

public class GetIndicatorRequestDataTableTransformer implements TableTransformer<GetIndicatorRequest> {

    public static final String COLUMN_INDICATOR_IDS = "ids";
    public static final String COLUMN_PLACES = "places";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_WITH_LABEL = "with label";
    public static final String COLUMN_WITH_RELATED = "with related";
    public static final String COLUMN_WITH_COMPONENTS = "with components";

    @Override
    public GetIndicatorRequest transform(DataTable dataTable) throws Throwable {

        return dataTable.asMaps()
                .stream()
                .skip(1)
                .map(column -> new GetIndicatorRequest(
                        Common.getListOfStrings(column.get(COLUMN_INDICATOR_IDS)),
                        Common.getListOfStrings(column.get(COLUMN_PLACES)),
                        Common.getString(column.get(COLUMN_TIME)),
                        Common.getBooleanFromYesOrNo(column.get(COLUMN_WITH_LABEL)),
                        Common.getBooleanFromYesOrNo(column.get(COLUMN_WITH_RELATED)),
                        Common.getBooleanFromYesOrNo(column.get(COLUMN_WITH_COMPONENTS))
                ))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

}
