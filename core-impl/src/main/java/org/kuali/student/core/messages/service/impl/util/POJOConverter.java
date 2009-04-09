package org.kuali.student.core.messages.service.impl.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;
import net.sf.dozer.util.mapping.MappingException;


import org.kuali.student.core.messages.MessageException;
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
			throw new MessageException("Error mapping dto to/from dao object", e);
        }
        return to;
    }

    @SuppressWarnings("unchecked")
	static public <E> List<E> mapList(List<?> sourceList, Class<E> destListClass) {
        List<E> result = new ArrayList<E>();
        try{
	        for (Object e : sourceList) {
	            result.add((E) mapper.map(e, destListClass));
	        }
        }
        catch(MappingException e){
        	logger.error("Error mapping dto to/from dao list", e);
			throw new MessageException("Error mapping dto to/from dao list", e);
        }
        return result;
    }

}
