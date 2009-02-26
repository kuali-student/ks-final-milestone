package org.kuali.student.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.entity.RichText;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LrTypeInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.lum.lu.dto.LuDocRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuLuRelationTypeInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.dto.LuiInfo;
import org.kuali.student.lum.lu.dto.LuiLuiRelationInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldTypeInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.entity.Clu;
import org.kuali.student.lum.lu.entity.CluCluRelation;
import org.kuali.student.lum.lu.entity.CluSet;
import org.kuali.student.lum.lu.entity.LrType;
import org.kuali.student.lum.lu.entity.LuDocumentRelation;
import org.kuali.student.lum.lu.entity.LuDocumentRelationType;
import org.kuali.student.lum.lu.entity.LuLuRelationType;
import org.kuali.student.lum.lu.entity.LuStatement;
import org.kuali.student.lum.lu.entity.LuStatementType;
import org.kuali.student.lum.lu.entity.LuType;
import org.kuali.student.lum.lu.entity.Lui;
import org.kuali.student.lum.lu.entity.LuiLuiRelation;
import org.kuali.student.lum.lu.entity.ReqComponent;
import org.kuali.student.lum.lu.entity.ReqComponentField;
import org.kuali.student.lum.lu.entity.ReqComponentFieldType;
import org.kuali.student.lum.lu.entity.ReqComponentType;
import org.springframework.beans.BeanUtils;

public class LuServiceAssembler extends BaseAssembler {

	public static List<CluCluRelationInfo> toCluCluRelationInfos(
			List<CluCluRelation> entities) {
		List<CluCluRelationInfo> dtos = new ArrayList<CluCluRelationInfo>(
				entities.size());
		for (CluCluRelation entity : entities) {
			dtos.add(toCluCluRelationInfo(entity));
		}
		return dtos;

	}

	public static CluCluRelationInfo toCluCluRelationInfo(CluCluRelation entity) {
		CluCluRelationInfo dto = new CluCluRelationInfo();
		BeanUtils.copyProperties(entity, dto,
				new String[] { "cluId", "relatedCluId", "cluRelationRequired",
						"attributes", "metaInfo" });

		dto.setIsCluRelationRequired(entity.isCluRelationRequired());
		dto.setCluId(entity.getClu().getId());
		dto.setRelatedCluId(entity.getRelatedClu().getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(),entity.getVersionInd()));

		return dto;

	}

	public List<CluInfo> toCluInfos(List<Clu> entities) {
		List<CluInfo> dtos = new ArrayList<CluInfo>(entities.size());
		for (Clu entity : entities) {
			dtos.add(toCluInfo(entity));
		}
		return dtos;

	}

	public static CluInfo toCluInfo(Clu entity) {
		CluInfo dto = new CluInfo();

		// TODO Fill in

		return dto;

	}

	public static List<CluSetInfo> toCluSetInfos(List<CluSet> entities) {
		List<CluSetInfo> dtos = new ArrayList<CluSetInfo>(entities.size());
		for (CluSet entity : entities) {
			dtos.add(toCluSetInfo(entity));
		}
		return dtos;

	}

	public static CluSetInfo toCluSetInfo(CluSet entity) {
		CluSetInfo dto = new CluSetInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "desc",
				"cluCriteria", "cluSets", "clus", "attributes", "metaInfo" });

		dto.setDesc(toRichTextInfo(entity.getDesc()));
		// TODO dto.setCluCriteria()
		List<String> cluSetIds = new ArrayList<String>(entity.getCluSets()
				.size());
		for (CluSet id : entity.getCluSets()) {
			cluSetIds.add(id.getId());
		}
		dto.setCluSetIds(cluSetIds);

		List<String> cluIds = new ArrayList<String>(entity.getClus().size());
		for (Clu id : entity.getClus()) {
			cluIds.add(id.getId());
		}
		dto.setCluIds(cluIds);

		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;

	}

	public static List<LrTypeInfo> toLrTypeInfos(List<LrType> entities) {
		List<LrTypeInfo> dtos = new ArrayList<LrTypeInfo>(entities.size());
		for (LrType entity : entities) {
			dtos.add(toLrTypeInfo(entity));
		}
		return dtos;

	}

	public static LrTypeInfo toLrTypeInfo(LrType entity) {
		LrTypeInfo dto = new LrTypeInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "id", "attributes" });

		dto.setKey(entity.getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;

	}

	public static List<LuDocRelationTypeInfo> toLuDocRelationType(
			List<LuDocumentRelationType> entities) {
		List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
				entities.size());
		for (LuDocumentRelationType entity : entities) {
			dtos.add(toLuDocRelationTypeInfo(entity));
		}
		return dtos;

	}

	public static LuDocRelationInfo toLuDocRelationInfo(
			LuDocumentRelation entity) {
		LuDocRelationInfo dto = new LuDocRelationInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "desc",
				"luDocumentRelationType", "attributes", "metInfo" });

		dto.setCluId(entity.getClu().getId());
		dto.setDesc(toRichTextInfo(entity.getDesc()));
		dto.setType(entity.getLuDocumentRelationType().getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static List<LuDocRelationTypeInfo> toLuDocRelationTypeInfos(
			List<LuDocumentRelationType> entities) {
		List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
				entities.size());
		for (LuDocumentRelationType entity : entities) {
			dtos.add(toLuDocRelationTypeInfo(entity));
		}
		return dtos;

	}

	public static List<LuDocRelationTypeInfo> toLuDocumentRelationTypes(
			List<LuDocumentRelationType> entities) {
		List<LuDocRelationTypeInfo> dtos = new ArrayList<LuDocRelationTypeInfo>(
				entities.size());
		for (LuDocumentRelationType entity : entities) {
			dtos.add(toLuDocRelationTypeInfo(entity));
		}
		return dtos;
	}

	public static LuDocRelationTypeInfo toLuDocRelationTypeInfo(
			LuDocumentRelationType entity) {
		LuDocRelationTypeInfo dto = new LuDocRelationTypeInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "id", "attributes" });

		dto.setKey(entity.getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static List<LuLuRelationTypeInfo> toLuLuRelationTypeInfos(
			List<LuLuRelationType> entities) {
		List<LuLuRelationTypeInfo> dtos = new ArrayList<LuLuRelationTypeInfo>(
				entities.size());
		for (LuLuRelationType entity : entities) {
			dtos.add(toLuLuRelationTypeInfo(entity));
		}
		return dtos;

	}

	public static LuLuRelationTypeInfo toLuLuRelationTypeInfo(
			LuLuRelationType entity) {
		LuLuRelationTypeInfo dto = new LuLuRelationTypeInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "id", "attributes" });

		dto.setKey(entity.getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static List<LuStatementInfo> toLuStatementInfos(
			List<LuStatement> entities) {
		List<LuStatementInfo> dtos = new ArrayList<LuStatementInfo>(entities
				.size());
		for (LuStatement entity : entities) {
			dtos.add(toLuStatementInfo(entity));
		}
		return dtos;

	}

	public static LuStatementInfo toLuStatementInfo(LuStatement entity) {
		LuStatementInfo dto = new LuStatementInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "children",
				"requiredComponents", "luStatementType", "attributes", "metaInfo" });

		List<String> statementIds = new ArrayList<String>(entity.getChildren().size());
		for (LuStatement statement : entity.getChildren()) {
			statementIds.add(statement.getId());
		}
		dto.setLuStatementIds(statementIds);

		List<String> componentIds = new ArrayList<String>(entity.getRequiredComponents().size());
		for (ReqComponent reqComponent : entity.getRequiredComponents()) {
			componentIds.add(reqComponent.getId());
		}
		dto.setReqComponentIds(componentIds);
		dto.setType(entity.getLuStatementType().getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static List<LuStatementTypeInfo> toLuStatementTypeInfos(
			List<LuStatementType> entities) {
		List<LuStatementTypeInfo> dtos = new ArrayList<LuStatementTypeInfo>(
				entities.size());
		for (LuStatementType entity : entities) {
			dtos.add(toLuStatementTypeInfo(entity));
		}
		return dtos;

	}

	public static LuStatementTypeInfo toLuStatementTypeInfo(
			LuStatementType entity) {
		LuStatementTypeInfo dto = new LuStatementTypeInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "id", "luTypes" });

		return dto;
	}

	public static List<LuTypeInfo> toLuTypeInfos(List<LuType> entities) {
		List<LuTypeInfo> dtos = new ArrayList<LuTypeInfo>(entities.size());
		for (LuType entity : entities) {
			dtos.add(toLuTypeInfo(entity));
		}
		return dtos;
	}

	public static LuTypeInfo toLuTypeInfo(LuType entity) {
		LuTypeInfo dto = new LuTypeInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "id", "attributes" });

		dto.setKey(entity.getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));


		return dto;
	}

	public static List<LuiInfo> toLuiInfos(List<Lui> entities) {
		List<LuiInfo> dtos = new ArrayList<LuiInfo>(entities.size());
		for (Lui entity : entities) {
			dtos.add(toLuiInfo(entity));
		}
		return dtos;

	}

	public static LuiInfo toLuiInfo(Lui entity) {
		LuiInfo dto = new LuiInfo();


		BeanUtils.copyProperties(entity, dto,
				new String[] { "clu", "atpId", "attributes" });

		dto.setCluId(entity.getClu().getId());
		dto.setAtpKey(entity.getAtpId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static List<LuiLuiRelationInfo> toLuiLuiRelationInfos(
			List<LuiLuiRelation> entities) {
		List<LuiLuiRelationInfo> dtos = new ArrayList<LuiLuiRelationInfo>(
				entities.size());
		for (LuiLuiRelation entity : entities) {
			dtos.add(toLuiLuiRelationInfo(entity));
		}
		return dtos;

	}

	public static LuiLuiRelationInfo toLuiLuiRelationInfo(LuiLuiRelation entity) {
		LuiLuiRelationInfo dto = new LuiLuiRelationInfo();


		BeanUtils.copyProperties(entity, dto,
				new String[] { "lui", "relatedLui", "attributes" });

		dto.setLuiId(entity.getLui().getId());
		dto.setRelatedLuiId(entity.getRelatedLui().getId());
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static List<ReqComponentTypeInfo> toReqComponentInfo(
			List<ReqComponentType> entities) {
		List<ReqComponentTypeInfo> dtos = new ArrayList<ReqComponentTypeInfo>(
				entities.size());
		for (ReqComponentType entity : entities) {
			dtos.add(toReqComponentTypeInfo(entity));
		}
		return dtos;

	}

	public static ReqComponentInfo toReqComponentInfo(ReqComponent entity) {
		ReqComponentInfo dto = new ReqComponentInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "requiredComponentType", "reqCompField", "meteInfo" });

		dto.setType(entity.getRequiredComponentType().getId());
		dto.setReqCompField(toReqCompFieldInfos(entity.getReqCompField()));
		dto.setMetaInfo(toMetaInfo(entity.getMeta(), entity.getVersionInd()));

		return dto;
	}

	public static ReqComponentTypeInfo toReqComponentTypeInfo(
			ReqComponentType entity) {
		ReqComponentTypeInfo dto = new ReqComponentTypeInfo();

		BeanUtils.copyProperties(entity, dto,
				new String[] { "id", "reqCompFieldTypes", "attributes" });

		dto.setReqCompFieldTypes(toReqCompFieldTypeInfos(entity.getReqCompFieldTypes()));
		dto.setAttributes(toAttributeMap(entity.getAttributes()));

		return dto;
	}

	public static RichTextInfo toRichTextInfo(RichText entity) {
		RichTextInfo dto = new RichTextInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "id" });

		return dto;

	}

	public static List<ReqCompFieldInfo> toReqCompFieldInfos(List<ReqComponentField> entities) {
		List<ReqCompFieldInfo> fields = new ArrayList<ReqCompFieldInfo>(entities.size());
		for (ReqComponentField field : entities) {
			fields.add(toReqCompFieldInfo(field));
		}
		return fields;
	}

	public static ReqCompFieldInfo toReqCompFieldInfo(ReqComponentField entity) {
		ReqCompFieldInfo dto = new ReqCompFieldInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "id" });

		return dto;
	}

	public static List<ReqCompFieldTypeInfo> toReqCompFieldTypeInfos(List<ReqComponentFieldType> entities) {
		List<ReqCompFieldTypeInfo> fields = new ArrayList<ReqCompFieldTypeInfo>(entities.size());
		for (ReqComponentFieldType field : entities) {
			fields.add(toReqCompFieldTypeInfo(field));
		}
		return fields;
	}

	public static ReqCompFieldTypeInfo toReqCompFieldTypeInfo(ReqComponentFieldType entity) {
		ReqCompFieldTypeInfo dto = new ReqCompFieldTypeInfo();

		BeanUtils.copyProperties(entity, dto, new String[] { "id" });
		// TODO dto.setFieldDescriptor(fieldDescriptor)

		return dto;
	}
}
