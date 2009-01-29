package org.kuali.student.core.organization.web.server.impl;

import java.util.List;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.organization.web.client.service.OrgRpcService;

public class OrgRpcServiceImpl implements OrgRpcService{

		private OrganizationService service;

		public OrganizationService getService() {
			return service;
		}

		public void setService(OrganizationService service) {
			this.service = service;
		}

		@Override
		public List<OrgHierarchyInfo> getOrgHierarchies() {
			try {
				return service.getOrgHierarchies();
			} catch (OperationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;	
		}

		@Override
		public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId) {
			// TODO Auto-generated method stub
			try {
				return service.getOrgOrgRelationsByOrg(orgId);
			} catch (DoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MissingParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PermissionDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList) {
			// TODO Auto-generated method stub
			try {
				return service.getOrganizationsByIdList(orgIdList);
			} catch (DoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MissingParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PermissionDeniedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

        @Override
        public List<String> getAllDescendants(String orgId, String orgHierarchy) {
            // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
            try {
                return service.getAllDescendants(orgId, orgHierarchy);
            } catch (InvalidParameterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (MissingParameterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OperationFailedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (PermissionDeniedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
}
