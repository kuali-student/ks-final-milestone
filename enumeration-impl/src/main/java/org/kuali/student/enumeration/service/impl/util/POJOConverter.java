package org.kuali.student.enumeration.service.impl.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;

public class POJOConverter {
    private static MapperIF mapper;

    static {
        List<String> mappingFiles = new ArrayList<String>();
        mappingFiles.add("map.xml");

        mapper = new DozerBeanMapper(mappingFiles);

    }

    public static Object map(Object from, Object to) {
        mapper.map(from, to);
        return to;
    }

    static public <E> List<E> mapList(List<?> sourceList, Class<E> destListClass) {
        List<E> result = new ArrayList<E>();
        for (Object e : sourceList) {
            result.add((E) mapper.map(e, destListClass));
        }
        return result;
    }
    public static void main(String[] a){
        System.out.println("asdf");
    }
}
