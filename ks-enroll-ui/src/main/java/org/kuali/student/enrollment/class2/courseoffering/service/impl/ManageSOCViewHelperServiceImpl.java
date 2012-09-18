package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ManageSOCStatusHistory;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ManageSOCViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;

import javax.xml.namespace.QName;
import java.util.List;

public class ManageSOCViewHelperServiceImpl extends ViewHelperServiceImpl implements ManageSOCViewHelperService {

    private transient AcademicCalendarService acalService;
    private transient CourseOfferingSetService courseOfferingSetService;
    private transient StateService stateService;
    private transient SchedulingService schedulingService;

    public List<TermInfo> getTermByCode(String termCode) throws Exception {

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

        QueryByCriteria criteria = qbcBuilder.build();

        AcademicCalendarService acalService = getAcalService();
        List<TermInfo> terms = acalService.searchForTerms(criteria, new ContextInfo());
        return terms;
    }

    public void loadDataUI(ManageSOCForm socForm){

        try {
            List<String> socIds = getCourseOfferingSetService().getSocIdsByTerm(socForm.getTermInfo().getId(), ContextUtils.createDefaultContextInfo());

            if (socIds.size() > 1){   //Handle multiple soc when it is implemented
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Should not have multiple SOCs for a term");
            }

            SocInfo socInfo = getCourseOfferingSetService().getSoc(socIds.get(0),ContextUtils.createDefaultContextInfo());

            String stateName = getStateName(socInfo.getStateKey());
            socForm.setSocStatus(stateName);
            for (AttributeInfo info : socInfo.getAttributes()){
                ManageSOCStatusHistory history = new ManageSOCStatusHistory(info.getKey(),info.getValue());
                socForm.getStatusHistory().add(history);
            }

        } catch (DoesNotExistException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,e.getMessage());
        } catch (PermissionDeniedException e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    protected String getStateName(String stateKey)
    throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        return getStateService().getState(stateKey,ContextUtils.createDefaultContextInfo()).getName();

    }

    protected AcademicCalendarService getAcalService() {
        if (acalService == null){
            acalService = CourseOfferingResourceLoader.loadAcademicCalendarService();
        }
        return acalService;
    }

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = CourseOfferingResourceLoader.loadCourseOfferingSetService();
        }
        return courseOfferingSetService;
    }

    public StateService getStateService() {
        if (stateService == null){
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    public SchedulingService getSchedulingService() {
        if (schedulingService == null){
            schedulingService = GlobalResourceLoader.getService(new QName(SchedulingServiceConstants.NAMESPACE,SchedulingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return schedulingService;
    }

}
