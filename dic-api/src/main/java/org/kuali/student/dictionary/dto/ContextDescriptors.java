
package org.kuali.student.dictionary.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "contextDescriptor"
})
@XmlRootElement(name = "contextDescriptors")
public class ContextDescriptors {

	 @XmlElement(required = true)
	 protected List<ContextDescriptor> contextDescriptor;
	
	 /**
	  * Gets the value of the context property.
	  * 
	  * <p>
	  * This accessor method returns a reference to the live list,
	  * not a snapshot. Therefore any modification you make to the
	  * returned list will be present inside the JAXB object.
	  * This is why there is not a <CODE>set</CODE> method for the context property.
	  * 
	  * <p>
	  * For example, to add a new item, do as follows:
	  * <pre>
	  *    getContext().add(newItem);
	  * </pre>
	  * 
	  * 
	  * <p>
	  * Objects of the following type(s) are allowed in the list
	  * {@link Context }
	  * 
	  * 
	  */
	 public List<ContextDescriptor> getContext() {
	     if (contextDescriptor == null) {
	    	 contextDescriptor = new ArrayList<ContextDescriptor>();
	     }
	     return this.contextDescriptor;
	 }

}
