package org.kuali.student.common.ui.client.mvc.breadcrumb;

import java.util.List;

/**
 * Interface for controllers/views which support breadcrumb collection
 * 
 * @author Kuali Student Team
 *
 */
public interface BreadcrumbSupport {
	/**
	 * Adds a human readable name(s) to the list passed in for use in the breadcrumb
	 * @param names
	 */
	public void collectBreadcrumbNames(List<String> names);
}
