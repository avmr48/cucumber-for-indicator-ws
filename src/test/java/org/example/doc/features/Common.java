package org.example.doc.features;

import io.cucumber.datatable.DataTable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Common {

    public static boolean getBooleanFromYesOrNo(String yesOrNoString) {
        return yesOrNoString != null && yesOrNoString.trim().toLowerCase().charAt(0) == 'y';
    }

    public static String getString(String str) {
        return str.trim();
    }

    public static String getNullableString(String str) {
        return str == null || str.equals("null") ? null : str.trim();
    }

    public static List<String> getListOfStrings(String idList) {
        return Arrays.stream(idList.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public static Double getNullableNumber(String str) {
        String nullableString = getNullableString(str);
        return nullableString == null ? null : Double.valueOf(nullableString);
    }

    public static <ENTITY, ENTITY_CATALOG> ENTITY_CATALOG convertDataTableToEntityCatalog(
            DataTable dataTable,
            Function<Map<String, String>, ENTITY> createEntity,
            Function<List<ENTITY>, ENTITY_CATALOG> createEntityCatalog
    ) {
        return dataTable.asMaps()
                .stream()
                .skip(1)
                .map(createEntity)
                .collect(collectingAndThen(
                        toList(),
                        createEntityCatalog
                ));
    }

    public static <T> void forEach(List<T> list, BiConsumer<T, Integer> biConsumer) {
        for (int i = 0; i < list.size(); i++) {
            T expected = list.get(i);
            biConsumer.accept(expected, i);
        }
    }
}
