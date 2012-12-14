package org.kuali.student.myplan.course.util;
/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import edu.uw.kuali.student.lib.client.studentservice.ServiceException;
import edu.uw.kuali.student.lib.client.studentservice.StudentServiceClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.plan.util.EnumerationHelper;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.beans.PropertyEditorSupport;
import java.io.Serializable;
import java.util.*;

public class TimeScheduleLinksListPropertyEditor extends PropertyEditorSupport implements Serializable {

    private final static Logger logger = Logger.getLogger(TimeScheduleLinksListPropertyEditor.class);

    private StudentServiceClient studentServiceClient;

    private String baseUrl = "";

    private String label1 = "See ";

    //separated the lable to make the time schedule name bold
    private String label2 = "{timeScheduleName} ";

    private String label3 = "Time Schedule";

    private String title = label1 + label2 + label3;

    private List<String> styleClasses;

    private transient CourseOfferingService courseOfferingService;

    private List<String> emptyListStyleClasses;

    private HashMap<String, String> instAbbreviations;

    public HashMap<String, String> getInstAbbreviations() {
        return instAbbreviations;
    }

    public void setInstAbbreviations(HashMap<String, String> instAbbreviations) {
        this.instAbbreviations = instAbbreviations;
    }

    private final static CollectionListPropertyEditorHtmlListType listType = CollectionListPropertyEditorHtmlListType.UL;

    /*TODO:Remove the HashMap after enumeration service is in the ehcache and remove the hashmap occurance in this*/
    private HashMap<String, List<EnumeratedValueInfo>> hashMap = new HashMap<String, List<EnumeratedValueInfo>>();

    public HashMap<String, List<EnumeratedValueInfo>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, List<EnumeratedValueInfo>> hashMap) {
        this.hashMap = hashMap;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (this.courseOfferingService == null) {
            //   TODO: Use constants for namespace.
            this.courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "coService"));
        }
        return this.courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public TimeScheduleLinksListPropertyEditor() {
        styleClasses = new ArrayList<String>();
        emptyListStyleClasses = new ArrayList<String>();
    }


    @Override
    public void setValue(Object value) {
        if (value == null) {
            logger.error("Object was null.");
            return;
        }

        if (!(value instanceof CourseDetails)) {
            logger.error(String.format("Value was thype [%s] instead of CourseDetails.", value.getClass()));
            return;
        }
        super.setValue(value);
    }

    @Override
    public String getAsText() {
        //  Don't alter course details.
        final CourseDetails courseDetails = (CourseDetails) super.getValue();

        if (courseDetails == null) {
            return "";
        }

        /*
         *  If the collection is empty and no empty list message is defined then return an empty string.
         *  Otherwise, add an empty list message to the list.
         */
        String styleClassNames = getEmptyListStyleClassesAsString();

        List<String> scheduledTerms = courseDetails.getScheduledTerms();
        if (scheduledTerms == null) {
            return "";
        }

        StringBuffer formattedText = new StringBuffer();
        formattedText.append("<" + listType.getListElementName() + " class=\"" + styleClassNames + "\">");

        // For all published terms
        for (String scheduledTerm : scheduledTerms) {
            Set<String> lightboxUrls = makeLightboxTimeScheduleUrl(scheduledTerm, courseDetails.getCode());
            for (String lightboxUrl : lightboxUrls) {
                String urlId = UUIDHelper.genStringUUID();
                String t = title.replace("{timeScheduleName}", scheduledTerm);
                String l1 = label1;
                String l2 = label2.replace("{timeScheduleName}", scheduledTerm);
                String l3 = label3;
                if (getInstAbbreviations().size() > 0 && getInstAbbreviations().containsKey(lightboxUrl)) {
                    l3 = l3 + " (" + getInstAbbreviations().get(lightboxUrl) + ")";
                }


                //need to change lightbox link url b bus --> bbus for bothell and tacoma


                formattedText.append("<" + listType.getListItemElementName() + ">")

                        /*
                       <input type="hidden" value=" createLightBoxLink('u86', {autoScale:true,centerOnScroll:true,transitionIn:"fade",transitionOut:"fade",speedIn:200,speedOut:200,hideOnOverlayClick:false,type:"iframe",width:"75%",height:"95%"}); " script="first_run">
                        */
                        .append("<input name=\"script\" type=\"hidden\" value=\"createLightBoxLink('" + urlId + "',{href:'" + lightboxUrl + "',autoScale:true,centerOnScroll:true,transitionIn:'fade',transitionOut:'fade',speedIn:200,speedOut:200,hideOnOverlayClick:true,type:'iframe',width:'90%',height:'95%'});\" script=\"first_run\">")
                        .append("<a id=\"" + urlId + "\" href=\"" + lightboxUrl + "\" target=\"_self\">")
                        .append(l1)
                        .append("<b>" + l2 + "</b>")
                        .append(l3)
                        .append("</a>")
                        .append("</" + listType.getListItemElementName() + ">");
            }
        }
        formattedText.append("</" + listType.getListElementName() + ">");

        return formattedText.toString();
    }

    /**
     * Format a UW SWS URL as: SPR2012/chem.html#chem142
     * term/curriculmum_abbreviation#curriculmum_abbreviation + course number
     * No space in curriculum code for Bothell/Tacoma
     *
     * @param term
     * @param courseCode
     * @return
     */
    private Set<String> makeLightboxTimeScheduleUrl(String term, String courseCode) {

        final CourseDetails courseDetails = (CourseDetails) super.getValue();
        HashMap<String, String> inst = new HashMap<String, String>();
        Set<String> urls = new HashSet<String>();
        if (courseDetails == null) {
            return new HashSet<String>();
        }

        /*
        *  If the collection is empty and no empty list message is defined then return an empty string.
        *  Otherwise, add an empty list message to the list.
        */
        String styleClassNames = getEmptyListStyleClassesAsString();

        List<String> campusLocation = courseDetails.getCampusLocations();

        String campusLocationString = campusLocation.get(0);

        StringBuilder url = new StringBuilder(baseUrl);


        //  Parse out all of the necessary params.
        String year = term.replaceAll("\\D+", "");
        String termName = term.replaceAll("\\d+", "").toLowerCase().trim();
        String courseNumber = courseCode.replaceAll("^\\D+", "");
        String curriculumCode = courseCode.replaceAll("\\d+$", "").toLowerCase().trim();

        //  Convert term to SWS format "SPR2012"
        String swsTerm = termName.substring(0, 3).toUpperCase() + year;


        if (campusLocationString.equals("Bothell")) { //Bothell
            url.append("B").append("/");
        } else if (campusLocationString.equals("Tacoma")) { //Tacoma
            url.append("T").append("/");
        }


        // Build the URL: SPR2012/chem.html#chem142
        url.append(swsTerm).append("/");

        //  Query the student web service to convert the curriculum abbreviation to a TimeScheduleLinkAbbreviation.

        ActivityOfferingInfo activityOfferingInfo = new ActivityOfferingInfo();
        try {
            activityOfferingInfo = getCourseOfferingService().getActivityOffering(year + "," + termName + "," + curriculumCode + "," + courseNumber, CourseSearchConstants.CONTEXT_INFO);
        } catch (Exception e) {
            logger.error("could not load the ActivityOfferinInfo from SWS", e);
        }
        for (AttributeInfo timeScheduleLinkAbbreviation : activityOfferingInfo.getAttributes()) {
            List<EnumeratedValueInfo> enumeratedValueInfoList = null;
            if (timeScheduleLinkAbbreviation.getKey().equalsIgnoreCase(CourseSearchConstants.TIME_SCHEDULE_KEY) && !timeScheduleLinkAbbreviation.getValue().isEmpty()) {
                String instCd = timeScheduleLinkAbbreviation.getValue().replaceAll("\\D+", "").trim();
                String instAbbr = null;
                if (!instCd.isEmpty()) {

                    if (!this.getHashMap().containsKey(CourseSearchConstants.COURSE_OFFERING_INSTITUTE)) {
                        enumeratedValueInfoList = EnumerationHelper.getEnumerationValueInfoList(CourseSearchConstants.COURSE_OFFERING_INSTITUTE);

                    } else {
                        enumeratedValueInfoList = hashMap.get(CourseSearchConstants.COURSE_OFFERING_INSTITUTE);
                    }
                    for (EnumeratedValueInfo enumeratedValueInfo : enumeratedValueInfoList) {
                        if (instCd.equalsIgnoreCase(enumeratedValueInfo.getSortKey())) {
                            instAbbr = enumeratedValueInfo.getAbbrevValue();
                        }
                    }

                }
                StringBuilder urlBuilder = new StringBuilder();
                urlBuilder = urlBuilder.append(url);
                urlBuilder.append(timeScheduleLinkAbbreviation.getValue())
                        .append(".html#");
                        //.append(".html?external=true&dialogMode=true#");

                if (campusLocationString.equals("Seattle")) { //Seattle
                    urlBuilder.append(curriculumCode.toLowerCase().replaceAll("\\s", ""));
                } else if (campusLocationString.equals("Bothell")) { //Bothell
                    urlBuilder.append(curriculumCode.toLowerCase().replaceAll("\\s", ""));
                } else if (campusLocationString.equals("Tacoma")) { //Tacoma
                    urlBuilder.append(curriculumCode.toLowerCase().replaceAll("\\s", ""));
                }

                urlBuilder.append(courseNumber);
                urls.add(urlBuilder.toString());
                if (enumeratedValueInfoList != null) {
                    inst.put(urlBuilder.toString(), instAbbr);
                }
            }
        }
        setInstAbbreviations(inst);
        return urls;
    }


    public List<String> getStyleClasses() {
        return this.styleClasses;
    }

    public void setStyleClasses(List<String> styleClasses) {
        this.styleClasses = styleClasses;
    }

    public String getStyleClassesAsString() {
        if (styleClasses != null) {
            return StringUtils.join(styleClasses, " ");
        }
        return "";
    }

    public List<String> getEmptyListStyleClasses() {
        return this.emptyListStyleClasses;
    }

    public void setEmptyListStyleClasses(List<String> styleClasses) {
        this.emptyListStyleClasses = styleClasses;
    }

    public String getEmptyListStyleClassesAsString() {
        if (emptyListStyleClasses != null) {
            return StringUtils.join(emptyListStyleClasses, " ");
        }
        return "";
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public StudentServiceClient getStudentServiceClient() {
        if (studentServiceClient == null) {
            studentServiceClient = (StudentServiceClient) GlobalResourceLoader.getService(StudentServiceClient.SERVICE_NAME);
        }
        return studentServiceClient;
    }

    public void setStudentServiceClient(StudentServiceClient studentServiceClient) {
        this.studentServiceClient = studentServiceClient;
    }
}
