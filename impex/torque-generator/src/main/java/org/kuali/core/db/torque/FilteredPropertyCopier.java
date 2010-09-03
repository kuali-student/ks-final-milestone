package org.kuali.core.db.torque;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.core.db.torque.StringFilter;

public class FilteredPropertyCopier {
	List<String> includeProperties = new ArrayList<String>();
	List<String> excludeProperties = new ArrayList<String>();

	public FilteredPropertyCopier() {
		super();
		addExclude("class");
	}

	public void addExclude(String property) {
		excludeProperties.add(property);
	}

	public void addInclude(String property) {
		includeProperties.add(property);
	}

	public List<String> getIncludeProperties() {
		return includeProperties;
	}

	public void setIncludeProperties(List<String> includeProperties) {
		this.includeProperties = includeProperties;
	}

	public List<String> getExcludeProperties() {
		return excludeProperties;
	}

	public void setExcludeProperties(List<String> excludeProperties) {
		this.excludeProperties = excludeProperties;
	}

	@SuppressWarnings("unchecked")
	public void copyProperties(Object dest, Object origin) throws PropertyHandlingException {
		try {
			Map<String, Object> description = BeanUtils.describe(origin);
			StringFilter filterer = new StringFilter(getIncludeProperties(), getExcludeProperties());
			filterer.filter(description.keySet().iterator());
			for (String property : description.keySet()) {
				BeanUtils.copyProperty(dest, property, description.get(property));
			}
		} catch (Exception e) {
			throw new PropertyHandlingException("Error copying properties", e);
		}
	}
}
