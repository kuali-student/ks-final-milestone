/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lui.infc;


//import com.sun.xml.internal.bind.AnyTypeAdapter;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.kuali.student.enrollment.common.infc.AttributeInfc;
import org.kuali.student.enrollment.common.infc.MetaInfc;

@XmlRootElement
//@XmlJavaTypeAdapter(AnyTypeAdapter.class)
@XmlAccessorType(XmlAccessType.PROPERTY)
public interface LuiPersonRelationInfc
{
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Name: LUI 
	* Unique identifier for a Learning Unit Instance (LUI).
	*/
	public void setLuiId(String luiId);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Name: LUI 
	* Unique identifier for a Learning Unit Instance (LUI).
	*/
	public String getLuiId();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Name: Person 
	* Unique identifier for a person record.
	*/
	public void setPersonId(String personId);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Name: Person 
	* Unique identifier for a person record.
	*/
	public String getPersonId();
	
	
	
	/**
	* Set ????
	*
	* Type: Date
	*
	* Name:Effective 
	* Date/time this relationship became effective. Must be less than or equal to the 
	* expirationDate specified.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get ????
	*
	* Type: Date
	*
	* Name:Effective 
	* Date/time this relationship became effective. Must be less than or equal to the 
	* expirationDate specified.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set ????
	*
	* Type: Date
	*
	* Name: Expiration 
	* Date/time this relationship is no longer effective. Must be greater than or 
	* equal to the effectiveDate specified.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get ????
	*
	* Type: Date
	*
	* Name: Expiration 
	* Date/time this relationship is no longer effective. Must be greater than or 
	* equal to the effectiveDate specified.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set ????
	*
	* Type:
	*
	* Name: Generic/dynamic 
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public void setAttributes(List<AttributeInfc> attributes);
	
	/**
	* Get ????
	*
	* Type: List<AttributeInfc>
	*
	* Name: Generic/dynamic 
	* List of key/value pairs, typically used for dynamic attributes.
	*/
	public List<AttributeInfc> getAttributes();
	
	
	
	/**
	* Set ????
	*
	* Type: MetaInfo
	*
	* Name: Create/Update meta 
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public void setMetaInfo(MetaInfc metaInfo);
	
	/**
	* Get ????
	*
	* Type: MetaInfo
	*
	* Name: Create/Update meta 
	* Create and last update info for the structure. This is optional and treated as 
	* read only since the data is set by the internals of the service during 
	* maintenance operations.
	*/
	public MetaInfc getMetaInfo();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Name: LUI Person Relation 
	* Unique identifier for the type of LUI to Person relation.
	*/
	public void setType(String type);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Name: LUI Person Relation 
	* Unique identifier for the type of LUI to Person relation.
	*/
	public String getType();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Name: Relation 
	* Unique identifier for the state of the relationship between a LUI and person.
	*/
	public void setState(String state);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Name: Relation 
	* Unique identifier for the state of the relationship between a LUI and person.
	*/
	public String getState();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* Name: LUI Person Relation 
	* Unique identifier for the LUI to Person relation. This is optional, due to the 
	* identifier being set at the time of creation. Once the relation has been 
	* created, this should be seen as required.
	*/
	public void setId(String id);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* Name: LUI Person Relation 
	* Unique identifier for the LUI to Person relation. This is optional, due to the 
	* identifier being set at the time of creation. Once the relation has been 
	* created, this should be seen as required.
	*/
	public String getId();
	
	
	
}

