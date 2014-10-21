/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.type.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.test.spring.log4j.KSLog4JConfigurer;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: andy
 * Date: 12/5/12
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class TypeServiceMockImpl implements TypeService, MockService {

    private Map<String, TypeInfo> allTypes = new HashMap<String, TypeInfo>();
    private Map<String, Map<String, TypeTypeRelationInfo>> relationOwners = new HashMap<String, Map<String, TypeTypeRelationInfo>>();
    private Map<String, Map<String, TypeInfo>> allowedTypes = new HashMap<String, Map<String, TypeInfo>>();
    private Map<String, Map<String, TypeInfo>> groupTypes = new HashMap<String, Map<String, TypeInfo>>();
    private Map<String, List<TypeInfo>> refObjfectUriMap = new HashMap<String, List<TypeInfo>>();
    private Map<String, List<TypeInfo>> typeKeyMap = new HashMap<String, List<TypeInfo>>();

    private static final String TYPE_KEY_HEADER = "type_key";
    private static final String NAME_HEADER =  "name";
    private static final String REF_OBJECT_URI_HEADER =  "ref_object_uri";
    private static final String  SERVICE_URI_HEADER = "service_uri";


    private static final org.slf4j.Logger log = KSLog4JConfigurer.getLogger(TypeServiceMockImpl.class);

    public TypeServiceMockImpl() {
        super();

        init();

        initilizeTypeInfoMaps();
    }

    @Override
    public void clear() {

        this.allowedTypes.clear();
        this.allTypes.clear();
        this.groupTypes.clear();
        this.relationOwners.clear();
        this.refObjfectUriMap.clear();
        this.typeKeyMap.clear();

        init();
        initilizeTypeInfoMaps();

    }

    @Override
    public TypeInfo getType(String typeKey,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        TypeInfo type = getType(typeKey);
        if (type == null) {
            throw new DoesNotExistException(typeKey);
        }
        return type;
    }

    @Override
    public List<TypeInfo> getTypesByKeys(List<String> typeKeys,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<String> getRefObjectUris( ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectUri(String refObjectUri,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<TypeInfo> types = refObjfectUriMap.get(refObjectUri);
//        if (types == null) {
//            throw new DoesNotExistException(refObjectUri);
//        }
        return types;
    }

    @Override
    public List<TypeInfo> getTypesForGroupType(String groupTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeInfo> result = new ArrayList<TypeInfo>();
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        result.add(typeInfo);
        return result;

    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Map<String, TypeInfo> relationTypes = allowedTypes.get(ownerTypeKey);
        if (relationTypes != null) {
            return new ArrayList<TypeInfo>(relationTypes.values());
        } else {
            return new ArrayList<TypeInfo>();
        }
    }

    private TypeInfo getType(String typeKey) {
        return allTypes.get(typeKey);
    }

    //    @Override
//    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI,  ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
//        Map<String, TypeInfo> relationTypes = allowedTypes.get(ownerTypeKey);
//        if (relationTypes != null) {
//            return new ArrayList<TypeInfo>(relationTypes.values());
//        } else {
//            return new ArrayList<TypeInfo>();
//        }
//    }
    @Override
    public List<ValidationResultInfo> validateType( String validationTypeKey, TypeInfo typeInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeInfo createType(String typeKey, TypeInfo typeInfo,  ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        allTypes.put(typeInfo.getKey(), typeInfo);

        //Load refObjectUriMap when new type is added (created) so it will be available for validation
        String uri = typeInfo.getRefObjectUri();
        List<TypeInfo> typeList = refObjfectUriMap.get(uri);
        if (typeList ==null) {
            typeList = new ArrayList<TypeInfo>();
            refObjfectUriMap.put(uri,typeList);
        }
        if (!typeList.contains(typeInfo)) {
            typeList.add(typeInfo);
        }
        return typeInfo;
    }

    @Override
    public TypeInfo updateType(String typeKey, TypeInfo typeInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if(allTypes.get(typeKey) == null){
            throw new DoesNotExistException(typeKey + " does not have a type in the system");
        }else{
            allTypes.put(typeKey, typeInfo);
            return typeInfo;
        }



    }

    @Override
    public StatusInfo deleteType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeTypeRelationInfo getTypeTypeRelation( String typeTypeRelationKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByIds(List<String> typeTypeRelationIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerAndType( String ownerTypeKey,  String relationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Map<String, TypeTypeRelationInfo> relationTypes = relationOwners.get(ownerTypeKey);
        if (relationTypes != null) {
            return new ArrayList<TypeTypeRelationInfo>(relationTypes.values());
        } else {
            return new ArrayList<TypeTypeRelationInfo>();
        }
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByRelatedTypeAndType( String relatedTypeKey,  String relationTypeKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TypeTypeRelationInfo> list = new ArrayList<TypeTypeRelationInfo>();
        for (Map<String, TypeTypeRelationInfo> relationTypes : relationOwners.values()) {
            for (TypeTypeRelationInfo rel : relationTypes.values()) {
                if (rel.getTypeKey().equals(relationTypeKey)) {
                    if (rel.getRelatedTypeKey().equals(relatedTypeKey)) {
                        list.add(rel);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<ValidationResultInfo> validateTypeTypeRelation( String validationTypeKey, String typeKey,  String typePeerKey, String typeTypeRelationTypeKey,  TypeTypeRelationInfo typeTypeRelationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public TypeTypeRelationInfo createTypeTypeRelation( String typeTypeRelationKey, String typeKey,  String typePeerKey,  TypeTypeRelationInfo typeTypeRelationInfo,  ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        TypeInfo typeType = getType(typeTypeRelationKey);
        if (typeType==null || !typeTypeRelationKey.equals(typeTypeRelationInfo.getTypeKey())) {
            throw new InvalidParameterException(String.format("[%s] is not a valid type",typeTypeRelationKey));
        }
        TypeInfo type = getType(typeKey);
        if (type==null || !typeKey.equals(typeTypeRelationInfo.getOwnerTypeKey())) {
            throw new InvalidParameterException(String.format("[%s] is not a valid type",type));
        }
        TypeInfo peerType = getType(typePeerKey);
        if (peerType==null || !typePeerKey.equals(typeTypeRelationInfo.getRelatedTypeKey())) {
            throw new InvalidParameterException(String.format("[%s] is not a valid type",peerType));
        }
        TypeTypeRelationInfo relationInfo = this.createTypeTypeRelationInfo(typeKey, typePeerKey);
        relationInfo.setRelatedTypeKey(typeTypeRelationKey);
        associate(typeTypeRelationKey+typeKey+typePeerKey,typeTypeRelationKey,typeKey,typePeerKey,"0",
                String.format("%s to %s relationship",type,typePeerKey));
        return relationInfo;
    }

    @Override
    public TypeTypeRelationInfo updateTypeTypeRelation( String typeTypeRelationKey,  TypeTypeRelationInfo typeTypeRelationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    @Override
    public StatusInfo deleteTypeTypeRelation( String typeTypeRelationKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented."); // TODO implement
    }

    private void initilizeTypeInfoMaps() {

        int type_key_pos = -1;
        int name_pos = -1;
        int ref_object_uri_pos = -1;
        int service_uri_pos = -1;

        Resource resource = new ClassPathResource("type-service-data.csv");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            boolean isHeaderFound = false;
            String line = null;
            while ((line = in.readLine()) != null) {
                if (line.trim().startsWith("#")) {
                    continue;
                }
                String[] tokens = StringUtils.split(line, ",");
//              // Determine the column header position
                if (!isHeaderFound) {
                    log.debug(String.format("Processing the header columns in line: %s", line));
                    for (int i = 0; i < tokens.length; i++) {
                        String header = tokens[i].trim();
                        if (TYPE_KEY_HEADER.equals(header)) {
                            type_key_pos = i;
                        } else if (NAME_HEADER.equals(header)) {
                            name_pos = i;
                        } else if (REF_OBJECT_URI_HEADER.equals(header)) {
                            ref_object_uri_pos = i;
                        } else if (SERVICE_URI_HEADER.equals(header)) {
                            service_uri_pos = i;
                        } else {
                            throw new IllegalArgumentException(String.format("found unidentified column header name %s", header));
                        }
                    }
                    if (type_key_pos == -1 || name_pos == -1 || ref_object_uri_pos == -1 || service_uri_pos == -1) {
                        throw new IllegalArgumentException(String.format("Some headers are missing in the column header %s", line));
                    }
                    isHeaderFound = true;
                } else {
                    TypeInfo typeInfo = new TypeInfo();
                    for (int i = 0; i < tokens.length; i++) {
                        String token = tokens[i].trim();
                        if (type_key_pos == i) {
                            typeInfo.setKey(token);
                        } else if (name_pos == i) {
                            typeInfo.setName(token);
                        } else if (ref_object_uri_pos == i) {
                            typeInfo.setRefObjectUri(token);
                        } else if (service_uri_pos == i) {
                            typeInfo.setServiceUri(token);
                        } else {
                            throw new IllegalArgumentException(String.format("found unidentified value at column %d", i + 1));
                        }
                    }
                    List<TypeInfo> refObjectUriList = refObjfectUriMap.get(typeInfo.getRefObjectUri());
                    if (refObjectUriList == null) {
                        refObjectUriList = new ArrayList<TypeInfo>();
                        refObjfectUriMap.put(typeInfo.getRefObjectUri(), refObjectUriList);
                    }
                    refObjectUriList.add(typeInfo);
                    List<TypeInfo> typeKeyList = typeKeyMap.get(typeInfo.getKey());
                    if (typeKeyList == null) {
                        typeKeyList = new ArrayList<TypeInfo>();
                        typeKeyMap.put(typeInfo.getKey(), refObjectUriList);
                    }
                    typeKeyList.add(typeInfo);
                }
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading type service keys", e);
        }

    }

    private void init() {
        List<String[]> typeArrays = new ArrayList<String[]>();
        typeArrays.add(new String[]{AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, "Academic Calendar", "Academic Calendar", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_HOLIDAY_CALENDAR_TYPE_KEY, "Holiday Calendar", "Holiday Calendar", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_FALL_TYPE_KEY, "Fall", "Fall Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_SPRING_TYPE_KEY, "Spring", "Spring Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.FallSpring", "Fall-Spring", "Fall & Spring Semesters", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.AY", "AY", "Full Academic Year", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.FY", "FY", "Fiscal Year", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Holiday", "Holiday", "Holiday", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SpringBreak", "SpringBreak", "Spring Break", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Thanksgiving", "Thanksgiving", "Thanksgiving", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfFall1", "Fall Half-Semester 1", "Fall Half-Semester 1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfFall2", "Fall Half-Semester 2", "Fall Half-Semester 2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfSpring1", "Spring Half-Semester 1", "Spring Half-Semester 1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.HalfSpring2", "Spring Half-Semester 1", "Spring Half-Semester 2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester1A", "Mini Semester 1A", "Mini Semester 1A", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester1B", "Mini Semester 1B", "Mini Semester 1B", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester2C", "Mini Semester 2C", "Mini Semester 2C", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Mini-mester2D", "Mini Semester 2D", "Mini Semester 2D", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Session1", "Session 1", "Session 1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Session2", "Session 2", "Session 2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SessionG1", "Session G1", "Session G1", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SessionG2", "Session G2", "Session G2", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_SUMMER_TYPE_KEY, "Summer", "Summer Semester", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.SummerEve", "Summer Eve", "Summer Eve", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_WINTER_TYPE_KEY, "Winter", "Winter", AtpServiceConstants.REF_OBJECT_URI_ATP});
        typeArrays.add(new String[]{"kuali.atp.type.Adhoc", "Ad hoc", "Ad hoc", AtpServiceConstants.REF_OBJECT_URI_ATP});

        //holidays
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_CHRISTMAS_TYPE_KEY, "Christmas", "Christmas", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_CHRISTMAS_OBSERVED_TYPE_KEY, "Christmas Observed", "Christmas Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FALL_BREAK_TYPE_KEY, "Fall Break", "Fall Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_LABOR_DAY_TYPE_KEY, "Labor Day", "Labor Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_TYPE_KEY, "Independence Day", "Independence Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY, "Independence Day Observed", "Independence Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MEMORIAL_DAY_TYPE_KEY, "Memorial Day", "Memorial Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MEMORIAL_DAY_OBSERVED_TYPE_KEY, "Memorial Day Observed", "Memorial Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MLK_DAY_TYPE_KEY, "Martin Luther King Day", "Martin Luther King Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_MLK_DAY_OBSERVED_TYPE_KEY, "MLK Day Observed", "MLK Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_TYPE_KEY, "New Years Day", "New Years Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_OBSERVED_TYPE_KEY, "New Years Day Observed", "New Years Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_PRESIDENTS_DAY_TYPE_KEY, "Presidents Day", "Presidents Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_SPRING_BREAK_TYPE_KEY, "Spring Break", "Spring Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_THANKSGIVING_BREAK_TYPE_KEY, "Thanksgiving Break", "Thanksgiving Break", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_VETERANS_DAY_TYPE_KEY, "Veterans Day", "Veterans Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_VETERANS_DAY_OBSERVED_TYPE_KEY, "Veterans Day Observed", "Veterans Day Observed", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //events    -- type_key, name, type_descr, REF_OBJECT_URI
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FAMILY_WEEKEND_TYPE_KEY, "Family Weekend", "Family Weekend", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY, "Homecoming", "Homecoming", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_GRADUATION_APPLICATION_DEADLINE_TYPE_KEY, "Graduation Application Deadline", "Deadline to apply for Graduation", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_ALUMNI_DAY_TYPE_KEY, "Alumni Day", "Alumni Day", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_BACCALAUREATE_TYPE_KEY, "Baccalaureate", "Baccalaureate", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_COMMENCEMENT_TYPE_KEY, "Commencement", "Commencement", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //for AtpAtpRelations
        typeArrays.add(new String[]{AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY, "includes", "Includes", TypeServiceConstants.REF_OBJECT_URI_TYPE_TYPE_RELATION});
        typeArrays.add(new String[]{AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY, "associated", "Associated", TypeServiceConstants.REF_OBJECT_URI_TYPE_TYPE_RELATION});

        //Population
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "Population person", "Population person", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "Population rule", "Population rule", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_RULE_TYPE_SEARCH_KEY, "Population search", "Population search", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, "Population union", "Population union", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY, "Population intersection", "Population intersection", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY, "Population exclusion", "Population exclusion", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_TYPE_KEY, "Population population", "Population population", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY, "Population student", "Population student", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});
        typeArrays.add(new String[]{PopulationServiceConstants.POPULATION_CATEGORY_TYPE_KEY, "Population category", "Population category", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE});

        for (String[] typeArray : typeArrays) {
            createTypeInfo(typeArray[0], typeArray[1], typeArray[2], typeArray[3]);
        }

        // Term Types Grouping
        Set<TypeInfo> termGroup = new HashSet<TypeInfo>();
        TypeInfo termGroupType = createTypeInfo(AtpServiceConstants.ATP_TERM_GROUPING_TYPE_KEY, "Group for Terms", "Group for terms", AtpServiceConstants.REF_OBJECT_URI_ATP);

        termGroup.add(getType(AtpServiceConstants.ATP_FALL_TYPE_KEY));
        termGroup.add(getType("kuali.atp.type.FallSpring"));
        termGroup.add(getType("kuali.atp.type.HalfFall1"));
        termGroup.add(getType("kuali.atp.type.HalfFall2"));
        termGroup.add(getType(AtpServiceConstants.ATP_WINTER_TYPE_KEY));
        termGroup.add(getType(AtpServiceConstants.ATP_SPRING_TYPE_KEY));
        termGroup.add(getType("kuali.atp.type.HalfSpring1"));
        termGroup.add(getType("kuali.atp.type.HalfSpring2"));
        termGroup.add(getType("kuali.atp.type.SpringBreak"));
        termGroup.add(getType(AtpServiceConstants.ATP_SUMMER_TYPE_KEY));
        termGroup.add(getType("kuali.atp.type.SummerEve"));
        termGroup.add(getType("kuali.atp.type.Session1"));
        termGroup.add(getType("kuali.atp.type.Mini-mester1A"));
        termGroup.add(getType("kuali.atp.type.Mini-mester1B"));
        termGroup.add(getType("kuali.atp.type.Session2"));
        termGroup.add(getType("kuali.atp.type.Mini-mester2C"));
        termGroup.add(getType("kuali.atp.type.Mini-mester2D"));
        termGroup.add(getType("kuali.atp.type.SessionG1"));
        termGroup.add(getType("kuali.atp.type.SessionG2"));
        termGroup.add(getType("kuali.atp.type.Adhoc"));
        for (TypeInfo type : termGroup) {
            createTypeTypeRelationInfo(termGroupType, type);
        }

        //keydates
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "Advance Registration Period", "Advance Registration Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "Registration Period", "Registration Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "Registration Begins Transfer", "Registration Begins Transfer", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "Drop Deadline Without Record", "Drop Deadline Without Record", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "Final Exam Period", "Final Exam Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "Grades Due", "Grades Due", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "Instructional Period", "Instructional Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY, "FinancialAid Census", "FinancialAid Census", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY, "CourseSelectionPeriodEnd", "CourseSelectionPeriodEnd", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY, "DropDate", "DropDate",  AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        //curriculum
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_COORDINATORS_KICKOFF_MEETING_TYPE_KEY, "Coordinators Kickoff Meeting", "Coordinators Kickoff Meeting", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_PROPOSAL_PERIOD_TYPE_KEY, "Proposal Period", "Proposal Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});
        typeArrays.add(new String[]{AtpServiceConstants.MILESTONE_REVIEW_PERIOD_TYPE_KEY, "Review Period", "Review Period", AtpServiceConstants.REF_OBJECT_URI_MILESTONE});

        for (String[] typeArray : typeArrays) {
            createTypeInfo(typeArray[0], typeArray[1], typeArray[2], typeArray[3]);
        }

        //Keydates grouping
        Set<TypeInfo> keydateGroup = new HashSet<TypeInfo>();
        TypeInfo keydateGroupType = createTypeInfo(AtpServiceConstants.MILESTONE_KEYDATE_GROUP, "Group for key dates", "Group for key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY));
        keydateGroup.add(getType(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY));
        for (TypeInfo type : keydateGroup) {
            createTypeTypeRelationInfo(keydateGroupType, type);
        }

        //Instructional Keydates grouping
        Set<TypeInfo> inskeydateGroup = new HashSet<TypeInfo>();
        TypeInfo inskeydateGroupType = createTypeInfo("kuali.milestone.type.group.registration", "Group for instructional key dates", "Group for instructional key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY));
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY));
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY));
        inskeydateGroup.add(getType(AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY));
        for (TypeInfo type : inskeydateGroup) {
            createTypeTypeRelationInfo(inskeydateGroupType, type);
        }

        //kuali.milestone.type.group.registration
        //registration Keydates grouping
        Set<TypeInfo> regkeydateGroup = new HashSet<TypeInfo>();
        TypeInfo regkeydateGroupType = createTypeInfo("kuali.milestone.type.group.instructional", "Group for registration key dates", "Group for registration key dates", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_COMMENCEMENT_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_COURSE_SELECTION_PERIOD_END_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY));
        regkeydateGroup.add(getType(AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY));
        for (TypeInfo type : regkeydateGroup) {
            createTypeTypeRelationInfo(regkeydateGroupType, type);
        }

        //curriculum grouping
        Set<TypeInfo> curriculumGroup = new HashSet<TypeInfo>();
        TypeInfo curriculumGroupType = createTypeInfo("kuali.milestone.type.group.curriculum", "Curriculum", "Curriculum", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        curriculumGroup.add(getType(AtpServiceConstants.MILESTONE_COORDINATORS_KICKOFF_MEETING_TYPE_KEY));
        curriculumGroup.add(getType(AtpServiceConstants.MILESTONE_PROPOSAL_PERIOD_TYPE_KEY));
        curriculumGroup.add(getType(AtpServiceConstants.MILESTONE_REVIEW_PERIOD_TYPE_KEY));
        for (TypeInfo type : curriculumGroup) {
            createTypeTypeRelationInfo(curriculumGroupType, type);
        }

        //Holiday types Grouping
        Set<TypeInfo> holidayGroup = new HashSet<TypeInfo>();
        TypeInfo holidayGroupType = createTypeInfo(AtpServiceConstants.MILESTONE_HOLIDAY_GROUPING_TYPE_KEY, "Holidays", "Holidays", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_CHRISTMAS_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_CHRISTMAS_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_FALL_BREAK_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_LABOR_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_INDEPENDENCE_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MEMORIAL_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MEMORIAL_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MLK_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_MLK_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_NEW_YEAR_DAY_OBSERVED_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_PRESIDENTS_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_SPRING_BREAK_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_THANKSGIVING_BREAK_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_VETERANS_DAY_TYPE_KEY));
        holidayGroup.add(getType(AtpServiceConstants.MILESTONE_VETERANS_DAY_OBSERVED_TYPE_KEY));

        for (TypeInfo type : holidayGroup) {
            createTypeTypeRelationInfo(holidayGroupType, type);
        }

        //Event types Grouping
        Set<TypeInfo> eventGroup = new HashSet<TypeInfo>();
        TypeInfo eventGroupType = createTypeInfo(AtpServiceConstants.MILESTONE_EVENT_GROUPING_TYPE_KEY, "Acal Events", "Acal Events", AtpServiceConstants.REF_OBJECT_URI_MILESTONE);

        eventGroup.add(getType(AtpServiceConstants.MILESTONE_FAMILY_WEEKEND_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_HOMECOMING_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_GRADUATION_APPLICATION_DEADLINE_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_ALUMNI_DAY_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_BACCALAUREATE_TYPE_KEY));
        eventGroup.add(getType(AtpServiceConstants.MILESTONE_COMMENCEMENT_TYPE_KEY));
        for (TypeInfo type : eventGroup) {
            createTypeTypeRelationInfo(eventGroupType, type);
        }

        // Allowed type relations
        List<String[]> allowedArrays = new ArrayList<String[]>();
//        allowedArrays.add(new String[] {"kuali.atp.type.type.relation.allowed.1", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, "kuali.atp.type.FallSpring", "1", "AcademicCalendar can contain FallSpring"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.2", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "2", "AcademicCalendar can contain Fall"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.3", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.atp.type.HalfFall1", "1", "Fall can contain HalfFall1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.4", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.atp.type.HalfFall2", "2", "Fall can contain HalfFall2"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.5", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, "3", "AcademicCalendar can contain Winter"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.6", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "4", "AcademicCalendar can contain Spring"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.7", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "kuali.atp.type.HalfSpring1", "1", "Spring can contain HalfSpring1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.8", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "kuali.atp.type.SpringBreak", "2", "Spring can contain SpringBreak"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.9", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, "kuali.atp.type.HalfSpring2", "3", "Spring can contain HalfSpring2"});
//        allowedArrays.add(new String[] {"kuali.atp.type.type.relation.allowed.10", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, "kuali.atp.type.Session1", "5", "AcademicCalendar can contain Session1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.11", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session1", "kuali.atp.type.Mini-mester1A", "1", "Session1 can contain Mini-mester1A"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.12", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session1", "kuali.atp.type.Mini-mester1B", "2", "Session1 can contain Mini-mester1B"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.13", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, "kuali.atp.type.Session2", "2", "Summer can contain Session2"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.14", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session2", "kuali.atp.type.Mini-mester2C", "1", "Session2 can contain Mini-mester2C"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.15", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.Session2", "kuali.atp.type.Mini-mester2D", "2", "Session2 can contain Mini-mester2D"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.16", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, "6", "AcademicCalendar can contain SummerEve"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.17", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.SummerEve", "kuali.atp.type.SessionG1", "1", "SummerEve can contain SessionG1"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.18", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, "kuali.atp.type.SummerEve", "kuali.atp.type.SessionG2", "2", "SummerEve can contain SessionG2"});
        // key dates for fall term
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.20", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Fall can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.21", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Fall can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.22", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Fall can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.23", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Fall can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.24", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Fall can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.25", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Fall can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.26", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Fall can have a grading period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.27", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY, "7", "Fall can have a financialaid"});
        // key dates for winter
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.30", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Winter can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.31", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Winter can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.32", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Winter can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.33", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Winter can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.34", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Winter can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.35", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Winter can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.36", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_WINTER_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Winter can have a grading period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.37", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, AtpServiceConstants.MILESTONE_FINANCIAL_AID_CENSUS_TYPE_KEY, "7", "Fall can have a financialaid"});
        // key dates for spring
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.40", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Spring can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.41", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Spring can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.42", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Spring can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.43", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Spring can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.44", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Spring can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.45", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Spring can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.46", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SPRING_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Spring can have a grading period"});

        // key dates for summer
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.50", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_ADVANCED_REGISTRATION_PERIOD_TYPE_KEY, "1", "Summer can have an advanced reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.51", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_INSTRUCTIONAL_PERIOD_TYPE_KEY, "2", "Summer can have an instructional period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.52", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, "3", "Summer can have an reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.53", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_REGISTRATION_BEGINS_TRANSFER_TYPE_KEY, "4", "Summer can transfer reg period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.54", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_DROP_DEADLINE_WITHOUT_RECORD_TYPE_KEY, "6", "Summer can have drop deadline"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.55", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_FINAL_EXAM_PERIOD_TYPE_KEY, "6", "Summer can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.56", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_SUMMER_TYPE_KEY, AtpServiceConstants.MILESTONE_GRADES_DUE_TYPE_KEY, "7", "Summer can have a grading period"});

        //keydates group
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.60", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.milestone.type.group.instructional", "1", "Summer can have a final exam period"});
        allowedArrays.add(new String[]{"kuali.atp.type.type.relation.allowed.61", TypeServiceConstants.TYPE_TYPE_RELATION_ALLOWED_TYPE_KEY, AtpServiceConstants.ATP_FALL_TYPE_KEY, "kuali.milestone.type.group.registration", "2", "Summer can have a grading period"});


        for (String[] allowedArray : allowedArrays) {
            associate(allowedArray[0], allowedArray[1], allowedArray[2], allowedArray[3], allowedArray[4], allowedArray[5]);
        }

        // Population Rule Types Grouping

        Set<TypeInfo> populationRuleGroup = new HashSet<TypeInfo>();
        TypeInfo populationRuleGroupType = createTypeInfo(PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "Group for Population Rule", "Group for Population Rule", PopulationServiceConstants.REF_OBJECT_URI_POPULATION_RULE);

        populationRuleGroup.add(getType(PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY));
        populationRuleGroup.add(getType(PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY));
        populationRuleGroup.add(getType(PopulationServiceConstants.POPULATION_RULE_TYPE_SEARCH_KEY));
        populationRuleGroup.add(getType(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY));
        populationRuleGroup.add(getType(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY));
        populationRuleGroup.add(getType(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY));

        for (TypeInfo type : populationRuleGroup) {
            createTypeTypeRelationInfo(populationRuleGroupType, type);
        }

        // Population Types Grouping
        Set<TypeInfo> populationGroup = new HashSet<TypeInfo>();
        TypeInfo populationGroupType = createTypeInfo("kuali.population.population.type", "Group for Population", "Group for Population", PopulationServiceConstants.REF_OBJECT_URI_POPULATION);

        populationGroup.add(getType(PopulationServiceConstants.POPULATION_TYPE_KEY));
        populationGroup.add(getType(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY));

        for (TypeInfo type : populationGroup) {
            createTypeTypeRelationInfo(populationGroupType, type);
        }

        //PopulationCategory type
        Set<TypeInfo> populationCategoryGroup = new HashSet<TypeInfo>();
        TypeInfo populationCategoryGroupType = createTypeInfo("kuali.population.population.category.type", "Group for Population Category", "Group for Population Category", PopulationServiceConstants.REF_OBJECT_URI_POPULATION);

        populationCategoryGroup.add(getType(PopulationServiceConstants.POPULATION_CATEGORY_TYPE_KEY));

        for (TypeInfo type : populationCategoryGroup) {
            createTypeTypeRelationInfo(populationCategoryGroupType, type);
        }

        /**
        // Add type attributes for instructional days
        addInstructionalDayAttribute(allTypes.get(AtpServiceConstants.ATP_FALL_TYPE_KEY), AtpServiceConstants.ATP_FALL_TYPE_KEY);
        addInstructionalDayAttribute(allTypes.get(AtpServiceConstants.ATP_SPRING_TYPE_KEY), AtpServiceConstants.ATP_SPRING_TYPE_KEY);
        addInstructionalDayAttribute(allTypes.get(AtpServiceConstants.ATP_SUMMER_TYPE_KEY), AtpServiceConstants.ATP_SUMMER_TYPE_KEY);
        addInstructionalDayAttribute(allTypes.get(AtpServiceConstants.ATP_SUMMER1_TYPE_KEY), AtpServiceConstants.ATP_SUMMER1_TYPE_KEY);
        addInstructionalDayAttribute(allTypes.get(AtpServiceConstants.ATP_SUMMER2_TYPE_KEY), AtpServiceConstants.ATP_SUMMER2_TYPE_KEY);
        addInstructionalDayAttribute(allTypes.get(AtpServiceConstants.ATP_WINTER_TYPE_KEY), AtpServiceConstants.ATP_WINTER_TYPE_KEY);
         **/
    }

    private void addInstructionalDayAttribute(TypeInfo typeInfo, String parentAtp){
        if(typeInfo != null){
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        AttributeInfo attrInfo = new AttributeInfo();
        attrInfo.setId("instr_day_for_" + parentAtp);
        attrInfo.setKey(TypeServiceConstants.ATP_TERM_INSTRUCTIONAL_DAYS_ATTR);
        attrInfo.setValue("MTWHF");
        attributes.add(attrInfo);

        typeInfo.setAttributes(attributes);
        }


    }

    private TypeInfo createTypeInfo(String typeKey, String typeName, String descr, String refObjectUri) {
        TypeInfo type = new TypeInfo();
        type.setKey(typeKey);
        type.setName(typeName);
        type.setDescr(new RichTextHelper().fromPlain(descr));
        type.setRefObjectUri(refObjectUri);
        type.setEffectiveDate(new Date());
        allTypes.put(type.getKey(), type);
        return type;
    }

    private TypeTypeRelationInfo createTypeTypeRelationInfo(TypeInfo ownerType, TypeInfo relatedType) {
        return createTypeTypeRelationInfo(ownerType.getKey(), relatedType.getKey());
    }

    private TypeTypeRelationInfo createTypeTypeRelationInfo(String ownerTypeKey, String relatedTypeKey) {
        TypeTypeRelationInfo relation = new TypeTypeRelationInfo();
        relation.setOwnerTypeKey(ownerTypeKey);
        relation.setRelatedTypeKey(relatedTypeKey);

        Map<String, TypeTypeRelationInfo> relationTypes = relationOwners.get(relation.getOwnerTypeKey());
        if (null == relationTypes) {
            relationTypes = (Map) new HashMap<String, Map<String, TypeTypeRelationInfo>>();
            relationOwners.put(relation.getOwnerTypeKey(), relationTypes);
        }
        relationTypes.put(relation.getRelatedTypeKey(), relation);

        return relation;
    }

    private void associate(String relationKey, String relationTypeKey, String ownerTypeKey, String relatedTypeKey, String relationRank, String relationName) {
        TypeTypeRelationInfo relation = new TypeTypeRelationInfo();

        relation.setTypeKey(relationTypeKey);
        relation.setOwnerTypeKey(ownerTypeKey);
        relation.setRelatedTypeKey(relatedTypeKey);
        relation.setRank(Integer.parseInt(relationRank));

        Map<String, TypeInfo> types = allowedTypes.get(relation.getOwnerTypeKey());
        if (null == types) {
            types = (Map) new HashMap<String, Map<String, TypeInfo>>();
            allowedTypes.put(relation.getOwnerTypeKey(), types);
        }
        TypeInfo relatedType = allTypes.get(relation.getRelatedTypeKey());
        types.put(relation.getRelatedTypeKey(), relatedType);
    }

    @Override
    public List<String> searchForTypeKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForTypeTypeRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeTypeRelationInfo> searchForTypeTypeRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TypeInfo> searchForTypes(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
