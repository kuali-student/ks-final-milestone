/**
 * 
 */
package org.kuali.student.rules.lumgui.server.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.lumgui.client.GregorianCalendar;
import org.kuali.student.rules.lumgui.client.service.LumGuiService;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.internal.common.entity.RuleElementType;

/**
 * @author zzraly
 */
public class LumGuiServiceImpl implements LumGuiService {

    FactFinderService factFinderService;

    
    
    /******************************************************************************************************************
     * 
     *                                                     GETTERS & SETTERS 
     *
     *******************************************************************************************************************/         
    
    public FactFinderService getFactFinderService() {
		return factFinderService;
	}

	public void setFactFinderService(FactFinderService factFinderService) {
		this.factFinderService = factFinderService;
	}    
}