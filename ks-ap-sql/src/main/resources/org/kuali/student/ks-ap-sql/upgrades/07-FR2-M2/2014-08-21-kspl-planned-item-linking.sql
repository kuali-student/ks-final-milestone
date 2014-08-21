-- Add unique constraint
ALTER TABLE KSPL_LRNG_PLAN_ITEM_ATTR ADD CONSTRAINT planitemattr_unique UNIQUE (OWNER_ID, ATTR_KEY, ATTR_VALUE)
/

--Add BSCI125 for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','477aba75-feb2-4424-8e25-850c569da554','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Summer1',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '477aba75-feb2-4424-8e25-850c569da554' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '477aba75-feb2-4424-8e25-850c569da554' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F7511E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F7511E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '477aba75-feb2-4424-8e25-850c569da554' and plan.plan_id = 'b.nanalPlan1'))
/

--Add CHEM271 for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F0B11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F7711E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F7711E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', '0113B8F46F0B11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F0B11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F7911E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F7911E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', '0113B8F46F0B11E6E050440A9B9A06D5')
/

--Add CHEM399 for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','0fbf58f5-b9ba-4201-891a-7fca47d17562','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Spring',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '0fbf58f5-b9ba-4201-891a-7fca47d17562' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0fbf58f5-b9ba-4201-891a-7fca47d17562' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F7B11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F7B11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0fbf58f5-b9ba-4201-891a-7fca47d17562' and plan.plan_id = 'b.nanalPlan1'))
/

--Add ENGL388 for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','0e8845d8-8e30-4633-acec-b5b0dbed530f','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Spring',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '0e8845d8-8e30-4633-acec-b5b0dbed530f' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0e8845d8-8e30-4633-acec-b5b0dbed530f' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F7D11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F7D11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0e8845d8-8e30-4633-acec-b5b0dbed530f' and plan.plan_id = 'b.nanalPlan1'))
/

--Add HIST306 for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','0f62820a-068b-460a-ad59-3bdb641cc2ef','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Summer1',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '0f62820a-068b-460a-ad59-3bdb641cc2ef' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0f62820a-068b-460a-ad59-3bdb641cc2ef' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F7F11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F7F11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0f62820a-068b-460a-ad59-3bdb641cc2ef' and plan.plan_id = 'b.nanalPlan1'))
/

--Add PHYS121 for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','515df57a-b171-43ed-a2c6-badaf47b348a','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Summer1',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F8311E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F8311E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F8511E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F8511E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F8711E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F8711E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F8911E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F8911E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.nanalPlan1'))
/

--Add WMST471 for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','a299ff21-6343-4bbf-b426-a951434cae12','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Summer1',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = 'a299ff21-6343-4bbf-b426-a951434cae12' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP') AND plan.plan_id = 'b.nanalPlan1'))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan, KSPL_LRNG_PLAN_ITEM_ATP_ID atp  where plan.REF_OBJ_ID = 'a299ff21-6343-4bbf-b426-a951434cae12' and plan.plan_id = 'b.nanalPlan1' and plan.id = atp.plan_item_id and atp.atp_id = 'kuali.atp.2014Summer1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F8111E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F8111E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan, KSPL_LRNG_PLAN_ITEM_ATP_ID atp  where plan.REF_OBJ_ID = 'a299ff21-6343-4bbf-b426-a951434cae12' and plan.plan_id = 'b.nanalPlan1' and plan.id = atp.plan_item_id and atp.atp_id = 'kuali.atp.2014Summer1'))
/

--Add ENGL329 to BACKUP for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('BACKUP','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','0f97b07a-8ff2-4ff0-a248-c7b2e3382b43','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Spring',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '0f97b07a-8ff2-4ff0-a248-c7b2e3382b43' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0f97b07a-8ff2-4ff0-a248-c7b2e3382b43' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F8B11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F8B11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '0f97b07a-8ff2-4ff0-a248-c7b2e3382b43' and plan.plan_id = 'b.nanalPlan1'))
/

--Add WMST200 to BACKUP for B.NANAL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('BACKUP','B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.nanalPlan1','d2cc869a-5765-47af-b2e3-4de555a1890f','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.NANAL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Fall',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = 'd2cc869a-5765-47af-b2e3-4de555a1890f' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'd2cc869a-5765-47af-b2e3-4de555a1890f' and plan.plan_id = 'b.nanalPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F8D11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F8D11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'd2cc869a-5765-47af-b2e3-4de555a1890f' and plan.plan_id = 'b.nanalPlan1'))
/


--Add BSCI125 for B.JULIL
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '477aba75-feb2-4424-8e25-850c569da554' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F6311E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F6311E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '477aba75-feb2-4424-8e25-850c569da554' and plan.plan_id = 'b.julilPlan1'))
/

--Add CHEM237 for B.JULIL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.julilPlan1','ca1a89d8-075f-4f48-af30-e7775602b3e7','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Spring',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = 'ca1a89d8-075f-4f48-af30-e7775602b3e7' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'ca1a89d8-075f-4f48-af30-e7775602b3e7' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F6511E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F6511E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'ca1a89d8-075f-4f48-af30-e7775602b3e7' and plan.plan_id = 'b.julilPlan1'))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'ca1a89d8-075f-4f48-af30-e7775602b3e7' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F6711E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F6711E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'ca1a89d8-075f-4f48-af30-e7775602b3e7' and plan.plan_id = 'b.julilPlan1'))
/

--Add ENGL379 for B.JULIL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.julilPlan1','5f4ce69e-1133-4b9f-a7ef-017bab10ca03','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Spring',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '5f4ce69e-1133-4b9f-a7ef-017bab10ca03' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '5f4ce69e-1133-4b9f-a7ef-017bab10ca03' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F6911E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F6911E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '5f4ce69e-1133-4b9f-a7ef-017bab10ca03' and plan.plan_id = 'b.julilPlan1'))
/

--Add PHYS121 for B.JULIL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.julilPlan1','515df57a-b171-43ed-a2c6-badaf47b348a','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Summer1',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F6B11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F6B11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '515df57a-b171-43ed-a2c6-badaf47b348a' and plan.plan_id = 'b.julilPlan1'))
/

--Add WMST269 for B.JULIL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.julilPlan1','1ead1a8a-4740-4a0f-8208-e7fcc7d89b26','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Summer1',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = '1ead1a8a-4740-4a0f-8208-e7fcc7d89b26' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '1ead1a8a-4740-4a0f-8208-e7fcc7d89b26' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F6D11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F6D11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = '1ead1a8a-4740-4a0f-8208-e7fcc7d89b26' and plan.plan_id = 'b.julilPlan1'))
/

--Add WMST471 for B.JULIL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('PLANNED','B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.julilPlan1','a299ff21-6343-4bbf-b426-a951434cae12','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Summer1',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = 'a299ff21-6343-4bbf-b426-a951434cae12' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'a299ff21-6343-4bbf-b426-a951434cae12' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F6F11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F6F11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'a299ff21-6343-4bbf-b426-a951434cae12' and plan.plan_id = 'b.julilPlan1'))
/

--Add WMST298 to BACKUP for B.JULIL
INSERT INTO KSPL_LRNG_PLAN_ITEM (CATEGORY,CREATEID,CREATETIME,ID,OBJ_ID,PLAN_ID,REF_OBJ_ID,REF_OBJ_TYPE_KEY,STATE,TYPE_ID,VER_NBR,UPDATEID,UPDATETIME) VALUES
('BACKUP','B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0',sys_guid(),sys_guid(),'b.julilPlan1','da26c791-d69b-484a-a86c-46711ab268ed','kuali.lu.type.CreditCourse','kuali.academicplan.planitem.state.active','kuali.academicplan.type.planitem',0,'B.JULIL',TIMESTAMP '2013-12-25 00:00:00.0')
/
INSERT INTO KSPL_LRNG_PLAN_ITEM_ATP_ID (ATP_ID,PLAN_ITEM_ID) VALUES
('kuali.atp.2014Spring',(SELECT plan.ID FROM KSPL_LRNG_PLAN_ITEM plan WHERE plan.REF_OBJ_ID = 'da26c791-d69b-484a-a86c-46711ab268ed' AND plan.ID not in (SELECT PLAN_ITEM_ID FROM KSPL_LRNG_PLAN_ITEM_ATP_ID) AND (plan.CATEGORY = 'PLANNED' OR plan.CATEGORY = 'BACKUP')))
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'da26c791-d69b-484a-a86c-46711ab268ed' and plan.plan_id = 'b.julilPlan1'), 'kuali.academicplan.item.item.relation.course2rg', '0113B8F46F4B11E6E050440A9B9A06D5')
/

INSERT INTO KSPL_LRNG_PLAN_ITEM_ATTR (ID, OBJ_ID, OWNER_ID, ATTR_KEY, ATTR_VALUE) VALUES
(sys_guid(), sys_guid(), '0113B8F46F4B11E6E050440A9B9A06D5', 'kuali.academicplan.item.item.relation.rg2course', (select plan.id from KSPL_LRNG_PLAN_ITEM plan where plan.REF_OBJ_ID = 'da26c791-d69b-484a-a86c-46711ab268ed' and plan.plan_id = 'b.julilPlan1'))
/