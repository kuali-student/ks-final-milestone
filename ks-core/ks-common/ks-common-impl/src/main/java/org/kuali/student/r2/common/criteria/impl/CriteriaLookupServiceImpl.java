package org.kuali.student.r2.common.criteria.impl;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.criteria.LookupCustomizer;
import org.kuali.student.r2.common.criteria.LookupCustomizer.PredicateTransform;

import java.util.List;

public class CriteriaLookupServiceImpl implements CriteriaLookupService {

    private CriteriaLookupDaoJpaImpl criteriaLookupDao;
    private List<PredicateTransform> additionalTransforms;
    private List<PredicateTransform> predicateTransforms;
    
    public List<PredicateTransform> getAdditionalTransforms() {
        return additionalTransforms;
    }

    public void setAdditionalTransforms(List<PredicateTransform> additionalTransforms) {
        this.additionalTransforms = additionalTransforms;
    }

    public List<PredicateTransform> getPredicateTransforms() {
        return predicateTransforms;
    }

    public void setPredicateTransforms(List<PredicateTransform> predicateTransforms) {
        this.predicateTransforms = predicateTransforms;
    }

    public void setCriteriaLookupDao(CriteriaLookupDaoJpaImpl criteriaLookupDao) {
        this.criteriaLookupDao = criteriaLookupDao;
    }
    
    @Override
    public <T> GenericQueryResults<T> lookup(Class<T> queryClass, QueryByCriteria criteria) {
        
        LookupCustomizer.Builder<T> lc = LookupCustomizer.Builder.create();
        lc.setPredicateTransforms(this.getPredicateTransforms());
        lc.setAdditionalTransforms(this.getAdditionalTransforms());
        
        return criteriaLookupDao.lookup(queryClass, criteria, lc.build());
    }

    @Override
    public <T> GenericQueryResults<T> lookup(Class<T> queryClass, QueryByCriteria criteria, LookupCustomizer<T> customizer) {
        
        customizer.getAdditionalTransforms().addAll(this.getAdditionalTransforms());
        customizer.getPredicateTransforms().addAll(this.getPredicateTransforms());
        
        return criteriaLookupDao.lookup(queryClass, criteria, customizer);
    }

    @Override
    public <T> GenericQueryResults<String> lookupIds(Class<T> queryClass, QueryByCriteria criteria) {

        LookupCustomizer.Builder<T> lc = LookupCustomizer.Builder.create();
        lc.setPredicateTransforms(this.getPredicateTransforms());
        lc.setAdditionalTransforms(this.getAdditionalTransforms());

        return criteriaLookupDao.lookupIds(queryClass, criteria, lc.build());
    }

    @Override
    public <T> GenericQueryResults<String> lookupIds(Class<T> queryClass, QueryByCriteria criteria, LookupCustomizer<T> customizer) {

        customizer.getAdditionalTransforms().addAll(this.getAdditionalTransforms());
        customizer.getPredicateTransforms().addAll(this.getPredicateTransforms());

        return criteriaLookupDao.lookupIds(queryClass, criteria, customizer);
    }

    @Override
    public <T> GenericQueryResults<List<String>> genericLookup(final Class<T> queryClass, final QueryByCriteria criteria, List<String> fields){
        LookupCustomizer.Builder<T> lc = LookupCustomizer.Builder.create();
        lc.setPredicateTransforms(this.getPredicateTransforms());
        lc.setAdditionalTransforms(this.getAdditionalTransforms());

        return criteriaLookupDao.genericLookup(queryClass, criteria, lc.build(), fields);
    }

    @Override
    public <T> GenericQueryResults<List<String>> genericLookup(final Class<T> queryClass, final QueryByCriteria criteria, final LookupCustomizer<T> customizer, List<String> fields){
        customizer.getAdditionalTransforms().addAll(this.getAdditionalTransforms());
        customizer.getPredicateTransforms().addAll(this.getPredicateTransforms());

        return criteriaLookupDao.genericLookup(queryClass, criteria, customizer, fields);

    }

}
