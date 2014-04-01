package org.kuali.student.ap.plannerreview.controller;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.infc.ConversationCommenter;
import org.kuali.student.ap.plannerreview.util.ConversationConstants;
import org.kuali.student.ap.plannerreview.LearningPlanReviewStrategy;
import org.kuali.student.ap.plannerreview.dto.ConversationAdvisorInfo;
import org.kuali.student.ap.plannerreview.dto.ConversationCommenterInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import java.util.Map;

public abstract class ConversationControllerBase extends UifControllerBase {
	
	private LearningPlanReviewStrategy learningPlanReviewStrategy;
	private PersonService personService;
	private OrganizationService organizationService;
	
	/**
	 * Add advisor data to a map for lookup later on
	 * @param advisorMap
	 * @param advisorId
	 * @param advisingRole
	 * @param advisingType
	 */
	protected void addAdvisorToMap(Map<String, ConversationAdvisorInfo> advisorMap, String advisorId, String advisingRole, String advisingType) {
		String key = advisorId + ":" + advisingRole;
		if (!advisorMap.containsKey(key)) {
			ConversationAdvisorInfo advisor = new ConversationAdvisorInfo();
			advisor.setUniqueId(key);
			advisor.setUserId(advisorId);
			advisor.setAdvisorType(advisingType);
			advisor.setAdvisorRoleId(advisingRole);
			advisor.setAdvisorRoleName(advisingRole);
			
			String advisorName = advisorId;
			if (ConversationConstants.CONV_ADVISOR_ID_TYPE_ADVISOR.equals(advisingType) || 
					ConversationConstants.CONV_COMMENTER_TYPE_STUDENT.equals(advisingType)) {
				Person person = getPersonService().getPerson(advisorId);
				advisorName = person.getName();
			}
			else {
				//advising office
				ContextInfo context = KsapFrameworkServiceLocator.getContext()
						.getContextInfo();
				OrgInfo org;
				try {
					org = getOrganizationService().getOrg(advisorId, context);
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("ORG lookup failure", e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("ORG lookup failure", e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("ORG lookup failure", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("ORG lookup failure", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("ORG lookup failure", e);
				}
				advisorName = org.getShortName();
			}
			
			advisor.setName(advisorName);
			
			advisorMap.put(advisor.getUniqueId(), advisor);
		}
	}
	
	protected void addAdvisorToMap(Map<String, ConversationAdvisor> advisorMap, ConversationAdvisor advisor) {
		if (!advisorMap.containsKey(advisor.getUniqueId())) {
			advisorMap.put(advisor.getUniqueId(), advisor);
		}
	}
	
	/**
	 * Method to get the current user;
	 * @return User's principal id
	 */
	protected String getUserId() {
		Person user = GlobalVariables.getUserSession().getPerson();
		return user.getPrincipalId();
	}
	
	protected ConversationCommenter getCurrentUser() {
		Person user = GlobalVariables.getUserSession().getPerson();
		ConversationCommenterInfo cci = new ConversationCommenterInfo();
		cci.setName(user.getName());
		cci.setUserId(user.getPrincipalId());
		return cci;
		
	}
	
	protected LearningPlanReviewStrategy getLearningPlanReviewStrategy() {
    	if (learningPlanReviewStrategy == null) {
    		learningPlanReviewStrategy = KsapFrameworkServiceLocator.getLearningPlanReviewStrategy();
    	}
    	return learningPlanReviewStrategy;
    }
	
	private PersonService getPersonService() {
		if (personService == null) {
			personService = KimApiServiceLocator.getPersonService();
		}
		return personService;
	}
	
	private OrganizationService getOrganizationService() {
		if (organizationService == null) {
			organizationService = KsapFrameworkServiceLocator.getOrganizationService();
		}
		return organizationService;
	}

}
