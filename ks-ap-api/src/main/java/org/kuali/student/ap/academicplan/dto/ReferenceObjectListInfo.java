package org.kuali.student.ap.academicplan.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.ap.academicplan.infc.ReferenceObjectList;
import org.kuali.student.ap.academicplan.infc.TypedObjectReference;
import org.w3c.dom.Element;

/**
 * Reference Object List message Structure
 *
 * @Author mguilla
 * Date: 1/21/14
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceObjectListInfo", propOrder = { "id", "references", "_futureElements" })
public class ReferenceObjectListInfo implements ReferenceObjectList, Serializable {
	
	private static final long serialVersionUID = 6019908398241577527L;

	@XmlAttribute
    private String id;
        
    @XmlElement
    private List<TypedObjectReferenceInfo> references;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public ReferenceObjectListInfo() {
    }
    
    public ReferenceObjectListInfo(ReferenceObjectList copy) {
    	id = copy.getId();
    	setReferences(copy.getReferences());
    }

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	
		
	public List<TypedObjectReference> getReferences() {
		if (references == null) {
			return Collections.emptyList();
		} else {
			return Collections.<TypedObjectReference> unmodifiableList(references);
		}
	}

	public void setReferences(List<? extends TypedObjectReference> references) {
		if (references != null) {
			List<TypedObjectReferenceInfo> referenceInfos = new ArrayList<TypedObjectReferenceInfo>(
					references.size());
			for (TypedObjectReference reference : references) {
				TypedObjectReferenceInfo referenceInfo = new TypedObjectReferenceInfo(
						reference);
				referenceInfos.add(referenceInfo);
			}
			this.references = referenceInfos;
		} else {
			this.references = null;
		}
	}


   
}
