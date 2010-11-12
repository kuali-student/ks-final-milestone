package org.kuali.student.lum.program.server.transform;

import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.assembly.transform.AbstractDataFilter;
import org.kuali.student.core.assembly.transform.DataBeanMapper;
import org.kuali.student.core.assembly.transform.DefaultDataBeanMapper;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * Add/remove the related CredentialProgram data & metadata
 * to/from Program data (for display purposes only, as the data
 * is obviously not persisted).
 *
 * @author Jim
 */
public class CredentialProgramFilter extends AbstractDataFilter {

    private MetadataServiceImpl metadataService;
    private ProgramService programService;
    private final DataBeanMapper mapper = new DefaultDataBeanMapper();
    private Metadata credPgmMetadata = null;

    /**
     * Remove CredentialProgram data and metadata
     */
    @Override
    public void applyInboundDataFilter(Data data, Metadata metadata,
                                       Map<String, Object> properties) throws Exception {
        // remove the CredentialProgram metadata from the metadata passed in
        Map<String, Metadata> metaProps = metadata.getProperties();
        metaProps.remove(ProgramConstants.CREDENTIAL_PROGRAM);

        // remove the CredentialProgram from the data passed in
        data.remove(new Data.StringKey(ProgramConstants.CREDENTIAL_PROGRAM));
    }

    /**
     * Add the related CredentialProgram data and metadata
     */
    @Override
    public void applyOutboundDataFilter(Data data, Metadata metadata,
                                        Map<String, Object> properties) throws Exception {

        // Get CredentialProgram associated with this data
        String credentialProgramId = data.get(ProgramConstants.CREDENTIAL_PROGRAM_ID);
        CredentialProgramInfo credPgm = programService.getCredentialProgram(credentialProgramId);
        // and convert to Data
        Data credPgmData = mapper.convertFromBean(credPgm);

        // Add the CredentialProgram to the data passed in
        data.set(ProgramConstants.CREDENTIAL_PROGRAM, credPgmData);

        // Add the CredentialProgram metadata to metadata passed in
        Map<String, Metadata> metaProps = metadata.getProperties();
        metaProps.put(ProgramConstants.CREDENTIAL_PROGRAM, getCredProgramMetadata());
    }

    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    private Metadata getCredProgramMetadata() {
        //if (credPgmMetadata == null) {
        credPgmMetadata = metadataService.getMetadata(CredentialProgramInfo.class.getName());
        //}
        return credPgmMetadata;
    }
}
