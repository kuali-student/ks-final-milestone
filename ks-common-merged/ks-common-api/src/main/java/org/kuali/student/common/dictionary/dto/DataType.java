package org.kuali.student.common.dictionary.dto;

import javax.xml.bind.annotation.XmlEnum;

//TODO KSCM-285 wait for Service Team confirmation
@XmlEnum
public enum DataType {
	STRING, DATE, TRUNCATED_DATE, BOOLEAN, INTEGER, FLOAT, DOUBLE, LONG, COMPLEX
}
