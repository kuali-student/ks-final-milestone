package org.kuali.student.lum.program.server.transform;

import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.assembly.transform.AbstractDataFilter;
import org.kuali.student.r1.common.assembly.transform.DataBeanMapper;
import org.kuali.student.r1.common.assembly.transform.DefaultDataBeanMapper;
import org.kuali.student.r1.common.assembly.transform.MetadataFilter;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

/**
 * Add/remove the related CredentialProgram data & metadata
 * to/from Program data (for display purposes only, as the data
 * is obviously not persisted).
 *
 * @author Jim
 */
public class MajorCredentialProgramFilter extends AbstractDataFilter implements MetadataFilter{

    private MetadataServiceImpl metadataService;
    private ProgramService programService;
    private final DataBeanMapper mapper = new DefaultDataBeanMapper();
    private Metadata credPgmMetadata = null;

    /**
     * Remove CredentialProgram data and metadata
     */
    @Override
    public void applyInboundDataFilter(Data data, Metadata metadata, Map<String, Object> properties) throws Exception {
        // remove the CredentialProgram from the data passed in
        data.remove(new Data.StringKey(ProgramConstants.CREDENTIAL_PROGRAM));
    }

    /**
     * Add the related CredentialProgram data
     */
    @Override
    public void applyOutboundDataFilter(Data data, Metadata metadata,
                                        Map<String, Object> properties) throws Exception {

        // Get CredentialProgram associated with this data    	
        String credentialProgramId = data.get(ProgramConstants.CREDENTIAL_PROGRAM_ID);
        if (credentialProgramId != null && !credentialProgramId.isEmpty()){
	        CredentialProgramInfo credPgm = programService.getCredentialProgram(credentialProgramId, ContextUtils.getContextInfo());
	        // and convert to Data
	        Data credPgmData = mapper.convertFromBean(credPgm, metadata );
	
	        // Add the CredentialProgram to the data passed in
	        data.set(ProgramConstants.CREDENTIAL_PROGRAM, credPgmData);
        }
    }

    /**
     * Add the related CredentialProgram metadata 
     */
    @Override
	public void applyMetadataFilter(String dtoName, Metadata metadata, Map<String, Object> filterProperties) {
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
