package org.kuali.student.r2.core.class1.state.dto;

import javax.xml.bind.annotation.XmlEnum;

/**
 * @Author Sri komandur@uw.edu
 */
@XmlEnum(String.class)
public enum StateConstraintOperator {
    ALL, EXISTS, NONE;
}