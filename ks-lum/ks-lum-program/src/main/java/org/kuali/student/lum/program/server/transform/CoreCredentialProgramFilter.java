package org.kuali.student.lum.program.server.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.assembly.transform.AbstractDataFilter;
import org.kuali.student.core.assembly.transform.DataBeanMapper;
import org.kuali.student.core.assembly.transform.DefaultDataBeanMapper;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * Add/remove the related CredentialPrograms titles to/from Program data (for display purposes only, as the data is obviously
 * not persisted).
 * 
 * @author Jim
 */
public class CoreCredentialProgramFilter extends AbstractDataFilter {

    private MetadataServiceImpl metadataService;
    private ProgramService programService;
    private LuService luService;
    private final DataBeanMapper mapper = new DefaultDataBeanMapper();
    private final Metadata credPgmMetadata = null;

    /**
     * Remove CredentialPrograms titles
     */
    @Override
    public void applyInboundDataFilter(Data data, Metadata metadata,
                                       Map<String, Object> properties) throws Exception {
        // remove the list of CredentialPrograms from the data passed in
        data.remove(new Data.StringKey(ProgramConstants.CREDENTIAL_PROGRAMS));
    }

    /**
     * Add the related CredentialPrograms' longTitle
     */
    @Override
    public void applyOutboundDataFilter(Data data, Metadata metadata,
                                        Map<String, Object> properties) throws Exception {

        String coreProgramId = data.get(ProgramConstants.ID);
        // TODO - This is heinous. It has to be a custom search for CredPgm titles
        // <jira>

        // get all Clu's related to this core program
        List<String> credPgmIds = luService.getCluIdsByRelation(coreProgramId, ProgramConstants.HAS_CORE_PROGRAM);
        List<String> credPgmTitles = new ArrayList<String>();
        Data credPgmData = new Data();
            
        // Get the long titles 
        for (String credPgmId : credPgmIds) {
            try {
                CredentialProgramInfo credPgm = programService.getCredentialProgram(credPgmId);
                credPgmData.add(credPgm.getLongTitle());
            } catch (DoesNotExistException dnee) {
                // no worries; just not a credential program
            }
        }

        // Add the Credential Programs' titles to the data passed in
        data.set(ProgramConstants.CREDENTIAL_PROGRAMS, credPgmData);
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }
}
