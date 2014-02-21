package org.kuali.student.ap.framework.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Simple JAXB marshalling utility.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.7.1
 * @deprecated
 */
public class XmlMarshalUtil {

	/**
	 * Convert a bean to XML format using JAXB.
	 * 
	 * @param bean
	 *            The bean to marshal as XML.
	 * @param bc
	 *            Additional classes to register on the JAXB context for
	 *            marshalling.
	 * @return An XML representation of the bean.
	 */
	public static <T> String marshal(T bean, Class<?>... bc) {
		assert bean != null;
		Class<?>[] bind;
		if (bc.length > 0) {
			bind = new Class<?>[bc.length + 1];
			bind[0] = bean.getClass();
			for (int i = 0; i < bc.length; i++)
				bind[i + 1] = bc[i];
		} else
			bind = new Class<?>[] { bean.getClass(), };
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(bind);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					new Boolean(true));
			marshaller.marshal(bean, baos);
		} catch (JAXBException e) {
			throw new IllegalStateException("Error marshalling", e);
		}
		return new String(baos.toByteArray());
	}

	/**
	 * Convert XML data to a bean using JAXB.
	 * 
	 * @param b
	 *            The bean, represented as XML.
	 * @param implClass
	 *            The implementation class of the bean.
	 * @param bc
	 *            Additional classes to add to the JAXB context.
	 * @return The bean, unmarshalled from the XML data using JAXB.
	 */
	public static <T> T unmarshal(String b, Class<T> implClass, Class<?>... bc) {
		Class<?>[] bind;
		if (bc.length > 1) {
			bind = new Class<?>[bc.length + 1];
			bind[0] = implClass;
			for (int i = 0; i < bc.length; i++) {
				bind[i + 1] = bc[i];
			}
		} else {
			bind = new Class<?>[] { implClass, };
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(b.getBytes());
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(bind);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return implClass.cast(unmarshaller.unmarshal(bais));
		} catch (JAXBException e) {
			throw new IllegalStateException("Error unmarshalling", e);
		}
	}

}
