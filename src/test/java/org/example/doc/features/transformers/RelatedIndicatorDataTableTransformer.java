package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.RelatedIndicator;

public class RelatedIndicatorDataTableTransformer implements TableTransformer<RelatedIndicator.Catalog> {

    @Override
    public RelatedIndicator.Catalog transform(DataTable dataTable) throws Throwable {
        return Common.convertDataTableToEntityCatalog(
                dataTable,
                column -> new RelatedIndicator(
                        Common.getString(column.get(0)),
                        Common.getString(column.get(1)),
                        Common.getString(column.get(2)),
                        Common.getString(column.get(3))
                ),
                RelatedIndicator.Catalog::new
        );
    }
}
