package org.kuali.student.myplan.plan.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

/**
 * Assembles a list of published terms.
 */
public class TermsListBuilder extends KeyValuesBase {

	private static final long serialVersionUID = 4456030609113645225L;

	private static int futureTermsCount = 6;

	/**
	 * Build and returns the list of available terms.
	 * 
	 * @return A List of available terms as KeyValue items.
	 */
	@Override
	public List<KeyValue> getKeyValues() {
		ContextInfo context = KsapFrameworkServiceLocator.getContext()
				.getContextInfo();
		AtpService atpService = KsapFrameworkServiceLocator.getAtpService();
		Calendar c = Calendar.getInstance();
		c.setTime(KsapFrameworkServiceLocator.getTermHelper().getCurrentTerms().get(0).getStartDate());
		c.add(Calendar.DATE, -1);
		Date startDate = c.getTime();
		c.setTime(new Date());
		c.add(Calendar.YEAR, futureTermsCount);
		Date endDate = c.getTime();
		List<KeyValue> keyValues = new java.util.ArrayList<KeyValue>();
		try {
			for (AtpInfo atp : atpService.getAtpsByDates(startDate, endDate,
					context))
				keyValues.add(new ConcreteKeyValue(atp.getId(), atp.getName()));
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("ATP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("ATP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("ATP lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("ATP lookup failure", e);
		}
		return keyValues;
	}

}
