package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.IndicatorValue;

public class IndicatorValueDataTableTransformer implements TableTransformer<IndicatorValue.Catalog> {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_GOAL = "goal";

    @Override
    public IndicatorValue.Catalog transform(DataTable dataTable) throws Throwable {
        return Common.convertDataTableToEntityCatalog(
                dataTable,
                column -> new IndicatorValue(
                        Common.getString(column.get(COLUMN_ID)),
                        Common.getString(column.get(COLUMN_TIME)),
                        Common.getString(column.get(COLUMN_PLACE)),
                        Common.getNumber(column.get(COLUMN_VALUE)),
                        Common.getNumber(column.get(COLUMN_GOAL))
                ),
                IndicatorValue.Catalog::new
        );
    }
}
