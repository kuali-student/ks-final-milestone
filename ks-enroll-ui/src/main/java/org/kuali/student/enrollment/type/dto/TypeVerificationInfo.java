/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by cmuller on 8/24/12
 */
package org.kuali.student.enrollment.type.dto;

import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class TypeVerificationInfo extends UifFormBase {
    private List<TypeVerificationBasics> equivalences;
    private List<TypeVerificationBasics> notInJava;
    private List<TypeVerificationBasics> notInDB;

    public TypeVerificationInfo(){
        equivalences = new ArrayList<TypeVerificationBasics>();
        notInJava = new ArrayList<TypeVerificationBasics>();
        notInDB = new ArrayList<TypeVerificationBasics>();
    }

    public List<TypeVerificationBasics> getEquivalences() {
        return equivalences;
    }

    public void setEquivalences(List<TypeVerificationBasics> equivalences) {
        this.equivalences = equivalences;
    }

    public List<TypeVerificationBasics> getNotInJava() {
        return notInJava;
    }

    public void setNotInJava(List<TypeVerificationBasics> notInJava) {
        this.notInJava = notInJava;
    }

    public List<TypeVerificationBasics> getNotInDB() {
        return notInDB;
    }

    public void setNotInDB(List<TypeVerificationBasics> notInDB) {
        this.notInDB = notInDB;
    }

}
