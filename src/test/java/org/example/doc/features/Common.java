package org.example.doc.features;

import io.cucumber.datatable.DataTable;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Common {

    /**
     * Number of lines corresponding to heading in data tables to ignore
     */
    public static final int COUNT_HEADERS_LINES = 2;

    public static boolean getBooleanFromYesOrNo(String yesOrNoString) {
        return yesOrNoString != null && yesOrNoString.trim().toLowerCase().charAt(0) == 'y';
    }

    public static String getString(String str) {
        return str.trim();
    }

    public static String getTimeId(String timeIdString) {
        return timeIdString.trim();
    }

    public static String getPlaceId(String placeIdString) {
        return placeIdString.trim();
    }

    public static List<String> getListOfIndicatorIds(String idList) {
        return Arrays.stream(idList.split(",")).map(String::trim).collect(Collectors.toList());
    }

    public static Double getNumber(String str) {
        return Double.valueOf(str);
    }

    // TODO get column by header instead of index to allow nullables
    public static <ENTITY, ENTITY_CATALOG> ENTITY_CATALOG convertDataTableToEntityCatalog(
            DataTable dataTable,
            Function<List<String>, ENTITY> createEntity,
            Function<List<ENTITY>, ENTITY_CATALOG> createEntityCatalog
    ) {
        return dataTable.cells()
                .stream()
                .skip(COUNT_HEADERS_LINES)
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
