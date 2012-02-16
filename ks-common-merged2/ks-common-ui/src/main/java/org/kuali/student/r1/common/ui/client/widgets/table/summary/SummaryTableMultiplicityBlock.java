package org.kuali.student.r1.common.ui.client.widgets.table.summary;

import org.kuali.student.r1.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;

public class SummaryTableMultiplicityBlock extends SummaryTableFieldBlock{
	MultiplicityConfiguration config;
	
	public MultiplicityConfiguration getConfig() {
		return config;
	}

	public void setConfig(MultiplicityConfiguration config) {
		this.config = config;
	}

	public SummaryTableMultiplicityBlock(MultiplicityConfiguration config){
		this.config = config;
	}
}
