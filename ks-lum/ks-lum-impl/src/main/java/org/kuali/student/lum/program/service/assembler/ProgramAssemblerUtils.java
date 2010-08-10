/*
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

package org.kuali.student.lum.program.service.assembler;

import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProgramAssemblerUtils {

    private LuService luService;
    private CluAssemblerUtils cluAssemblerUtils;

    public Object assembleBasics(CluInfo clu, Object o) throws AssemblyException {

        Method method;
        Class[] parms;
        Object[] value;

		try 	{
            //TODO Special logic to handle credentialProgramType field of credentialProgramInfo? Or can we just rename the field as <type>?

            if (clu.getType() != null) {
                parms = new Class[]{String.class};
                method = o.getClass().getMethod("setType", parms);
                value = new Object[]{clu.getType()};
                method.invoke(o, value);
            }

            if (clu.getState() != null) {
                parms = new Class[]{String.class};
                method  = o.getClass().getMethod("setState", parms);
                value = new Object[]{clu.getState()};
                method.invoke(o, value);
            }

            if (clu.getMetaInfo() != null) {
                parms = new Class[]{MetaInfo.class};
                method  = o.getClass().getMethod("setMetaInfo", parms);
                value = new Object[]{clu.getMetaInfo()};
                method.invoke(o, value);
            }

            if (clu.getAttributes() != null) {
                parms = new Class[]{Map.class};
                method  = o.getClass().getMethod("setAttributes", parms);
                value = new Object[]{clu.getAttributes()};
                method.invoke(o, value);
            }

            if (clu.getId() != null) {
                parms = new Class[]{String.class};
                method  = o.getClass().getMethod("setId", parms);
                value = new Object[]{clu.getId()};
                method.invoke(o, value);                
            }

            if (clu.getDescr() != null) {
                parms = new Class[]{RichTextInfo.class};
                method = o.getClass().getMethod("setDescr", parms);
                value = new Object[]{clu.getDescr()};
                method.invoke(o, value);
            }

		}
		catch (IllegalAccessException   e){
            throw new AssemblyException("Error assembling program basics", e);
		}
        catch (InvocationTargetException e){
            throw new AssemblyException("Error assembling program basics", e);
		}
		catch (NoSuchMethodException e)
		{
            throw new AssemblyException("Error assembling program basics", e);
		}

        return o;
        
    }

    //TODO assembleRequirements .  Or maybe this should be in CluAssemblerUtils??
    public Object assembleRequirements(CluInfo clu, Object o) throws AssemblyException {

//        Method method;
//        Class[] parms;
//        Object[] value;
//
//       try    {

//            if (clu.getDescr() != null) {
//                parms = new Class[]{RichTextInfo.class};
//                method = o.getClass().getMethod("setDescr", parms);
//                value = new Object[]{clu.getDescr()};
//                method.invoke(o, value);
//            }
//    }
//    catch (IllegalAccessException   e){
//        throw new AssemblyException("Error assembling program basics", e);
//    }
//    catch (InvocationTargetException e){
//        throw new AssemblyException("Error assembling program basics", e);
//    }
//    catch (NoSuchMethodException e)
//    {
//        throw new AssemblyException("Error assembling program basics", e);
//    }

        return o;

    }

    public Object assembleIdentifiers(CluInfo clu, Object o) throws AssemblyException {

        try    {
            Method method;
            Class[] parms;
            Object[] value;
            if (clu.getOfficialIdentifier().getShortName() != null) {
                parms = new Class[]{String.class};
                method = o.getClass().getMethod("setShortTitle", parms);
                value = new Object[]{clu.getOfficialIdentifier().getShortName()};
                method.invoke(o, value);
            }
            if (clu.getOfficialIdentifier().getLongName() != null) {
                parms =  new Class[]{String.class};
                method = o.getClass().getMethod("setLongTitle", parms);
                value = new Object[]{clu.getOfficialIdentifier().getLongName()};
                method.invoke(o, value);
            }
            if (clu.getOfficialIdentifier().getCode() != null) {
                parms =  new Class[]{String.class};
                method = o.getClass().getMethod("setCode", parms);
                value = new Object[]{clu.getOfficialIdentifier().getCode()};
                method.invoke(o, value);

            }

            for (CluIdentifierInfo cluIdInfo : clu.getAlternateIdentifiers()) {
                String idInfoType = cluIdInfo.getType();
                if (ProgramAssemblerConstants.TRANSCRIPT.equals(idInfoType)) {
                    parms =  new Class[]{String.class};
                    method = o.getClass().getMethod("setTranscriptTitle", parms);
                    value = new Object[]{cluIdInfo.getShortName()};
                    method.invoke(o, value);
                } else if (ProgramAssemblerConstants.DIPLOMA.equals(idInfoType)) {
                    parms =  new Class[]{String.class};
                    method = o.getClass().getMethod("setDiplomaTitle", parms);
                    value = new Object[]{cluIdInfo.getShortName()};
                    method.invoke(o, value);
                }
            }
        }
        catch (IllegalAccessException   e){
            throw new AssemblyException("Error assembling program titles", e);
        }
        catch (InvocationTargetException e){
            throw new AssemblyException("Error assembling program titles", e);
        }
        catch (NoSuchMethodException e)
        {
            throw new AssemblyException("Error assembling program titles", e);
        }

        return o;
    }

    public Object assembleLuCodes(CluInfo clu, Object o) throws AssemblyException {
        try {
            if (clu.getLuCodes() != null) {
                for (LuCodeInfo codeInfo : clu.getLuCodes()) {
                    if (ProgramAssemblerConstants.CIP_2000.equals(codeInfo.getType())) {
                        buildLuCode(o, codeInfo.getValue(), "setCip2000Code");
                    } else if (ProgramAssemblerConstants.CIP_2010.equals(codeInfo.getType())) {
                        buildLuCode(o, codeInfo.getValue(), "setCip2010Code");
                    } else if (ProgramAssemblerConstants.HEGIS.equals(codeInfo.getType())) {
                        buildLuCode(o, codeInfo.getValue(), "setHegisCode");
                    } else if (ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION.equals(codeInfo.getType())) {
                        buildLuCode(o, codeInfo.getValue(), "setUniversityClassification");
                    } else if (ProgramAssemblerConstants.SELECTIVE_ENROLLMENT.equals(codeInfo.getType())) {
                        buildLuCode(o, codeInfo.getValue(), "setSelectiveEnrollmentCode");
                    }
                 }
            }
        }
        catch (IllegalAccessException   e){
            throw new AssemblyException("Error assembling program titles", e);
        }
        catch (InvocationTargetException e){
            throw new AssemblyException("Error assembling program titles", e);
        }
        catch (NoSuchMethodException e)
        {
            throw new AssemblyException("Error assembling program titles", e);
        }
        return o;
    }

    public Object assembleOrgs(CluInfo clu, Object o) throws AssemblyException {

        try {
            if (clu.getAdminOrgs() != null) {
                for (AdminOrgInfo cluOrg : clu.getAdminOrgs()) {
                    if (cluOrg.getType().equals(ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_DIVISION)) {
                        addOrg(o, cluOrg, "getDivisionsContentOwner", "setDivisionsContentOwner");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION)) {
                        addOrg(o, cluOrg, "getDivisionsStudentOversight", "setDivisionsStudentOversight");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_DIVISION)) {
                        addOrg(o, cluOrg, "getDivisionsDeployment", "setDivisionsDeployment");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION)) {
                        addOrg(o, cluOrg, "getDivisionsFinancialResources", "setDivisionsFinancialResources");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION)) {
                        addOrg(o, cluOrg, "getDivisionsFinancialControl", "setDivisionsFinancialControl");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.CURRICULUM_OVERSIGHT_UNIT)) {
                        addOrg(o, cluOrg, "getUnitsContentOwner", "setUnitsContentOwner");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT)) {
                        addOrg(o, cluOrg, "getUnitsStudentOversight", "setUnitsStudentOversight");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_UNIT)) {
                        addOrg(o, cluOrg, "getUnitsDeployment", "setUnitsDeployment");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT)) {
                        addOrg(o, cluOrg, "getUnitsFinancialResources", "setUnitsFinancialResources");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT)) {
                        addOrg(o, cluOrg, "getUnitsFinancialControl", "setUnitsFinancialControl");
                    }
                }
            }
        }
        catch (IllegalAccessException   e){
            throw new AssemblyException("Error assembling program orgs", e);
        }
        catch (InvocationTargetException e){
            throw new AssemblyException("Error assembling program orgs", e);
        }
        catch (NoSuchMethodException e)
        {
            throw new AssemblyException("Error assembling program orgs", e);
        }

        return o;
    }

    public List<String> assembleResultOptions(String cluId, String resultType) throws AssemblyException {
        List<String> resultOptions = null;
        try{
            List<CluResultInfo> cluResults = luService.getCluResultByClu(cluId);

            //TODO Just one result type here?
            resultOptions = cluAssemblerUtils.assembleCluResults(resultType, cluResults);

        } catch (DoesNotExistException e){
        } catch (Exception e) {
            throw new AssemblyException("Error getting major results", e);
        }
        return resultOptions;
    }

    public Object assembleDates(CluInfo clu, Object o) throws AssemblyException {

        Method method;
        Class[] parms;
        Object[] value;

        try {
            if (clu.getExpectedFirstAtp() != null) {
                parms = new Class[]{String.class};
                method = o.getClass().getMethod("setStartTerm", parms);
                value = new Object[]{clu.getExpectedFirstAtp()};
                method.invoke(o, value);
            }
            if (clu.getLastAtp() != null) {
                parms = new Class[]{String.class};
                method = o.getClass().getMethod("setEndTerm", parms);
                value = new Object[]{clu.getLastAtp()};
                method.invoke(o, value);
            }
            if (clu.getLastAdmitAtp() != null) {
                parms = new Class[]{String.class};
                method = o.getClass().getMethod("setEndProgramEntryTerm", parms);    
                value = new Object[]{clu.getLastAdmitAtp()};
                method.invoke(o, value);
            }
            if (clu.getNextReviewPeriod() != null) {
                parms = new Class[]{String.class};
                method = o.getClass().getMethod("setNextReviewPeriod", parms);
                value = new Object[]{clu.getNextReviewPeriod()};
                method.invoke(o, value);
            }
            if (clu.getEffectiveDate() != null) {
                parms = new Class[]{Date.class};
                method = o.getClass().getMethod("setEffectiveDate", parms);
                value = new Object[]{clu.getEffectiveDate()};
                method.invoke(o, value);
            }
        }
        catch (IllegalAccessException   e){
            throw new AssemblyException("Error assembling program dates", e);
        }
        catch (InvocationTargetException e){
            throw new AssemblyException("Error assembling program dates", e);
        }
        catch (NoSuchMethodException e)
        {
            throw new AssemblyException("Error assembling program dates", e);
        }
        return o;
    }



    public Object assemblePublicationInfo(CluInfo clu, Object o) throws AssemblyException {

        Method method;
        Class[] parms;
        Object[] value;

//
        try {
            if (clu.getReferenceURL() != null) {
                parms = new Class[]{String.class};
                method = o.getClass().getMethod("setReferenceURL", parms);
                value = new Object[]{clu.getReferenceURL()};
                method.invoke(o, value);
            }


            RichTextInfo description = assembleCatalogDescr(clu.getId());
            if (description != null) {
                parms = new Class[]{RichTextInfo.class};
                method = o.getClass().getMethod("setCatalogDescr", parms);
                value = new Object[]{description};
                method.invoke(o, value);
            }
//TODO        mdInfo.setCatalogPublicationTargets(clu.getPublicationInfo());

        }
        catch (IllegalAccessException   e){
            throw new AssemblyException("Error assembling program dates", e);
        }
        catch (InvocationTargetException e){
            throw new AssemblyException("Error assembling program dates", e);
        }
        catch (NoSuchMethodException e)
        {
            throw new AssemblyException("Error assembling program dates", e);
        }


        return o;
    }


    public String assembleCredentialProgramIDs(String cluId, String credentialType) throws AssemblyException {
        List<String> credentialProgramIDs = null;
        try {
            credentialProgramIDs = luService.getCluIdsByRelation(cluId, credentialType);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        // Can a MajorDiscipline have more than one Credential Program?
        // TODO - do we need to validate that?
        if (null == credentialProgramIDs || credentialProgramIDs.size() == 0) {
            throw new AssemblyException("MajorDiscipline with ID == " + cluId + " has no Credential Program associated with it.");
        } else if (credentialProgramIDs.size() > 1) {
            throw new AssemblyException("MajorDiscipline with ID == " + cluId + " has more than one Credential Program associated with it.");
        }

        return credentialProgramIDs.get(0);
    }

    private void buildLuCode(Object o, String codeValue, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class[] parms = new Class[]{String.class};
        Method method = o.getClass().getMethod(methodName, parms);
        Object[] value= new Object[]{codeValue};
        method.invoke(o, value);
    }

    private void addOrg(Object o, AdminOrgInfo cluOrg, String getMethod, String setMethod) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Method method = o.getClass().getMethod(getMethod, null);
        List<AdminOrgInfo> objOrgs = (List<AdminOrgInfo>)method.invoke(o, null);

        if (objOrgs == null)     {
            objOrgs = new ArrayList<AdminOrgInfo>();
        }
        objOrgs.add(cluOrg);
        Class[] parms =  new Class[]{List.class};
        method = o.getClass().getMethod(setMethod, parms);
        Object[] value = new Object[]{objOrgs};
        method.invoke(o, value);
    }

        private RichTextInfo assembleCatalogDescr(String cluId) throws AssemblyException {
//        RichTextInfo returnInfo = new RichTextInfo();
//        try {
//            List<CluPublicationInfo> pubs = luService.getCluPublicationsByCluId(cluId);
//            for (CluPublicationInfo pubInfo : pubs) {
//                for (FieldInfo fieldInfo : pubInfo.getVariants()) {
//                    if (fieldInfo.getId().equals(ProgramAssemblerConstants.CLU_INFO + "." + ProgramAssemblerConstants.DESCR)) {
//                        returnInfo.setPlain(fieldInfo.getValue());
//                        return returnInfo; // or break to a label to avoid multiple return points
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new AssemblyException(e);
//        }
//        return returnInfo;
        return null;
    }

    // Spring setters
    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }
}
