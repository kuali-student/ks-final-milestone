package org.kuali.student.common.assembly.client;


public interface HasChangeCallbacks {
	public enum ChangeType {
		ADD, REMOVE, UPDATE;
	}
	public static interface ChangeCallback {
		void onChange(ChangeType type, QueryPath path);
	}
	public static interface ChangeCallbackRegistration {
		void remove();
	}
	ChangeCallbackRegistration addChangeCallback(ChangeCallback callback);	
}
