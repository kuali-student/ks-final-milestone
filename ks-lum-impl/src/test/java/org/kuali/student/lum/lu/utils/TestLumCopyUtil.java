/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.lum.lu.utils;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in.
 *
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class TestLumCopyUtil {

    @Test
    public void testCluCopy () {

        CluInfo clu =  buildClu1();
        CluInfo cloned = LumCopyUtil.copyClu(clu);

        //TODO Add more tests
        assertFalse(clu.toString().equals(cloned.toString()));

        assertFalse(clu.getOfficialIdentifier().toString().equals(cloned.getOfficialIdentifier().toString()));
        assertFalse(clu.getOfficialIdentifier().getShortName().equals(cloned.getOfficialIdentifier().getShortName()));
        assertFalse(clu.getOfficialIdentifier().getLongName().equals(cloned.getOfficialIdentifier().getLongName()));

        cloned.setStudySubjectArea("Changed");
        assertFalse(clu.getStudySubjectArea().equals(cloned.getStudySubjectArea()));

        cloned.getDesc().setPlain("Changed");
        assertFalse(clu.getDesc().getPlain().equals(cloned.getDesc().getPlain()));

        cloned.getMarketingDesc().setPlain("Changed");
        assertFalse(clu.getMarketingDesc().getPlain().equals(cloned.getMarketingDesc().getPlain()));

        AccreditationInfo newAccrediation = new AccreditationInfo();
        newAccrediation.setId("sdkh77");
        newAccrediation.setOrgId("87");
        cloned.getAccreditationList().add(newAccrediation);
        assertFalse(clu.getAccreditationList().size() ==  cloned.getAccreditationList().size());


        cloned.getCampusLocationList().add("Central Campus");
        assertFalse(clu.getCampusLocationList().size() ==  cloned.getCampusLocationList().size());

        cloned.setAdminOrg("Biology");
        assertFalse(clu.getAdminOrg().equals(cloned.getAdminOrg()));

        assertFalse(clu.getPrimaryInstructor().toString().equals(cloned.getPrimaryInstructor().toString()));
        cloned.getPrimaryInstructor().getAttributes().put("EyeColour", "Green");
        assertFalse(clu.getPrimaryInstructor().getAttributes().get("EyeColour").equals(cloned.getPrimaryInstructor().getAttributes().get("EyeColour")));

    }

    @Test
    public void testProposalCopy () {

        ProposalInfo proposal =  buildProposal1();
        ProposalInfo cloned = LumCopyUtil.copyProposal(proposal);

        assertFalse(proposal.toString().equals(cloned.toString()));

        cloned.setDetailDesc("Test a change");
        assertFalse(proposal.getDetailDesc().equals(cloned.getDetailDesc()));

        cloned.setName("Test a change");
        assertFalse(proposal.getName().equals(cloned.getName()));

        cloned.setProposalReferenceType("Test a change");
        assertFalse(proposal.getProposalReferenceType().equals(cloned.getProposalReferenceType()));

        cloned.setRationale("Test a change");
        assertFalse(proposal.getRationale().equals(cloned.getRationale()));

        cloned.setName("Test a change");
        assertFalse(proposal.getState().equals(cloned.getState()));

        cloned.setType("Test a change");
        assertFalse(proposal.getType().equals(cloned.getType()));

        Calendar lastMonth = GregorianCalendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);
        cloned.setEffectiveDate(lastMonth.getTime());
        assertFalse(proposal.getEffectiveDate().equals(cloned.getEffectiveDate()));

        Calendar nextMonth = GregorianCalendar.getInstance();
        nextMonth.add(Calendar.MONTH, 1);
        cloned.setEffectiveDate(nextMonth.getTime());
        assertFalse(proposal.getExpirationDate().equals(cloned.getExpirationDate()));


    }


    private ProposalInfo buildProposal1() {
        ProposalInfo proposal = new ProposalInfo();

        Calendar effectiveDate = GregorianCalendar.getInstance();
        effectiveDate.add(Calendar.MONTH, 1);

        Calendar expiryDate = GregorianCalendar.getInstance();
        expiryDate.add(Calendar.YEAR, 2);

        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateId("hjoh");
        metaInfo.setCreateTime(GregorianCalendar.getInstance().getTime());


        proposal.setDetailDesc("Detailed description of proposal");
        proposal.setEffectiveDate(effectiveDate.getTime());
        proposal.setExpirationDate(expiryDate.getTime());
        proposal.setId("837dhfj");
        proposal.setMetaInfo(metaInfo);
        proposal.setName("Name of proposal");
        proposal.setProposalReference(new ArrayList<String>(Arrays.asList("ABC-123", "XYZ-987")));
        proposal.setProposalReferenceType("Faculty proposer");
        proposal.setProposerOrg(new ArrayList<String>(Arrays.asList("Chemistry")));
        proposal.setProposerPerson(new ArrayList<String>(Arrays.asList("Johns Smith")));
        proposal.setRationale("Just because");
        proposal.setState("Draft");
        proposal.setType("Credit Course");

        return proposal;
    }


    private CluInfo buildClu1() {

        CluInfo clu1 = new CluInfo();

        CluIdentifierInfo idInfo = new CluIdentifierInfo();
        idInfo.setCluId("CHEM100");
        idInfo.setCode("CHEM100");
        idInfo.setDivision("UGRAD");
        idInfo.setId("1234-dfgh");
        idInfo.setLevel("2");
        idInfo.setLongName("Basic Chemistry");
        idInfo.setShortName("Chemistry");
        idInfo.setState("Active");
        idInfo.setType("Course");
        idInfo.setVariation("2");
        clu1.setOfficialIdentifier(idInfo);

        // alternate ids here

        clu1.setAcademicSubjectOrgs(new ArrayList<String>(Arrays.asList("Chemitry", "Physics")));
        clu1.setStudySubjectArea("Biochemistry");

        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("This is plain text description");
        desc.setFormatted("This is <b>formatted</b> description");
        clu1.setDesc(desc);

        RichTextInfo marketingDesc = new RichTextInfo();
        marketingDesc.setPlain("This is plain text description");
        marketingDesc.setFormatted("This is <b>formatted</b> description");
        clu1.setMarketingDesc(marketingDesc);

        clu1.setCampusLocationList(new ArrayList<String>(Arrays.asList("South Campus", "Downtown Campus")));

        AccreditationInfo accreditation = new AccreditationInfo();
        accreditation.setId("3409850495-sdfgsdfg");
        accreditation.setOrgId("99");
        Calendar date = GregorianCalendar.getInstance();
        date.add(Calendar.YEAR, -3);
        accreditation.setEffectiveDate(date.getTime());
        clu1.setAccreditationList(new ArrayList<AccreditationInfo>(Arrays.asList(accreditation)));

        clu1.setAdminOrg("Department of Chemistry");
        clu1.setParticipatingOrgs(new ArrayList<String>(Arrays.asList("Chemistry", "Physics", "Biology")));

        CluInstructorInfo instructor = new CluInstructorInfo();
        instructor.setOrgId("123abc");
        instructor.setPersonId("485456");
        instructor.getAttributes().put("EyeColour", "Blue");
        clu1.setPrimaryInstructor(instructor);
//      clu1.setInstructors(instructors);

        Calendar effectiveDate = GregorianCalendar.getInstance();
        effectiveDate.add(Calendar.MONTH, 1);
        clu1.setEffectiveDate(effectiveDate.getTime());

        Calendar expiryDate = GregorianCalendar.getInstance();
        expiryDate.add(Calendar.YEAR, 2);
        clu1.setExpirationDate(expiryDate.getTime());

        TimeAmountInfo time = new TimeAmountInfo();
        time.setTimeQuantity(new Integer(4));
        time.setAtpDurationTypeKey("weeks");
        clu1.setIntensity(time);

        time = new TimeAmountInfo();
        time.setTimeQuantity(new Integer(10));
        time.setAtpDurationTypeKey("sessions");
        clu1.setStdDuration(time);

        clu1.setCanCreateLui(false);
        clu1.setReferenceURL("http://www.ubc.ca");
//      clu1.setLuCodes(luCodes);
//      clu1.setCreditInfo(creditInfo);
//      clu1.setPublishingInfo(publishingInfo);

        clu1.setNextReviewPeriod("2012W");
        clu1.setEnrollable(false);
        clu1.setOfferedAtpTypes(new ArrayList<String>(Arrays.asList("Summer", "Winter")));
        clu1.setHasEarlyDropDeadline(false);
        clu1.setDefaultEnrollmentEstimate(30);
        clu1.setDefaultMaximumEnrollment(50);
        clu1.setHazardousForDisabledStudents(false);

//      clu1.setFeeInfo(feeInfo);
//      clu1.setAccountingInfo(accountingInfo);

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("TeachingLanguage", "Welsh");
        clu1.setAttributes(attributes);

        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setCreateId("hjoh");
        metaInfo.setCreateTime(GregorianCalendar.getInstance().getTime());
        clu1.setMetaInfo(metaInfo);

        clu1.setType("Course");
        clu1.setState("Active");

        clu1.setId("23124lk4t8nv09vnsdf");

        return clu1;
    }


}

