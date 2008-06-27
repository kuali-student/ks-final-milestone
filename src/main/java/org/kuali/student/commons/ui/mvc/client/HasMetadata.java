package org.kuali.student.commons.ui.mvc.client;

import java.util.Set;

public interface HasMetadata {
	public Set<String> getTypeHierarchy();
	public boolean isAssignableFrom(HasMetadata o);
	public boolean isAssignableTo(HasMetadata o);
	public HasMetadata newInstance();
	public Class<? extends HasMetadata> getDefinitionClass();
	public String getFormattedDefinitionClassname();
}
