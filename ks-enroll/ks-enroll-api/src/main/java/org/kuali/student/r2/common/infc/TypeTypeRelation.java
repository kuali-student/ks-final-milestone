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
package org.kuali.student.r2.common.infc;



public interface TypeTypeRelation extends KeyEntity, HasEffectiveDates {



 /**
  * The key for the type that is the controlling or "main" type in this type-type relationship.
  * @namne Owner Type Key
  */
 public String getOwnerTypeKey ();

 /**
  * The key for the type that is the controlled or "secondary" type in this type-type relationship.
  * @name Related Type Key
  */
 public String getRelatedTypeKey ();
 
 /**
  * The rank or ordering of this relationship as compared to other relationships of the same type and same owner type.
  * @name Rank
  */    
 public Integer getRank();
}

