package org.kuali.student.myplan.course.util;

import org.junit.Test;

import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class CreditsFormatterTest {
    @Test
    public void formatCreditsNullEmptyAndFixed() {
        CourseInfo courseInfo = new CourseInfo();

        //  Null credit options list.
        assertEquals("", CreditsFormatter.formatCredits(courseInfo));

        //  Empty credit options list.
        List<ResultComponentInfo> creditOptions = new ArrayList<ResultComponentInfo>();
        courseInfo.setCreditOptions(creditOptions);

        assertEquals("", CreditsFormatter.formatCredits(courseInfo));

        //  Credit options: fixed.
        String creditsText = "3";
        ResultComponentInfo rci = new ResultComponentInfo();
        rci.setType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED);
        Map<String, String> courseOptionAttributes = new HashMap<String, String>();
        courseOptionAttributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE, creditsText);
        rci.setAttributes(courseOptionAttributes);
        creditOptions.add(rci);

        assertEquals(creditsText, CreditsFormatter.formatCredits(courseInfo));
    }

    @Test
    public void formatCreditsMultiple() {

        CourseInfo courseInfo = new CourseInfo();
        List<ResultComponentInfo> creditOptions = new ArrayList<ResultComponentInfo>();

        //  Credit options: list.
        String creditsText = "1, 2, 5, 25";
        ResultComponentInfo rci = new ResultComponentInfo();
        rci.setType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE);

        List<String> resultValues = new ArrayList<String>();
        resultValues.add("1");
        resultValues.add("2");
        resultValues.add("5");
        resultValues.add("25");

        rci.setResultValues(resultValues);

        creditOptions.add(rci);

        courseInfo.setCreditOptions(creditOptions);

        assertEquals(creditsText, CreditsFormatter.formatCredits(courseInfo));
    }

    @Test
    public void formatCreditsRange() {
        CourseInfo courseInfo = new CourseInfo();
        List<ResultComponentInfo> creditOptions = new ArrayList<ResultComponentInfo>();

        //  Credit options: list.
        String creditsText = "1.5-25";
        ResultComponentInfo rci = new ResultComponentInfo();
        rci.setType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE);

        Map<String, String> courseOptionAttributes = new HashMap<String, String>();
        courseOptionAttributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE, "1.5");
        courseOptionAttributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE, "25");

        rci.setAttributes(courseOptionAttributes);

        creditOptions.add(rci);

        courseInfo.setCreditOptions(creditOptions);

        assertEquals(creditsText, CreditsFormatter.formatCredits(courseInfo));
    }

    @Test
    public void multipleCreditOptionsFirstIsUsed() {
        CourseInfo courseInfo = new CourseInfo();

        List<ResultComponentInfo> creditOptions = new ArrayList<ResultComponentInfo>();
        courseInfo.setCreditOptions(creditOptions);

        //  Credit options: fixed.
        String creditsText = "3";

        ResultComponentInfo rci1 = new ResultComponentInfo();
        rci1.setType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED);

        Map<String, String> courseOptionAttributes = new HashMap<String, String>();
        courseOptionAttributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE, creditsText);
        rci1.setAttributes(courseOptionAttributes);
        creditOptions.add(rci1);

        ResultComponentInfo rci2 = new ResultComponentInfo();
        rci2.setType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE);
        creditOptions.add(rci2);

        assertEquals(creditsText, CreditsFormatter.formatCredits(courseInfo));
    }

    @Test
    public void unknownCreditType() {
        CourseInfo courseInfo = new CourseInfo();

        List<ResultComponentInfo> creditOptions = new ArrayList<ResultComponentInfo>();
        courseInfo.setCreditOptions(creditOptions);

        //  Credit options: fixed.
        String creditsText = "";

        ResultComponentInfo rci = new ResultComponentInfo();
        rci.setType("Unknown");

        Map<String, String> courseOptionAttributes = new HashMap<String, String>();
        courseOptionAttributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE, "25");
        rci.setAttributes(courseOptionAttributes);
        creditOptions.add(rci);

        assertEquals(creditsText, CreditsFormatter.formatCredits(courseInfo));
    }

    @Test
    public void trim() {
        assertEquals("1", CreditsFormatter.trimCredits("1"));
        assertEquals("1", CreditsFormatter.trimCredits("1.0"));
        assertEquals("1.5", CreditsFormatter.trimCredits("1.5"));
        assertEquals("1.5", CreditsFormatter.trimCredits("1.5 "));
        assertEquals("1.5", CreditsFormatter.trimCredits(" 1.5 "));
        assertEquals("", CreditsFormatter.trimCredits(null));
        assertEquals("", CreditsFormatter.trimCredits(" "));
    }
}
