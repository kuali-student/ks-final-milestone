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
package org.kuali.student.enrollment.lpr.mock;

import org.kuali.student.common.infc.*;
import org.kuali.student.enrollment.lpr.infc.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.kuali.student.common.dto.AttributeInfo;
import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.infc.LuiInfc;
import org.kuali.student.enrollment.lui.infc.LuiLuiRelationInfc;

/**
 * A helper class for the Mock implementation
 * @author nwright
 */
public class CopierHelper {

 public MetaInfc makeCopy(MetaInfc orig) {
  if (orig == null) {
   return null;
  }
  MetaInfc copy = new MetaInfo();
  BeanUtils.copyProperties(orig, copy);
  return copy;
 }

 public LuiPersonRelationTypeInfc makeCopy(LuiPersonRelationTypeInfc orig) {
  if (orig == null) {
   return null;
  }
  LuiPersonRelationTypeInfc copy = new LuiPersonRelationTypeInfo();
  BeanUtils.copyProperties(orig, copy);
  copy.setAttributes(makeCopy(orig.getAttributes()));
  return copy;
 }

 public LuiPersonRelationStateInfc makeCopy(LuiPersonRelationStateInfc orig) {
  if (orig == null) {
   return null;
  }
  LuiPersonRelationStateInfc copy = new LuiPersonRelationStateInfo();
  BeanUtils.copyProperties(orig, copy);
  copy.setAttributes(makeCopy(orig.getAttributes()));
  return copy;
 }

 public AttributeInfc makeCopy(AttributeInfc orig) {
  if (orig == null) {
   return null;
  }
  AttributeInfc copy = new AttributeInfo();
  BeanUtils.copyProperties(orig, copy);
  return copy;
 }

 public List<AttributeInfc> makeCopy(List<? extends AttributeInfc> orig) {
  if (orig == null) {
   return null;
  }
  List<AttributeInfc> copy = new ArrayList();
  for (AttributeInfc attr : orig) {
   copy.add(this.makeCopy(attr));
  }
  return copy;
 }

 public LuiPersonRelationInfc makeCopy(LuiPersonRelationInfc orig) {
  if (orig == null) {
   return null;
  }
  LuiPersonRelationInfc copy = new LuiPersonRelationInfo();
  BeanUtils.copyProperties(orig, copy);
  copy.setAttributes(this.makeCopy(orig.getAttributes()));
  copy.setMetaInfo(this.makeCopy(orig.getMetaInfo()));
  return copy;
 }

  public LuiInfc makeCopy(LuiInfc orig) {
  if (orig == null) {
   return null;
  }
  LuiInfc copy = new LuiInfo();
  BeanUtils.copyProperties(orig, copy);
  copy.setAttributes(this.makeCopy(orig.getAttributes()));
  copy.setMetaInfo(this.makeCopy(orig.getMetaInfo()));
  return copy;
 }


  public LuiLuiRelationInfc makeCopy(LuiLuiRelationInfc orig) {
  if (orig == null) {
   return null;
  }
  LuiLuiRelationInfc copy = new LuiLuiRelationInfo();
  BeanUtils.copyProperties(orig, copy);
  copy.setAttributes(this.makeCopy(orig.getAttributes()));
  copy.setMetaInfo(this.makeCopy(orig.getMetaInfo()));
  return copy;
 }

 public MetaInfc createMeta(ContextInfc context) {
  MetaInfc meta = new MetaInfo();
  Date now = new Date();
  meta.setCreateId(context.getPrincipalId());
  meta.setCreateTime(now);
  meta.setUpdateId(context.getPrincipalId());
  meta.setUpdateTime(now);
  meta.setVersionInd("1");
  return meta;
 }

 public MetaInfc updateMeta(MetaInfc orig, ContextInfc context) {
  MetaInfc meta = makeCopy(orig);
  Date now = new Date();
  meta.setUpdateId(context.getPrincipalId());
  meta.setUpdateTime(now);
  int oldVersionInd = Integer.parseInt(meta.getVersionInd());
  meta.setVersionInd("" + (oldVersionInd + 1));
  return meta;
 }
}

