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

/**
 * A small object to hold rich text along with plain formatting that can be
 * more easily searched.
 *
 * @author Kuali Student Team (Kamal)
 *
 */
public interface RichText {

    /**
     * Plain text version of the rich text with all the special formatting 
     * stripped out.
     *
     * @name Plan Text
     */
    public String getPlain();
    

    /**
     * Formatted version of the rich text with all the formatting included.
     *
     * TODO: Define what format is to be used? Is it assumed to be HTML?  or WORD?
     * or Wiki?
     * @name Formatted Text
     */
    public String getFormatted();
    
}

