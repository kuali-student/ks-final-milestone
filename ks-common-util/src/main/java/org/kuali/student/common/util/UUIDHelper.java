package org.kuali.student.common.util;


import org.apache.log4j.Logger;

public class UUIDHelper {
	
    private static final Logger LOG = Logger.getLogger(UUIDHelper.class);
	
	public static String genStringUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	public static String genStringUUID(String originalUUID) {
		if (originalUUID != null) {
			try {
				return java.util.UUID.fromString(originalUUID).toString();
			} catch (IllegalArgumentException e) {
				LOG.warn("Given ID \""+originalUUID+"\" is not a valid UUID.  A new UUID will replace the given ID.");
			}
		}
		return genStringUUID();
	}
}
