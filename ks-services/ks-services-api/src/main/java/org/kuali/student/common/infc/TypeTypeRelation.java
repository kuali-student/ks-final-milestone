/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may  obtain a copy of the License at
 *
 *  http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.infc;



public interface TypeTypeRelation extends KeyEntity, HasEffectiveDates {



 /**
  * Name: Owner Type Key
  *
  * The key for the type that is the controlling or "main" type in this type-type relationship.
  */
 public String getOwnerTypeKey ();

 /**
  * Name: Related Type Key
  *
  * The key for the type that is the controlled or "secondary" type in this type-type relationhip.
  */
 public String getRelatedTypeKey ();
 
 /**
  * Name: Rank
  *
  * The rank or ordering of this relationship as compared to other relationships of the same type and same owner type.
  */    
 public Integer getRank();
}

