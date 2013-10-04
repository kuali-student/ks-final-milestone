/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.lum.lu.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 * @author nwright
 */
public class CluDataLoader {

    private CluService cluService;
    private ContextInfo contextInfo;

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public ContextInfo getContextInfo() {
        return contextInfo;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }

    public void load() {
        loadCourse("COURSE1", "2012FA", "CHEM", "CHEM123", "Chemistry 123", "description 1");
        loadCourse("COURSE2", "2012SP", "ENG", "ENG101", "Intro English 1", "description 2");
        loadCourse("COURSE3", "2012FA", "CHEM", "CHEM124", "Chemistry 124", "description 3");
        loadCourse("COURSE4", "2012SP", "ENG", "ENG102", "Intro English 2", "description 4");
        loadCourse("COURSE5", "2012FA", "CHEM", "CHEM125", "Chemistry 125", "description 5");
        loadCourse("COURSE6", "2012SP", "ENG", "ENG103", "Intro English 3", "description 6");
        loadCourse("COURSE7", "2012FA", "CHEM", "CHEM126", "Chemistry 126", "description 7");
        loadCourse("COURSE8", "2012SP", "ENG", "ENG104", "Intro English 4", "description 8");

        loadProgram("PROGRAM1", "2012FA", "PROG1", "Program 1", "Program description 1");
        loadProgram("PROGRAM2", "2012SP", "PROG2", "Program 2", "Program description 2");
    }

    public CluInfo loadCourse(String id, String startTermId, String subjectArea, String code, String title,
                              String description) {
        CluIdentifierInfo official = new CluIdentifierInfo();
        official.setDivision(subjectArea);
        official.setCode(code);
        official.setSuffixCode(code.substring(subjectArea.length()));
        official.setShortName(title);
        official.setLongName(title);
        return loadClu(id, official, startTermId, description, CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
    }

    public CluInfo loadProgram(String id, String startTermId, String code, String title,
                              String description) {
        CluIdentifierInfo official = new CluIdentifierInfo();
        official.setCode(code);
        official.setShortName(title);
        official.setLongName(title);
        return loadClu(id, official, startTermId, description, "kuali.lu.type.MajorDiscipline");
    }

    private CluInfo loadClu(String id, CluIdentifierInfo official, String startTermId, String description, String type) {
        CluInfo info = new CluInfo();
        info.setId(id);
        info.setExpectedFirstAtp(startTermId);
        info.setEffectiveDate(calcEffectiveDateForTerm(startTermId, id));
        info.setOfficialIdentifier(official);
        RichTextInfo rt = new RichTextInfo();
        rt.setPlain(description);
        info.setDescr(rt);
        info.setTypeKey(type);
        info.setStateKey("Active");
        try {
            return this.cluService.createClu(type, info, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Date calcEffectiveDateForTerm(String termId, String context) {
        String year = termId.substring(0, 4);
        String mmdd = "09-01";
        if (termId.endsWith("FA")) {
            mmdd = "09-01";
        } else if (termId.endsWith("WI")) {
            mmdd = "01-01";
        } else if (termId.endsWith("SP")) {
            mmdd = "03-01";
        } else if (termId.endsWith("SU")) {
            mmdd = "06-01";
        }
        return str2Date(year + "-" + mmdd + " 00:00:00.0", context);
    }

    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.S");
        try {
            Date date = df.parse(str);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }
}
