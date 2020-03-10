package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.Indicator;

public class IndicatorDataTableTransformer implements TableTransformer<Indicator.Catalog> {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";

    @Override
    public Indicator.Catalog transform(DataTable dataTable) throws Throwable {
        return Common.convertDataTableToEntityCatalog(
                dataTable,
                column -> new Indicator(
                        Common.getString(column.get(COLUMN_ID)),
                        Common.getString(column.get(COLUMN_NAME)),
                        Common.getNullableString(column.get(COLUMN_DESCRIPTION))
                ),
                Indicator.Catalog::new
        );
    }
}
