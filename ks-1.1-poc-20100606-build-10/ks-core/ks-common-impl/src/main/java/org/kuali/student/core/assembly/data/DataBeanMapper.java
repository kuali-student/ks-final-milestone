package org.kuali.student.core.assembly.data;

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
	public Object convertFromData(Data data, Class<?> clazz) throws Exception;

}