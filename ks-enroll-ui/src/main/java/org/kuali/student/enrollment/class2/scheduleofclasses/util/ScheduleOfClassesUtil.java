package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/25/12
 * Time: 3:10 PM
 * General Schedule of Classes utility file. Methods should be static
 */
public class ScheduleOfClassesUtil {

    private static final Logger LOG = Logger.getLogger(ScheduleOfClassesUtil.class);

    /**
     * Hiding the constructor this this is a utility class (all static methods).
     */
    private ScheduleOfClassesUtil() {}

    /**
     * Builds a list of terms by querying for SOCs with state == published, then querying for the related ATPs by id.
     *
     * @param courseOfferingSetService
     * @param atpService
     * @param context
     * @return
     */
    public static List<AtpInfo> getValidSocTerms(CourseOfferingSetService courseOfferingSetService, AtpService atpService, ContextInfo context) {
        List<SocInfo> socs;
        List<String> termIds = new ArrayList<String>();
        List<AtpInfo> atps = new ArrayList<AtpInfo>();

        // Build a predicate to search for published Socs
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates();
        Predicate pred = equal(CourseOfferingSetServiceConstants.SearchParameters.SOC_STATE, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY);
        qBuilder.setPredicates(pred);
        try {
            socs = courseOfferingSetService.searchForSocs(qBuilder.build(), context);
            for(SocInfo soc: socs){
                // Add all published Soc termIds to termIds List
                termIds.add(soc.getTermId());
            }
            // Use AtpService to get Term name by Id
            atps = atpService.getAtpsByIds(termIds, context);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Valid SOC Terms", e);
        }
        return atps;
    }

    /**
     * This method will return the term with the start date that is closest in the future.
     * If there is no future term then return null.
     * @param atpInfos
     * @return
     */
    public static AtpInfo getClosestAtp(List<AtpInfo> atpInfos) {
        Date now = new Date();
        AtpInfo closestAtp = null;

        for (AtpInfo atp : atpInfos) {
            if (isAtpValid(atp)) {
                //  Only consider the ATP if it's in the future.
                boolean isFuture = atp.getStartDate().after(now);
                if (isFuture) {
                    if (closestAtp == null) {
                        closestAtp = atp;
                    } else {
                        //  If this ATP has a sooner start date then make it "closest".
                        boolean isCloser = atp.getStartDate().before(closestAtp.getStartDate());
                        if (isCloser) {
                            closestAtp = atp;
                        }
                    }
                }
            }
        }
        return closestAtp;
    }

    /**
     * Validates that the ATP and its start and end dates aren't null.
     * @param atp
     * @return
     */
    private static boolean isAtpValid(AtpInfo atp){
        if (atp != null && atp.getStartDate() != null && atp.getEndDate() != null) {
            return true;
        } else {
            LOG.error(String.format("ATP %s has a null start or end date.", atp.getId()));
            return false;
        }
    }
}
