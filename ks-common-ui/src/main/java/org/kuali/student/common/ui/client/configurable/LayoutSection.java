package org.kuali.student.common.ui.client.configurable;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.kuali.student.core.validation.dto.ValidationResult.ErrorLevel;

import com.google.gwt.user.client.ui.Composite;

public abstract class LayoutSection<T extends Idable> extends Composite {
	private String sectionTitle = null;
	private String instructions = null;
	private ConfigurableLayout<T> parentLayout = null;

	public String getSectionTitle() {
		return sectionTitle;
	}

	public LayoutSection<T> setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
		return this;
	}

	public String getInstructions() {
		return instructions;
	}

	public LayoutSection<T> setInstructions(String instructions) {
		this.instructions = instructions;
		return this;
	}

	public LayoutSection<T> setParentLayout(ConfigurableLayout<T> parentLayout) {
		this.parentLayout = parentLayout;
		return this;
	}

	public ConfigurableLayout<T> getParentLayout() {
		return parentLayout;
	}
	
	public abstract void validate(Callback<ErrorLevel> callback);
	public abstract void populate();
	public abstract void updateObject();
	
}
