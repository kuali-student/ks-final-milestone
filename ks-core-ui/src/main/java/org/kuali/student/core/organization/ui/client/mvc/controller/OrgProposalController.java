package org.kuali.student.core.organization.ui.client.mvc.controller;

import org.kuali.student.common.ui.client.configurable.mvc.PagedSectionLayout;
import org.kuali.student.core.organization.ui.client.mvc.view.OrgConfigurer;

public class OrgProposalController extends PagedSectionLayout{

	@Override
	public Class<? extends Enum<?>> getViewsEnum() {
		return OrgConfigurer.OrgSections.class;
	}

}
