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
package org.kuali.student.common.infc;

public interface ValidationResultInfc {

    public enum ErrorLevel {

        OK(0), WARN(1), ERROR(2);
        int level;

        private ErrorLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        public static ErrorLevel min(ErrorLevel e1, ErrorLevel e2) {
            return e1.ordinal() < e2.ordinal() ? e1 : e2;
        }

        public static ErrorLevel max(ErrorLevel e1, ErrorLevel e2) {
            return e1.ordinal() > e2.ordinal() ? e1 : e2;
        }
    }


    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getMessage();

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * ???
     */
    public String getElement();

    /**
     * Get ????
     * <p/>
     * Type: Integer
     * <p/>
     * Returns the ValidationResult's error level
     */
    public int getLevel();

    /**
     * Get ????
     * <p/>
     * Type: boolean
     * <p/>
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
     */
    public boolean isOk();

    /**
     * Get ????
     * <p/>
     * Type: boolean
     * <p/>
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
     */
    public boolean isWarn();

    /**
     * Get ????
     * <p/>
     * Type: boolean
     * <p/>
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
     */
    public boolean isError();
}

