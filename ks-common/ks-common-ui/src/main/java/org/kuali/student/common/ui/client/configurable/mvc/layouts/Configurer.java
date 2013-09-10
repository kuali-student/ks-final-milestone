package org.kuali.student.common.ui.client.configurable.mvc.layouts;

import org.kuali.student.common.ui.client.mvc.DataModelDefinition;

/**
 * Interface must be implemented by configurers for ConfigurableLayouts.
 *  
 * @author Will Gomes
 *
 */
public interface Configurer {
	public void configure(ConfigurableLayout layout);
	public void setModelDefinition(DataModelDefinition modelDefinition);
}
