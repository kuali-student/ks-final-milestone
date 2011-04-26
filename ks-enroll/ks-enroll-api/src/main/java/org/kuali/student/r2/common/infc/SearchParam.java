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
package org.kuali.student.r2.common.infc;

import java.util.List;

/**
 * Search Parameter Info
 *
 * A structure that holds a key value pair as a way to supply a parameter
 * to a search.
 *
 * @author nwright
 */
public interface SearchParam {


    /**
     * Name: Search Parameter Key
     *
     * The key that identifies the name of the search parameter
     */
    public String getKey();

    /**
     * Name: Values
     * 
     * The value(s) of the search parameter
     *
     * Most parameters take only a single value but the list is available for
     * operations such as "IN" that take a list of values.
     */
    public List<String> getValues();
}

