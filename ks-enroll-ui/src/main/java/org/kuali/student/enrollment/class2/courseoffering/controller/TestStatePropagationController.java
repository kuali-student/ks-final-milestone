/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 5/6/13
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.form.TestStatePropagationForm;
import org.kuali.student.enrollment.class2.courseoffering.service.TestStatePropagationViewHelperService;
import org.kuali.student.enrollment.class2.courseofferingset.service.decorators.CourseOfferingSetServiceAftDecorator;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.OperationNotSupportedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class provides controller methods to test state propagation through the ui
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/testStatePropagation")
public class TestStatePropagationController extends UifControllerBase {
    private TestStatePropagationViewHelperService viewHelperService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TestStatePropagationController.class);

    public static final String PAGE_ID = "pageId";

    public static final String CHANGE_SOC_STATE_DEFAULT_TERM = "201301";

    private CourseOfferingSetServiceAftDecorator socService;
    private AcademicCalendarService acalService;

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new TestStatePropagationForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (!(form instanceof TestStatePropagationForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type TestServiceCallForm. Got " + form.getClass().getSimpleName());
        }

        TestStatePropagationForm theForm = (TestStatePropagationForm) form;
        populateFormWithTargetSocInfo( theForm );
        Map paramMap = request.getParameterMap();
        if (paramMap.containsKey(PAGE_ID)) {
            String pageId = ((String []) paramMap.get(PAGE_ID))[0];
            if (pageId.equals("testStatePropagationPageId")) {
                return _startStatePropagationTest(form, result, request, response);
            }
        }

        return getUIFModelAndView(theForm);
    }

    private ModelAndView _startStatePropagationTest(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        // Doesn't do anything really, but is there for customization
        TestStatePropagationForm theForm = (TestStatePropagationForm) form;
        LOGGER.info("firstServiceCall");
        return getUIFModelAndView(theForm);
    }

    @Transactional
    @RequestMapping(params = "methodToCall=testStatePropagation")
    public ModelAndView testStatePropagation(@ModelAttribute("KualiForm") TestStatePropagationForm form, @SuppressWarnings("unused") BindingResult result,
                                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        TestStatePropagationViewHelperService helper = getViewHelperService(form);
        helper.runTests(form);
        return getUIFModelAndView(form);
    }

    public TestStatePropagationViewHelperService getViewHelperService(TestStatePropagationForm serviceCallForm) {
        if (viewHelperService == null) {
            if (serviceCallForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (TestStatePropagationViewHelperService) serviceCallForm.getView().getViewHelperService();
            } else {
                viewHelperService = (TestStatePropagationViewHelperService) serviceCallForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    @Transactional
    @RequestMapping(params = "methodToCall=changeSocState")
    public ModelAndView changeSocState( @ModelAttribute("KualiForm") TestStatePropagationForm form ) throws PermissionDeniedException, OperationFailedException, OperationNotSupportedException, InvalidParameterException, MissingParameterException, DoesNotExistException, DataValidationErrorException, VersionMismatchException, ReadOnlyException {

        String environment = StringUtils.defaultIfBlank( ConfigContext.getCurrentContextConfig().getProperty( "environment" ), StringUtils.EMPTY );
        if( !"DEV".equalsIgnoreCase( environment ) ) {
            throw new OperationNotSupportedException( "Cannot change state of SOC in non-dev environment (env is:" + environment + ")" );
        }

        // update soc-state
        ContextInfo contextInfo = new ContextInfo();
        SocInfo targetSocInfo = getTargetSocInfoForTerm( form.getTermCodeForSocStateChange(), contextInfo );
        targetSocInfo.setStateKey( form.getNewSocStateForSocStateChange() );
        putBypassBusinessLogicFlagOntoContext( contextInfo );
        this.getSocService().updateSoc( targetSocInfo.getId(), targetSocInfo, contextInfo );

        populateFormWithTargetSocInfo( form );

        return getUIFModelAndView(form);
    }

    private void populateFormWithTargetSocInfo( TestStatePropagationForm form ) {
        try {
            ContextInfo contextInfo = new ContextInfo();
            String targetTermId = StringUtils.defaultIfEmpty( form.getTermCodeForSocStateChange(), CHANGE_SOC_STATE_DEFAULT_TERM );
            SocInfo targetSocInfo = getTargetSocInfoForTerm( targetTermId, contextInfo );
            TermInfo targetTermInfo = this.getAcalService().getTerm( targetSocInfo.getTermId(), contextInfo );

            form.setTermCodeForSocStateChange( targetTermInfo.getCode() );
            form.setNewSocStateForSocStateChange( targetSocInfo.getStateKey() );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SocInfo getTargetSocInfoForTerm( String targetTermCode, ContextInfo contextInfo ) throws MissingParameterException, PermissionDeniedException, OperationFailedException, InvalidParameterException, DoesNotExistException {
        String targetTermId = this.getAcalService().getTermsByCode( targetTermCode, contextInfo ).get(0).getId();
        return CourseOfferingSetUtil.getMainSocForTermId(targetTermId, contextInfo);
    }

    private void putBypassBusinessLogicFlagOntoContext( ContextInfo contextInfo ) {
        List<AttributeInfo> attrs = new ArrayList<AttributeInfo>();
        attrs.add(new AttributeInfo(CourseOfferingSetServiceConstants.BYPASS_BUSINESS_LOGIC_ON_SOC_STATE_CHANGE_FOR_AFT_TESTING, String.valueOf(true)));
        contextInfo.setAttributes(attrs);
    }

    private CourseOfferingSetServiceAftDecorator getSocService() {
        if( socService == null ) {
            socService = (CourseOfferingSetServiceAftDecorator) GlobalResourceLoader.getService( new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART ) );
        }
        return socService;
    }

    private AcademicCalendarService getAcalService() {
        if( acalService == null ) {
            acalService = GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

}
