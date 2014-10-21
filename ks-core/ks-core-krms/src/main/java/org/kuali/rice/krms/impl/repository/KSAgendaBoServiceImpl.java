package org.kuali.rice.krms.impl.repository;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.criteria.QueryResults;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.core.api.mo.ModelObjectUtils;
import org.kuali.rice.krad.data.DataObjectService;
import org.kuali.rice.krad.data.PersistenceOption;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsAttributeDefinition;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.kuali.rice.core.api.criteria.PredicateFactory.in;
import static org.kuali.rice.krms.impl.repository.BusinessObjectServiceMigrationUtils.deleteMatching;
import static org.kuali.rice.krms.impl.repository.BusinessObjectServiceMigrationUtils.findMatching;
import static org.kuali.rice.krms.impl.repository.BusinessObjectServiceMigrationUtils.findSingleMatching;

/**
 * Created by SW Genis on 2014/05/06.
 */
public class KSAgendaBoServiceImpl implements AgendaBoService {

    // TODO: deal with active flag

    private DataObjectService dataObjectService;
    private KrmsAttributeDefinitionService attributeDefinitionService;

    // used for converting lists of BOs to model objects
    private static final ModelObjectUtils.Transformer<AgendaItemBo, AgendaItemDefinition> toAgendaItemDefinition =
            new ModelObjectUtils.Transformer<AgendaItemBo, AgendaItemDefinition>() {
                public AgendaItemDefinition transform(AgendaItemBo input) {
                    return AgendaItemBo.to(input);
                };
            };

    // used for converting lists of BOs to model objects
    private static final ModelObjectUtils.Transformer<AgendaBo, AgendaDefinition> toAgendaDefinition =
            new ModelObjectUtils.Transformer<AgendaBo, AgendaDefinition>() {
                public AgendaDefinition transform(AgendaBo input) {
                    return AgendaBo.to(input);
                };
            };


    /**
     * This overridden method creates a KRMS Agenda in the repository
     */
    @Override
    public AgendaDefinition createAgenda(AgendaDefinition agenda) {
        if (agenda == null){
            throw new RiceIllegalArgumentException("agenda is null");
        }
        final String nameKey = agenda.getName();
        final String contextId = agenda.getContextId();
        final AgendaDefinition existing = getAgendaByNameAndContextId(nameKey, contextId);
        if (existing != null){
            throw new IllegalStateException("the agenda to create already exists: " + agenda);
        }

        AgendaBo agendaBo = from(agenda);
        agendaBo = dataObjectService.save(agendaBo, PersistenceOption.FLUSH);
        return to(agendaBo);
    }

    /**
     * This overridden method updates an existing Agenda in the repository
     */
    @Override
    public void updateAgenda(AgendaDefinition agenda) {
        if (agenda == null){
            throw new RiceIllegalArgumentException("agenda is null");
        }

        // must already exist to be able to update
        final String agendaIdKey = agenda.getId();
        final AgendaBo existing = dataObjectService.find(AgendaBo.class, agendaIdKey);
        if (existing == null) {
            throw new IllegalStateException("the agenda does not exist: " + agenda);
        }
        final AgendaDefinition toUpdate;
        if (existing.getId().equals(agenda.getId())) {
            toUpdate = agenda;
        } else {
            // if passed in id does not match existing id, correct it
            final AgendaDefinition.Builder builder = AgendaDefinition.Builder.create(agenda);
            builder.setId(existing.getId());
            toUpdate = builder.build();
        }

        // copy all updateable fields to bo
        AgendaBo boToUpdate = from(toUpdate);

        // move over AgendaBo members that don't get populated from AgendaDefinition
        boToUpdate.setItems(existing.getItems());

        // delete any old, existing attributes
        Map<String,String> fields = new HashMap<String,String>(1);
        fields.put("agenda.id", toUpdate.getId());
        deleteMatching(dataObjectService, AgendaAttributeBo.class, fields);

        // update new agenda and create new attributes
        dataObjectService.save(boToUpdate, PersistenceOption.FLUSH);
    }

    @Override
    public void deleteAgenda(String agendaId) {
        if (agendaId == null){ throw new RiceIllegalArgumentException("agendaId is null"); }
        final AgendaBo bo = dataObjectService.find(AgendaBo.class, agendaId);
        if (bo == null){ throw new IllegalStateException("the Agenda to delete does not exists: " + agendaId);}

        List<AgendaItemDefinition> agendaItems = this.getAgendaItemsByAgendaId(bo.getId());
        for( AgendaItemDefinition agendaItem : agendaItems) {
            dataObjectService.delete(AgendaItemBo.from(agendaItem));
        }

        dataObjectService.delete(bo);
    }

    /**
     * This overridden method retrieves an Agenda from the repository
     */
    @Override
    public AgendaDefinition getAgendaByAgendaId(String agendaId) {
        if (StringUtils.isBlank(agendaId)){
            throw new RiceIllegalArgumentException("agenda id is null or blank");
        }
        AgendaBo bo = dataObjectService.find(AgendaBo.class, agendaId);
        return to(bo);
    }

    /**
     * This overridden method retrieves an agenda from the repository
     */
    @Override
    public AgendaDefinition getAgendaByNameAndContextId(String name, String contextId) {
        if (StringUtils.isBlank(name)) {
            throw new RiceIllegalArgumentException("name is blank");
        }
        if (StringUtils.isBlank(contextId)) {
            throw new RiceIllegalArgumentException("contextId is blank");
        }

        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("contextId", contextId);

        AgendaBo myAgenda = findSingleMatching(dataObjectService, AgendaBo.class, map);
        return to(myAgenda);
    }

    /**
     * This overridden method retrieves a set of agendas from the repository
     */
    @Override
    public List<AgendaDefinition> getAgendasByContextId(String contextId) {
        if (StringUtils.isBlank(contextId)){
            throw new RiceIllegalArgumentException("context ID is null or blank");
        }
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("contextId", contextId);
        List<AgendaBo> bos = findMatching(dataObjectService, AgendaBo.class, map);

        return convertAgendaBosToImmutables(bos);
    }

    /**
     * This overridden method creates a new Agenda in the repository
     */
    @Override
    public AgendaItemDefinition createAgendaItem(AgendaItemDefinition agendaItem) {
        if (agendaItem == null){
            throw new RiceIllegalArgumentException("agendaItem is null");
        }
        if (agendaItem.getId() != null){
            final AgendaDefinition existing = getAgendaByAgendaId(agendaItem.getId());
            if (existing != null){
                throw new IllegalStateException("the agendaItem to create already exists: " + agendaItem);
            }
        }

        AgendaItemBo bo = AgendaItemBo.from(agendaItem);
        bo = dataObjectService.save(bo, PersistenceOption.FLUSH);
        return AgendaItemBo.to(bo);
    }

    /**
     * This overridden method updates an existing Agenda in the repository
     */
    @Override
    public void updateAgendaItem(AgendaItemDefinition agendaItem) {
        if (agendaItem == null){
            throw new RiceIllegalArgumentException("agendaItem is null");
        }
        final String agendaItemIdKey = agendaItem.getId();
        final AgendaItemDefinition existing = getAgendaItemById(agendaItemIdKey);
        if (existing == null) {
            throw new IllegalStateException("the agenda item does not exist: " + agendaItem);
        }
        final AgendaItemDefinition toUpdate;
        if (existing.getId().equals(agendaItem.getId())) {
            toUpdate = agendaItem;
        } else {
            final AgendaItemDefinition.Builder builder = AgendaItemDefinition.Builder.create(agendaItem);
            builder.setId(existing.getId());
            toUpdate = builder.build();
        }

        AgendaItemBo aiBo = AgendaItemBo.from(toUpdate);
        //updateActionAttributes(aiBo);    KSENROLL-12750 - Don't think this is required with jpa.
        dataObjectService.save(aiBo, PersistenceOption.FLUSH);
    }

    // KSENROLL-12750 - Don't think this is required with jpa.
    /*private void updateActionAttributes(AgendaItemBo aiBo) {
        if(aiBo.getRule()!=null){
            updateActionAttributes(aiBo.getRule().getActions());
        }
        if(aiBo.getWhenTrue()!=null){
            updateActionAttributes(aiBo.getWhenTrue());
        }
        if(aiBo.getWhenFalse()!=null){
            updateActionAttributes(aiBo.getWhenFalse());
        }
        if(aiBo.getAlways()!=null){
            updateActionAttributes(aiBo.getAlways());
        }
    }

    private void updateActionAttributes(List<ActionBo> actionBos) {
        for (ActionBo action : actionBos) {
            for (ActionAttributeBo aa : action.getAttributeBos()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("actionId", action.getId());
                Collection<ActionAttributeBo> aaBos = findMatching(dataObjectService, ActionAttributeBo.class, map);

                for (ActionAttributeBo aaBo : aaBos) {
                    if (StringUtils.equals(aaBo.getAttributeDefinitionId(), aa.getAttributeDefinitionId())) {
                        aa.setId(aaBo.getId());
                        aa.setVersionNumber(aaBo.getVersionNumber());
                    }
                }
            }
        }
    }*/

    /**
     * This overridden method adds a new AgendaItemDefinition to the repository
     */
    @Override
    public void addAgendaItem(AgendaItemDefinition agendaItem, String parentId, Boolean position) {
        if (agendaItem == null){
            throw new RiceIllegalArgumentException("agendaItem is null");
        }
        AgendaItemDefinition parent = null;
        if (parentId != null){
            parent = getAgendaItemById(parentId);
            if (parent == null){
                throw new IllegalStateException("parent agendaItem does not exist in repository. parentId = " + parentId);
            }
        }
        // create new AgendaItemDefinition
        final AgendaItemDefinition toCreate;
        if (agendaItem.getId() == null) {
            final AgendaItemDefinition.Builder builder = AgendaItemDefinition.Builder.create(agendaItem);
            builder.setId(AgendaItemBo.agendaItemIdIncrementer.getNewId());
            toCreate = builder.build();
        } else {
            toCreate = agendaItem;
        }
        createAgendaItem(toCreate);

        // link it to it's parent (for whenTrue/whenFalse, sibling for always
        if (parentId != null) {
            final AgendaItemDefinition.Builder builder = AgendaItemDefinition.Builder.create(parent);
            if (position == null){
                builder.setAlwaysId( toCreate.getId() );
            } else if (position.booleanValue()){
                builder.setWhenTrueId( toCreate.getId() );
            } else if (!position.booleanValue()){
                builder.setWhenFalseId( toCreate.getId() );
            }
            final AgendaItemDefinition parentToUpdate = builder.build();
            updateAgendaItem( parentToUpdate );
        }
    }

    /**
     * This overridden method retrieves an AgendaItemDefinition from the repository
     */
    @Override
    public AgendaItemDefinition getAgendaItemById(String id) {
        if (StringUtils.isBlank(id)){
            throw new RiceIllegalArgumentException("agenda item id is null or blank");
        }

        AgendaItemBo bo = dataObjectService.find(AgendaItemBo.class, id);

        return AgendaItemBo.to(bo);
    }

    @Override
    public List<AgendaItemDefinition> getAgendaItemsByAgendaId(String agendaId) {
        if (StringUtils.isBlank(agendaId)){
            throw new RiceIllegalArgumentException("agenda id is null or null");
        }
        List<AgendaItemDefinition> results = null;

        Collection<AgendaItemBo> bos = findMatching(dataObjectService, AgendaItemBo.class, Collections.singletonMap(
                "agendaId", agendaId));

        if (CollectionUtils.isEmpty(bos)) {
            results = Collections.emptyList();
        } else {
            results = Collections.unmodifiableList(ModelObjectUtils.transform(bos, toAgendaItemDefinition));
        }

        return results;
    }

    @Override
    public List<AgendaDefinition> getAgendasByType(String typeId) throws RiceIllegalArgumentException {
        if (StringUtils.isBlank(typeId)){
            throw new RiceIllegalArgumentException("type ID is null or blank");
        }

        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("typeId", typeId);
        List<AgendaBo> bos = findMatching(dataObjectService, AgendaBo.class, map);

        return convertAgendaBosToImmutables(bos);
    }

    @Override
    public List<AgendaDefinition> getAgendasByTypeAndContext(String typeId,
                                                             String contextId) throws RiceIllegalArgumentException {
        if (StringUtils.isBlank(typeId)){
            throw new RiceIllegalArgumentException("type ID is null or blank");
        }
        if (StringUtils.isBlank(contextId)){
            throw new RiceIllegalArgumentException("context ID is null or blank");
        }
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("typeId", typeId);
        map.put("contextId", contextId);
        Collection<AgendaBo> bos = findMatching(dataObjectService, AgendaBo.class, map);

        return convertAgendaBosToImmutables(bos);
    }

    @Override
    public List<AgendaItemDefinition> getAgendaItemsByType(String typeId) throws RiceIllegalArgumentException {
        return findAgendaItemsForAgendas(getAgendasByType(typeId));
    }

    @Override
    public List<AgendaItemDefinition> getAgendaItemsByContext(String contextId) throws RiceIllegalArgumentException {
        return findAgendaItemsForAgendas(getAgendasByContextId(contextId));
    }

    @Override
    public List<AgendaItemDefinition> getAgendaItemsByTypeAndContext(String typeId,
                                                                     String contextId) throws RiceIllegalArgumentException {
        return findAgendaItemsForAgendas(getAgendasByTypeAndContext(typeId, contextId));
    }

    @Override
    public void deleteAgendaItem(String agendaItemId) throws RiceIllegalArgumentException {
        if (StringUtils.isBlank(agendaItemId)) {
            throw new RiceIllegalArgumentException("agendaItemId must not be blank or null");
        }

        deleteMatching(dataObjectService, AgendaItemBo.class, Collections.singletonMap("id", agendaItemId));
    }

    private List<AgendaItemDefinition> findAgendaItemsForAgendas(List<AgendaDefinition> agendaDefinitions) {
        List<AgendaItemDefinition> results = null;

        if (!CollectionUtils.isEmpty(agendaDefinitions)) {
            List<AgendaItemBo> boResults = new ArrayList<AgendaItemBo>(agendaDefinitions.size());

            List<String> agendaIds = new ArrayList<String>(20);
            for (AgendaDefinition agendaDefinition : agendaDefinitions) {
                agendaIds.add(agendaDefinition.getId());

                if (agendaIds.size() == 20) {
                    // fetch batch

                    Predicate predicate = in("agendaId", agendaIds.toArray());
                    QueryByCriteria criteria = QueryByCriteria.Builder.fromPredicates(predicate);
                    QueryResults<AgendaItemBo> batch = getDataObjectService().findMatching(AgendaItemBo.class, criteria);

                    boResults.addAll(batch.getResults());

                    // reset agendaIds
                    agendaIds.clear();
                }
            }

            if (agendaIds.size() > 0) {
                Predicate predicate = in("agendaId", agendaIds.toArray());
                QueryByCriteria criteria = QueryByCriteria.Builder.fromPredicates(predicate);
                QueryResults<AgendaItemBo> batch = getDataObjectService().findMatching(AgendaItemBo.class, criteria);

                boResults.addAll(batch.getResults());
            }

            results = Collections.unmodifiableList(ModelObjectUtils.transform(boResults, toAgendaItemDefinition));
        } else {
            results = Collections.emptyList();
        }

        return results;
    }

    /**
     * Sets the dataObjectService attribute value.
     *
     * @param dataObjectService The dataObjectService to set.
     */
    public void setDataObjectService(final DataObjectService dataObjectService) {
        this.dataObjectService = dataObjectService;
    }

    protected DataObjectService getDataObjectService() {
        if ( dataObjectService == null ) {
            dataObjectService = KRADServiceLocator.getDataObjectService();
        }
        return dataObjectService;
    }

    protected KrmsAttributeDefinitionService getAttributeDefinitionService() {
        if (attributeDefinitionService == null) {
            attributeDefinitionService = KrmsRepositoryServiceLocator.getKrmsAttributeDefinitionService();
        }
        return attributeDefinitionService;
    }

    public void setAttributeDefinitionService(KrmsAttributeDefinitionService attributeDefinitionService) {
        this.attributeDefinitionService = attributeDefinitionService;
    }

    /**
     * Converts a Set<AgendaBo> to an Unmodifiable Set<Agenda>
     *
     * @param agendaBos a mutable Set<AgendaBo> to made completely immutable.
     * @return An unmodifiable Set<Agenda>
     */
    public List<AgendaDefinition> convertAgendaBosToImmutables(final Collection<AgendaBo> agendaBos) {
        if (CollectionUtils.isEmpty(agendaBos)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(ModelObjectUtils.transform(agendaBos, toAgendaDefinition));
    }

    /**
     * Converts a mutable bo to it's immutable counterpart
     * @param bo the mutable business object
     * @return the immutable object
     */
    @Override
    public AgendaDefinition to(AgendaBo bo) {
        if (bo == null) { return null; }
        return org.kuali.rice.krms.api.repository.agenda.AgendaDefinition.Builder.create(bo).build();
    }


    /**
     * Converts a immutable object to it's mutable bo counterpart
     * @param im immutable object
     * @return the mutable bo
     */
    @Override
    public AgendaBo from(AgendaDefinition im) {
        if (im == null) { return null; }

        AgendaBo bo = new AgendaBo();
        bo.setId(im.getId());
        bo.setName( im.getName() );
        bo.setTypeId( im.getTypeId() );
        bo.setContextId( im.getContextId() );
        bo.setFirstItemId( im.getFirstItemId() );
        bo.setVersionNumber( im.getVersionNumber() );
        bo.setActive(im.isActive());
        Set<AgendaAttributeBo> attributes = buildAgendaAttributeBo(im, bo);

        bo.setAttributeBos(attributes);

        return bo;
    }

    private Set<AgendaAttributeBo> buildAgendaAttributeBo(AgendaDefinition im, AgendaBo agendaBo) {
        Set<AgendaAttributeBo> attributes = new HashSet<AgendaAttributeBo>();

        // build a map from attribute name to definition
        Map<String, KrmsAttributeDefinition> attributeDefinitionMap = new HashMap<String, KrmsAttributeDefinition>();

        List<KrmsAttributeDefinition> attributeDefinitions =
                getAttributeDefinitionService().findAttributeDefinitionsByType(im.getTypeId());

        for (KrmsAttributeDefinition attributeDefinition : attributeDefinitions) {
            attributeDefinitionMap.put(attributeDefinition.getName(), attributeDefinition);
        }

        // for each entry, build an AgendaAttributeBo and add it to the set
        for (Map.Entry<String,String> entry  : im.getAttributes().entrySet()){
            KrmsAttributeDefinition attrDef = attributeDefinitionMap.get(entry.getKey());

            if (attrDef != null) {
                AgendaAttributeBo attributeBo = new AgendaAttributeBo();
                attributeBo.setAgenda(agendaBo);
                attributeBo.setValue(entry.getValue());
                attributeBo.setAttributeDefinition(KrmsAttributeDefinitionBo.from(attrDef));
                attributes.add( attributeBo );
            } else {
                throw new RiceIllegalStateException("there is no attribute definition with the name '" +
                        entry.getKey() + "' that is valid for the agenda type with id = '" + im.getTypeId() +"'");
            }
        }
        return attributes;
    }
}
