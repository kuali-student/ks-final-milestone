package org.kuali.student.myplan.course.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.myplan.course.dataobject.TrendingDataObject;
import org.kuali.student.myplan.main.service.MyPlanLookupableImpl;

public class TrendingLookupableHelperImpl extends MyPlanLookupableImpl {

	private static final long serialVersionUID = 5464213712478709248L;

	@Override
	protected List<TrendingDataObject> getSearchResults(LookupForm lookupForm,
			Map<String, String> fieldValues, boolean unbounded) {
		Set<String> kws = CourseSearchController.getCurrentTrend();
		List<TrendingDataObject> rv = new java.util.ArrayList<TrendingDataObject>(
				kws.size());
		for (String kw : kws) {
			TrendingDataObject.Builder tb = new TrendingDataObject.Builder();
			tb.setXid((kw = kw.toLowerCase()).replaceAll("[^A-Z0-9_]", "_"));
			tb.setKeyword(kw);
			rv.add(tb.build());
		}
		return rv;
	}

}
