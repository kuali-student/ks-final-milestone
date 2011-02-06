package ca.ubc.student.kuali.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgInfo;


public class DepartmentQualifierResolver extends AbstractOrgQualifierResolver{
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		
		List<String> orgIdList = new ArrayList<String>();
		
		orgIdList.add(getQualificationId(routeContext));
		
		List<OrgInfo> results;
		try {
			results = getOrganizationsFromList(orgIdList);
		} catch (DoesNotExistException e) {
			LOG.error("Error calling org service");
			throw new RuntimeException(e);
		} catch (InvalidParameterException e) {
			LOG.error("Error calling org service");
			throw new RuntimeException(e);
		} catch (MissingParameterException e) {
			LOG.error("Error calling org service");
			throw new RuntimeException(e);
		} catch (OperationFailedException e) {
			LOG.error("Error calling org service");
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			LOG.error("Error calling org service");
			throw new RuntimeException(e);
		}
		
		return attributeOrganizationSetFromSearchResult("department", "departmentId", results);
	}
}


