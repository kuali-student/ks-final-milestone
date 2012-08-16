package org.kuali.student.r2.core.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.dao.CrudDao;
import org.kuali.student.r1.common.entity.Attribute;
import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.Meta;
import org.kuali.student.r1.common.entity.Type;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.springframework.beans.BeanUtils;

/**
 * 
 * This is a temporary helper classes to copy r1 xyztypeinfo to r2 typeinfo objects.
 * 
 * Should be removed once all services are r2 compatible.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class AssemblerHelper {
    
    final static Logger logger = Logger.getLogger(AssemblerHelper.class);

    public static List<AttributeInfo> toAttributeList(List<? extends Attribute<?>> attributes) {
        
        List<AttributeInfo> attributeInfos = new ArrayList<AttributeInfo>();

        for (Attribute<?> attribute : attributes) {
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.setKey(attribute.getName());
            attributeInfo.setValue(attribute.getValue());
            attributeInfos.add(attributeInfo);
        }
        
        return attributeInfos;
    }

    public static <S extends Type<?>> List<TypeInfo> toGenericTypeInfoList(List<S> typeEntities) {
        List<TypeInfo> typeInfoList = new ArrayList<TypeInfo>();
        if(typeEntities!=null){
            for (S typeEntity : typeEntities) {
                typeInfoList.add(toGenericTypeInfo(typeEntity));
            }
        }
        return typeInfoList;
    }
    
    public static <S extends Type<?>> TypeInfo toGenericTypeInfo(S typeEntity) {
        if (typeEntity == null) {
            return null;
        }
        
        TypeInfo typeInfo;
        try {
            // Create a new TypeInfo based on the <T> class and copy the
            // properties
            typeInfo = new TypeInfo();
            BeanUtils.copyProperties(typeEntity, typeInfo,
                                     new String[] { "attributes", "descr" });
            
            typeInfo.setKey(typeEntity.getId());
            
            // Copy the attributes
            typeInfo.setAttributes(toAttributeList(typeEntity.getAttributes()));
            
            //Copy the description
            RichTextInfo richText = new RichTextInfo();
            richText.setFormatted(typeEntity.getDescr());
            richText.setPlain(typeEntity.getDescr());
            typeInfo.setDescr(richText);
            
            return typeInfo;

        } catch (Exception e) {
            logger.error("Exception occured: ", e);
        }

        return null;
    }
    
    public static MetaInfo toMetaInfo(Meta meta, Long versionInd) {
        
        MetaInfo metaInfo = new MetaInfo();
        // If there was a meta passed in then copy the values
        if (meta != null) {
            BeanUtils.copyProperties(meta, metaInfo);
        }
        if (versionInd==null) {
            metaInfo.setVersionInd(null);
        } else {
            metaInfo.setVersionInd(versionInd.toString());
        }
        
        return metaInfo;
    }
    
    public static <A extends Attribute<O>, O extends AttributeOwner<A>> List<A> toGenericAttributes(Class<A> attributeClass, List<AttributeInfo> attributeList, O owner, CrudDao dao) throws InvalidParameterException {
        
        List<A> attributes = new ArrayList<A>();
        
        if (owner.getAttributes()==null) {
            owner.setAttributes(new ArrayList<A>());
        }
        
        Map<String, A> currentAttributes = new HashMap<String,A>();
        
        // Find all the old attributes(if the owner is not null)
        for (A attribute : owner.getAttributes()) {
            currentAttributes.put(attribute.getName(), attribute);
        }
        
        //Clear out the attributes
        owner.getAttributes().clear();
        
        if (attributeList==null) {
            return attributes;
        }
        
        //Update anything that exists, or create a new attribute if it doesn't
        for (AttributeInfo attributeEntry : attributeList) {
            A attribute;
            if (currentAttributes.containsKey(attributeEntry.getKey())) {
                attribute = currentAttributes.remove(attributeEntry.getKey());
            } else {
                try {
                    attribute = attributeClass.newInstance();
                } catch(Exception e) {
                    throw new RuntimeException("Error copying attributes.",e);
                }
                attribute.setName(attributeEntry.getKey());
                attribute.setOwner(owner);
            }
            attribute.setValue(attributeEntry.getValue());
            attributes.add(attribute);
        }
        
        //Delete leftovers here if behavior is desired
        
        return attributes;
    }
}
