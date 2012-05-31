package org.kuali.student.r2.common.assembler;

import org.kuali.student.r2.common.entity.AttributeOwnerNew;
import org.kuali.student.r2.common.entity.BaseAttributeEntityNew;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.HasAttributes;

import java.util.*;

/**
 * Utility class containing utility methods to aide in DTO & Entity transformations
 */
public class TransformUtility {


    /**
     *
     * This converts DTO attributes to entity attributes and updates the orphansToDelete method with orphan attributes
     * to delete.
     *
     * @param attributeClass The attribute entity class for the attributes on the entity owner
     * @param dto The dto to copy attributes from
     * @param owner The owner entity of the attributes.
     * @param orphansToDelete The orphaned attributes to delete
     *
     * @return The attributes to set on the entity
     */
    public static <A extends BaseAttributeEntityNew<O>, O extends AttributeOwnerNew<A>> Set<A>
        toEntityAttributes(Class<A> attributeClass, HasAttributes dto, O owner, List<Object> orphansToDelete) {

        Set<A> attributes = new HashSet<A>();

        if(owner.getAttributes()==null){
            owner.setAttributes(new HashSet<A>());
        }

        // Existing Attributes
        Map<String, A> existingAttributes = new HashMap<String, A>();

        // Find all the old attributes
        for (A attribute : owner.getAttributes()) {
            existingAttributes.put(attribute.getKey(), attribute);
        }

        //Clear out the attributes
        owner.getAttributes().clear();

        //Update anything that exists, or create a new attribute if it doesn't
        for (Attribute attributeInfo: dto.getAttributes()) {
            A attribute;
            if (existingAttributes.containsKey(attributeInfo.getKey())) {
                attribute = existingAttributes.remove(attributeInfo.getKey());
            }else{
                try{
                    attribute = attributeClass.newInstance();
                }catch(Exception e){
                    throw new RuntimeException("Error copying attributes.",e);
                }

                attribute.setOwner(owner);
                attribute.setKey(attributeInfo.getKey());
            }
            attribute.setValue(attributeInfo.getValue());
            attributes.add(attribute);
        }

        //Remove the orphaned attributes
        orphansToDelete.addAll(existingAttributes.values());

        return attributes;
    }

}
