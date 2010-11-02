package org.kuali.student.common.ui.shared;

import org.kuali.student.core.rice.StudentIdentityConstants;

public class IdAttributes {
	
	public static final String ID_TYPE = "ID_TYPE";
	
	public enum IdType {
		//	TODO: OBJECT_ID has no references
		KS_KEW_OBJECT_ID(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID), DOCUMENT_ID(StudentIdentityConstants.DOCUMENT_NUMBER), OBJECT_ID("objectId"), COPY_OF_OBJECT_ID("copyOfObjectId");
        
		final String stringValue;

		private IdType(String value) {
            this.stringValue = value;
        }

        public String toString() {
            return stringValue;
        }
        
        public static IdType fromString(String name) {
            for (IdType idTypeEnum : values()) {
                if (name.equals(idTypeEnum.toString())) {
                    return idTypeEnum;
                }
            }
            return null;   
        }
	}		
}
