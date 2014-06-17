/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.ap.coursesearch;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

/**
 * Handles the formatting and parsing of Credit information
 */
public class CreditsFormatter {

	private static final Logger LOG = LoggerFactory.getLogger(CreditsFormatter.class);

    private static final String FIXED_CREDIT_ATTRIBUTE_KEY = "fixedCreditValue";
    private static final String MIN_CREDIT_ATTRIBUTE_KEY = "minCreditValue";
    private static final String MAX_CREDIT_ATTRIBUTE_KEY = "maxCreditValue";

    /**
     * Stores information about a variety of values
     */
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

    /**
     * Retrieves the range of values for the credits of a course
     *
     * @param course - The course to retrieve credit information for
     * @return A filled in Range for possible credit values of the course
     */
    public static Range getRange(Course course){
        List<ResultValuesGroupInfo> options = course.getCreditOptions();

        // Check group options
        int creditGroupIndex = 0;
        if (options.size() == 0) {
            LOG.warn("Credit options list was empty.");
            return null;
        }
		/* At UW this list should only contain one item. */
        if (options.size() > 1) {
            LOG.warn("Credit option list contained more than one value.");
            // If more than 1 are found determine which to use
            creditGroupIndex = 0;  //For ksap we are just using the first option
        }

        // Retrieve group options
        ResultValuesGroupInfo creditGroup = options.get(creditGroupIndex);

        // Parse range
        Range range = getRange(creditGroup);

        return range;
    }

    /**
     * Parse the range of values for a specific credit group
     *
     * @param creditGroup - The credit group to parse
     * @return A filled in Range for possible credit values
     */
	public static Range getRange(ResultValuesGroupInfo creditGroup) {
		LRCService lrc = KsapFrameworkServiceLocator.getLrcService();
		BigDecimal min = BigDecimal.ZERO, max = BigDecimal.ZERO;

		/**
		 * Credit values are provided in three formats: FIXED, LIST (Multiple),
		 * and RANGE (Variable). Determine the format and parse it into a String
		 * representation.
		 */
		String type = creditGroup.getTypeKey();
		if (type.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
			String creditString = null;
			boolean useAttributes = creditGroup.getResultValueKeys().isEmpty();
			if (!useAttributes)
				try {
					ResultValueInfo resultValue = lrc.getResultValue(KSCollectionUtils.getRequiredZeroElement(creditGroup.getResultValueKeys()),
							KsapFrameworkServiceLocator.getContext().getContextInfo());
					if (resultValue == null)
						useAttributes = true;
					else
						creditString = resultValue.getValue();
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
				creditString = creditGroup.getAttributeValue(FIXED_CREDIT_ATTRIBUTE_KEY);

			if (creditString != null)
				try {
					min = max = new BigDecimal(creditString);
				} catch (NumberFormatException e) {
					LOG.warn(String.format("Invalid credits value for scale %s", creditGroup.getResultScaleKey()), e);
				}

		} else if (type.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {

			String minString, maxString;
			ResultValueRangeInfo resultValueRange = creditGroup.getResultValueRange();
			if (resultValueRange != null) {
				minString = resultValueRange.getMinValue();
				maxString = resultValueRange.getMaxValue();
			} else {
				minString = creditGroup.getAttributeValue(MIN_CREDIT_ATTRIBUTE_KEY);
				maxString = creditGroup.getAttributeValue(MAX_CREDIT_ATTRIBUTE_KEY);
			}

			if (minString != null)
				try {
					min = new BigDecimal(minString);
				} catch (NumberFormatException e) {
					LOG.warn(String.format("Invalid min credits value for scale %s", creditGroup.getResultScaleKey()), e);
				}

			if (maxString != null)
				try {
					max = new BigDecimal(maxString);
				} catch (NumberFormatException e) {
					LOG.warn(String.format("Invalid min credits value for scale %s", creditGroup.getResultScaleKey()), e);
				}

			if (min == null)
				min = max;
			if (max == null)
				max = min;

		} else if (type.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
			List<String> resultValueKeys = creditGroup.getResultValueKeys();
			List<BigDecimal> resultValues = new java.util.ArrayList<BigDecimal>(resultValueKeys.size());
			for (String resultValueKey : resultValueKeys)
				try {
					resultValues.add(new BigDecimal(lrc.getResultValue(resultValueKey,
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
			Collections.sort(resultValues);
			return new Range(resultValues);
		}

		return new Range(min, max);
	}

    /**
     * Parse the type of credit values for a credit group
     *
     * @param creditGroup - The credit group to parse
     * @return The type of credit values for the group.
     */
    public static String getCreditType(ResultValuesGroupInfo creditGroup){
        String type;
        try{
            TypeInfo typeInfo = KsapFrameworkServiceLocator.getTypeService().getType(creditGroup.getTypeKey(),
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            type = typeInfo.getName();
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Type lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalStateException("Type lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalStateException("Type lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Type lookup error", e);
        } catch (DoesNotExistException e) {
            LOG.warn("Unable to find type for "+creditGroup.getTypeKey(),e);
            type="Unknown";
        }
        return type;
    }

    /**
     * Parse the type of credit values for a list of credit groups
     *
     * @param creditGroups - The credit groups to parse
     * @return The a map linking the credit group to their parsed type
     */
    public static Map<String,String> getCreditType(List<ResultValuesGroupInfo> creditGroups){
        Map<String,String> types = new HashMap<String,String>();

        try{
            Predicate typePredicate[] = new Predicate[creditGroups.size()];
            for(int i=0;i<creditGroups.size();i++){
                typePredicate[i]=equal("id", creditGroups.get(i).getTypeKey());
            }
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(or(typePredicate));
            List<TypeInfo> typeInfos = KsapFrameworkServiceLocator.getTypeService().searchForTypes(query,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
            for(TypeInfo info : typeInfos){
                types.put(info.getKey(),info.getName());
            }
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Type lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalStateException("Type lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalStateException("Type lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Type lookup error", e);
        }

        for(ResultValuesGroupInfo resultValue : creditGroups){
            if(!types.containsKey(resultValue.getTypeKey())){
                types.put(resultValue.getKey(),"Unknown");
            }
        }

        return types;
    }

	/**
	 * Formats credit values of a course
	 * 
	 * @param course - The course to retrieve credit value for
	 * @return Formatted string of the credit values of the course
	 */
	public static String formatCredits(Course course) {
		return formatCredits(getRange(course));
	}

    /**
     * Format credit values of a course offering
     * @param courseOffering - The course offering to retrieve credit value for
     * @return Formatted string of the credit values of the course offering
     */
    public static String formatCredits(CourseOffering courseOffering) {
        LRCService lrc = KsapFrameworkServiceLocator.getLrcService();
        ResultValuesGroupInfo resultValuesGroupInfo = null;
        try {
            resultValuesGroupInfo = lrc.getResultValuesGroup(courseOffering.getCreditOptionId(),
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("LRC lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LRC lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("LRC lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("LRC lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("LRC lookup error", e);
        }
        return formatCredits(getRange(resultValuesGroupInfo));
    }

    /**
     * Formats credit values of a range
     *
     * @param range - The range of values to format
     * @return Formatted string from the range values
     */
	public static String formatCredits(Range range) {
		StringBuilder sb = new StringBuilder();
		if (range != null) {
            if (range.multiple != null) {
                for (BigDecimal resultValue : range.multiple) {
                    if (sb.length() > 0)
                        sb.append(", ");
                    sb.append(trimCredits(resultValue.toString()));
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

    /**
     * Comparator used specifically for the credit facet.  We used to have only integers in there
     * but switched to floats so that we could support partial credits (3.5) and the addition of a
     * value that wraps up all values larger than some limit (6+)
     */
    public static final Comparator<String> CREDIT = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;
            if (o1.endsWith("+") && !o2.endsWith("+"))
                return 1;
            if (o2.endsWith("+") && !o1.endsWith("+"))
                return -1;
            float i1;
            try {
                i1 = Float.parseFloat(o1);
            } catch (NumberFormatException e) {
                i1 = Float.MAX_VALUE;
            }
            float i2;
            try {
                i2 = Float.parseFloat(o2);
            } catch (NumberFormatException e) {
                i2 = Float.MAX_VALUE;
            }
            return i1 == i2 ? 0 : i1 < i2 ? -1 : 1;
        }
    };
}
