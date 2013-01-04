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

package org.kuali.student.common.ui.server.serialization;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class SerializationUtils {
    // Server Side String utils
    public static String escapeForSingleQuotedJavaScriptString( String s ) 
    {
        s = escapeScriptTags( s ); // <script> -> <xxxscript>
        s = escapeBackslash( s );
        s = escapeSingleQuotes( s );
        return s;
    }
    public static String escapeScriptTags( String s )
    {
        return s
            .replaceAll("(?si)<\\s*script.*?>", "<xxxscript>")
            .replaceAll("(?si)</\\s*script\\s*>", "</xxxscript>");
    }
    public static String escapeBackslash( String s )
    {
        return s.replaceAll("\\\\","\\\\\\\\" );
    }
    public static String escapeSingleQuotes( String s ) {
        return s.replaceAll("'","\\\\'" );
    }

}
