package org.kuali.rice.krad.uif.element.extension;

import org.kuali.rice.krad.uif.element.ContentElementBase;

/**
 * Place-holder content element for representing a flexible radio control.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 1.1
 */
public class ShoppingCartRadioComponent extends ContentElementBase {

	private static final long serialVersionUID = -5743330838885750100L;

	private String propertyName;
	private String optionValue;
	private String optionLabel;
	private boolean selected;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
