package org.kuali.student.common.util.spring;

import java.beans.PropertyEditorSupport;

public class SimpleEnumPropertyEditor extends PropertyEditorSupport {
	Class<? extends Enum<?>> enumClass;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		for (Object e : enumClass.getEnumConstants()) {
			if (e.toString().toUpperCase().equals(text.toUpperCase())) {
				this.setValue(e);
			}
		}
	}

	public SimpleEnumPropertyEditor(Class<? extends Enum<?>> enumClass) {
		super();
		if(enumClass==null||!enumClass.isEnum()){
			throw new IllegalArgumentException("Must set a valid Enum Class in the constructor");
		}
		this.enumClass = enumClass;
	}

}
