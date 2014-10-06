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
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private static final int MULTI_CREDIT_DISPLAY_LIMIT = 3;
    private static final String COURSE_RESULT_TYPE = "kuali.resultType.creditCourseResult";
    private static final String CREDIT_TYPE_PREFIX = "kuali.result.values.group.type.";


    /**
     * Stores information about a variety of values
     */
	public static class Range {
		private final BigDecimal min;
        private final BigDecimal max;
		private final List<BigDecimal> multiple;
        private final String type;

		public Range(BigDecimal min, BigDecimal max, String type) {
			this.min = min;
			this.max = max;
			this.multiple = null;
            this.type = type.replace(CREDIT_TYPE_PREFIX,"").toUpperCase();
		}

		public Range(List<BigDecimal> multiple, String type) {

            if (multiple == null || multiple.isEmpty()) {
                LOG.warn("the input of a multiple-value list is empty or null");
                this.min = null;
                this.max = null;
                this.multiple = multiple;
            } else {
                Collections.sort(multiple);
                this.min = multiple.get(0);
                this.max = multiple.get(multiple.size()-1);
                this.multiple = multiple;
            }
            this.type = type.replace(CREDIT_TYPE_PREFIX,"").toUpperCase();
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

        public String getType(){
            return type;
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

        /* At UW this list should only contain one item. */
        if (options.size() > 1) {
            LOG.warn("Credit option list contained more than one value.");
        }

        // Check group options
        ResultValuesGroupInfo creditGroup = getSelectedCreditGroup(options);

        if (creditGroup == null) {
            LOG.warn("Credit options list was empty.");
            return null;
        }

        // Parse range
        Range range = getRange(creditGroup);

        return range;
    }

    /**
     * Retrieves the range of values for the credits of a course
     *
     * @param courseId - The course to retrieve credit information for
     * @return A filled in Range for possible credit values of the course
     */
    public static Range getRange(String courseId){
        ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext().getContextInfo();
        List<CluResultInfo> cluResults = null;
        try {
            cluResults = KsapFrameworkServiceLocator.getCluService().getCluResultByClu(courseId,contextInfo);
        } catch (DoesNotExistException e) {
            throw new RuntimeException("Clu service error", e);
        } catch (InvalidParameterException e) {
            throw new RuntimeException("Clu service error", e);
        } catch (MissingParameterException e) {
            throw new RuntimeException("Clu service error", e);
        } catch (OperationFailedException e) {
            throw new RuntimeException("Clu service error", e);
        }

        List<ResultValuesGroupInfo> results = new ArrayList<ResultValuesGroupInfo>();
        //Loop through all the CluResults to find the one with the matching type
        for(CluResultInfo cluResult:cluResults){
            if(COURSE_RESULT_TYPE.equals(cluResult.getTypeKey())){
                //Loop through all options and add to the list of Strings
                for(ResultOptionInfo resultOption: cluResult.getResultOptions()){
                    try {
                        if(resultOption.getResultComponentId()!=null){
                            ResultValuesGroupInfo resultValuesGroup = KsapFrameworkServiceLocator.getLrcService().getResultValuesGroup(resultOption.getResultComponentId(), contextInfo);
                            results.add(resultValuesGroup);
                        }
                    } catch (Exception e) {
                        LOG.warn("Course Credit option:"+resultOption.getId()+" refers to a invalid ResultValuesGroupInfo "+ resultOption.getResultComponentId());
                    }
                }
            }
        }
        ResultValuesGroupInfo creditGroup = getSelectedCreditGroup(results);

        if (creditGroup == null) {
            LOG.warn("Credit options list was empty.");
            return null;
        }

        // Parse range
        Range range = getRange(creditGroup);

        return range;
    }

    private static ResultValuesGroupInfo getSelectedCreditGroup(List<ResultValuesGroupInfo> options){
        ResultValuesGroupInfo creditGroup = null;

        for(ResultValuesGroupInfo option : options){
            if(option.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED) ||
                    option.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE) ||
                    option.getTypeKey().equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)){
                creditGroup=option;
                break;
            }
        }

        return creditGroup;
    }


    /**
     * Retrieves the range of values for the credits of a course offering
     *
     * @param courseOffering - The course offering to retrieve credit information for
     * @return A filled in Range for possible credit values of the course offering
     */
    public static Range getRange(CourseOffering courseOffering) {
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
        Range range = getRange(resultValuesGroupInfo);
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
			return new Range(resultValues,type);
		}

		return new Range(min, max,type);
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
	 * 
	 * @param course - The course to retrieve credit value for
	 * @return Formatted string of the credit values of the course
	 */
	public static String formatCredits(Course course) {
		return formatCredits(getRange(course));
	}

    /**
     * Formats credit values of a range
     * For multi credits should always display all the possible values:1, 1.5, 2, 2.5, 3
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
                    sb.append("&ndash;").append(trimCredits(range.max.toString()));
            }
        }
		return sb.toString();
	}

    /**
     * Formats credit values of a course with a short version
     *
     *
     * @param course - The course to retrieve credit value for
     * @return Formatted string of the credit values of the course
     */
    public static String formatCreditsShortVersion(Course course) {
        return formatCreditsShortVersion(getRange(course));
    }

    /**
     * Formats credit values of a range
     * For multi credits with only two credit options should display as: 1, 5
     * For multi credits with more than two credit options should display as a range: 1-5.5
     *
     * @param range - The range of values to format
     * @return Formatted string from the range values
     */
    public static String formatCreditsShortVersion(Range range) {
        StringBuilder sb = new StringBuilder();
        if (range != null) {
            if (range.multiple != null && !range.multiple.isEmpty() ) {
                if (range.multiple.size()>2){
                    sb.append(trimCredits(range.min.toString()));
                    sb.append("&ndash;").append(trimCredits(range.max.toString()));
                }
                else if (range.multiple.size()== 2){
                    sb.append(trimCredits(range.min.toString()));
                    sb.append(", ").append(trimCredits(range.max.toString()));
                }
                else if (range.multiple.size()== 1){
                    sb.append(trimCredits(range.min.toString()));
                }
            } else {
                sb.append(trimCredits(range.min.toString()));
                if (range.min.compareTo(range.max) < 0)
                    sb.append("&ndash;").append(trimCredits(range.max.toString()));
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
		if (credits.endsWith(".0")) {
			credits = credits.substring(0, credits.length() - 2);
        }
        else if (credits.endsWith(".00")){
            credits = credits.substring(0, credits.length() - 3);
        }
		return credits;
	}

    /**
     * Generate a formatted string representing the credits, but truncate when you hit the
     * max limit, as specified by MULTI_CREDIT_DISPLAY_LIMIT.  Once you hit that limit, the last value is replaced
     * by an ellipsis character.
     * @param credits - Array of multiple credit values
     * @param initialDisplay - Previously computed, full credit display
     * @return
     */
    public static String formatCreditsTruncated(List<BigDecimal> credits, String initialDisplay) {
        StringBuilder creditStr = new StringBuilder(initialDisplay);
        if (credits != null && credits.size() > MULTI_CREDIT_DISPLAY_LIMIT) {
            creditStr = new StringBuilder();
            for (int i=0; i<MULTI_CREDIT_DISPLAY_LIMIT-1; i++) {
                creditStr.append(trimCredits(String.valueOf(credits.get(i))));
                creditStr.append(", ");
            }
            creditStr.append("&hellip;");
        }
        return creditStr.toString();
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
