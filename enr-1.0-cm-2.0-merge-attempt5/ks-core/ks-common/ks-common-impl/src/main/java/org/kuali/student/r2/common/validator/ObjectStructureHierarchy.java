package org.kuali.student.r2.common.validator;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/10/31
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectStructureHierarchy {

    private ObjectStructureDefinition objectStructure;

    private ObjectStructureHierarchy parentObjectStructureHierarchy;

    public ObjectStructureDefinition getObjectStructure() {
        return objectStructure;
    }

    public void setObjectStructure(ObjectStructureDefinition objectStructure) {
        this.objectStructure = objectStructure;
    }

    public ObjectStructureHierarchy getParentObjectStructureHierarchy() {
        return parentObjectStructureHierarchy;
    }

    public void setParentObjectStructureHierarchy(ObjectStructureHierarchy parentObjectStructureHierarchy) {
        this.parentObjectStructureHierarchy = parentObjectStructureHierarchy;
    }

    public ObjectStructureDefinition getParentObjectStructure() {
        if (parentObjectStructureHierarchy == null){
            return null;
        }

        return parentObjectStructureHierarchy.getObjectStructure();
    }

    public ObjectStructureDefinition getRootObjectStructure() {
        ObjectStructureHierarchy rootObjectStructure = this;

        while (rootObjectStructure.getParentObjectStructureHierarchy() != null){
            rootObjectStructure = rootObjectStructure.getParentObjectStructureHierarchy();
        }

        return rootObjectStructure.getObjectStructure();
    }
}
