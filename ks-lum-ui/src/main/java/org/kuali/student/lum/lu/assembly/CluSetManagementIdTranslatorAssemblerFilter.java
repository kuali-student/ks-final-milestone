package org.kuali.student.lum.lu.assembly;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.Data.DataType;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.common.assembly.old.Assembler;
import org.kuali.student.r1.common.assembly.old.PassThroughAssemblerFilter;
import org.kuali.student.r1.common.assembly.old.data.SaveResult;
import org.kuali.student.r1.common.assembly.util.IdTranslation;
import org.kuali.student.r1.common.assembly.util.IdTranslator;
import org.kuali.student.lum.common.client.widgets.CluSetHelper;

public class CluSetManagementIdTranslatorAssemblerFilter extends PassThroughAssemblerFilter<Data, Void> {

    final Logger LOG = Logger.getLogger(CluSetManagementIdTranslatorAssemblerFilter.class);

    private IdTranslator idTranslator;
    private MetadataServiceImpl metadataService;
    
    public MetadataServiceImpl getMetadataService() {
        return metadataService;
    }

    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }

    public CluSetManagementIdTranslatorAssemblerFilter(IdTranslator idTranslator) {
        this.idTranslator = idTranslator;
    }
    
    @Override
    public void doGetFilter(FilterParamWrapper<String> id, FilterParamWrapper<Data> response, GetFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doGetFilter(id, response);
        Data data = response.getValue(); 
        if (data != null) {
            translateIds(data, chain);
        }
    }
    
    @Override
    public void doSaveFilter(FilterParamWrapper<Data> request, FilterParamWrapper<SaveResult<Data>> response, SaveFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doSaveFilter(request, response);
        SaveResult<Data> saveResult = response.getValue();
        Data data = saveResult != null && saveResult.getValue() != null ? saveResult.getValue() : null;
        if(data != null) {
            translateIds(data, chain);
        }
    }

    private void translateIds(Data data, AssemblerManagerAccessable<Data, Void> chain) throws AssemblyException {
        CluSetHelper cluSetHelper = new CluSetHelper(data);
        Assembler<Data, Void> a = chain.getManager().getTarget();
        Metadata metadata = null;
        if (cluSetHelper.getType() == null || cluSetHelper.getType().equals("kuali.cluSet.type.CreditCourse")) {
            metadata = metadataService.getMetadata("courseSet");
        } else {
            metadata = metadataService.getMetadata("programSet");
        }
        if (metadata != null && data != null) {
            __translateIds(data, metadata);
        }
    }
    
    /**
     * Uses the IdTranslator and Metadata to convert IDs into their display text and stores those translations in the
     * _runtimeData node
     * 
     * @param data
     *            the Data instance containing IDs to be translated
     * @param metadata
     *            the Metadata instance representing the data provided.
     * @throws AssemblyException
     */
    private void __translateIds(Data data, Metadata metadata)
            throws AssemblyException {
        try{
            if (data != null && metadata != null) {
                //Iterate through all the data;s properties
                for (Iterator<Property> iter = data.realPropertyIterator(); iter.hasNext();) {
                    Property prop = iter.next();
                    
                    Object fieldData = prop.getValue();
                    Object fieldKey = prop.getKey();

                    Metadata fieldMetadata = metadata.getProperties().get(fieldKey);
                    
                    //if the fieldMetadata is null then try to use the parent metadata as in the case of lists
                    if(fieldMetadata==null){
                        fieldMetadata=metadata;
                    }
                    
                    //If the fieldData is Data itself the recurse
                    if (fieldData instanceof Data) {
                        if (DataType.LIST.equals(fieldMetadata.getDataType())) {
                            //Lists are a special case where the metadata property name is "*"
                            Metadata listChildMetadata = fieldMetadata.getProperties().get("*");
                            //see if this is a list of data or a list of fields
                            if(DataType.DATA.equals(listChildMetadata.getDataType())){
                                __translateIds((Data) prop.getValue(), listChildMetadata);
                            }else{
                                //its a list of fields so loop through and translate using the "index"
                                for(Iterator<Property> listIter = ((Data)fieldData).realPropertyIterator(); listIter.hasNext();){
                                    Property listItem = listIter.next();
                                    Object listData = listItem.getValue();
                                    if (listData != null && listData instanceof String) {
                                        if (fieldMetadata.getInitialLookup() != null
                                                && !StringUtils.isEmpty((String) listData)) {
                                            //This is a string with a lookup so do the translation
                                            IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(), (String) listData);
                                            if (trans != null) {
                                                Integer index = listItem.getKey();
                                                setTranslation((Data)fieldData, listItem.getKey().toString(), index, trans.getDisplay());
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            //Otherwise just use the fieldMetadata
                            __translateIds((Data) prop.getValue(), fieldMetadata);
                        }
                    } else if (fieldData != null && fieldData instanceof String) {
                        if (fieldMetadata.getInitialLookup() != null
                                && !StringUtils.isEmpty((String) fieldData)) {
                            //This is a string with a lookup so do the translation
                            IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(), (String) fieldData);
                            if (trans != null) {
                                setTranslation(data, prop.getKey().toString(), null, trans.getDisplay());
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            LOG.error("Error translating", e);
        }
    }

    private static void setTranslation(Data data, String field, Integer index, String translation) {
        if (data != null) {
            //Get runtime data for the node and create if it doesn't exist
            Data runtime = data.get("_runtimeData");
            if (runtime == null) {
                runtime = new Data();
                data.set("_runtimeData", runtime);
            }
            if(index != null) {
                //If the index is set this is a list item (foo/bar/0/, foo/bar/1/)
                Data fieldIndexData = runtime.get(index);
                if(fieldIndexData==null){
                    fieldIndexData = new Data();
                    runtime.set(index, fieldIndexData);
                }
                fieldIndexData.set("id-translation", translation);
            }else{
                //Otherwise set the translation directly referenced by the field
                //If the index is set this is a list item (foo/bar/0/, foo/bar/1/)
                Data fieldData = runtime.get(field);
                if(fieldData==null){
                    fieldData = new Data(); 
                    runtime.set(field, fieldData);
                }
                fieldData.set("id-translation", translation);
            }
        }
    }
}
