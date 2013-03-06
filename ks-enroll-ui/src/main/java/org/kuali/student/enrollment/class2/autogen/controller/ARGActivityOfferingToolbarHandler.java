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
 * Created by David Yin on 3/4/13
 */
package org.kuali.student.enrollment.class2.autogen.controller;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ToolbarUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ColocatedOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ARGActivityOfferingToolbarHandler extends UifControllerBase {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ARGActivityOfferingToolbarHandler.class);
    private CourseOfferingManagementViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ARGCourseOfferingManagementForm();
    }

    @RequestMapping(params = "methodToCall=addActivityOfferings")
    public ModelAndView addActivityOfferings(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm) throws Exception {

        String activityId = theForm.getActivityIdForNewAO();
        String formatId = theForm.getFormatIdForNewAO();
        int aoCount = Integer.parseInt(theForm.getNoOfActivityOfferings());

        getViewHelperService(theForm).createActivityOfferings(formatId, activityId, aoCount, theForm);

        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
        //KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_SUCCESS);
        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=approveAOs")
    public ModelAndView approveAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        getViewHelperService(theForm).approveActivityOfferings(theForm);
        reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=suspendAOs")
    public ModelAndView suspendAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO suspendAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelAOs")
    public ModelAndView cancelAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //TODO: cancelAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=reinstateAOs")
    public ModelAndView reinstateAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: reinstateAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteAOs")
    public ModelAndView deleteAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();
        CourseOfferingWrapper currentCoWrapper = theForm.getCurrentCourseOfferingWrapper();
        currentCoWrapper.setColocatedAoToDelete(false);

        boolean bNoDeletion = false;
        int checked = 0;
        int enabled = 0;

        selectedIndexList.clear();
        for(ActivityOfferingWrapper ao : aoList) {

            if(ao.isEnableDeleteButton() && ao.getIsChecked()) {
                ao.setActivityCode(ao.getAoInfo().getActivityCode());
                selectedIndexList.add(ao);
                if(ao.getAoInfo().getIsPartOfColocatedOfferingSet())  {
                    currentCoWrapper.setColocatedAoToDelete(true);
                    String colocateInfo = createColocatedDisplayData(ao.getAoInfo());
                    ao.setColocatedAoInfo(colocateInfo);
                }
                enabled++;
            } else if (ao.getIsChecked()){
                checked++;
                if (!bNoDeletion) {
                    bNoDeletion = true;
                }
            }
        }

        if (selectedIndexList.isEmpty()) {
            theForm.setSelectedIllegalAOInDeletion(false);
            if (bNoDeletion) {
                theForm.setSelectedIllegalAOInDeletion(true);
            }
        }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=draftAOs")
    public ModelAndView draftAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        getViewHelperService(theForm).draftActivityOfferings(theForm);
        reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=addCluster")
    public ModelAndView addCluster(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: addCluster
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=moveToCluster")
    public ModelAndView moveToCluster(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: moveToCluster
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyAOs")
    public ModelAndView copyAOs(@ModelAttribute("KualiForm") ARGCourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: copyAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    private void reloadActivityOffering(ARGCourseOfferingManagementForm theForm) throws Exception {
          // Reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        loadActivityOfferings(theCourseOffering, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm);
    }

    private void loadActivityOfferings(CourseOfferingInfo theCourseOffering, ARGCourseOfferingManagementForm theForm) throws Exception{
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        ToolbarUtil.processAoToolbarForUser(theForm.getActivityWrapperList(), theForm);
    }

    private String createColocatedDisplayData(ActivityOfferingInfo ao ) throws InvalidParameterException, MissingParameterException, PermissionDeniedException,
            OperationFailedException, DoesNotExistException {

        StringBuffer buffer = new StringBuffer();
        buffer.append(" ");
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        List<ColocatedOfferingSetInfo> colos = getCourseOfferingService().getColocatedOfferingSetsByActivityOffering(ao.getActivityId(),
                context);
        for(ColocatedOfferingSetInfo colo : colos) {
            List<ActivityOfferingInfo> aoList = getCourseOfferingService().getActivityOfferingsByIds(colo.getActivityOfferingIds(), context);
            for(ActivityOfferingInfo aoInfo : aoList) {
                buffer.append(aoInfo.getCourseOfferingCode() + " " + aoInfo.getActivityCode());
            }
        }

        return buffer.toString();
    }

    public CourseOfferingManagementViewHelperService getViewHelperService(ARGCourseOfferingManagementForm theForm){

        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null){
                viewHelperService = (CourseOfferingManagementViewHelperService) theForm.getView().getViewHelperService();
            }else{
                viewHelperService= (CourseOfferingManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }

        return viewHelperService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

}
