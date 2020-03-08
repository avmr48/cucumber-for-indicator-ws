package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.GetIndicatorRequest;

public class GetIndicatorRequestDataTableTransformer implements TableTransformer<GetIndicatorRequest> {

    @Override
    public GetIndicatorRequest transform(DataTable dataTable) throws Throwable {
        return dataTable.cells()
                .stream()
                .skip(Common.COUNT_HEADERS_LINES)
                .map(column -> new GetIndicatorRequest(
                        Common.getListOfIndicatorIds(column.get(0)),
                        Common.getPlaceId(column.get(1)),
                        Common.getTimeId(column.get(2)),
                        Common.getBooleanFromYesOrNo(column.get(3)),
                        Common.getBooleanFromYesOrNo(column.get(4)),
                        Common.getBooleanFromYesOrNo(column.get(5))
                ))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

}
