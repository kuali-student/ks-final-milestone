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
package org.kuali.student.core.role.dao;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.role.entity.Qualifier;
import org.kuali.student.core.role.entity.QualifierHierarchy;
import org.kuali.student.core.role.entity.QualifierType;

/**
 * This is a description of what this class does - Rich don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public interface QualifierHierarchyDAO {
    
    public String createQualifier(Qualifier q);

    public String createQualifierType(QualifierType qualifierType);

    public String createQualifierHierarchy(QualifierHierarchy qualifierHierarchy);
    
    public String createQualifierTree(Qualifier root);

    public Qualifier updateQualifierTree(Qualifier root);

    /**
     * This method deletes a node from a tree and returns the tree minus the node.
     * 
     * @param node
     * @return the tree minus the node, or null if the tree is a standalone
     */
    public Qualifier deleteQualifierNode(Qualifier node);

    public void deleteQualifierTree(Qualifier root);

    // public List<Principal> findPrincipalsAtQualifier(Qualifier qualifier, Role role);

    // public List<Principal> findPrincipalsAtOrAboveQualifier(Qualifier qualifier, Role role);

    public Qualifier findQualifierByType(String qualifierType, String qualifierName);

    public Qualifier findCompositeQualifierByType(Map<String, String> qualifications);

    public Qualifier fetchQualifier(String qualifierId);

    // public List<String> getPrincipalIdsWithQualifiedRole(String roleId, Map<String, String> qualifications);

    public List<Qualifier> findRootQualifiers(String qhId);

    public List<Qualifier> findQualifiersAtOrAboveQualifier(Qualifier qualifier);
    
    public List<Qualifier> findQualifiersAtOrBelowQualifier(Qualifier qualifier);
    
    public List<Qualifier> findQualifierDirectDescendents(String qid, String qhId);

    public QualifierType fetchQualifierType(String id);

    public List<QualifierType> findQualifierTypes();

    public List<QualifierHierarchy> findQualifierHierarchies();
    
    public QualifierHierarchy fetchQualifierHierarchyByName(String name);

    public QualifierHierarchy fetchQualifierHierarchy(String id);

    /*
     * public List<Principal> findPrincipalsAtOrAboveQualifierWithPermissions( String qualifierId, String permissionId);
     */

    void updateNestingForQualifier(Qualifier q);
}
