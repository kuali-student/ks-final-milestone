package org.kuali.student.common.assembly.transform;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;

public interface DataBeanMapper {

	/**
	 * Takes a DTO Bean and converts it to a Data map structure.
	 * 
	 * @param value
	 * @return the converted bean object
	 */
	public Data convertFromBean(Object value) throws Exception;

	/**
	 * Takes a data map and converts it to the corresponding DTO Bean
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public Object convertFromData(Data data, Class<?> clazz, Metadata metadata) throws Exception;

}