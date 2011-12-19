/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.process.poc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.atp.service.AtpServiceDecorator;

/**
 *
 * @author nwright
 */
public class ProcessPocAtpServiceDecorator extends AtpServiceDecorator {

    public ProcessPocAtpServiceDecorator(AtpService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        _initializeData();
    }

    private void _initializeData() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-Initializer");

        AtpInfo spring2011 = this._createAtp(ProcessPocConstants.SPRING_2011_TERM_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "Spring 2011", "2011-02-01", "2011-05-31", context);
        AtpInfo summer2011 = this._createAtp(ProcessPocConstants.SUMMER_2011_TERM_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, "Summer 2011", "2011-06-01", "2011-08-31", context);
        AtpInfo fall2011 = this._createAtp(ProcessPocConstants.FALL_2011_TERM_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "Fall 2011", "2011-09-01", "2011-12-31", context);
        AtpInfo spring2012 = this._createAtp(ProcessPocConstants.SPRING_2012_TERM_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "Spring 2012", "2012-02-01", "2012-05-31", context);

        _createRegMilestone(spring2011, "2011-01-20", "2011-02-01", context);
        _createRegMilestone(summer2011, "2011-06-01", "2011-12-31", context);
        _createRegMilestone(fall2011, "2011-09-01", "2011-12-31", context);
        _createRegMilestone(spring2012, "2012-01-20", "2012-02-01", context);
    }

    private MilestoneInfo _createRegMilestone(AtpInfo atp, String start, String end, ContextInfo context) {
        MilestoneInfo milestone = new MilestoneInfo();
        milestone.setKey(atp.getKey() + "_reg.period");
        milestone.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        milestone.setStateKey(AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        milestone.setStartDate(parseDate(start));
        milestone.setEndDate(parseDate(end));
        milestone.setName("Registration Period for " + atp.getName());
        try {
            MilestoneInfo createdMilestone = this.createMilestone(milestone, context);
            this.addMilestoneToAtp(milestone.getKey(), atp.getKey(), context);
            return createdMilestone;
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }


    }

    private AtpInfo _createAtp(String key, String type, String name, String start, String end, ContextInfo context) {
        AtpInfo atp = new AtpInfo();
        atp.setKey(key);
        atp.setTypeKey(type);
        atp.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atp.setStartDate(parseDate(start));
        atp.setEndDate(parseDate(end));
        atp.setName(name);
        try {
            AtpInfo createdAtp = this.createAtp(atp.getKey(), atp, context);
            return createdAtp;
        } catch (Exception ex) {
            throw new RuntimeException("unexpected", ex);
        }
    }

    private Date parseDate(String str) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        } catch (ParseException ex) {
            throw new RuntimeException(str, ex);
        }
        return date;
    }

    private List<String> _getTermTypeKeys() {
        List<String> list = new ArrayList<String>();
        list.add(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        list.add(AtpServiceConstants.ATP_WINTER_TYPE_KEY);
        list.add(AtpServiceConstants.ATP_SPRING_TYPE_KEY);
        list.add(AtpServiceConstants.ATP_SUMMER_TYPE_KEY);
        return list;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (!_getTermTypeKeys().contains(typeKey)) {
            throw new DoesNotExistException();
        }
        TypeInfo info = new TypeInfo();
        info.setKey(typeKey);
        info.setName(typeKey);
        info.setRefObjectURI(AtpServiceConstants.REF_OBJECT_URI_ATP);
        return info;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (ownerTypeKey.equals(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY) && relationTypeKey.equals(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY)) {
            List<TypeTypeRelationInfo> relations = new ArrayList();
            for (String termKey : this._getTermTypeKeys()) {
                relations.add(_createTermTypeRelation(termKey));
            }
            return relations;
        }
        throw new OperationFailedException ("not impelmented");

    }

    private TypeTypeRelationInfo _createTermTypeRelation(String related) {
        return this._createTypeTypeRelation(TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY,
                AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY,
                related);

    }

    private TypeTypeRelationInfo _createTypeTypeRelation(String relationType, String owner, String related) {
        TypeTypeRelationInfo info = new TypeTypeRelationInfo();
        info.setTypeKey(relationType);
        info.setStateKey(TypeServiceConstants.TYPE_TYPE_RELATION_ACTIVE_STATE_KEY);
        info.setOwnerTypeKey(owner);
        info.setRelatedTypeKey(related);
        info.setKey(owner + "." + related);
        return info;
    }
}
