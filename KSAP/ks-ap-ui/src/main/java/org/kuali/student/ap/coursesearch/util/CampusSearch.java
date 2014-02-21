package org.kuali.student.ap.coursesearch.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

/**
 * Logic for building list of FacetItems and coding CourseSearchItems.
 */
public class CampusSearch extends KeyValuesBase {

	private static final long serialVersionUID = -3361419903987151309L;

	private boolean blankOption;

	private HashMap<String, List<OrgInfo>> hashMap;

	public HashMap<String, List<OrgInfo>> getHashMap() {
		if (this.hashMap == null) {
			this.hashMap = new HashMap<String, List<OrgInfo>>();
		}
		return this.hashMap;
	}

	public void setHashMap(HashMap<String, List<OrgInfo>> hashMap) {
		this.hashMap = hashMap;
	}

	@Override
	public List<KeyValue> getKeyValues() {
		ContextInfo context = KsapFrameworkServiceLocator.getContext()
				.getContextInfo();
		List<KeyValue> keyValues = new ArrayList<KeyValue>();
		if (blankOption) {
			keyValues.add(new ConcreteKeyValue("", ""));
		}
		// List<OrgInfo> orgInfoList = new ArrayList<OrgInfo>();
		// if (!this.getHashMap().containsKey(
		// CourseSearchConstants.CAMPUS_LOCATION)) {
		// // Campus locations are drawn from database KSOR_ORG with
		// // TYPE="kuali.org.type.campus"
		// orgInfoList = KsapFrameworkServiceLocator.getOrgHelper().getOrgInfo(
		// CourseSearchConstants.CAMPUS_LOCATION,
		// CourseSearchConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST,
		// CourseSearchConstants.ORG_TYPE_PARAM, context);
		// getHashMap()
		// .put(CourseSearchConstants.CAMPUS_LOCATION, orgInfoList);
		// } else {
		// orgInfoList = getHashMap().get(
		// CourseSearchConstants.CAMPUS_LOCATION);
		// }
		// if (orgInfoList != null && orgInfoList.size() > 0) {
		// for (OrgInfo entry : orgInfoList) {
		// keyValues.add(new ConcreteKeyValue(entry.getId(), entry
		// .getLongName()));
		// }
		// }
		List<EnumeratedValueInfo> enumeratedValueInfoList = KsapFrameworkServiceLocator
				.getEnumerationHelper().getEnumerationValueInfoList(
						"kuali.lu.campusLocation");
		if (enumeratedValueInfoList != null
				&& enumeratedValueInfoList.size() > 0) {
			for (EnumeratedValueInfo entry : enumeratedValueInfoList) {
				if (entry.getCode().equals("AL")) {
					keyValues.add(new ConcreteKeyValue(entry.getCode(), entry
							.getValue() + " campuses"));
				} else {
					keyValues.add(new ConcreteKeyValue(entry.getCode(), entry
							.getValue() + " campus"));
				}
			}
		}
		Collections.sort(keyValues, new Comparator<KeyValue>() {
			@Override
			public int compare(KeyValue keyValue1, KeyValue keyValue2) {
				return keyValue1.getKey().compareTo(keyValue2.getKey());
			}
		});
		return keyValues;
	}

	public CampusSearch() {
		super();
	}

	public boolean isBlankOption() {
		return blankOption;
	}

	public void setBlankOption(boolean blankOption) {
		this.blankOption = blankOption;
	}
}
