--Set Graduate studies to be type of college
update KSOR_ORG SET TYPE='kuali.org.College' WHERE ID='26'
/

-- Orgs
insert into KSOR_ORG (ID, VER_NBR, LNG_NAME, SHRT_NAME, ST, TYPE, OBJ_ID, EFF_DT) values ('224', 0, 'Bioengineering Dept ', 'Bioengineering', 'Active', 'kuali.org.Department', '572084a6-06e9-43ef-bd79-b7fe50c40fcc', TO_DATE( '20100701000000', 'YYYYMMDDHH24MISS' ))
/
insert into KSOR_ORG (ID, VER_NBR, LNG_NAME, SHRT_NAME, ST, TYPE, OBJ_ID, EFF_DT) values ('225', 0, 'Bioengineering Dept Curriculum Committee ', 'BioengineeringCOC', 'Active', 'kuali.org.COC', 'b3e8902a-6284-4a7d-b1e2-d6fe19719401', TO_DATE( '20100701000000', 'YYYYMMDDHH24MISS' ))
/
insert into KSOR_ORG (ID, VER_NBR, LNG_NAME, SHRT_NAME, ST, TYPE, OBJ_ID, EFF_DT) values ('226', 0, 'Entomology Dept ', 'Entomology', 'Active', 'kuali.org.Department', '64bf7d03-e592-42f2-aa2f-935937b93e05', TO_DATE( '20100701000000', 'YYYYMMDDHH24MISS' ))
/
insert into KSOR_ORG (ID, VER_NBR, LNG_NAME, SHRT_NAME, ST, TYPE, OBJ_ID, EFF_DT) values ('227', 0, 'Entomology Dept Curriculum Committee ', 'EntomologyCOC', 'Active', 'kuali.org.COC', 'c12a6b9f-2f01-420c-8826-c9684db174b4', TO_DATE( '20100701000000', 'YYYYMMDDHH24MISS' ))
/

-- Org Relationships
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('7dcee63c-899c-4eb6-8e49-8b297d85a098', null, null, 'admin', TIMESTAMP '2011-08-02 16:30:40', 5, TIMESTAMP '1999-12-31 00:00:00', null, null, '31', '224', 'kuali.org.CurriculumParent', '52fc5eea-e6c4-4f41-9187-ff3f6d81c58d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('54496d35-5a9d-4dd3-8ff4-f780f318c599', null, null, 'admin', TIMESTAMP '2011-08-02 16:30:40', 4, TIMESTAMP '1999-12-31 00:00:00', null, null, '31', '224', 'kuali.org.Contain', 'f4748bb7-704a-450b-a5f4-d142f5fe3835')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('12c96088-90fa-40a1-9c5f-c978635abd88', null, null, 'admin', TIMESTAMP '2011-08-02 16:50:45', 1, TIMESTAMP '1999-12-31 00:00:00', null, null, '224', '225', 'kuali.org.CurriculumParent', '8f8ef13c-0508-4f7c-b7f2-104b8ebe5c2f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('97baf1fc-7cf0-47a2-9864-869d8c339b75', null, null, 'admin', TIMESTAMP '2011-08-02 16:47:06', 2, TIMESTAMP '2011-08-01 00:00:00', null, null, '30', '226', 'kuali.org.CurriculumParent', 'f5fa2102-eb53-40fd-8e8d-7e2ef2ac8ad5')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('7052d19b-35c3-41a2-b63d-a543eedfc35c', null, null, 'admin', TIMESTAMP '2011-08-02 16:47:06', 2, TIMESTAMP '2011-08-01 00:00:00', null, null, '30', '226', 'kuali.org.Contain', 'c7a2d9e4-10ae-491a-a4ef-d7a9cfc9fc73')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('708dc7b4-8eb7-44cf-9901-24e9167a3125', null, null, 'admin', TIMESTAMP '2011-08-02 16:49:37', 2, TIMESTAMP '2011-08-01 00:00:00', null, null, '226', '227', 'kuali.org.CurriculumParent', '616a44c5-9ed5-43ec-901f-a8300838466e')
/

-- Posistion Restrictions
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID) values ('c06cf5c8-d6fc-459c-aedb-e74aa9de451f', 'admin', TIMESTAMP '2011-08-02 16:50:45', 'admin', TIMESTAMP '2011-08-02 16:50:45', 0, 'Chair of Bioengineering COC', '100', 1, null, null, 'Chair of Bioengineering COC', '225', 'kuali.org.PersonRelation.Chair', '1115f9d0-1b5c-4d13-ae3c-2d71bea5bf99')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID) values ('7bb96587-9e0b-4fed-b3f5-08a1e83cf347', 'admin', TIMESTAMP '2011-08-02 16:50:45', 'admin', TIMESTAMP '2011-08-02 16:50:45', 0, 'Member of Bioengineering COC', '100', 1, null, null, 'Member of Bioengineering COC', '225', 'kuali.org.PersonRelation.Member', '7e9824bb-6f99-47ea-b246-baf1240c15ca')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID) values ('22644949-b363-449d-aefd-e4003eb00dab', 'admin', TIMESTAMP '2011-08-02 16:49:37', 'admin', TIMESTAMP '2011-08-02 16:49:37', 0, 'Chair of the Entomology COC', '100', 1, null, null, 'Chair of the Entomology COC', '227', 'kuali.org.PersonRelation.Chair', '1cff7894-2ebc-4db4-a6f6-a42727f20c81')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID) values ('12a24267-099d-4db3-a764-3db49fb02a34', 'admin', TIMESTAMP '2011-08-02 16:49:37', 'admin', TIMESTAMP '2011-08-02 16:49:37', 0, 'Member of the Entomology COC', '100', 1, null, null, 'Member of the Entomology COC', '227', 'kuali.org.PersonRelation.Member', 'e11564b0-183d-4267-9d3a-829c10ac7aee')
/

-- Memberships
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('d86c8a4b-9e63-4697-a4f6-4ddeb6e03bc7', 'admin', TIMESTAMP '2011-08-03 14:15:35', 'admin', TIMESTAMP '2011-08-03 14:15:35', 0, TIMESTAMP '2011-08-01 00:00:00', null, 'testuser7', null, '215', 'kuali.org.PersonRelation.Chair', '2bb6bdbb-655b-4d10-837e-375f7286027d')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('4d8b8dc3-4734-4daa-a002-1375a9408dc3', 'admin', TIMESTAMP '2011-08-03 14:15:35', 'admin', TIMESTAMP '2011-08-03 14:15:35', 0, TIMESTAMP '2011-08-01 00:00:00', null, 'testuser8', null, '215', 'kuali.org.PersonRelation.Member', 'ac8bb02e-ee42-4680-b35b-b6cda90032e7')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('b08edadd-34d2-46ec-bbc8-1e8f3be36982', 'admin', TIMESTAMP '2011-08-03 14:16:44', 'admin', TIMESTAMP '2011-08-03 14:16:44', 0, null, null, 'testuser9', null, '174', 'kuali.org.PersonRelation.Chair', '3d313444-8cdd-4cc2-b9f0-49db6d390263')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('a51dcc09-9dbc-490f-869b-4187e1f25b26', 'admin', TIMESTAMP '2011-08-03 14:16:44', 'admin', TIMESTAMP '2011-08-03 14:16:44', 0, null, null, 'testuser10', null, '174', 'kuali.org.PersonRelation.Member', 'c8a68d02-7360-4402-bf1b-6b8473dea1c5')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('dcaac3cc-ed85-445a-a0ff-ee0e5341b3fe', 'admin', TIMESTAMP '2011-08-03 14:17:52', 'admin', TIMESTAMP '2011-08-03 14:17:52', 0, null, null, 'user5', null, '152', 'kuali.org.PersonRelation.Chair', 'c414382b-551b-4069-af22-26b526fcea8a')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('70d237f3-7ec3-444f-95e8-993749115454', 'admin', TIMESTAMP '2011-08-03 14:17:52', 'admin', TIMESTAMP '2011-08-03 14:17:52', 0, null, null, 'user6', null, '152', 'kuali.org.PersonRelation.Member', '887b3299-a73f-4eb2-b4f9-faff14e19acd')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('2f703cc6-2372-469d-ac3d-a66fa31ff9a0', 'admin', TIMESTAMP '2011-08-03 14:20:48', 'admin', TIMESTAMP '2011-08-03 14:20:48', 0, TIMESTAMP '2011-08-01 00:00:00', null, 'user7', null, '143', 'kuali.org.PersonRelation.Chair', '12efb943-bf12-4bbe-aa42-82d8e8b2dbcc')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('40087561-7727-47a6-955f-f2bdff030499', 'admin', TIMESTAMP '2011-08-03 14:20:48', 'admin', TIMESTAMP '2011-08-03 14:20:48', 0, TIMESTAMP '2011-08-01 00:00:00', null, 'user8', null, '143', 'kuali.org.PersonRelation.Member', '550d21ee-37ab-4f9f-a785-0b16903a1da5')
/
