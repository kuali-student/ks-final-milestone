/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.dictionary.model;

import java.util.List;

/**
 * Abstration of the spreadsheet that holds the data so we can implement both
 * google and excel versions as well as caching and expanding ones.
 * @author nwright
 */
public interface SearchModel
{

 public List<String> getSourceNames ();

 /**
  * get search types
  * @return
  */
 public List<SearchType> getSearchTypes ();

 

}
