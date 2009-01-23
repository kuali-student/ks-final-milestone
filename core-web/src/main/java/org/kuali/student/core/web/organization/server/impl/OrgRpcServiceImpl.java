package org.kuali.student.core.web.organization.server.impl;

import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.web.organization.client.service.OrgRpcService;

public class OrgRpcServiceImpl implements OrgRpcService{

		private OrganizationService service;

		public OrganizationService getService() {
			return service;
		}

		public void setService(OrganizationService service) {
			this.service = service;
		}
}
