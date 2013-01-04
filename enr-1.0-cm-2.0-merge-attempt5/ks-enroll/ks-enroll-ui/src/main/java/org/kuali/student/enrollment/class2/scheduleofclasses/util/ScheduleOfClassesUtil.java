package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/25/12
 * Time: 3:10 PM
 * General Schedule of Classes utility file. Methods should be static
 */
public class ScheduleOfClassesUtil {

    public  static List<AtpInfo> getValidSocTerms(CourseOfferingSetService courseOfferingSetService, AtpService atpService, ContextInfo context){
        List<SocInfo> socs;
        List<String> termIds = new ArrayList<String>();
        List<AtpInfo> atps = new ArrayList<AtpInfo>();

        //Build a predicate to search for published Socs
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        qBuilder.setPredicates();
        Predicate pred = equal("socState", "kuali.soc.state.published");
        qBuilder.setPredicates(pred);
        try {
            //Try the search
            socs = courseOfferingSetService.searchForSocs(qBuilder.build(), context);
            for(SocInfo soc: socs){
                //Add all published Soc termIds to termIds List
                termIds.add(soc.getTermId());
            }
            //Use AtpService to get Term name by Id
            atps = atpService.getAtpsByIds(termIds, context);

        } catch (Exception e) {
            throw new RuntimeException("Error getting Valid SOC Terms", e);
        }

        return atps;
    }

    /**
     * This method will return the first ATP that contains the current time. If that isn't possible it will return
     * the closest to the current time.
     * @param atpInfos
     * @return
     */
    public static AtpInfo getCurrentAtp(List<AtpInfo> atpInfos){

        Long currTime = System.currentTimeMillis();
        AtpInfo closest = null;

        for(AtpInfo atp : atpInfos){
            if(isValid(atp)){
                if(isBetween(currTime, atp))  {
                    return atp;
                }else{
                    if(closest == null){
                        closest = atp;
                    }  else{
                        closest = getClosest(currTime,closest,atp);
                    }
                }

            }
        }



        return closest;

    }

    /**
     * checks if the atp is not null, and that the start and end dates are not null
     * @param atp
     * @return
     */
    private static boolean isValid(AtpInfo atp){
        if(atp != null && atp.getStartDate() != null && atp.getEndDate() != null)
            return true;
        else
            return false;

    }


    /**
     * If the currTime is between the atp start and end date return true else false
     * @param currTime
     * @param atp
     * @return
     */
    private static boolean isBetween(Long currTime, AtpInfo atp){
        if(atp.getStartDate() != null && atp.getStartDate() != null){
            Long startDate = atp.getStartDate().getTime();
            Long endDate = atp.getEndDate().getTime();
            if(currTime >= startDate && currTime <= endDate){
                return true;
            }
        }
        return false;
    }

    /**
     * returns the atp closest to the currTime.
     * @param currTime
     * @param pointA
     * @param pointB
     * @return
     */
    private static AtpInfo getClosest(Long currTime, AtpInfo pointA, AtpInfo pointB){
         Long distA = Math.abs(currTime - getAverageTime(pointA));
         Long distB = Math.abs(currTime - getAverageTime(pointB));

         if(distA <= distB)
            return pointA;
         else
             return pointB;
    }

    private static Long getAverageTime(AtpInfo atp)  {
         if(isValid(atp)){
             return Long.valueOf((atp.getStartDate().getTime() + atp.getEndDate().getTime())/2);
         } else{
             return Long.MIN_VALUE;
         }

    }

}
