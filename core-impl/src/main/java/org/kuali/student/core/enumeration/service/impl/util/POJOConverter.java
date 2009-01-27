package org.kuali.student.core.enumeration.service.impl.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;
import net.sf.dozer.util.mapping.MappingException;

import org.kuali.student.core.enumeration.EnumerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class POJOConverter {
    private static MapperIF mapper;
    final static Logger logger = LoggerFactory.getLogger(POJOConverter.class);
    
    static {
        List<String> mappingFiles = new ArrayList<String>();
        mappingFiles.add("dozerMap.xml");
        mapper = new DozerBeanMapper(mappingFiles);
    }

    public static Object map(Object from, Object to) {
        try{
        	mapper.map(from, to);
        }
        catch(MappingException e){
        	logger.error("Error mapping dto to/from dao object", e);
			throw new EnumerationException("Error mapping dto to/from dao object", e);
        }
        return to;
    }

	static public <E> List<E> mapList(List<?> sourceList, Class<E> destListClass) {
        List<E> result = new ArrayList<E>();
        try{
	        for (Object e : sourceList) {
	            @SuppressWarnings("unchecked")
	            E map = (E) mapper.map(e, destListClass);
				result.add(map);
	        }
        }
        catch(MappingException e){
        	logger.error("Error mapping dto to/from dao list", e);
			throw new EnumerationException("Error mapping dto to/from dao list", e);
        }
        return result;
    }

}
