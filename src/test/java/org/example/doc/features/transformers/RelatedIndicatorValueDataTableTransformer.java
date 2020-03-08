package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.RelatedIndicatorValue;

public class RelatedIndicatorValueDataTableTransformer implements TableTransformer<RelatedIndicatorValue.Catalog> {

    @Override
    public RelatedIndicatorValue.Catalog transform(DataTable dataTable) throws Throwable {
        return Common.convertDataTableToEntityCatalog(
                dataTable,
                column -> new RelatedIndicatorValue(
                        Common.getString(column.get(0)),
                        Common.getTimeId(column.get(1)),
                        Common.getPlaceId(column.get(2)),
                        Common.getNumber(column.get(3)),
                        Common.getNumber(column.get(4))
                ),
                RelatedIndicatorValue.Catalog::new
        );
    }

}
