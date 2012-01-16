package org.kuali.student.common.ui.client.widgets.table.summary;

import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;

public class SummaryTableMultiplicityFieldRow extends SummaryTableFieldRow{
	
	private MultiplicityConfiguration config;

	public SummaryTableMultiplicityFieldRow(MultiplicityConfiguration config) {
		this.setConfig(config);
	}

	public void setConfig(MultiplicityConfiguration config) {
		this.config = config;
	}

	public MultiplicityConfiguration getConfig() {
		return config;
	}

}
