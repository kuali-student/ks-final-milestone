package org.kuali.student.common.olddictionary.dto;

import javax.xml.bind.annotation.XmlEnum;

@Deprecated
@XmlEnum
public enum DataType {
	STRING, DATE, TRUNCATED_DATE, BOOLEAN, INTEGER, FLOAT, DOUBLE, LONG, COMPLEX
}
