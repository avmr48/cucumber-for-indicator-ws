package org.example.doc.features.transformers;

import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.Common;
import org.example.doc.features.types.Indicator;

public class IndicatorDataTableTransformer implements TableTransformer<Indicator.Catalog> {

    @Override
    public Indicator.Catalog transform(DataTable dataTable) throws Throwable {
        return Common.convertDataTableToEntityCatalog(
                dataTable,
                column -> new Indicator(
                        Common.getString(column.get(0)),
                        Common.getString(column.get(1)),
                        Common.getString(column.get(2))
                ),
                Indicator.Catalog::new
        );
    }
}
