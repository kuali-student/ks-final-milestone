package org.kuali.student.r1.common.assembly.transform;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.DataType;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.util.IdTranslation;
import org.kuali.student.r1.common.assembly.util.IdTranslator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;

public class IdTranslatorFilter extends AbstractDataFilter {
    final static Logger LOG = Logger.getLogger(IdTranslatorFilter.class);

    private IdTranslator idTranslator;

    public IdTranslatorFilter(IdTranslator idTranslator) {
        this.idTranslator = idTranslator;
    }

    /**
     * This filter will lookup translations for fields in the outbound data object and add
     * translations to the data map's runtime data field.
     */
    @Override
    public void applyOutboundDataFilter(Data data, Metadata metadata, Map<String, Object> properties) {
        if (data != null && metadata != null) {
            translateIds(idTranslator, data, metadata);
        }
    }

    /**
     * Uses the IdTranslator and Metadata to convert IDs into their display text and
     * stores those translations in the _runtimeData node.
     * 
     * This method is made public static to allow other filters to translate ids for
     * additional appendeded data. See ProposalWorkflow filter
     * 
     * @param data the Data instance containing IDs to be translated
     * @param metadata the Metadata instance representing the data provided.
     * @throws AssemblyException
     */
    public static void translateIds(IdTranslator idTranslator, Data data, Metadata metadata) {
        try {
            if (data != null && metadata != null) {
                //Iterate through all the data;s properties
                for (Iterator<Property> iter = data.realPropertyIterator(); iter.hasNext();) {
                    Property prop = iter.next();

                    Object fieldData = prop.getValue();
                    Object fieldKey = prop.getKey();

                    Metadata fieldMetadata = metadata.getProperties().get(fieldKey);

                    //if the fieldMetadata is null then try to use the parent metadata as in the case of lists
                    if (fieldMetadata == null) {
                        fieldMetadata = metadata;
                    }

                    //If the fieldData is Data itself the recurse
                    if (fieldData instanceof Data) {
                        if (DataType.LIST.equals(fieldMetadata.getDataType())) {
                            //Lists are a special case where the metadata property name is "*"
                            Metadata listChildMetadata = fieldMetadata.getProperties().get("*");
                            //see if this is a list of data or a list of fields
                            if (DataType.DATA.equals(listChildMetadata.getDataType())) {
                                translateIds(idTranslator, (Data) prop.getValue(), listChildMetadata);
                            } else {
                                //its a list of fields so loop through and translate using the "index"
                                for (Iterator<Property> listIter = ((Data) fieldData).realPropertyIterator(); listIter
                                        .hasNext();) {
                                    Property listItem = listIter.next();
                                    Object listData = listItem.getValue();
                                    if (listData != null && listData instanceof String) {
                                        if (fieldMetadata.getInitialLookup() != null
                                                && !StringUtils.isEmpty((String) listData)) {
                                            //This is a string with a lookup so do the translation
                                            IdTranslation trans = idTranslator.getTranslation(
                                                    fieldMetadata.getInitialLookup(), (String) listData);
                                            if (trans != null) {
                                                Integer index = listItem.getKey();
                                                setTranslation((Data) fieldData, listItem.getKey().toString(), index,
                                                        trans.getDisplay());
                                            }
                                        }

                                    }
                                    if (listData != null && listData instanceof Data) {
                                        if (fieldMetadata.getInitialLookup() != null) {
                                            IdTranslation trans = idTranslator.getTranslation(
                                                    fieldMetadata.getInitialLookup(),
                                                    ((Data) listData).<String> get("orgId"));
                                            if (trans != null) {
                                                Integer index = listItem.getKey();
                                                setTranslation((Data) fieldData, listItem.getKey().toString(), index,
                                                        trans.getDisplay());
                                            }
                                        }

                                    }
                                }
                            }
                        } else if (((Data) prop.getValue()).size() == 1 && ((Data) prop.getValue()).get(0) != null) {
                            //Check for things that are lists of size 1
                            if (fieldMetadata.getInitialLookup() != null) {
                                //This is a string with a lookup so do the translation
                                IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(),
                                        ((Data) prop.getValue()).get(0).toString());
                                if (trans != null) {
                                    setTranslation(data, prop.getKey().toString(), null, trans.getDisplay());
                                }
                            }
                            //Transform the list of 1 to a straight string value
                            data.set(prop.getKey().toString(), ((Data) prop.getValue()).get(0).toString());
                            iter.remove();
                        } else {
                            //Otherwise just use the fieldMetadata
                            translateIds(idTranslator, (Data) prop.getValue(), fieldMetadata);
                        }
                    } else if (fieldData != null && fieldData instanceof String) {
                        if (fieldMetadata.getInitialLookup() != null
                                && !StringUtils.isEmpty((String) fieldData)) {
                            //This is a string with a lookup so do the translation
                            IdTranslation trans = idTranslator.getTranslation(fieldMetadata.getInitialLookup(),
                                    (String) fieldData);
                            if (trans != null) {
                                setTranslation(data, prop.getKey().toString(), null, trans.getDisplay());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
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
            if (index != null) {
                //If the index is set this is a list item (foo/bar/0/, foo/bar/1/)
                Data fieldIndexData = runtime.get(index);
                if (fieldIndexData == null) {
                    fieldIndexData = new Data();
                    runtime.set(index, fieldIndexData);
                }
                fieldIndexData.set("id-translation", translation);
            } else {
                //Otherwise set the translation directly referenced by the field
                //If the index is set this is a list item (foo/bar/0/, foo/bar/1/)
                Data fieldData = runtime.get(field);
                if (fieldData == null) {
                    fieldData = new Data();
                    runtime.set(field, fieldData);
                }
                fieldData.set("id-translation", translation);
            }
        }
    }

    public String getTranslationForAtp(String value, ContextInfo contextInfo) {
        return idTranslator.getTranslationForAtp(value, contextInfo);
    }
}
