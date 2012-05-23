package org.kuali.student.common.conversion.util.converter;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerConverter;

/**
 * A custom Dozer converter that can be used to convert between a String and a list with a
 * single String item (and backwards).
 */
public class StringToListConverter extends DozerConverter<String, List> {

    public StringToListConverter() {
        super(String.class, List.class);
    }

    @Override
    public List convertTo(String source, List destination) {
        List<String> stringList = null;
        if (source != null) {
            stringList = new ArrayList<String>();
            stringList.add(source);
        }
        return stringList;
    }

    @Override
    public String convertFrom(List source, String destination) {
        String value = null;
        if (source != null && !source.isEmpty()) {
            value = (String) source.get(0);
        }
        return value;
    }

}
