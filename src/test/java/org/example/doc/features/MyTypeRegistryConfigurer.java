package org.example.doc.features;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableTransformer;
import org.example.doc.features.transformers.*;
import org.example.doc.features.types.*;

import java.util.Locale;

public class MyTypeRegistryConfigurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    // TODO replace with annotation based configuration
    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
        addType(registry, GetIndicatorRequest.class, new GetIndicatorRequestDataTableTransformer());
        addType(registry, Indicator.Catalog.class, new IndicatorDataTableTransformer());
        addType(registry, IndicatorValue.Catalog.class, new IndicatorValueDataTableTransformer());
        addType(registry, RelatedIndicator.Catalog.class, new RelatedIndicatorDataTableTransformer());
        addType(registry, RelatedIndicatorValue.Catalog.class, new RelatedIndicatorValueDataTableTransformer());
    }

    private <T> void addType(TypeRegistry typeRegistry, Class<T> type, TableTransformer<T> transformer) {
        typeRegistry.defineDataTableType(new DataTableType(type, transformer));
    }
}
