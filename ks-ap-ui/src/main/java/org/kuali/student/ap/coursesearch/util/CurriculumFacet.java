package org.kuali.student.ap.coursesearch.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.context.KsapContext;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.dataobject.FacetItem;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class CurriculumFacet extends AbstractFacet {

	private HashMap<String, Map<String, String>> hashMap;

	public HashMap<String, Map<String, String>> getHashMap() {
		if (this.hashMap == null) {
			this.hashMap = new HashMap<String, Map<String, String>>();
		}
		return this.hashMap;
	}

	public void setHashMap(HashMap<String, Map<String, String>> hashMap) {
		this.hashMap = hashMap;
	}

	public CurriculumFacet() {
		super();
	}

	private HashSet<String> curriculumFacetSet = new HashSet<String>();

	public Set<String> getFacetSet() {
		return curriculumFacetSet;
	}

	@Override
	public List<FacetItem> getFacetItems() {
		String[] list = curriculumFacetSet.toArray(new String[0]);
		Arrays.sort(list);

		for (String display : list) {
			FacetItem item = new FacetItem();

			item.setKey(display);
			item.setDisplayName(display);
			String title = this.getTitle(display);
			if (title != null || title != "") {
				item.setTitle(title);
			}
			facetItems.add(item);
		}

		return facetItems;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void process(CourseSearchItem course) {
        String subject = null;
        String orgId = "ORGID-" + course.getSubject();
        KsapContext ksapContext = KsapFrameworkServiceLocator.getContext();
        ContextInfo contextInfo = ksapContext.getContextInfo();
        OrganizationService  organizationService = KsapFrameworkServiceLocator.getOrganizationService();
        OrgInfo orgInfo = null;
		try {

            orgInfo = organizationService.getOrg(orgId, contextInfo) ;
            subject = orgInfo.getShortName();

		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("ORG lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("ORG lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("ORG lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("ORG lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("ORG lookup error", e);
		}

		if (subject == null || subject.equals("")) {
			subject = unknownFacetKey;
		}

		curriculumFacetSet.add(subject);

		// Code the item with the facet key.
		Set<String> keys = new HashSet<String>();
		keys.add(subject);
		((CourseSearchItemImpl) course).setCurriculumFacetKeys(keys);
	}

	/**
	 * To get the title for the respective display name
	 * 
	 * @param display
	 * @return
	 */
	protected String getTitle(String display) {
		Map<String, String> subjects;
		HashMap<String, Map<String, String>> h = getHashMap();
		if ((subjects = h.get(CourseSearchConstants.SUBJECT_AREA)) == null)
			h.put(CourseSearchConstants.SUBJECT_AREA,
					subjects = KsapFrameworkServiceLocator.getOrgHelper()
							.getTrimmedSubjectAreas());
		return subjects.get(display);
	}

}
