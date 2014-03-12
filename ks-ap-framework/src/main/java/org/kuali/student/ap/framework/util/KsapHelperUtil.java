package org.kuali.student.ap.framework.util;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.infc.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.or;

public class KsapHelperUtil {

    private static final Logger LOG = LoggerFactory.getLogger(KsapHelperUtil.class);

    static List<String> termTypes;

    public static Predicate[] getTermPredicates() {
        Predicate predicates[] = new Predicate[getTermTypes().size()];
        for(int i=0;i<getTermTypes().size();i++){
            predicates[i]=equal("typeKey", getTermTypes().get(i));
        }
        return predicates;
    }

    public static List<String> getTermTypes(){
        if(termTypes==null){
            termTypes = new ArrayList<String>();
            String types[] = ConfigContext.getCurrentContextConfig().getProperty("ks.ap.planner.term.types").split(",");
            for(String term : types){
                termTypes.add(term);
            }
        }
        return termTypes;
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
}
