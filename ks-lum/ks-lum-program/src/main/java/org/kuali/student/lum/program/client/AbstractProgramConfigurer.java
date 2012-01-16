package org.kuali.student.lum.program.client;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.configurable.mvc.Configurer;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.common.client.configuration.AbstractControllerConfiguration;
import org.kuali.student.lum.common.client.configuration.Configuration;
import org.kuali.student.lum.common.client.configuration.ConfigurationManager;
import org.kuali.student.lum.program.client.major.edit.MajorSummaryConfiguration;

/**
 * @author Igor
 */
public abstract class AbstractProgramConfigurer extends Configurer {

    protected ProgramController programController;

    protected ConfigurationManager programSectionConfigManager;

    public static final String PROPOSAL_PATH = "proposal";
    
    {
        groupName = ProgramMsgConstants.PROGRAM_MSG_GROUP;
    }
    
    public void configure(ProgramController viewController) {
        this.programController = viewController;
        configureProgramSections();
    }
 
    public String getProposalPath() {
        return PROPOSAL_PATH;
    }
    /**
     * Configures menu for Program Sections
     */
    private void configureProgramSections() {
        String programSectionLabel = getLabel(ProgramMsgConstants.PROGRAM_MENU_SECTIONS);
        programController.addMenu(programSectionLabel);
        ArrayList<Configuration> configurations = getProgramSectionConfigManager().getConfigurations();
        for (Configuration configuration : configurations) {
            if (configuration instanceof AbstractControllerConfiguration) {
                ((AbstractControllerConfiguration) configuration).setController(programController);
            }
            if (configuration instanceof MajorSummaryConfiguration) {
                programController.addSpecialMenuItem(configuration.getView(), programSectionLabel);
            } else {
                programController.addMenuItem(programSectionLabel, configuration.getView());
            }
        }
    }

    public ProgramController getProgramController() {
        return programController;
    }

    public void applyPermissions() {
        DataModel dataModel = programController.getProgramModel();
        for (Configuration configuration : programSectionConfigManager.getConfigurations()) {
            if (configuration.checkPermission(dataModel)) {
                configuration.applyRestrictions();
            }else{
                configuration.removeRestrictions();
            }
        }
    }

    public ConfigurationManager getProgramSectionConfigManager() {
        return programSectionConfigManager;
    }
}
