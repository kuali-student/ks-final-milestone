package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;


public class RegistrationGroupTransformer {
    private LuiService luiService;

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    /**
     * KSENROLL-10515
     * By passing in foId and aoIds, this can convert the Lui to RG a little faster by avoiding a service call
     * to look it up.  Used by CourseOfferingServiceImpl::createRegistrationGroup
     * @param lui lui corresponding to RG
     * @param context
     * @param foId FO id associated with this RG (passed in to remove a service call lookup)
     * @param aoIds List of ao IDs associated with this RG (passed in to remove a service call lookup)
     * @return
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    public RegistrationGroupInfo lui2RgOptimized(LuiInfo lui, ContextInfo context, String foId, List<String> aoIds)
            throws MissingParameterException, PermissionDeniedException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        return lui2RgInternal(lui, context, true, foId, aoIds);
    }

    /**
     * Private internal version
     * @param lui Lui corresponding to RG
     * @param context
     * @param useCached true, if foId and aoIds are passed, false if those params are ignored
     * @param foId Format offering ID with RG. Use this if useCached is true, otherwise ignore
     * @param aoIds List of AO IDs with RG. Use this if useCached is true, otherwise ignore
     * @return Filled out RG
     * @throws PermissionDeniedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    private RegistrationGroupInfo lui2RgInternal(LuiInfo lui, ContextInfo context,
                                                 boolean useCached,
                                                 String foId, List<String> aoIds)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {
        RegistrationGroupInfo regGroup = new RegistrationGroupInfo();
        regGroup.setId(lui.getId());
        regGroup.setMeta(lui.getMeta());
        regGroup.setStateKey(lui.getStateKey());
        regGroup.setTypeKey(lui.getTypeKey());
        regGroup.setDescr(lui.getDescr());

        if (lui.getOfficialIdentifier() != null) {
            regGroup.setRegistrationCode(lui.getOfficialIdentifier().getCode());
        }

        //Dynamic attributes - Some lui dynamic attributes are defined fields on Activity Offering
        List<AttributeInfo> attributes = regGroup.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            if (CourseOfferingServiceConstants.IS_REGISTRATION_GROUP_GENERATED_INDICATOR_ATTR.equals(attr.getKey())) {
                regGroup.setIsGenerated(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.AOCLUSTER_ID_ATTR.equals(attr.getKey())) {
                regGroup.setActivityOfferingClusterId(attr.getValue());
            } else {
                attributes.add(new AttributeInfo(attr));
            }
        }
        regGroup.setAttributes(attributes);

        regGroup.setName(lui.getName());  // This is the 4-digit reg group code unique to RG within a CO
        regGroup.setTermId(lui.getAtpId());

        // LuiLuiRelation (to set courseOfferingId, activityOfferingIds)

        if (!useCached) {
            assembleLuiLuiRelations(regGroup, lui.getId(), context);
        } else {
            // Use parameters passed in (which avoids a service call)
            regGroup.setActivityOfferingIds(aoIds);
            regGroup.setFormatOfferingId(foId);
        }

        return regGroup;
    }

    /**
     * Converts a lui to an RG (since Luis are saved in the DB, not RGs).
     * See also: lui2RgOptimized
     * @param lui A lui fetched from the DB
     * @param context
     * @return The Registration Group created from the RG.
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     * @throws DoesNotExistException
     */
    public RegistrationGroupInfo lui2Rg(LuiInfo lui, ContextInfo context) throws
            InvalidParameterException, MissingParameterException, PermissionDeniedException,
            OperationFailedException, DoesNotExistException {
        return lui2RgInternal(lui, context, false, null, null);
    }


    public LuiInfo rg2Lui(RegistrationGroupInfo regGroup, ContextInfo context)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {

        LuiInfo lui = new LuiInfo();
        lui.setId(regGroup.getId());
        lui.setTypeKey(regGroup.getTypeKey());
        lui.setStateKey(regGroup.getStateKey());
        lui.setDescr(regGroup.getDescr());
        lui.setMeta(regGroup.getMeta());

        // Lui Official Identifier
        LuiIdentifierInfo officialIdentifier = new LuiIdentifierInfo();
        officialIdentifier.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        officialIdentifier.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        officialIdentifier.setCode(regGroup.getRegistrationCode());
        lui.setOfficialIdentifier(officialIdentifier);

        // Dynamic attributes - Some lui dynamic attributes are defined fields on Activity Offering
        List<AttributeInfo> attributes = lui.getAttributes();
        for (Attribute attr : regGroup.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }
        AttributeInfo isGenerated = new AttributeInfo();
        isGenerated.setKey(CourseOfferingServiceConstants.IS_REGISTRATION_GROUP_GENERATED_INDICATOR_ATTR);
        isGenerated.setValue(String.valueOf(regGroup.getIsGenerated()));
        attributes.add(isGenerated);


        AttributeInfo aocIdAttrib = new AttributeInfo();
        aocIdAttrib.setKey(CourseOfferingServiceConstants.AOCLUSTER_ID_ATTR);
        aocIdAttrib.setValue(String.valueOf(regGroup.getActivityOfferingClusterId()));
        attributes.add(aocIdAttrib);

        lui.setAttributes(attributes);

        lui.setName(regGroup.getName());

        //set cluId
        String formatId = getFo(regGroup.getFormatOfferingId(), context).getFormatId();
        if (formatId != null && !formatId.isEmpty()) {
            lui.setCluId(formatId);
        }

        lui.setAtpId(regGroup.getTermId());

        return lui;
    }

    /**
     * This method populates the passed in RegistrationGroupInfo with its related ActivityOffering Ids and
     * the related FormatOffering Id
     * @param rg
     * @param rgLuiId The id for the RG
     * @param context
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public void assembleLuiLuiRelations(RegistrationGroupInfo rg, String rgLuiId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {


        List<String> aoIds = new ArrayList<String>();
        List<LuiLuiRelationInfo> rels = luiService.getLuiLuiRelationsByLui(rgLuiId, context);
        if (rels != null && !rels.isEmpty()) {
            for (LuiLuiRelationInfo rel : rels) {
                if (rel.getLuiId().equals(rgLuiId)
                        && rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY)) {
                    if (!aoIds.contains(rel.getRelatedLuiId())) {
                        aoIds.add(rel.getRelatedLuiId());
                    }
                } else if (rel.getRelatedLuiId().equals(rgLuiId)
                        && rel.getTypeKey().equals(LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY)) {
                    rg.setFormatOfferingId(rel.getLuiId());
                }
            }
        }

        rg.setActivityOfferingIds(aoIds);

    }

    private FormatOfferingInfo getFo(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        LuiInfo lui = luiService.getLui(formatOfferingId, context);
        FormatOfferingInfo fo = new FormatOfferingInfo();
        new FormatOfferingTransformer().lui2Format(lui, fo);

        return fo;
    }

}
