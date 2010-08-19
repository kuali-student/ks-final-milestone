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

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramAssemblerUtils {

    private LuService luService;
    private CluAssemblerUtils cluAssemblerUtils;

     /**
     * Copy basic values from clu to program
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
    public Object assembleBasics(CluInfo clu, Object o) throws AssemblyException {

        Method method;
        Class[] parms;
        Object[] value;

		try 	{
            if (clu.getType() != null) {
                parms = new Class[]{String.class};
                try {
                    method = o.getClass().getMethod("setType", parms);
                    if (null != method) {
                        value = new Object[]{clu.getType()};
                        method.invoke(o, value);
                    }
                } catch (NoSuchMethodException nsme) {
                    // CredentialProgramInfo has "credentialprogramType" rather than "type"
                }
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

    /**
     * Copy basic values from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassembleBasics(CluInfo clu, Object o, NodeOperation operation) throws AssemblyException {

         Method method;
         Class[] parms;
         Object[] value;

         try 	{

             try {
                 method = o.getClass().getMethod("getType", null);
                 String type = (String)method.invoke(o, null);
                 clu.setType(type);

             } catch (NoSuchMethodException nsme) {
                 // CredentialProgramInfo has "credentialprogramType" rather than "type"
             }

             method = o.getClass().getMethod("getId", null);
             String id = (String)method.invoke(o, null);
             clu.setId(UUIDHelper.genStringUUID(id));

             method = o.getClass().getMethod("getState", null);
             String state = (String)method.invoke(o, null);
             clu.setState(state);

             method = o.getClass().getMethod("getMetaInfo", null);
             MetaInfo meta = (MetaInfo)method.invoke(o, null);
             clu.setMetaInfo(meta);

             method = o.getClass().getMethod("getAttributes", null);
             Map attr = (Map)method.invoke(o, null);
             clu.setAttributes(attr);

         }
         catch (IllegalAccessException   e){               }
         catch (InvocationTargetException e){             }
         catch (NoSuchMethodException e)            {
             throw new AssemblyException("Error disassembling program basics", e);
         }

         return clu;

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

    //TODO   disassembleRequirements    Or maybe this should be in CluAssemblerUtils??
    public CluInfo disassembleRequirements(CluInfo clu, Object o, NodeOperation operation) throws AssemblyException {

        return clu;

    }


    /**
     * Copy identifier values from clu to program
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
    public Object assembleIdentifiers(CluInfo clu, Object o) throws AssemblyException {

        try    {
            Method method;
            Class[] parms;
            Object[] value;
            if (clu.getOfficialIdentifier() != null) {
                if (clu.getOfficialIdentifier().getShortName() != null) {
                    parms = new Class[]{String.class};
                    method = o.getClass().getMethod("setShortTitle", parms);
                    value = new Object[]{clu.getOfficialIdentifier().getShortName()};
                    method.invoke(o, value);
                }
                if (clu.getOfficialIdentifier().getLongName() != null) {
                    parms = new Class[]{String.class};
                    method = o.getClass().getMethod("setLongTitle", parms);
                    value = new Object[]{clu.getOfficialIdentifier().getLongName()};
                    method.invoke(o, value);
                }
                if (clu.getOfficialIdentifier().getCode() != null) {
                    parms = new Class[]{String.class};
                    method = o.getClass().getMethod("setCode", parms);
                    value = new Object[]{clu.getOfficialIdentifier().getCode()};
                    method.invoke(o, value);
                }
            }
            if (clu.getAlternateIdentifiers() != null) {
                for (CluIdentifierInfo cluIdInfo : clu.getAlternateIdentifiers()) {
                    String idInfoType = cluIdInfo.getType();
                    if (ProgramAssemblerConstants.TRANSCRIPT.equals(idInfoType)) {
                        parms = new Class[]{String.class};
                        method = o.getClass().getMethod("setTranscriptTitle", parms);
                        value = new Object[]{cluIdInfo.getShortName()};
                        method.invoke(o, value);
                    } else if (ProgramAssemblerConstants.DIPLOMA.equals(idInfoType)) {
                        parms = new Class[]{String.class};
                        method = o.getClass().getMethod("setDiplomaTitle", parms);
                        value = new Object[]{cluIdInfo.getShortName()};
                        method.invoke(o, value);
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


    /**
     * Copy identifier values from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassembleIdentifiers(CluInfo clu, Object o, NodeOperation operation) throws AssemblyException {

        Method method;
        String value;

        try 	{
            CluIdentifierInfo official = new CluIdentifierInfo();

            method = o.getClass().getMethod("getCode", null);
            String code = (String)method.invoke(o, null);
            official.setCode(code);
            method = o.getClass().getMethod("getLongTitle", null);
            String longTitle = (String)method.invoke(o, null);
            official.setLongName(longTitle);
            method = o.getClass().getMethod("getShortTitle", null);
            String shortTitle = (String)method.invoke(o, null);
            official.setShortName(shortTitle);
            official.setType(ProgramAssemblerConstants.OFFICIAL);
            clu.setOfficialIdentifier(official);

            //TODO check for existing alt ids of this type     for UPDATE op

            try {
                method = o.getClass().getMethod("getDiplomaTitle", null);
                value = (String)method.invoke(o, null);
                if (value != null) {
                    CluIdentifierInfo diploma = new CluIdentifierInfo();
                    diploma.setCode(official.getCode());
                    diploma.setShortName(value);
                    diploma.setType(ProgramAssemblerConstants.DIPLOMA);
                    clu.getAlternateIdentifiers().add(diploma);
                }
                method = o.getClass().getMethod("getTranscriptTitle", null);
                value = (String)method.invoke(o, null);
                if (value != null) {
                    CluIdentifierInfo transcript = new CluIdentifierInfo();
                    transcript.setCode(official.getCode());
                    transcript.setShortName(value);
                    transcript.setType(ProgramAssemblerConstants.TRANSCRIPT);
                    clu.getAlternateIdentifiers().add(transcript);
                }

            }
            catch (NoSuchMethodException e)        {
                //ignore - only Major and Variation have diploma title and transcript title
            }

        }
        catch (IllegalAccessException   e){
            throw new AssemblyException("Error disassembling program basics", e);
        }
        catch (InvocationTargetException e){
            throw new AssemblyException("Error disassembling program basics", e);
        }
        catch (NoSuchMethodException e)  {
            throw new AssemblyException("Error disassembling program basics", e);
        }

        return clu;
    }

    /**
     * Copy Lu Codes from clu to program
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
    public Object assembleLuCodes(CluInfo clu, Object o) throws AssemblyException {
        try {
            if (clu.getLuCodes() != null) {
                for (LuCodeInfo codeInfo : clu.getLuCodes()) {
                    if (ProgramAssemblerConstants.CIP_2000.equals(codeInfo.getType())) {
                        buildLuCodeFromClu(o, codeInfo.getValue(), "setCip2000Code");
                    } else if (ProgramAssemblerConstants.CIP_2010.equals(codeInfo.getType())) {
                        buildLuCodeFromClu(o, codeInfo.getValue(), "setCip2010Code");
                    } else if (ProgramAssemblerConstants.HEGIS.equals(codeInfo.getType())) {
                        buildLuCodeFromClu(o, codeInfo.getValue(), "setHegisCode");
                    } else if (ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION.equals(codeInfo.getType())) {
                        buildLuCodeFromClu(o, codeInfo.getValue(), "setUniversityClassification");
                    } else if (ProgramAssemblerConstants.SELECTIVE_ENROLLMENT.equals(codeInfo.getType())) {
                        buildLuCodeFromClu(o, codeInfo.getValue(), "setSelectiveEnrollmentCode");
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


    /**
     * Copy Lu Codes from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     * @throws AssemblyException
     */
    public CluInfo disassembleLuCodes(CluInfo clu, Object o, NodeOperation operation) throws AssemblyException {

         if (clu.getLuCodes() == null) {
             clu.setLuCodes(new ArrayList<LuCodeInfo>());
         }
        //TODO check for existing LuCodes of same type    for UPDATE op
         LuCodeInfo code = buildLuCodeFromProgram(o, "getCip2000Code", ProgramAssemblerConstants.CIP_2000);
         if (code != null)
             clu.getLuCodes().add(code);
         code = buildLuCodeFromProgram( o, "getCip2010Code", ProgramAssemblerConstants.CIP_2010);
         if (code != null)
             clu.getLuCodes().add(code);
         code = buildLuCodeFromProgram( o, "getHegisCode", ProgramAssemblerConstants.HEGIS);
         if (code != null)
             clu.getLuCodes().add(code);
         code = buildLuCodeFromProgram( o, "getUniversityClassification", ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION);
         if (code != null)
             clu.getLuCodes().add(code);
         code = buildLuCodeFromProgram( o, "getSelectiveEnrollmentCode", ProgramAssemblerConstants.SELECTIVE_ENROLLMENT);
         if (code != null)
             clu.getLuCodes().add(code);

        return clu;

    }

    /**
     * Copy AdminOrg values from clu to program
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
    public Object assembleAdminOrgs(CluInfo clu, Object o) throws AssemblyException {

        try {
            if (clu.getAdminOrgs() != null) {
                for (AdminOrgInfo cluOrg : clu.getAdminOrgs()) {
                    if (cluOrg.getType().equals(ProgramAssemblerConstants.CONTENT_OWNER_DIVISION)) {
                        addOrgToProgram(o, cluOrg, "getDivisionsContentOwner", "setDivisionsContentOwner");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION)) {
                        addOrgToProgram(o, cluOrg, "getDivisionsStudentOversight", "setDivisionsStudentOversight");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_DIVISION)) {
                        addOrgToProgram(o, cluOrg, "getDivisionsDeployment", "setDivisionsDeployment");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION)) {
                        addOrgToProgram(o, cluOrg, "getDivisionsFinancialResources", "setDivisionsFinancialResources");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION)) {
                        addOrgToProgram(o, cluOrg, "getDivisionsFinancialControl", "setDivisionsFinancialControl");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.CONTENT_OWNER_UNIT)) {
                        addOrgToProgram(o, cluOrg, "getUnitsContentOwner", "setUnitsContentOwner");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT)) {
                        addOrgToProgram(o, cluOrg, "getUnitsStudentOversight", "setUnitsStudentOversight");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.DEPLOYMENT_UNIT)) {
                        addOrgToProgram(o, cluOrg, "getUnitsDeployment", "setUnitsDeployment");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT)) {
                        addOrgToProgram(o, cluOrg, "getUnitsFinancialResources", "setUnitsFinancialResources");
                    }
                    else if (cluOrg.getType().equals(ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT)) {
                        addOrgToProgram(o, cluOrg, "getUnitsFinancialControl", "setUnitsFinancialControl");
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


    /**
     * Copy AdminOrg values from program to clu
     *
     * @param clu
     * @param t
     * @param operation
     */
    public CluInfo disassembleAdminOrgs(CluInfo clu, Object t, NodeOperation operation){

        if (clu.getAdminOrgs() == null) {
            clu.setAdminOrgs(new ArrayList<AdminOrgInfo>());
        }        

        //TODO check for existing admin orgs for type for UPDATE op
    	List<AdminOrgInfo> orgs  = getAdminOrgsFromProgram(t, "getDivisionsContentOwner");
  		if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getDivisionsStudentOversight");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getDivisionsDeployment");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getDivisionsFinancialResources");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getDivisionsFinancialControl");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getUnitsContentOwner");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getUnitsStudentOversight");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getUnitsDeployment");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getUnitsFinancialResources");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);
    	orgs = getAdminOrgsFromProgram(t, "getUnitsFinancialControl");
    	if(orgs != null) clu.getAdminOrgs().addAll(orgs);

        return clu;

    }

    /**
     * Copy result option values from clu to program
     *
     * @param cluId
     * @param resultType
     * @return
     * @throws AssemblyException
     */
    public List<String> assembleResultOptions(String cluId, String resultType) throws AssemblyException {
        List<String> resultOptions = null;
        try{
            List<CluResultInfo> cluResults = luService.getCluResultByClu(cluId);

            resultOptions = cluAssemblerUtils.assembleCluResults(resultType, cluResults);

        } catch (DoesNotExistException e){
        } catch (Exception e) {
            throw new AssemblyException("Error getting major results", e);
        }
        return resultOptions;
    }


    //TODO disassembleResultOptions
    /**
     *  Copy result options from program to clu
     *
     * @param clu
     * @param t
     * @param operation
     * @return
     */
    public CluInfo disassembleResultOptions(CluInfo clu, Object t, NodeOperation operation){

        return clu;

    }

    /**
     * Copy atp values  from clu to program
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
    public Object assembleAtps(CluInfo clu, Object o) throws AssemblyException {

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


    /**
     * Copy atp values from Program to clu
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassembleAtps(CluInfo clu, Object o, NodeOperation operation) throws AssemblyException {

        Method method;
        String value;

        try 	{

            method = o.getClass().getMethod("getStartTerm", null);
            value = (String)method.invoke(o, null);
            clu.setExpectedFirstAtp(value);

            method = o.getClass().getMethod("getEndTerm", null);
            value = (String)method.invoke(o, null);
            clu.setLastAtp(value);

            method = o.getClass().getMethod("getEndProgramEntryTerm", null);
            value = (String)method.invoke(o, null);
            clu.setLastAdmitAtp(value);

        }
        catch (IllegalAccessException   e){          }
        catch (InvocationTargetException e){         }
        catch (NoSuchMethodException e)             {
            throw new AssemblyException("Error disassembling program basics", e);
        }


        return clu;

    }

    /**
     * Copy publication values from clu to program
     *
     * @param clu
     * @param o
     * @return
     * @throws AssemblyException
     */
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

            //TODO assemble catalogDescr

            RichTextInfo description = assembleCatalogDescr(clu.getId());
//            if (description != null) {
//                parms = new Class[]{RichTextInfo.class};
//                method = o.getClass().getMethod("setCatalogDescr", parms);
//                value = new Object[]{description};
//                method.invoke(o, value);
//            }
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

    /**
     * Copy publication values from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     * @return
     * @throws AssemblyException
     */
    public CluInfo disassemblePublicationInfo(CluInfo clu, Object o, NodeOperation operation) throws AssemblyException {

        Method method;
        String value;

        try 	{
            method = o.getClass().getMethod("getReferenceURL", null);
            value = (String)method.invoke(o, null);
            clu.setReferenceURL(value);

            //TODO diasassembleCatalogDescr
//            method = o.getClass().getMethod("getCatalogDescr", null);
//            RichTextInfo descr = (RichTextInfo)method.invoke(o, null);
//            clu.setDescr(descr);

//TODO        clu.setPublicationInfo(major.getCatalogPublicationTargets());        

        }

        catch (IllegalAccessException   e){
            throw new AssemblyException("Error disassembling program publication info", e);
        }
        catch (InvocationTargetException e){
            throw new AssemblyException("Error disassembling program  publication info", e);
        }
        catch (NoSuchMethodException e)  {
            throw new AssemblyException("Error disassembling program  publication info", e);
        }
        return clu;

    }

    /**
     * Retrieve credential program ids for clu 
     *
     * @param cluId
     * @param credentialType
     * @return
     * @throws AssemblyException
     */
    public String assembleCredentialProgramIDs(String cluId, String credentialType) throws AssemblyException {
        List<String> credentialProgramIDs = null;
        try {
            credentialProgramIDs = luService.getCluIdsByRelation(cluId, credentialType);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        // Can a Program have more than one Credential Program?
        // TODO - do we need to validate that?
        if (null == credentialProgramIDs || credentialProgramIDs.size() == 0) {
            throw new AssemblyException("Program with ID == " + cluId + " has no Credential Program associated with it.");
        } else if (credentialProgramIDs.size() > 1) {
            throw new AssemblyException("Program with ID == " + cluId + " has more than one Credential Program associated with it.");
        }

        if (credentialProgramIDs == null || credentialProgramIDs.isEmpty()) {
            return null;
        }
        else {
            return credentialProgramIDs.get(0);
        }
    }


    /**
     * Copy credential program id value from program to clu
     *
     * @param clu
     * @param o
     * @param operation
     * @return
     * @throws AssemblyException
     */
     public List<BaseDTOAssemblyNode<?,?>>  disassembleCredentialProgram(Object o, NodeOperation operation, String relationType) throws AssemblyException {

        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();
        CluInfo credentialClu = null;

        Method  method = null;
        String cluId = null;
        String credentialId = null;
        String state = null;
        try {
            method = o.getClass().getMethod("getCredentialProgramId", null);
            credentialId = (String)method.invoke(o, null);

            method = o.getClass().getMethod("getId", null);
            cluId = (String)method.invoke(o, null);

            method = o.getClass().getMethod("getState", null);
            state = (String)method.invoke(o, null);

        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
            throw new AssemblyException("Error disassembling program credential", e);
        }

        try {
            credentialClu = luService.getClu(credentialId);
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Credential Clu does not exist for " + credentialId);
        }

        //TODO checks for update and delete

        CluCluRelationInfo relation = new CluCluRelationInfo();
        relation.setCluId(cluId);
        relation.setRelatedCluId(credentialId);
        relation.setType(relationType);
        relation.setState(state);

        BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                null);
        relationNode.setNodeData(relation);
        relationNode.setOperation(NodeOperation.CREATE);

        results.add(relationNode);

        return results;
    }

    private LuCodeInfo buildLuCodeFromProgram(Object o, String methodName, String codeType) throws AssemblyException {

        LuCodeInfo code = null;
        try {
            Method method = o.getClass().getMethod(methodName, null);
            String value = (String)method.invoke(o, null);

            if (value != null && !value.isEmpty()) {
                code = new LuCodeInfo();
                code.setType(codeType);
                code.setValue(value);
                code.setAttributes(new HashMap<String, String>());
            }

        } catch (NoSuchMethodException e) {
            //ignore - this program type doesn't have this method
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
            throw new AssemblyException("Error while disassembling program LU codes", e);
        }
        return code;
    }

    private void buildLuCodeFromClu(Object o, String codeValue, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class[] parms = new Class[]{String.class};
        Method method = o.getClass().getMethod(methodName, parms);
        Object[] value= new Object[]{codeValue};
        method.invoke(o, value);
    }

    private void addOrgToProgram(Object o, AdminOrgInfo cluOrg, String getMethod, String setMethod) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

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

    //TODO assembleCatalogDescr
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

    //TODO disassembleCatalogDescr
     private CluInfo disassembleCatalogDescr(String cluId) throws AssemblyException {
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

	private List<AdminOrgInfo> getAdminOrgsFromProgram(Object t, String methodName){
        List<AdminOrgInfo> result;
		try	{
			 Class<?> clazz = t.getClass();
			 Method method = clazz.getMethod(methodName, null);
            result = (List<AdminOrgInfo>)method.invoke(t, null);
        }
		catch (IllegalAccessException   ex){
			return null;
		}
		catch (InvocationTargetException  ex){
			return null;
		}
		catch (NoSuchMethodException ex) {
			 return null;
		}
        return result;
    }

    // Spring setters
    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }
}
