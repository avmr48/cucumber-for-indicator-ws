package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.RelatedIndicator;

public class RelatedIndicatorDataTableTransformer implements TableTransformer<RelatedIndicator.Catalog> {

    public static final String COLUMN_INDICATOR = "indicator";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";

    @Override
    public RelatedIndicator.Catalog transform(DataTable dataTable) throws Throwable {
        return Common.convertDataTableToEntityCatalog(
                dataTable,
                column -> new RelatedIndicator(
                        Common.getString(column.get(COLUMN_INDICATOR)),
                        Common.getString(column.get(COLUMN_ID)),
                        Common.getString(column.get(COLUMN_NAME)),
                        Common.getNullableString(column.get(COLUMN_DESCRIPTION))
                ),
                RelatedIndicator.Catalog::new
        );
    }
}
