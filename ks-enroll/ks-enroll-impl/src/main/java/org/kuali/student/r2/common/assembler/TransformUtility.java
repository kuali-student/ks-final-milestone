package org.kuali.student.r2.common.assembler;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.HasAttributes;

import java.util.*;

/**
 * Utility class containing utility methods to aide in DTO & Entity transformations
 */
public class TransformUtility {


    /**
     *
     * This merges DTO attributes into Entity attributes and returns the orphaned attributes to delete
     *
     * @param attributeClass The attribute entity class for the attributes on the entity owner
     * @param dto The dto to copy attributes from
     * @param owner The owner entity of the attributes.
     *
     * @return The orphaned attributes to delete.
     */
    public static <A extends BaseAttributeEntity<O>, O extends AttributeOwner<A>> List<Object>
    mergeToEntityAttributes(Class<A> attributeClass, HasAttributes dto, O owner) {


        // Existing Attributes
        Map<String, A> existingAttributes = new HashMap<String, A>();

        // Find all the old attributes and add to existing attributes map
        if(owner.getAttributes()!=null){
            for (A attribute : owner.getAttributes()) {
                existingAttributes.put(attribute.getKey(), attribute);
            }
        }

        //Clear out the attributes
        List<A> attributes = new ArrayList<A>();

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
        owner.setAttributes(attributes);

        //Remove the orphaned attributes
        List<Object> orphansToDelete = new ArrayList<Object>();
        orphansToDelete.addAll(existingAttributes.values());

        return orphansToDelete;
    }


    /**
     * Converts attributes from an entity to list of AttributeInfo objects for a DTO
     *
     * @param owner The entity containing the attributes
     * @return list of attributeInfo object
     */
    public static <A extends BaseAttributeEntity<O>, O extends AttributeOwner<A>> List<AttributeInfo> toAttributeInfoList(AttributeOwner<A> owner){
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        if (null != owner.getAttributes()){
            for (A attr : owner.getAttributes()) {
                AttributeInfo attrInfo = attr.toDto();
                attributes.add(attrInfo);
            }
       }

        return attributes;
    }
}
