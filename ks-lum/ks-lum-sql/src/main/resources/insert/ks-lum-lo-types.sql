insert into KSLO_LO_CATEGORY_TYPE (ID, DESCR, EFF_DT, EXPIR_DT, NAME) values ('loCategoryType.accreditation', 'Accreditation', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'Accreditation');
insert into KSLO_LO_CATEGORY_TYPE (ID, DESCR, EFF_DT, EXPIR_DT, NAME) values ('loCategoryType.skillarea', 'Skill', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'Skill');
insert into KSLO_LO_CATEGORY_TYPE (ID, DESCR, EFF_DT, EXPIR_DT, NAME) values ('loCategoryType.subject', 'Subject', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'Subject');

insert into KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) values ('RICHTEXT-10', '<p>Learning objectives defined by faculty that are specific to a course</p>', 'Learning objectives defined by faculty that are specific to a course');

insert into KSLO_LO_REPOSITORY (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, LO_ROOT_ID) values ('kuali.loRepository.key.singleUse', null, null, null, null, 1, {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'singleUse', 'RICHTEXT-10', null);

insert into KSLO_LO_TYPE (ID, DESCR, EFF_DT, EXPIR_DT, NAME) values ('kuali.lo.type.governed', 'LO governed by an organization external to department, e.g., the college at large, or a state or accrediting organization', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'Governed');
insert into KSLO_LO_TYPE (ID, DESCR, EFF_DT, EXPIR_DT, NAME) values ('kuali.lo.type.singleUse', 'LO created in support of programs or courses, e.g., faculty-inspired additional LO for a course', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'Single use');

insert into KSLO_LO_RELTN_TYPE (ID, DESCR, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME) values ('kuali.lo.relation.type.inSupportOf', null, {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'inSupportOf', null, 'supports');
insert into KSLO_LO_RELTN_TYPE (ID, DESCR, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME) values ('kuali.lo.relation.type.includes', 'Parent-child relationship between a parent LO and sub LO. Currently used in the context of LOs that are related within a single CLU.', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'includes', 'Child-parent relationship between a child LO and an LO that includes the child.  Currently used in the context of LOs that are related within a single CLU.', 'is-included-by');

insert into KSLO_LO_ALLOWED_RELTN_TYPE (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LO_TYPE_ID, LO_REL_TYPE_ID, LOLO_RELTN_TYPE_ID) values ('KSLO_LO-AllowedRelations1', null, null, null, null, 1, {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},'kuali.lo.type.singleUse', 'kuali.lo.type.singleUse', 'kuali.lo.relation.type.includes');
