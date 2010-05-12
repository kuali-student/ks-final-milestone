/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.lum.lu.dto.AcademicSubjectOrgInfo;
import org.kuali.student.lum.lu.dto.AccreditationInfo;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;

/**
 *
 * N.B.  Incomplete !!!!!!!!!!!!!!!!
 * N.B.  Incomplete !!!!!!!!!!!!!!!!
 *
 * This class is incomplete but I've checked it in while I work on something else.
 * It will probably be moved to a different package once completed
 *
 * This class performs a selective deep clone of LUM objects (currently CLU and Proposal)
 * with additional logic to set initial values for some fields.
 *
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LumCopyUtil {

    public static CluInfo copyClu(CluInfo oldClu) {

        CluInfo newClu = new CluInfo();

        // TODO ADT team notes say to exclude official identifier but Services team notes specify that
        //       name (which is in official id) of clone should be "Copy of..."
        newClu.setOfficialIdentifier(cloneCluIdentifier(oldClu));
        // alternate ids:   ADT excluded

        newClu.setAcademicSubjectOrgs(cloneAcademicSubjectOrgs(oldClu.getAcademicSubjectOrgs()));
        newClu.setStudySubjectArea(oldClu.getStudySubjectArea());
        newClu.setDescr(cloneRichTextInfo(oldClu.getDescr()));

        newClu.setCampusLocations(cloneStringList(oldClu.getCampusLocations()));
        newClu.setAccreditations(cloneAccreditationInfoList(oldClu.getAccreditations()));

        // TODO Renamed and retyped in new version
        newClu.setPrimaryInstructor(cloneCluInstructorInfo(oldClu.getPrimaryInstructor()));
        newClu.setInstructors(cloneCluInstructorInfoList(oldClu.getInstructors()));
        // effectiveDate: ADT excluded
        // expirationDate: ADT excluded
        newClu.setIntensity(cloneAmountInfo(oldClu.getIntensity()));

        newClu.setStdDuration(cloneTimeAmountInfo(oldClu.getStdDuration()));
        newClu.setCanCreateLui(oldClu.isCanCreateLui());
        newClu.setReferenceURL(oldClu.getReferenceURL());
        newClu.setLuCodes(cloneLuCodeInfoList(oldClu.getLuCodes()));
//      newClu.setNextReviewPeriod(oldClu.getNextReviewPeriod());  ADT Excluded

        newClu.setEnrollable(oldClu.isEnrollable());
        newClu.setOfferedAtpTypes(cloneStringList(oldClu.getOfferedAtpTypes()));
        newClu.setHasEarlyDropDeadline(oldClu.isHasEarlyDropDeadline());
        newClu.setDefaultEnrollmentEstimate(oldClu.getDefaultEnrollmentEstimate());

        newClu.setDefaultMaximumEnrollment(oldClu.getDefaultMaximumEnrollment());
        newClu.setHazardousForDisabledStudents(oldClu.isHazardousForDisabledStudents());
        newClu.setFeeInfo(cloneCluFeeInfo(oldClu.getFeeInfo()));
        newClu.setAccountingInfo(cloneCluAccountingInfo(oldClu.getAccountingInfo()));
        newClu.setAttributes(cloneAttributes(oldClu.getAttributes()));
        newClu.setMetaInfo(initializeMetaInfo());

        newClu.setType(oldClu.getType());
        newClu.setState("Proposed");  // TODO how do we determine the initial state?
        // id : ADT excluded


        return newClu;

    }

    public static ProposalInfo copyProposal(ProposalInfo oldProposal) {

        ProposalInfo newProposal = new ProposalInfo();
        newProposal.setName(oldProposal.getName());
        newProposal.setProposalReferenceType(oldProposal.getProposalReferenceType());
        newProposal.setRationale(oldProposal.getRationale());
        newProposal.setDetailDesc(oldProposal.getDetailDesc());
        newProposal.setAttributes(cloneAttributes(oldProposal.getAttributes()));
        newProposal.setType(oldProposal.getType());

//      newProposal.setState(oldProposal.getState());  Excluded
//      newProposal.setProposerPerson(cloneStringList(oldProposal.getProposerPerson()));  Excluded
//      newProposal.setProposerOrg(cloneStringList(oldProposal.getProposerOrg())); Excluded
//      newProposal.setProposalReference(cloneStringList(oldProposal.getProposalReference()));  Excluded
//      newProposal.setId(oldProposal.getId());  Excluded
//      newProposal.setMetaInfo(cloneMetaInfo(oldProposal.getMetaInfo());  Excluded

        return newProposal;

    }


    private static CluIdentifierInfo cloneCluIdentifier(CluInfo old) {
        if (old == null)
            return null;

        CluIdentifierInfo clone = new CluIdentifierInfo();
        clone.setShortName("Copy of " + old.getOfficialIdentifier().getShortName());
        clone.setLongName("Copy of " + old.getOfficialIdentifier().getLongName());
        return clone;
    }

    private static CluInstructorInfo cloneCluInstructorInfo(CluInstructorInfo old) {
        if (old == null)
            return null;

        CluInstructorInfo clone = new CluInstructorInfo();
        clone.setOrgId(old.getOrgId());
        clone.setPersonId(old.getPersonId());

        clone.setAttributes(cloneAttributes(old.getAttributes()));

        return clone;
    }

    private static List<CluInstructorInfo> cloneCluInstructorInfoList(List<CluInstructorInfo> old) {
        if (old == null)
            return null;

        List<CluInstructorInfo> clone = new ArrayList<CluInstructorInfo>();
        for (CluInstructorInfo  info : old) {
            clone.add(cloneCluInstructorInfo(info));
        }
        return clone;
    }

    private static LuCodeInfo cloneLuCodeInfo(LuCodeInfo old) {
        if (old == null)
            return null;

        LuCodeInfo clone = new LuCodeInfo();

        clone.setDescr(old.getDescr());
        clone.setValue(old.getValue());
        clone.setId(old.getId());
        clone.setAttributes(cloneAttributes(old.getAttributes()));

        return clone;
    }
    private static List<LuCodeInfo> cloneLuCodeInfoList(List<LuCodeInfo> old) {
        if (old == null)
            return null;

        List<LuCodeInfo> clone = new ArrayList<LuCodeInfo>();
        for (LuCodeInfo  info : old) {
            clone.add(cloneLuCodeInfo(info));
        }
        return clone;
    }

    private static List<AccreditationInfo> cloneAccreditationInfoList(List<AccreditationInfo> old) {
        if (old == null)
            return null;

        List<AccreditationInfo> clone = new ArrayList<AccreditationInfo>();
        for (AccreditationInfo  info : old) {
            clone.add(cloneAccreditationInfo(info));
        }
        return clone;
    }

    private static List<AcademicSubjectOrgInfo> cloneAcademicSubjectOrgs(List<AcademicSubjectOrgInfo> old) {
        if (old == null)
            return null;

        List<AcademicSubjectOrgInfo> clone = new ArrayList<AcademicSubjectOrgInfo>();
        for (AcademicSubjectOrgInfo  info : old) {
        	AcademicSubjectOrgInfo newInfo = new AcademicSubjectOrgInfo();
        	newInfo.setOrgId(info.getOrgId());
            clone.add(newInfo);
        }
        return clone;
    }

    
    private static AccreditationInfo cloneAccreditationInfo(AccreditationInfo old) {
        if (old == null)
            return null;

        AccreditationInfo clone = new AccreditationInfo();
        clone.setId(old.getId());
        clone.setOrgId(old.getOrgId());
        clone.setEffectiveDate(Calendar.getInstance().getTime());
        clone.setMetaInfo(initializeMetaInfo());
        clone.setAttributes(cloneAttributes(old.getAttributes()));

        return clone;
    }

    private static CluFeeInfo cloneCluFeeInfo(CluFeeInfo old) {
        if (old == null)
            return null;

        CluFeeInfo clone = new CluFeeInfo();

        clone.setAttributes(cloneAttributes(old.getAttributes()));

        return clone;
    }

    private static CluAccountingInfo cloneCluAccountingInfo(CluAccountingInfo old) {
        if (old == null)
            return null;

        CluAccountingInfo clone = new CluAccountingInfo();

        clone.setAttributes(cloneAttributes(old.getAttributes()));

        return clone;
    }


    private static Map<String, String> cloneAttributes(Map<String, String> old) {
        if (old == null)
            return null;

        Map<String, String> clone = new HashMap<String, String>();
        for (Entry<String, String> entry : old.entrySet()) {
            clone.put(entry.getKey(), entry.getValue());
        }
        return clone;
    }

    private static RichTextInfo cloneRichTextInfo(RichTextInfo old) {
        if (old == null)
            return null;

        RichTextInfo clone = new RichTextInfo();
        clone.setFormatted(old.getFormatted());
        clone.setPlain(old.getPlain());
        return clone;
    }



    private static List<String> cloneStringList(List<String> old) {
        if (old == null)
            return null;

        List<String> clone = new ArrayList<String>();
        for (String s : old) {
            clone.add(s);
        }
        return clone;

    }

    //TODO what should the initial values be?
    private static MetaInfo initializeMetaInfo() {

        MetaInfo clone = new MetaInfo();
        clone.setCreateId("Dummy");   // How do we get the id?
        clone.setCreateTime(GregorianCalendar.getInstance().getTime());

        return clone;

    }

    private static TimeAmountInfo cloneTimeAmountInfo(TimeAmountInfo old) {
        if (old == null)
            return null;

        TimeAmountInfo clone = new TimeAmountInfo();
        clone.setAtpDurationTypeKey(old.getAtpDurationTypeKey());
        clone.setTimeQuantity(new Integer(old.getTimeQuantity().toString()));
        return clone;
    }

    
    private static AmountInfo cloneAmountInfo(AmountInfo old) {
        if (old == null)
            return null;

        AmountInfo clone = new AmountInfo();
        clone.setUnitQuantity(old.getUnitQuantity());
        clone.setUnitType(old.getUnitType());
        return clone;
    }

}

