package org.kuali.student.core.validation.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationResultInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	public enum ErrorLevel {
        OK(0), WARN(1), ERROR(2);

        int level;

        private ErrorLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }
        
        public static ErrorLevel min(ErrorLevel e1, ErrorLevel e2) {
        	return e1.ordinal() < e2.ordinal() ? e1 : e2;
        }
        public static ErrorLevel max(ErrorLevel e1, ErrorLevel e2) {
        	return e1.ordinal() > e2.ordinal() ? e1 : e2;
        }
    }	
	@XmlElement
	protected ErrorLevel level;

	@XmlElement
	protected String message;
	
	/**
	 * @return the level
	 */
	public ErrorLevel getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(ErrorLevel level) {
		this.level = level;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
