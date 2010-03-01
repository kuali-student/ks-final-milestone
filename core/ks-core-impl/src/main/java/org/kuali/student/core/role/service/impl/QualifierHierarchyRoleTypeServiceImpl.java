/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.role.dao.QualifierHierarchyDAO;
import org.kuali.student.core.role.entity.Qualifier;
import org.kuali.student.core.role.entity.QualifierHierarchy;
import org.kuali.student.core.role.entity.QualifierType;
import org.kuali.student.core.role.service.QualifierHierarchyRoleTypeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 */
@WebService(endpointInterface = "org.kuali.student.core.role.service.QualifierHierarchyRoleTypeService", serviceName = "QHRoleTypeService", portName = "QHRoleTypeService", targetNamespace = "http://student.kuali.org/wsdl/qhRoleType")
@Transactional

//TODO: Implement this correctly using KimRoleTypeService
//public class QualifierHierarchyRoleTypeServiceImpl implements QualifierHierarchyRoleTypeService, KimRoleTypeService {
public class QualifierHierarchyRoleTypeServiceImpl implements QualifierHierarchyRoleTypeService{
    
    private QualifierHierarchyDAO qualifierHierarchyDAO;
    private String qualifierHierarchyName;
    
    public void setQualifierHierarchyDAO(QualifierHierarchyDAO qualifierHierarchyDAO){
        this.qualifierHierarchyDAO = qualifierHierarchyDAO;
    }
    
    public QualifierHierarchyDAO getQualifierHierarchyDAO(){
        return qualifierHierarchyDAO;
    }
    
    public void setQualifierHierarchy(String qualifierHierarchyName){
        this.qualifierHierarchyName = qualifierHierarchyName;
    }
    
    public String getQualifierHierarchy(){
        return qualifierHierarchyName;
    }
    
    /**
     * @see org.kuali.rice.kim.service.support.KimRoleTypeService#doesRoleQualifierMatchQualification(java.util.Map, java.util.Map)
     */
    @Override
    public boolean doesRoleQualifierMatchQualification(AttributeSet qualification, AttributeSet roleQualifier) {
        
        List<AttributeSet> impliedRoleQualifications = getAllImpliedQualifications(roleQualifier);
        
        for(AttributeSet impliedRoleQualifier : impliedRoleQualifications){
            if (qualification.equals(impliedRoleQualifier)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * @see org.kuali.rice.kim.service.support.KimRoleTypeService#doRoleQualifiersMatchQualification(java.util.Map, java.util.List)
     */
    @Override
    public boolean doRoleQualifiersMatchQualification(AttributeSet qualification, List<AttributeSet> roleQualifierList) {
        
        for(AttributeSet roleQualifier : roleQualifierList){
        	if(doesRoleQualifierMatchQualification(qualification, roleQualifier)){
        		return true;
        	}
        }
        return false;
    }
    
    /**
     * @see org.kuali.rice.kim.service.support.KimRoleTypeService#getMembersThatMatchQualification(java.util.Map, java.util.List)
     */
    public boolean isApplicationRoleType(){
        return false;
    }
    
    public List<String> getPrincipalIdsFromApplicationRole( String namespaceCode, String roleName, AttributeSet qualification ){
        return null;
    }
    
    public List<String> getGroupIdsFromApplicationRole( String namespaceCode, String roleName, AttributeSet qualification ){
    	return null;
    }
    
    /**
     * This method would return all qualifications that the given qualification implies. (down)
     * Assuming you draw your tree with root on top and leaves at the bottom, then down is
     * going from root to leaves.
     */
    public List<AttributeSet> getAllImpliedQualifications(AttributeSet qualification) {
        
        Qualifier foundQualifier = null;
        
        // first check if the qualification represents a composite qualifier or not, then find that qualifier
        if(qualification.size() > 1){
            // its composite
            foundQualifier = qualifierHierarchyDAO.findCompositeQualifierByType(qualification);
        } else if (qualification.size() == 1 ){
            // not composite, just one key-value pair
            for (String qualifierType : qualification.keySet()) {
                foundQualifier = qualifierHierarchyDAO.findQualifierByType(qualifierType, qualification.get(qualifierType));
            }
        }
        
        List<Qualifier> lowerQualifiers = qualifierHierarchyDAO.findQualifiersAtOrBelowQualifier(foundQualifier);
        List<AttributeSet> qualificationList = new ArrayList<AttributeSet>();
        
        // Go through each lower qualifier in the tree and convert them to an AttributeSet (aka a Map)
        for(Qualifier q : lowerQualifiers){
            AttributeSet as = new AttributeSet();
            
            // if it is a composite get the pk's that make it up and shove them into the attribute set
            if(q.getQualifierType().isComposite()){
                for(Qualifier pkQ : q.getPkQualifiers()){
                    as.put(pkQ.getQualifierType().getName(), pkQ.getName());
                }
            } else {
                as.put(q.getQualifierType().getName(), q.getName());
            }
            qualificationList.add(as);
        }
        return qualificationList;
    }

    /**
     * This method would return all qualifications that imply this qualification. (up)
     * Assuming you draw your tree with root on top and leaves at the bottom, then up
     * is going from leaves to root.
     */
    @Override
    public List<AttributeSet> getAllImplyingQualifications(AttributeSet qualification) {
        
        Qualifier foundQualifier = null;
        
        // first check if the qualification represents a composite qualifier or not, then find that qualifier
        if(qualification.size() > 1){
            // its composite
            foundQualifier = qualifierHierarchyDAO.findCompositeQualifierByType(qualification);
        } else if (qualification.size() == 1 ){
            // not composite, just one key-value pair
            for (String qualifierType : qualification.keySet()) {
                foundQualifier = qualifierHierarchyDAO.findQualifierByType(qualifierType, qualification.get(qualifierType));
            }
        }
        
        List<Qualifier> upperQualifiers = qualifierHierarchyDAO.findQualifiersAtOrAboveQualifier(foundQualifier);
        List<AttributeSet> qualificationList = new ArrayList<AttributeSet>();
        
        // Go through each upper qualifier in the tree and convert them to an AttributeSet (aka a Map)
        for(Qualifier q : upperQualifiers){
            AttributeSet as = new AttributeSet();

            // if it is a composite get the pk's that make it up and shove them into the attribute set
            if(q.getQualifierType().isComposite()){
                for(Qualifier pkQ : q.getPkQualifiers()){
                    as.put(pkQ.getQualifierType().getName(), pkQ.getName());
                }
            } else {
                as.put(q.getQualifierType().getName(), q.getName());
            }
            qualificationList.add(as);
        }
        return qualificationList;
    }
    
    /** 
     * Return a list of attribute names that will be accepted by this role type.  They
     * are either understood directly, or can be translated by this service into that
     * required. 
     */
    @Override
    public List<String> getAcceptedAttributeNames(){
        // returns a list of Qualifier types names associated with the qualifier hierarchy name.
        if(qualifierHierarchyDAO != null){
    	QualifierHierarchy qualifierHierarchy = qualifierHierarchyDAO.fetchQualifierHierarchyByName(qualifierHierarchyName);
    	if(qualifierHierarchy.getQualifierTypes() == null){
    		qualifierHierarchy.setQualifierTypes(new ArrayList<QualifierType>());
    	}
        List<QualifierType> lstQualifierTypes = qualifierHierarchy.getQualifierTypes();
        
        List<String> attributeNames = new ArrayList<String>();
        for(QualifierType qt : lstQualifierTypes){
            attributeNames.add(qt.getName());
        }
        
        return attributeNames;
        } else {return null;}
    }
    
    /**
     * Convert a set of attributes that need to be converted.  For example,
     * this method could take [chart=BL,org=PSY] and return [campus=BLOOMINGTON]
     * if this role was based on the campus and the role assigned to it was based 
     * on organization.
     */
    //@Override
    public AttributeSet convertQualificationAttributesToRequired(AttributeSet qualificationAttributes ){
        return null;
    }
    
    /**
     * All the methodes that follow come from the 
     * org.kuali.rice.kim.service.support.KimTypeService Interface.
     * Don't know if Kuali Student needs them
     */
    
    public String getWorkflowDocumentTypeName(){
    	return null;
    }
    
    public AttributeSet validateAttributes( AttributeSet attributes ){
    	return null;
    }
    
    public List<String> validateAttribute( String attributeName, String attributeValue ){
    	return null;
    }
    
    public String getLookupUrl( String attributeName ){
    	return null;
    }
    
    public String getInquiryUrl( String attributeName, AttributeSet relevantAttributeData ){
    	return null;
    }
    
    public boolean performMatch(final AttributeSet inputAttributeSet, final AttributeSet standardAttributeSet){
    	return false;
    }
    
    public boolean performMatches(final AttributeSet inputAttributeSet, final List<AttributeSet> standardAttributeSet){
    	return false;
    }
    
    public AttributeSet translateInputAttributeSet(final AttributeSet inputAttributeSet){
    	return null;
    }
    
    public boolean supportsAttributes( List<String> attributeNames ){
    	return false;
    }

    public AttributeSet getValidValues(String arg0) {
        return null;
    }
}
