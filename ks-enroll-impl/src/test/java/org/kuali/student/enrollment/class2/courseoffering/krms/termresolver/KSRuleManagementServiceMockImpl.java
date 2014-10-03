package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.impl.repository.mock.RuleManagementServiceMockImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class gets around errors in the KRMS mock impl.
 */
public class KSRuleManagementServiceMockImpl extends RuleManagementServiceMockImpl {


    @Override
    public ReferenceObjectBinding createReferenceObjectBinding(ReferenceObjectBinding referenceObjectDefinition)
            throws RiceIllegalArgumentException {
        // CREATE
        ReferenceObjectBinding orig = null;
        try {
            orig = this.getReferenceObjectBinding(referenceObjectDefinition.getId());
        } catch (RiceIllegalArgumentException e) {
            //Do nothing! it doesn't exist!
        }
        if (orig != null) {
            throw new RiceIllegalArgumentException(referenceObjectDefinition.getId());
        }
        ReferenceObjectBinding.Builder copy = ReferenceObjectBinding.Builder.create(referenceObjectDefinition);
        if (copy.getId() == null) {
            copy.setId(UUID.randomUUID().toString());
        }
        referenceObjectDefinition = copy.build();
        try {
            Field f = RuleManagementServiceMockImpl.class.getDeclaredField("referenceObjectBindingMap");
            f.setAccessible(true);
            Map<String, ReferenceObjectBinding> referenceObjectBindingMap = (Map<String, ReferenceObjectBinding>) f.get(this);
            referenceObjectBindingMap.put(referenceObjectDefinition.getId(), referenceObjectDefinition);
            return referenceObjectDefinition;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    //The super method of this was failing if there was no exisitng agenda
    public AgendaDefinition createAgenda(AgendaDefinition agendaDefinition)
            throws RiceIllegalArgumentException {
        if (agendaDefinition.getId() != null) {
            AgendaDefinition orig = this.getAgenda(agendaDefinition.getId());
            if (orig != null) {
                throw new RiceIllegalArgumentException(agendaDefinition.getId() + "." + agendaDefinition.getName());
            }
        }
        AgendaDefinition existing = this.getAgendaByNameAndContextId(agendaDefinition.getName(), agendaDefinition.getContextId());
        if (existing != null) {
            throw new RiceIllegalArgumentException(agendaDefinition.getName() + " " + agendaDefinition.getContextId() + " already exists");
        }
        AgendaDefinition.Builder copy = AgendaDefinition.Builder.create(agendaDefinition);
        if (copy.getId() == null) {
            copy.setId(UUID.randomUUID().toString());
        }
        copy.setVersionNumber(0l);
        agendaDefinition = copy.build();

        try {
            Field f = RuleManagementServiceMockImpl.class.getDeclaredField("agendaMap");
            f.setAccessible(true);
            Map<String, AgendaDefinition> agendaMap = (Map<String, AgendaDefinition>) f.get(this);
            agendaMap.put(agendaDefinition.getId(), agendaDefinition);
            return agendaDefinition;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public List<ReferenceObjectBinding> findReferenceObjectBindingsByReferenceObject(String referenceObjectReferenceDiscriminatorType, String referenceObjectId) throws RiceIllegalArgumentException {
        Map<String, ReferenceObjectBinding> referenceObjectBindingMap;
        try {
            Field f = RuleManagementServiceMockImpl.class.getDeclaredField("referenceObjectBindingMap");
            f.setAccessible(true);
            referenceObjectBindingMap = (Map<String, ReferenceObjectBinding>) f.get(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        List<ReferenceObjectBinding> list = new ArrayList<>();
        for (ReferenceObjectBinding info : referenceObjectBindingMap.values()) {
            if (info.getReferenceDiscriminatorType().equals(referenceObjectReferenceDiscriminatorType)
                    && info.getReferenceObjectId().equals(referenceObjectId)) {
                list.add(info);
            }
        }
        return list;
    }
}
