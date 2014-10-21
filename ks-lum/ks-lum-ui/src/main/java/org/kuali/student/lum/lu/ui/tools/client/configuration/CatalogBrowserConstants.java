/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.ui.tools.client.configuration;


/**
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class CatalogBrowserConstants
{
// metadata
 public static final String COURSE_CATALOG = "courseCatalog";
 public static final String BY_SUBJECT_AREA = "bySubjectArea";
	public static final String BY_SCHOOL_OR_COLLEGE = "bySchoolOrCollege";
 public static final String COURSE_ID = "courseId";
 public static final String FULLY_QUALIFIED_BY_SUBJECT_AREA = 
   COURSE_CATALOG + "/" + BY_SUBJECT_AREA + "/" + COURSE_ID;
 public static final String FULLY_QUALIFIED_BY_SCHOOL_OR_COLLEGE =
   COURSE_CATALOG + "/" + BY_SCHOOL_OR_COLLEGE + "/" + COURSE_ID;

	// Message keys for top-level section label lookup
	public static final String BROWSE_BY_SUBJECT_AREA_LABEL_KEY =	"browseBySubjectArea";
	public static final String BROWSE_BY_SCHOOL_LABEL_KEY =			"browseBySchool"; 
	public static final String BROWSE_BY_SUBJECT_AREA =				"Browse Catalog by Subject Area";
	public static final String BROWSE_BY_SUBJECT_AREA_INFO =		"Subject Area Information";
	public static final String BROWSE_BY_SCHOOL = 					"Browse Catalog by School";
	public static final String BROWSE_BY_SCHOOL_INFO = 				"School Information";

    // Message keys for breadcrumb label lookup
    public static final String COURSE_CATALOG_BREADCRUMB = "courseCatalogBreadcrumb";
}

