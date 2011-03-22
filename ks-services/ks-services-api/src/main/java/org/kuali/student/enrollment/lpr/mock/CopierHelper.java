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
  return new MetaInfo.Builder(orig).build();
 }

 public LuiPersonRelationTypeInfc makeCopy(LuiPersonRelationTypeInfc orig) {
  if (orig == null) {
   return null;
  }
  LuiPersonRelationTypeInfo.Builder builder = new LuiPersonRelationTypeInfo.Builder(orig);
  
  return builder.build();
 }

 public LuiPersonRelationStateInfc makeCopy(LuiPersonRelationStateInfc orig) {
  if (orig == null) {
   return null;
  }
  return new LuiPersonRelationStateInfo.Builder(orig).build();
 }

 public AttributeInfc makeCopy(AttributeInfc orig) {
  if (orig == null) {
   return null;
  }
  return new AttributeInfo.Builder(orig).build();
 }

 public List<AttributeInfc> makeCopy(List<? extends AttributeInfc> orig) {
  if (orig == null) {
   return null;
  }
  List<AttributeInfc> copy = new ArrayList<AttributeInfc>();
  for (AttributeInfc attr : orig) {
   copy.add(this.makeCopy(attr));
  }
  return copy;
 }

 public LuiPersonRelationInfc makeCopy(LuiPersonRelationInfc orig) {
  if (orig == null) {
   return null;
  }
  return new LuiPersonRelationInfo.Builder(orig).setAttributes(this.makeCopy(orig.getAttributes())).setMetaInfo(this.makeCopy(orig.getMetaInfo())).build();
 }

  public LuiInfc makeCopy(LuiInfc orig) {
  if (orig == null) {
   return null;
  }
  return(new LuiInfo.Builder(orig).build());
 }


  public LuiLuiRelationInfc makeCopy(LuiLuiRelationInfc orig) {
  if (orig == null) {
   return null;
  }
  return new LuiLuiRelationInfo.Builder(orig).build();
 }

 public MetaInfc createMeta(ContextInfc context) {
	MetaInfo.Builder builder = new MetaInfo.Builder();
	Date now = new Date();
	
	builder.setCreateId(context.getPrincipalId()).setCreateTime(now).setUpdateId(context.getPrincipalId());
	return builder.setUpdateTime(now).setVersionInd("1").build();
 }

 public MetaInfc updateMeta(MetaInfc orig, ContextInfc context) {
	Date now = new Date();
	int oldVersionInd = Integer.parseInt(orig.getVersionInd());
	return new MetaInfo.Builder(orig).setUpdateId(context.getPrincipalId()).setUpdateTime(now).setVersionInd("" + (oldVersionInd + 1)).build();
 }
}

