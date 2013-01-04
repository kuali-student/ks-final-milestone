package org.kuali.student.r1.common.assembly.transform;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;

public interface DataBeanMapper {

	/**
	 * Takes a DTO Bean and converts it to a Data map structure.
	 * 
	 * @param value The DTO Bean to convert to data map
	 * @param metadata Metadata describing the dto bean
	 * @return the converted bean object
	 */
	public Data convertFromBean(Object value, Metadata metadata) throws Exception;

	/**
	 * Takes a data map and converts it to the corresponding DTO Bean
	 * 
	 * @param data
	 * @param clazz
	 * @return
	 */
	public Object convertFromData(Data data, Class<?> clazz, Metadata metadata) throws Exception;

}