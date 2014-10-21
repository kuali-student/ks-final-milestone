package org.kuali.student.lum.lu.ui.tools.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataService;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.assembly.old.Assembler;
import org.kuali.student.r1.common.assembly.old.data.SaveResult;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.common.rice.authorization.PermissionTypeGwt;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CluSetDataService  implements DataService{

    private static final Logger LOG = LoggerFactory.getLogger(CluSetDataService.class);
    private Assembler<Data, Void> assembler;
    private MetadataServiceImpl metadataService;
    
    public MetadataServiceImpl getMetadataService() {
        return metadataService;
    }

    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }

    public Assembler<Data, Void> getAssembler() {
        return assembler;
    }

    public void setAssembler(Assembler<Data, Void> assembler) {
        this.assembler = assembler;
    }

    @Override
    public Data getData(String dataId, ContextInfo contextInfo) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        Data result = null;
        try {
            result = assembler.get(dataId);
        } catch (Exception e){
            throw new OperationFailedException("Failed to retrieve data from id " + dataId, e);
        }
        return result;
    }

    @Override
    public Metadata getMetadata(String id, Map<String,String> idAttributes, ContextInfo contextInfo) {
        Metadata metadata = metadataService.getMetadata(id);
        return metadata;
    }
    
    protected String getDefaultMetaDataType() {
        return null; //DEFAULT_METADATA_STATE;
    }

    protected String getDefaultMetaDataState() {
        return null; //DEFAULT_METADATA_STATE;
    }

    @Override
    public Boolean isAuthorized(PermissionTypeGwt type, Map<String, String> attributes, ContextInfo contextInfo) {
        return true;
    }

    @Override
    public DataSaveResult saveData(Data data, ContextInfo contextInfo) throws OperationFailedException {
        try {
            SaveResult<Data> saveResult = assembler.save(data);
            if (saveResult != null) {
                return new DataSaveResult(saveResult.getValidationResults(), saveResult.getValue());
            }
        } catch (Exception e) {
            LOG.error("Unable to save", e);
            throw new OperationFailedException("Unable to save");
        }
        return null;
    }

	@Override
	public List<ValidationResultInfo> validateData(Data data, ContextInfo contextInfo)
			throws OperationFailedException {
		return null;
	}
}
