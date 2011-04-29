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

public interface ValidationResult {

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
     * Name: Message explaining this validation result
     *
     * If an error it is an an error message.
     *
     * TODO: decide if this is a key that then gets resolved into a real localized message using the message service or the final localized message itself
     */
    public String getMessage();

    /**
     * Name: Element
     *
     * Identifies the element (field) that is the focus of the validation.
     * Uses xpath (dot) notation to navigate to the field, for example:
     * officialIdentifier.code
     *
     * TODO: find out how repeating substructures are handled in this notation, with [n] occurrence brackets?
     */
    public String getElement();

    /**
     * Name: Level
     *
     * Indicates the serverity of the validation error
     *
     * 0=OK
     * 1=WARN
     * 2=ERROR
     */
    public Integer getLevel();

    /**
     * Name: Invalid Data
     *
     * The actual data that caused the error or warning.  Used to help debug problems.
     *
     * Note: Since this is an "Object" it should be flagged as transient and os not remoted through the web servce
     */
    public Object getInvalidData();

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
     */
    public boolean isOk();

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
     */
    public boolean isWarn();

    /**
     * Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
     */
    public boolean isError();
}

