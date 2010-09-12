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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;

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

        try {
            CluIdentifierInfo official = null != clu.getOfficialIdentifier() ? clu.getOfficialIdentifier() : new CluIdentifierInfo();

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

            //Remove any existing diploma or transcript alt identifiers
            CluIdentifierInfo diplomaInfo = null;
            CluIdentifierInfo transcriptInfo = null;
            for(Iterator<CluIdentifierInfo> iter = clu.getAlternateIdentifiers().iterator();iter.hasNext();){
                CluIdentifierInfo cluIdentifier = iter.next();
                if (ProgramAssemblerConstants.DIPLOMA.equals(cluIdentifier.getType())) {
                   diplomaInfo = cluIdentifier; 
                } else if (ProgramAssemblerConstants.TRANSCRIPT.equals(cluIdentifier.getType())) {
                    transcriptInfo = cluIdentifier;
                }
            }

            try {
                method = o.getClass().getMethod("getDiplomaTitle", null);
                value = (String)method.invoke(o, null);
                if (value != null) {
                    if (diplomaInfo == null) {
                        diplomaInfo = new CluIdentifierInfo();
                        diplomaInfo.setState(ProgramAssemblerConstants.ACTIVE);
                        clu.getAlternateIdentifiers().add(diplomaInfo);
	                }
                    diplomaInfo.setCode(official.getCode());
                    diplomaInfo.setShortName(value);
                    diplomaInfo.setType(ProgramAssemblerConstants.DIPLOMA);
                }
                method = o.getClass().getMethod("getTranscriptTitle", null);
                value = (String)method.invoke(o, null);
                if (value != null) {
                    if (transcriptInfo == null) {
                        transcriptInfo = new CluIdentifierInfo();
                        transcriptInfo.setState(ProgramAssemblerConstants.ACTIVE);
                        clu.getAlternateIdentifiers().add(transcriptInfo);
                    }
                    transcriptInfo.setCode(official.getCode());
                    transcriptInfo.setShortName(value);
                    transcriptInfo.setType(ProgramAssemblerConstants.TRANSCRIPT);
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

        clu.setLuCodes(new ArrayList<LuCodeInfo>());

        addLuCode(clu, o, "getCip2000Code", ProgramAssemblerConstants.CIP_2000);
        addLuCode(clu, o, "getCip2010Code", ProgramAssemblerConstants.CIP_2010);
        addLuCode(clu, o,  "getHegisCode", ProgramAssemblerConstants.HEGIS);
        addLuCode(clu, o, "getUniversityClassification", ProgramAssemblerConstants.UNIVERSITY_CLASSIFICATION);
        addLuCode(clu, o,  "getSelectiveEnrollmentCode", ProgramAssemblerConstants.SELECTIVE_ENROLLMENT);

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
     * @param o
     * @param operation
     */
    public CluInfo disassembleAdminOrgs(CluInfo clu, Object o, NodeOperation operation){

        // clear out all old admin orgs
        clu.setAdminOrgs(new ArrayList<AdminOrgInfo>());

        addAdminOrgs(clu, o, "getDivisionsContentOwner");
        addAdminOrgs(clu, o, "getDivisionsStudentOversight");
    	addAdminOrgs(clu, o, "getDivisionsDeployment");
    	addAdminOrgs(clu, o, "getDivisionsFinancialResources");
    	addAdminOrgs(clu, o, "getDivisionsFinancialControl");
    	addAdminOrgs(clu, o, "getUnitsContentOwner");
    	addAdminOrgs(clu, o, "getUnitsStudentOversight");
    	addAdminOrgs(clu, o, "getUnitsDeployment");
    	addAdminOrgs(clu, o,"getUnitsFinancialResources");
    	addAdminOrgs(clu, o, "getUnitsFinancialControl");

        return clu;

    }

    private void addAdminOrgs(CluInfo clu, Object o, String methodName) {
        List<AdminOrgInfo> orgs  = getAdminOrgsFromProgram(o, methodName);
        if(orgs != null)
            clu.getAdminOrgs().addAll(orgs);
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

        Method  method = null;
        String programId = null;
        String credentialId = null;
        try {
            method = o.getClass().getMethod("getCredentialProgramId", null);
            credentialId = (String)method.invoke(o, null);

            method = o.getClass().getMethod("getId", null);
            programId = (String)method.invoke(o, null);

        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
            throw new AssemblyException("Error disassembling program credential", e);
        }

        try {
            CluInfo credentialClu = luService.getClu(credentialId);
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Credential Clu does not exist for " + credentialId);
        }

        Map<String, String> currentRelations = new HashMap<String, String>();

        if (!NodeOperation.CREATE.equals(operation)) {
            try {
                List<CluCluRelationInfo> cluRelations = luService.getCluCluRelationsByClu(credentialId);

                for (CluCluRelationInfo cluRelation : cluRelations) {
                    if (relationType.equals(cluRelation.getType())) {
                        currentRelations.put(cluRelation.getRelatedCluId(), cluRelation.getId());
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (InvalidParameterException e) {
            } catch (MissingParameterException e) {
            } catch (OperationFailedException e) {
                throw new AssemblyException("Error getting related clus", e);
            }
        }


        //  If this is a create then vreate new relation
        if (NodeOperation.CREATE == operation
                || (NodeOperation.UPDATE == operation && !currentRelations.containsKey(credentialId) )) {
            // the relation does not exist, so create
            CluCluRelationInfo relation = new CluCluRelationInfo();
            relation.setCluId(credentialId);
            relation.setRelatedCluId(programId);
            relation.setType(relationType);
            relation.setState(ProgramAssemblerConstants.ACTIVE);

            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                    null);
            relationNode.setNodeData(relation);
            relationNode.setOperation(NodeOperation.CREATE);

            results.add(relationNode);
        } else if (NodeOperation.UPDATE == operation
                && currentRelations.containsKey(credentialId)) {
            // If the relationship already exists update it

            // remove this entry from the map so we can tell what needs to
            // be deleted at the end
            currentRelations.remove(credentialId);
        } else if (NodeOperation.DELETE == operation
                && currentRelations.containsKey(programId))  {
            // Delete the Format and its relation
            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
            relationToDelete.setId( currentRelations.get(programId) );
            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);

            // remove this entry from the map so we can tell what needs to
            // be deleted at the end
            currentRelations.remove(programId);
        }

        for (Map.Entry<String, String> entry : currentRelations.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
            relationToDelete.setId( entry.getValue() );
            BaseDTOAssemblyNode<Object, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<Object, CluCluRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);
        }
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

     private void addLuCode(CluInfo clu, Object o, String methodName, String codeType ) throws AssemblyException {

        LuCodeInfo code = buildLuCodeFromProgram(o, methodName, codeType );
        if (code != null) {
//            if (currentCodes.containsKey(code.getType())) {
//                clu.getLuCodes().remove(currentCodes.get(code.getType()));
//            }
            clu.getLuCodes().add(code);
        }
    }

    // Spring setters
    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }

    public String getCredentialProgramID(String cluId) throws AssemblyException {

        List<String> credentialProgramIDs = null;
        try {
            credentialProgramIDs = luService.getCluIdsByRelation(cluId, ProgramAssemblerConstants.HAS_MAJOR_PROGRAM);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        // Can a Program have more than one Credential Program?
        // TODO - do we need to validate that?
        if (null == credentialProgramIDs || credentialProgramIDs.isEmpty()) {
            throw new AssemblyException("Program with ID == " + cluId + " has no Credential Program associated with it.");
        } else if (credentialProgramIDs.size() > 1) {
            throw new AssemblyException("Program with ID == " + cluId + " has more than one Credential Program associated with it.");
        }
        return credentialProgramIDs.get(0);
    }

    public String getProgramLevel(String credentialProgramId) throws AssemblyException {
        CluInfo credProgramInfo = null;
        try {
            credProgramInfo = luService.getClu(credentialProgramId);
        } catch (Exception e) {
            throw new AssemblyException(e);
        }
        return credProgramInfo.getOfficialIdentifier().getLevel();
    }
}
