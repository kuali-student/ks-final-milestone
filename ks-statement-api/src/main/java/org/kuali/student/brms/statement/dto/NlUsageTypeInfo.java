package org.kuali.student.brms.statement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class NlUsageTypeInfo extends TypeInfo implements Serializable, Idable, HasAttributes {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "NlUsageTypeInfo[id=" + getId() + "]";
	}
}
