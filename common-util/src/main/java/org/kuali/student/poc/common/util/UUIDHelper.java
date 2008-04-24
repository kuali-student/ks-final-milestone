package org.kuali.student.poc.common.util;

public class UUIDHelper {
	public static String genStringUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	public static String genStringUUID(String originalUUID) {
		if (originalUUID != null) {
			try {
				return java.util.UUID.fromString(originalUUID).toString();
			} catch (IllegalArgumentException e) {

			}
		}
		return genStringUUID();
	}
}
