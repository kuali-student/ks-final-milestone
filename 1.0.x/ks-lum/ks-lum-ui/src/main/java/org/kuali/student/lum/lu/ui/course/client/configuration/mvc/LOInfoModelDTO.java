/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.lum.lo.dto.LoInfo;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class LOInfoModelDTO extends ModelDTO {
    
    private static final long serialVersionUID = 1L;
    public static final String DTO_KEY = "loInfo";
    static ModelDTOValue.ModelDTOType createLOInfoModelDTOValue(String dtoKey) {
        ModelDTO loInfoModelDTO = new ModelDTO(LoInfo.class.getName());
        loInfoModelDTO.setKey(dtoKey);
        ModelDTOValue.ModelDTOType loInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
        loInfoModelDTOValue.set(loInfoModelDTO);
        return loInfoModelDTOValue;
    }
    public LOInfoModelDTO() {
        ModelDTOValue.ModelDTOType loInfoModelDTOValue = createLOInfoModelDTOValue(DTO_KEY);        
        map.put(DTO_KEY, loInfoModelDTOValue);
    }
}
