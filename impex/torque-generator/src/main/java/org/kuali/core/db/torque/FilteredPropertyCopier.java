package org.kuali.core.db.torque;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public void copyProperties(Object dest, Object origin) {
		try {
			Set<String> properties = getPropertySet(origin);
			StringFilter filterer = new StringFilter(getIncludeProperties(), getExcludeProperties());
			filterer.filter(properties.iterator());
			Map<String, Object> description = BeanUtils.describe(origin);
			for (String property : properties) {
				BeanUtils.copyProperty(dest, property, description.get(property));
			}
		} catch (Exception e) {
			throw new RuntimeException("Error copying properties", e);
		}
	}

	@SuppressWarnings("unchecked")
	protected Set<String> getPropertySet(Object bean) {
		try {
			Map<String, Object> description = BeanUtils.describe(bean);
			return description.keySet();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
