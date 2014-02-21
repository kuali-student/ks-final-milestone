package org.kuali.student.ap.framework.course;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

/**
 * Turns credits info into Strings.
 */
public class CreditsFormatter {

	private static final Logger LOG = Logger.getLogger(CreditsFormatter.class);

	public static class Range {
		private final BigDecimal min;
		private final BigDecimal max;
		private final List<BigDecimal> multiple;

		public Range(BigDecimal min, BigDecimal max) {
			this.min = min;
			this.max = max;
			this.multiple = null;
		}

		public Range(List<BigDecimal> multiple) {
			this.min = null;
			this.max = null;
			this.multiple = multiple;
		}

		public BigDecimal getMin() {
			return min;
		}

		public BigDecimal getMax() {
			return max;
		}

		public List<BigDecimal> getMultiple() {
			return multiple;
		}
	}

	public static Range getRange(Course course) {
		LRCService lrc = KsapFrameworkServiceLocator.getLrcService();

		BigDecimal min = BigDecimal.ZERO, max = BigDecimal.ZERO;

		List<ResultValuesGroupInfo> options = course.getCreditOptions();
		if (options.size() == 0) {
			LOG.warn("Credit options list was empty.");
			return null;
		}
		/* At UW this list should only contain one item. */
		if (options.size() > 1) {
			LOG.warn("Credit option list contained more than one value.");
		}

        // Aways use the first credit option if multiple are found.
        ResultValuesGroupInfo rci;
        try{
            rci = KSCollectionUtils.getRequiredZeroElement(options);
        }catch(OperationFailedException e){
            throw new RuntimeException("Multiple Credit Options Defined", e);
        }
		/**
		 * Credit values are provided in three formats: FIXED, LIST (Multiple),
		 * and RANGE (Variable). Determine the format and parse it into a String
		 * representation.
		 */
		String type = rci.getTypeKey();
		if (type.equals("kuali.result.values.group.type.fixed")) {
			String creditString = null;
			boolean useAttributes = rci.getResultValueKeys().isEmpty();
			if (!useAttributes)
				try {
					ResultValueInfo rv = lrc.getResultValue(KSCollectionUtils.getRequiredZeroElement(rci.getResultValueKeys()),
							KsapFrameworkServiceLocator.getContext().getContextInfo());
					if (rv == null)
						useAttributes = true;
					else
						creditString = rv.getValue();
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
				creditString = rci.getAttributeValue("fixedCreditValue");

			if (creditString != null)
				try {
					min = max = new BigDecimal(creditString);
				} catch (NumberFormatException e) {
					LOG.warn("Invalid credits value for course " + course.getId(), e);
				}

		} else if (type.equals("kuali.result.values.group.type.range")) {

			String minString, maxString;
			ResultValueRangeInfo rvr = rci.getResultValueRange();
			if (rvr != null) {
				minString = rvr.getMinValue();
				maxString = rvr.getMaxValue();
			} else {
				minString = rci.getAttributeValue("minCreditValue");
				maxString = rci.getAttributeValue("maxCreditValue");
			}

			if (minString != null)
				try {
					min = new BigDecimal(minString);
				} catch (NumberFormatException e) {
					LOG.warn("Invalid min credits value for course " + course.getId(), e);
				}

			if (maxString != null)
				try {
					max = new BigDecimal(maxString);
				} catch (NumberFormatException e) {
					LOG.warn("Invalid min credits value for course " + course.getId(), e);
				}

			if (min == null)
				min = max;
			if (max == null)
				max = min;

		} else if (type.equals("kuali.result.values.group.type.multiple")) {
			List<String> rvks = rci.getResultValueKeys();
			List<BigDecimal> rvs = new java.util.ArrayList<BigDecimal>(rvks.size());
			for (String rvk : rvks)
				try {
					rvs.add(new BigDecimal(lrc.getResultValue(rvk,
							KsapFrameworkServiceLocator.getContext().getContextInfo()).getValue()));
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
			return new Range(rvs);
		}

		return new Range(min, max);
	}

	/**
	 * Formats credit options list as a String.
	 * 
	 * @param course
	 * @return
	 */
	public static String formatCredits(Course course) {
		return formatCredits(getRange(course));
	}

	public static String formatCredits(Range range) {
		StringBuilder sb = new StringBuilder();
		if (range != null) {
            if (range.multiple != null) {
                for (BigDecimal rv : range.multiple) {
                    if (sb.length() > 0)
                        sb.append(", ");
                    sb.append(trimCredits(rv.toString()));
                }
            } else {
                sb.append(trimCredits(range.min.toString()));
                if (range.min.compareTo(range.max) < 0)
                    sb.append(" - ").append(trimCredits(range.max.toString()));
            }
        }
		return sb.toString();
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
