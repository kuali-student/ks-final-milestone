/*
 * Copyright 2012 The Kuali Foundation
 *
 * Licensed under the the Educational Community License, Version 1.0
 * (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.spring;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Kuali Student Team
 */
public abstract class AbstractFakeService implements Serializable {
	
		// this reference is here so that when we serialize this class the file size will be large
		// it will let us see the benefit of using the proxy
		protected LinkedList<String> bigData = new LinkedList<String>();

		/**
		 * 
		 */
        public AbstractFakeService() {
	        super();
	        
	        for (int i = 0; i < 236598; i++) {
	            bigData.add( String.valueOf(i));
            }
        }

		
}
