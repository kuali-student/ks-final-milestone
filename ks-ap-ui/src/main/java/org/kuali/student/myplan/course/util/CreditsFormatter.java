package org.kuali.student.myplan.course.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

/**
 * Turns credits info into Strings.
 */
public class CreditsFormatter {

	final static Logger logger = Logger.getLogger(CreditsFormatter.class);

	/**
	 * Formats credit options list as a String.
	 * 
	 * @param courseInfo
	 * @return
	 */
	public static String formatCredits(CourseInfo courseInfo) {
		LRCService lrc = KsapFrameworkServiceLocator.getLrcService();
		String credits = "";

		List<ResultValuesGroupInfo> options = courseInfo.getCreditOptions();
		if (options.size() == 0) {
			logger.warn("Credit options list was empty.");
			return credits;
		}
		/* At UW this list should only contain one item. */
		if (options.size() > 1) {
			logger.warn("Credit option list contained more than one value.");
		}
		ResultValuesGroupInfo rci = options.get(0);

		/**
		 * Credit values are provided in three formats: FIXED, LIST (Multiple),
		 * and RANGE (Variable). Determine the format and parse it into a String
		 * representation.
		 */
		String type = rci.getTypeKey();
		if (type.equals("kuali.result.values.group.type.fixed")) {
			boolean useAttributes = rci.getResultValueKeys().isEmpty();
			if (!useAttributes)
				try {
					ResultValueInfo rv = lrc.getResultValue(rci
							.getResultValueKeys().get(0),
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
					if (rv == null)
						useAttributes = true;
					else
						credits = trimCredits(rv.getValue());
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("LRC lookup error", e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("LRC lookup error", e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("LRC lookup error", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("LRC lookup error", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("LRC lookup error", e);
				}
			if (useAttributes)
				credits = trimCredits(rci.getAttributeValue("fixedCreditValue"));
		} else if (type.equals("kuali.result.values.group.type.range")) {
			ResultValueRangeInfo rvr = rci.getResultValueRange();
			if (rvr != null)
				credits = trimCredits(rvr.getMinValue()) + "-"
						+ trimCredits(rvr.getMaxValue());
			else
				credits = trimCredits(rci.getAttributeValue("minCreditValue"))
						+ "-"
						+ trimCredits(rci.getAttributeValue("maxCreditValue"));
		} else if (type.equals("kuali.result.values.group.type.multiple")) {
			List<String> rvks = rci.getResultValueKeys();
			List<BigDecimal> rvs = new java.util.ArrayList<BigDecimal>(
					rvks.size());
			for (String rvk : rvks)
				try {
					rvs.add(new BigDecimal(lrc.getResultValue(
							rvk,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo()).getValue()));
				} catch (DoesNotExistException e) {
					throw new IllegalArgumentException("LRC lookup error", e);
				} catch (InvalidParameterException e) {
					throw new IllegalArgumentException("LRC lookup error", e);
				} catch (MissingParameterException e) {
					throw new IllegalArgumentException("LRC lookup error", e);
				} catch (OperationFailedException e) {
					throw new IllegalStateException("LRC lookup error", e);
				} catch (PermissionDeniedException e) {
					throw new IllegalStateException("LRC lookup error", e);
				}
			Collections.sort(rvs);
			StringBuilder sb = new StringBuilder();
			for (BigDecimal rv : rvs) {
				if (sb.length() > 0)
					sb.append(", ");
				sb.append(trimCredits(rv.toString()));
			}
			credits = sb.toString();
		} else {
			logger.error("Unknown Course Credit type [" + type + "].");
		}
		return credits;
	}

	/**
	 * Drop the decimal point and and trailing zero from credits.
	 * 
	 * @return The supplied value minus the trailing ".0"
	 */
	public static String trimCredits(String credits) {
		credits = credits == null ? "" : credits.trim();
		if (credits.endsWith(".0"))
			credits = credits.substring(0, credits.length() - 2);
		return credits;
	}
}
