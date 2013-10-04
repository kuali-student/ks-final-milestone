/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.krms.dto;

import java.io.Serializable;

/**
 * This is a lightweight wrapper for Core Clu Information used in the KRMS UI.
 *
 * @author Kuali Student Team
 */
public class CluCore implements Serializable, Comparable<CluCore> {

    private String code;
    private String shortName;
    private String credits;

    public CluCore(){
        super();
    }

    public CluCore(String code, String shortName, String credits){
        this.code = code;
        this.shortName = shortName;
        this.credits = credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int compareTo(CluCore clu) {
        return this.code.compareTo(clu.getCode());
    }

    public void clear() {
        this.code = null;
        this.shortName = null;
        this.credits = null;
    }

}
