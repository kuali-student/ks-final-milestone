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
package org.kuali.student.ui.kitchensink.client.kscommons.suggestboxpicker;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.common.ui.client.widgets.list.testData.Color;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class ColorDTOs {
    private List<Color> colors = new ArrayList<Color>(); 
    private List<Color> emptyColors = new ArrayList<Color>(); 


    public ColorDTOs() {
        try {
            initPersons();
            emptyColors.add(new Color("","","",""));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initPersons() {
        colors.add(new Color("1", "Blue", "Cool", "Primary"));
        colors.add(new Color("2", "Red", "Warm", "Primary"));
        colors.add(new Color("3", "Orange", "Warm", "Secondary"));
        colors.add(new Color("4", "Yellow", "Warm", "Primary"));
        colors.add(new Color("5", "Green", "Cool", "Secondary"));
        colors.add(new Color("6", "Purple", "Cool", "Secondary"));
        colors.add(new Color("7", "Black", "Neutral", "None"));
    }

    /**
     * @return the persons
     */
    public List<Color> getColors() {
        return colors;
    }
    
    public List<Color> getEmptyColors() {
        return emptyColors;
    }

}
