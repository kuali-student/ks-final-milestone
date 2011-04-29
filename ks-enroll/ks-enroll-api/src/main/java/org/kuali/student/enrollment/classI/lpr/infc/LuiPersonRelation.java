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
package org.kuali.student.enrollment.classI.lpr.infc;

//import com.sun.xml.internal.bind.AnyTypeAdapter;
import org.kuali.student.r2.common.infc.Relationship;

/**
 * Detailed information about a single LUI to Person Relation.
 * 
 * This is used to link together a learning unit instance and a person in such widely defined
 * domains as a student registering in a course or an instructor being assigned
 * to advise students in a particular program.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue Mar 01 15:53:57 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/luiPersonRelationInfo+Structure">LuiPersonRelationInfo</a>
 */
public interface LuiPersonRelation
        extends Relationship {

 /**
  * Name: LUI Id
  * Unique identifier for a Learning Unit Instance (LUI).
  */
 public String getLuiId();

 /**
  * Name: Person Id
  * Unique identifier for a person record.
  */
 public String getPersonId();
}

