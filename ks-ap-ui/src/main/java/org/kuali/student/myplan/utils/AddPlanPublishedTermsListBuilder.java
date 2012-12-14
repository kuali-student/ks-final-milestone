package org.kuali.student.myplan.utils;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinder;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.kuali.student.myplan.plan.form.PlanForm;
import org.kuali.student.myplan.plan.util.AtpHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Assembles a list of published terms.
 */
public class AddPlanPublishedTermsListBuilder extends UifKeyValuesFinderBase {

    private final Logger logger = Logger.getLogger(AddPlanPublishedTermsListBuilder.class);

    /**
     * An optional suffix to add to each value in the list.
     */
    private String suffix = "";

    /**
     * An optional map of items to include in the list at the top or bottom.
     * Note: The suffix doesn't get added to these items.
     */
    private Map<String, String> additionalListItemsTop;
    private Map<String, String> additionalListItemsBottom;

    private AcademicCalendarService academicCalendarService;

    /**
     * Build and returns the list of available terms.
     *
     * @return A List of available terms as KeyValue items.
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        this.setAddBlankOption(false);

        PlanForm planForm = (PlanForm) model;

        CourseDetails courseDetails = planForm.getCourseDetails();


        if (courseDetails.getScheduledTerms().size() > 0) {
            //  Prepend and additional items to the list.
            if (additionalListItemsTop != null && additionalListItemsTop.size() > 0) {
                for (Map.Entry<String, String> entry : additionalListItemsTop.entrySet()) {
                    keyValues.add(new ConcreteKeyValue(entry.getKey(), entry.getValue()));
                }
            }

            //  Add the individual term items.
            for (String term : courseDetails.getScheduledTerms()) {
                String[] splitStr = term.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                String termsOffered = null;
                boolean atpAlreadyexists=false;
                Matcher m = CourseSearchConstants.TERM_PATTERN.matcher(term);
                if (m.matches()) {
                    termsOffered = m.group(1).substring(0, 2).toUpperCase() + " " + m.group(2);
                }
                String atp = AtpHelper.getAtpIdFromTermAndYear(splitStr[0].trim(), splitStr[1].trim());

                if (courseDetails.getPlannedList()!=null){
                for(PlanItemDataObject plan:courseDetails.getPlannedList()) {
                     if(plan.getAtp().equalsIgnoreCase(atp)){
                         atpAlreadyexists=true;
                     }
                }
                }
                if (courseDetails.getBackupList()!=null && !atpAlreadyexists){
                    for(PlanItemDataObject plan:courseDetails.getBackupList()) {
                        if(plan.getAtp().equalsIgnoreCase(atp)){
                            atpAlreadyexists=true;
                        }
                    }
                }
                if (!atpAlreadyexists) {
                    keyValues.add(new ConcreteKeyValue(atp, termsOffered + " " + "(Scheduled according to " + termsOffered + " Time Schedule)"));
                    planForm.setShowOther(true);
                }


            }

            //  Append and additional items to the list.
            if(planForm.isShowOther()){
            if (additionalListItemsBottom != null && additionalListItemsBottom.size() > 0) {
                for (Map.Entry<String, String> entry : additionalListItemsBottom.entrySet()) {
                    keyValues.add(new ConcreteKeyValue(entry.getKey(), entry.getValue()));
                }
            }
            }
        }
        return keyValues;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Map<String, String> getAdditionalListItemsTop() {
        return additionalListItemsTop;
    }

    public void setAdditionalListItemsTop(Map<String, String> additionalListItems) {
        this.additionalListItemsTop = additionalListItems;
    }

    public Map<String, String> getAdditionalListItemsBottom() {
        return additionalListItemsBottom;
    }

    public void setAdditionalListItemsBottom(Map<String, String> additionalListItems) {
        this.additionalListItemsBottom = additionalListItems;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if (this.academicCalendarService == null) {
            this.academicCalendarService = (AcademicCalendarService) GlobalResourceLoader
                    .getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                            AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }
}
