package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.SeatPoolWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.coursewaitlist.dto.CourseWaitListInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseWaitListServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActivityOfferingWrapperInquirableImpl extends InquirableImpl {
    private static final long serialVersionUID = 1L;

    @Override
    public ActivityOfferingWrapper retrieveDataObject(Map<String, String> dataObjectKeys) {
        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

            ActivityOfferingInfo aoInfo = CourseOfferingManagementUtil.getCourseOfferingService().getActivityOffering(dataObjectKeys.get(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID), contextInfo);
            ActivityOfferingWrapper aoWapper = new ActivityOfferingWrapper(aoInfo);

            //get the course offering
            CourseOfferingInfo courseOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(aoInfo.getCourseOfferingId(), contextInfo);

            // get the format offering
            FormatOfferingInfo formatOfferingInfo = CourseOfferingManagementUtil.getCourseOfferingService().getFormatOffering(aoInfo.getFormatOfferingId(), contextInfo);
            aoWapper.setFormatOffering(formatOfferingInfo);

            //get the waitlist info
            aoWapper.setHasWaitlistCO(courseOfferingInfo.getHasWaitlist());
            //From Bonnie: The logic has some problem. when courseWaitListInfoList.size() == 0, here you try to display an empty waitlist
           List <CourseWaitListInfo> courseWaitListInfoList = CourseOfferingManagementUtil.getCourseWaitListService().getCourseWaitListsByActivityOffering(aoInfo.getId(), contextInfo);
           int firstCourseWaitListInfo = 0;
           CourseWaitListInfo courseWaitListInfo = new CourseWaitListInfo();
            //set waitlist info in wrapper
            if ( null != courseWaitListInfoList && courseWaitListInfoList.size() > 0 )
            {
                courseWaitListInfo = courseWaitListInfoList.get(firstCourseWaitListInfo);
            }
            aoWapper.setCourseWaitListInfo(courseWaitListInfo);
            aoWapper.updateWaitListType();
            //looks like in inquiry view.xml we are using HasWaitlist for the "Waitlist active" field
            aoWapper.setHasWaitlist(false);
            if (CourseWaitListServiceConstants.COURSE_WAIT_LIST_ACTIVE_STATE_KEY.equals(courseWaitListInfo.getStateKey())){
                aoWapper.setHasWaitlist(true);
            }

            // Now have to deal with subterms: have to check if it's subterm or term
            TermInfo term = null;
            TermInfo subTerm=null;
            aoWapper.setSubTermName("None");
            TermInfo termTemp = CourseOfferingManagementUtil.getAcademicCalendarService().getTerm(aoInfo.getTermId(), contextInfo);
            List<TypeTypeRelationInfo> terms = CourseOfferingManagementUtil.getTypeService().getTypeTypeRelationsByRelatedTypeAndType(termTemp.getTypeKey(), TypeServiceConstants.TYPE_TYPE_RELATION_CONTAINS_TYPE_KEY, contextInfo);
            if (terms == null || terms.isEmpty()) {
                term = new TermInfo(termTemp);
            } else {
                subTerm = new TermInfo(termTemp);
                term = CourseOfferingManagementUtil.getAcademicCalendarService().getContainingTerms(aoInfo.getTermId(), contextInfo).get(0);
                TypeInfo subTermType = CourseOfferingManagementUtil.getTypeService().getType(subTerm.getTypeKey(), contextInfo);
                aoWapper.setSubTermName(subTermType.getName());
            }
            aoWapper.setTerm(term);
            if (term != null) {
                aoWapper.setTermName(term.getName());
            }
            aoWapper.setTermDisplayString(getTermDisplayString(aoInfo.getTermId(), term));

            if (subTerm!=null) {
                aoWapper.setTermStartEndDate(getTermStartEndDate(aoInfo.getTermId(), subTerm));
            } else {
                aoWapper.setTermStartEndDate(getTermStartEndDate(aoInfo.getTermId(), term));
            }
            // end subterms

            aoWapper.setCourseOfferingCode(aoInfo.getCourseOfferingCode());
            aoWapper.setCourseOfferingTitle(aoInfo.getCourseOfferingTitle());

            String sCredits = courseOfferingInfo.getCreditCnt();
            if (sCredits == null) {
                sCredits = "0";
            }
            aoWapper.setCredits(sCredits);
            //wrapper.setAbbreviatedActivityCode(info.getActivityCode().toUpperCase().substring(0,3));
            aoWapper.setActivityCode(aoInfo.getActivityCode());
            aoWapper.setAbbreviatedCourseType(CourseOfferingManagementUtil.getTypeService().getType(aoInfo.getTypeKey(), contextInfo).getName().toUpperCase().substring(0, 3));

            boolean readOnlyView = Boolean.parseBoolean(dataObjectKeys.get("readOnlyView"));
            aoWapper.setReadOnlyView(readOnlyView);


            StateInfo state = CourseOfferingManagementUtil.getStateService().getState(aoWapper.getAoInfo().getStateKey(), contextInfo);
            aoWapper.setStateName(state.getName());
            TypeInfo typeInfo = CourseOfferingManagementUtil.getTypeService().getType(aoWapper.getAoInfo().getTypeKey(), contextInfo);
            aoWapper.setTypeName(typeInfo.getName());

            // Get/Set SeatPools
            List<SeatPoolDefinitionInfo> seatPoolDefinitionInfoList = CourseOfferingManagementUtil.getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(aoInfo.getId(), contextInfo);

            //Sort the seatpools by priority order
            Collections.sort(seatPoolDefinitionInfoList, new Comparator<SeatPoolDefinitionInfo>() {
                @Override
                public int compare(SeatPoolDefinitionInfo sp1, SeatPoolDefinitionInfo sp2) {
                    return sp1.getProcessingPriority().compareTo(sp2.getProcessingPriority());
                }
            });

            List<SeatPoolWrapper> seatPoolWrapperList = new ArrayList<SeatPoolWrapper>();

            for (SeatPoolDefinitionInfo seatPoolDefinitionInfo : seatPoolDefinitionInfoList) {
                SeatPoolWrapper spWrapper = new SeatPoolWrapper();

                PopulationInfo pInfo = CourseOfferingManagementUtil.getPopulationService().getPopulation(seatPoolDefinitionInfo.getPopulationId(), contextInfo);
                spWrapper.setSeatPoolPopulation(pInfo);
                spWrapper.setSeatPool(seatPoolDefinitionInfo);
                spWrapper.setId(seatPoolDefinitionInfo.getId());
                seatPoolWrapperList.add(spWrapper);
            }
            aoWapper.setSeatpools(seatPoolWrapperList);

            CourseOfferingManagementUtil.getScheduleHelper().loadSchedules(aoWapper,contextInfo);

            return aoWapper;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getTermDisplayString(String termId, TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        String displayString = termId; // use termId as a default.
        if (term != null) {
            String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            String termType = term.getName();
            formatter.format("%s (%s to %s)", termType, startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }

    private String getTermStartEndDate(String termId, TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        String displayString = termId; // use termId as a default.
        if (term != null) {
            String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            formatter.format("%s - %s", startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }
}
