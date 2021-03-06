package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.RelatedIndicatorValue;

public class RelatedIndicatorValueDataTableTransformer implements TableTransformer<RelatedIndicatorValue.Catalog> {

    public static final String COLUMN_INDICATOR_ID = "indicator id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_GOAL = "goal";

    @Override
    public RelatedIndicatorValue.Catalog transform(DataTable dataTable) throws Throwable {
        return Common.convertDataTableToEntityCatalog(
                dataTable,
                column -> new RelatedIndicatorValue(
                        Common.getNullableString(column.get(COLUMN_INDICATOR_ID)),
                        Common.getString(column.get(COLUMN_TIME)),
                        Common.getString(column.get(COLUMN_PLACE)),
                        Common.getNullableString(column.get(COLUMN_VALUE)),
                        Common.getNullableString(column.get(COLUMN_GOAL))
                ),
                RelatedIndicatorValue.Catalog::new
        );
    }
}
