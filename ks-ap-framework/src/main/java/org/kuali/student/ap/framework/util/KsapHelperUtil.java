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
package org.kuali.student.ap.framework.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

/**
 * This is a common utility class for the KSAP application project containing widely used static methods.
 */
public class KsapHelperUtil {

    private static final Logger LOG = LoggerFactory.getLogger(KsapHelperUtil.class);
    private static final String CURRENT_DATE_OVERRIDE = "ks.ap.current.date.override";

    static List<String> termTypes;
    private static Date currentDate = null;

    /**
     * Gets the list of term types used by the ksap application when looking for and handling atp terms.  This includes
     * loading term data, available term options, etc. The term type list is set in the application configuration file
     * using the key "ks.ap.planner.term.types"
     *
     * @return The list of term types found for this deployment of the application
     */
    public static List<String> getTermTypes(){
        if(termTypes==null){
            termTypes = new ArrayList<String>();
            String types[] = ConfigContext.getCurrentContextConfig().getProperty("ks.ap.planner.term.types").split(",");
            for(String term : types){
                if(termTypes.contains(term)) continue;
                termTypes.add(term);
            }
        }
        return termTypes;
    }

    /**
     * This creates an array of equal predicates for when the typeKey column equals one of the term types used in the
     * application
     *
     * @return A list of equal predicates
     */
    public static Predicate[] getTermPredicates() {
        Predicate predicates[] = new Predicate[getTermTypes().size()];
        for(int i=0;i<getTermTypes().size();i++){
            predicates[i]=equal("typeKey", getTermTypes().get(i));
        }
        return predicates;
    }

    /**
     * Build an array of equal predicates for a list of terms.
     * Filters terms by the SOC state of published
     *
     * @param terms - List of terms to be in predicate
     * @return An array of equal predicates of ("atpId',termId)
     */
    public static Predicate[] getTermPredicates(List<Term> terms) {
        // Build predicate based on term id
        Predicate termPredicates[] = new Predicate[terms.size()];
        for(int i=0;i<terms.size();i++){
            termPredicates[i]=equal("termId", terms.get(i).getId());
        }
        try {
            // Get Published Soc states based on term predicates
            QueryByCriteria query = QueryByCriteria.Builder.fromPredicates(
                    or(termPredicates), equal("socState", CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY));
            List<SocInfo> socs = KsapFrameworkServiceLocator.getCourseOfferingSetService().searchForSocs(query,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());

            // Create term predicates based on published soc states
            Predicate predicates[] = new Predicate[socs.size()];
            for(int j=0;j<socs.size();j++){
                predicates[j]=equal("atpId", socs.get(j).getTermId());
            }
            return predicates;
        } catch (Exception e) {
            LOG.warn("Unable to build term list for scheduling", e);
        }

        // If predicate list can not be created return empty
        return new Predicate[0];
    }

    /**
     * Build an array of equal predicates for a list of courses
     *
     * @param courses - List of courses to be in the predicate
     * @return An array of equal predicates of ("cluId",courseId)
     */
    public static Predicate[] getCoursePredicates(List<CourseSearchItem> courses) {
        Predicate predicates[] = new Predicate[courses.size()];
        for(int i=0;i<courses.size();i++){
            predicates[i]=equal("cluId", courses.get(i).getCourseId());
        }
        return predicates;
    }

    /**
     * Build an array of equal predicates for a list of course ids
     *
     * @param courses - List of course ids to be in the predicate
     * @return An array of equal predicates of ("cluId",courseId)
     */
    public static Predicate[] getCourseIdPredicates(List<String> courses) {
        Predicate predicates[] = new Predicate[courses.size()];
        for(int i=0;i<courses.size();i++){
            predicates[i]=equal("cluId", courses.get(i));
        }
        return predicates;
    }

    public static String getCellValue(SearchResultRow row, String key) {
        for (SearchResultCell cell : row.getCells()) {
            if (key.equals(cell.getKey())) {
                return cell.getValue();
            }
        }
        LOG.warn("cell result '" + key + "' not found...returning ");
        return "";
    }

    public static Date getCurrentDate(){
        if(currentDate == null){
            String override = ConfigContext.getCurrentContextConfig().getProperty(CURRENT_DATE_OVERRIDE);
            if(StringUtils.isEmpty(override)){
                currentDate = new Date();
            }else{
                try{
                    currentDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.parse(override);
                }catch (IllegalArgumentException e){
                    LOG.error("Unable to set override date using actual date instead.", e);
                    currentDate = new Date();
                }
            }
        }

        return currentDate;
    }

    /**
     * Comparator for sorting the facet values of Numerical facets
     */
    public static final Comparator<String> NUMERIC = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;
            if (o1.startsWith("<") && !o2.startsWith("<"))
                return -1;
            if (o2.startsWith("<") && !o1.startsWith("<"))
                return 1;
            int i1;
            try {
                i1 = Integer.parseInt(o1);
            } catch (NumberFormatException e) {
                i1 = Integer.MAX_VALUE;
            }
            int i2;
            try {
                i2 = Integer.parseInt(o2);
            } catch (NumberFormatException e) {
                i2 = Integer.MAX_VALUE;
            }
            return i1 == i2 ? 0 : i1 < i2 ? -1 : 1;
        }
    };

    /**
     * Comparator for sorting the facet values of Alphabetical facets
     */
    public static final Comparator<String> ALPHA = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;
            if (o1.equals(o2))
                return 0;
            if ("Unknown".equals(o1) || "None".equals(o1))
                return 1;
            if ("Unknown".equals(o2) || "None".equals(o2))
                return -1;
            return o1.compareTo(o2);
        }
    };

    /**
     * Comparator for sorting the facet values of Terms
     */
    public static final Comparator<String> TERMS = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {

            // Compare terms for nulls
            if (o1 == null && o2 == null)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;
            if (o1.equals(o2))
                return 0;
            if ("Unknown".equals(o1) || "None".equals(o1))
                return 1;
            if ("Unknown".equals(o2) || "None".equals(o2))
                return -1;

            // Compare terms for proper format
            String[] s1 = o1.split(" ");
            String[] s2 = o2.split(" ");
            if (s1.length != 2 && s2.length != 2)
                return o1.compareTo(o2);
            if (s1.length != 2)
                return 1;
            if (s2.length != 2)
                return -1;

            // Compare year value of the terms
            int year1;
            try {
                year1 = Integer.parseInt(s1[1]);
                if (year1 < 0 || year1 > 100)
                    year1 = -1;
            } catch (NumberFormatException e) {
                year1 = -1;
            }
            int year2;
            try {
                year2 = Integer.parseInt(s2[1]);
                if (year2 < 0 || year2 > 100)
                    year2 = -1;
            } catch (NumberFormatException e) {
                year2 = -1;
            }
            if (year1 == -1)
                return -1;
            if (year2 == -1)
                return 1;
            if(year1 != year2) return Integer.compare(year1,year2);

            // Compare term value of the terms
            String termOrder = ConfigContext.getCurrentContextConfig().getProperty("ks.ap.search.terms.offered.abbrev");
            int term1Location = termOrder.indexOf(s1[0]);
            int term2Location = termOrder.indexOf(s2[0]);
            return Integer.compare(term1Location,term2Location);
        }

    };
}
