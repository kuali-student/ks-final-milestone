package org.kuali.student.enrollment.lpr.model.usinginfc;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import org.kuali.student.common.infc.AttributeBean;
import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.MetaInfc;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelationEntity implements LuiPersonRelationInfc, Serializable {

 private static final long serialVersionUID = -8711908979901134064L;
 @Id
 @GeneratedValue
 private Long id;
 private String personId;
 private String luiId;
 @Temporal(TemporalType.DATE)
 private Date effectiveDate;
 @Temporal(TemporalType.DATE)
 private Date expirationDate;
 @OneToMany
 @JoinColumn(name = "lui_person_relation_id")
 private List<AttributeEntity> dynamicAttributes;
 private String type;
 private String state;

 public Long getLprId() {
  return id;
 }

 public void setLprId(Long id) {
  this.id = id;
 }

 @Override
 public String getId() {
  return "" + id;
 }

 @Override
 public void setId(String id) {
  this.id = Long.parseLong(id);
 }

 @Override
 public String getPersonId() {
  return personId;
 }

 @Override
 public void setPersonId(String personId) {
  this.personId = personId;
 }

 @Override
 public String getLuiId() {
  return luiId;
 }

 @Override
 public void setLuiId(String luiId) {
  this.luiId = luiId;
 }

 @Override
 public Date getEffectiveDate() {
  return effectiveDate;
 }

 @Override
 public void setEffectiveDate(Date effectiveDate) {
  this.effectiveDate = effectiveDate;
 }

 @Override
 public Date getExpirationDate() {
  return expirationDate;
 }

 @Override
 public void setExpirationDate(Date expirationDate) {
  this.expirationDate = expirationDate;
 }

 @Override
 public String getType() {
  return type;
 }

 @Override
 public void setType(String type) {
  this.type = type;
 }

 @Override
 public String getState() {
  return this.state;
 }

 @Override
 public void setState(String state) {
  this.state = state;
 }

 @Override
 public List<AttributeInfc> getAttributes() {
  if (this.dynamicAttributes == null)
  {
   return null;
  }
  List<AttributeInfc> list = new ArrayList (dynamicAttributes.size());
  for (AttributeEntity dae : this.dynamicAttributes)
  {
   AttributeInfc da = new AttributeBean ();
   da.setKey(dae.getKey());
   da.setValue(dae.getValue());
  }
  return list;
 }

 @Override
 public void setAttributes(List<AttributeInfc> attributes) {
  // TODO: write match to existing AttributeEntity updating as needed, removeing as needed and adding as needed
  // TODO: add an ID to the AttributeInfc interface to make the matching easier
 }

 @Override
 public MetaInfc getMetaInfo() {
  throw new UnsupportedOperationException("Not supported yet.");
 }

 @Override
 public void setMetaInfo(MetaInfc metaInfo) {
  throw new UnsupportedOperationException("Not supported yet.");
 }




}
