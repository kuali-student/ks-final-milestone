/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.lum.lu.service.impl;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 *
 * @author nwright
 */
public class CluSetDataLoader {

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
        loadCourseSet("COURSE-SET1", "COURSE-SET1-NAME", "COURSE1");
        loadCourseSet("COURSE-SET2", "COURSE-SET2-NAME", "COURSE2", "COURSE3");
        loadCourseSet("COURSE-SET3", "COURSE-SET3-NAME", "COURSE4", "COURSE5", "COURSE6", "COURSE7");

        loadProgramSet("PROG-SET1", "PROG-SET1-NAME", "PROGRAM1");
    }

    public CluSetInfo loadCourseSet(String id, String name, String... cluIds) {
        return loadCluSet(id, name, CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE, cluIds);
    }

    public CluSetInfo loadProgramSet(String id, String name, String... cluIds) {
        return loadCluSet(id, name, CluServiceConstants.CLUSET_TYPE_PROGRAM, cluIds);
    }

    private CluSetInfo loadCluSet(String id, String name, String type, String[] cluIds) {
        CluSetInfo cluSetInfo = new CluSetInfo();
        cluSetInfo.setId(id);
        cluSetInfo.setTypeKey(type);
        cluSetInfo.setStateKey("Active");
        cluSetInfo.setName(name);
        cluSetInfo.setEffectiveDate(new Date());
        cluSetInfo.setIsReferenceable(Boolean.TRUE);
        cluSetInfo.setIsReusable(Boolean.FALSE);
        // convert cluIds into version independent ids
        List<String> versionIndCluIds = new ArrayList<String>();
        cluSetInfo.setCluIds(versionIndCluIds);
        List<CluInfo> clus;
        try {
            clus = this.cluService.getClusByIds(Arrays.asList(cluIds), contextInfo);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        for (CluInfo cluInfo : clus) {
            versionIndCluIds.add(cluInfo.getVersion().getVersionIndId());
        }
        try {
            cluSetInfo = this.cluService.createCluSet(cluSetInfo.getTypeKey(), cluSetInfo, contextInfo);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return cluSetInfo;
    }


}
