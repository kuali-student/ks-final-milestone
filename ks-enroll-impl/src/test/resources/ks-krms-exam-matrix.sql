--
--Insert Statement
--Source : MySQL documentation
--

DELETE FROM krms_term_parm_t WHERE term_parm_id IN ('KS-KRMS-TERM-PARM-13467')
/
DELETE FROM krms_term_t WHERE term_id IN ('KS-KRMS-TERM-13422')
/
DELETE FROM krms_term_spec_t WHERE term_spec_id IN ('KS-KRMS-TERM-SPEC-10017')
/
DELETE FROM krms_prop_parm_t WHERE prop_parm_id IN ('KS-KRMS-PROP-PARM-20258', 'KS-KRMS-PROP-PARM-20259', 'KS-KRMS-PROP-PARM-20260')
/
DELETE FROM krms_actn_attr_t WHERE actn_attr_data_id IN ('KS-KRMS-ACTN-ATTR-10000', 'KS-KRMS-ACTN-ATTR-10001', 'KS-KRMS-ACTN-ATTR-10002')
/
DELETE FROM krms_actn_t WHERE actn_id IN ('KS-KRMS-ACTN-10000')
/
DELETE FROM krms_attr_defn_t WHERE attr_defn_id IN ('KS-KRMS-ATTR-DEFN-10000', 'KS-KRMS-ATTR-DEFN-10001', 'KS-KRMS-ATTR-DEFN-10002')
/
DELETE FROM krms_rule_t WHERE rule_id IN ('KS-KRMS-RULE-12037')
/
DELETE FROM krms_prop_t WHERE prop_id IN ('KS-KRMS-PROP-14726')
/
DELETE FROM krms_typ_t WHERE typ_id IN ('KS-KRMS-TYP-10081', 'KS-KRMS-TYP-10082', 'KS-KRMS-TYP-10083')
/

INSERT INTO krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr)
	VALUES ('KS-KRMS-TYP-10081', 'Action Type', 'EXAM-POC', 'actionTypeService', 'Y', 0)
/
INSERT INTO krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr)
	VALUES ('KS-KRMS-TYP-10082', 'Rule Type', 'EXAM-POC', 'ruleTypeService', 'Y', 0)
/
INSERT INTO krms_typ_t (typ_id, nm, nmspc_cd, srvc_nm, actv, ver_nbr)
	VALUES ('KS-KRMS-TYP-10083', 'Simple Proposition Type', 'EXAM-POC', 'simplePropositionTypeService', 'Y', 0)
/

INSERT INTO krms_rule_t (rule_id, nmspc_cd, nm, typ_id, prop_id, actv, ver_nbr, desc_txt)
    VALUES ('KS-KRMS-RULE-12037', 'EXAM-POC', 'Match Time Slot Rule', 'KS-KRMS-TYP-10082', NULL, 'Y', 0, 'Match Time Slot Rule')
/
	
INSERT INTO krms_prop_t (prop_id, desc_txt, typ_id, dscrm_typ_cd, cmpnd_op_cd, rule_id, ver_nbr, cmpnd_seq_no)
	VALUES ('KS-KRMS-PROP-14726', 'Must have ADL for given time slot parameters', 'KS-KRMS-TYP-10083', 'S', NULL, 'KS-KRMS-RULE-12037', 1, NULL)
/

UPDATE krms_rule_t SET prop_id = 'KS-KRMS-PROP-14726' WHERE rule_id = 'KS-KRMS-RULE-12037'
/

INSERT INTO krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) VALUES ('KS-KRMS-PROP-PARM-20258', 'KS-KRMS-PROP-14726', 'KS-KRMS-TERM-13422', 'T', 1, 1)
/
INSERT INTO krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) VALUES ('KS-KRMS-PROP-PARM-20259', 'KS-KRMS-PROP-14726', 'true', 'C', 2, 1)
/
INSERT INTO krms_prop_parm_t (prop_parm_id, prop_id, parm_val, parm_typ_cd, seq_no, ver_nbr) VALUES ('KS-KRMS-PROP-PARM-20260', 'KS-KRMS-PROP-14726', '=', 'O', 3, 1)
/

INSERT INTO krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
	VALUES ('KS-KRMS-TERM-SPEC-10017', 'matchingTimeSlot', 'java.lang.Boolean', 'Y', 1, 'Match the linked time slot to given parameters', 'EXAM-POC')
/
	
INSERT INTO krms_term_t (term_id, term_spec_id, ver_nbr, desc_txt) VALUES ('KS-KRMS-TERM-13422', 'KS-KRMS-TERM-SPEC-10017', 1, 'TimeSlot: MWF 9am - 12am')
/
	
INSERT INTO krms_term_parm_t (term_parm_id, term_id, nm, val, ver_nbr) VALUES ('KS-KRMS-TERM-PARM-13467', 'KS-KRMS-TERM-13422', 'kuali.term.parameter.type.timeslot.weekday.string', 'MWF', 1)
/

INSERT INTO krms_attr_defn_t (actv, attr_defn_id, cmpnt_nm, desc_txt, lbl, nm, nmspc_cd, ver_nbr)
  VALUES ('Y', 'KS-KRMS-ATTR-DEFN-10000', null, 'Weekdays list', 'Weekdays list', 'weekdays', 'EXAM-POC', 1)
/
INSERT INTO krms_attr_defn_t (actv, attr_defn_id, cmpnt_nm, desc_txt, lbl, nm, nmspc_cd, ver_nbr)
  VALUES ('Y', 'KS-KRMS-ATTR-DEFN-10001', null, 'Start Time', 'Start Time', 'startTime', 'EXAM-POC', 1)
/
INSERT INTO krms_attr_defn_t (actv, attr_defn_id, cmpnt_nm, desc_txt, lbl, nm, nmspc_cd, ver_nbr)
  VALUES ('Y', 'KS-KRMS-ATTR-DEFN-10002', null, 'End Time', 'End Time', 'endTime', 'EXAM-POC', 1)
/

INSERT INTO krms_actn_t (actn_id, desc_txt, nm, nmspc_cd, rule_id, seq_no, typ_id, ver_nbr)
  VALUES ('KS-KRMS-ACTN-10000', 'Create requested delivery logistic', 'createRDL', 'EXAM-POC', 'KS-KRMS-RULE-12037', 1, 'KS-KRMS-TYP-10081', 1)
/

INSERT INTO krms_actn_attr_t (actn_attr_data_id, actn_id, attr_defn_id, attr_val, ver_nbr)
  VALUES ('KS-KRMS-ACTN-ATTR-10000', 'KS-KRMS-ACTN-10000', 'KS-KRMS-ATTR-DEFN-10000', 'M', 1)
/
INSERT INTO krms_actn_attr_t (actn_attr_data_id, actn_id, attr_defn_id, attr_val, ver_nbr)
  VALUES ('KS-KRMS-ACTN-ATTR-10001', 'KS-KRMS-ACTN-10000', 'KS-KRMS-ATTR-DEFN-10001', '45000000', 1)
/
INSERT INTO krms_actn_attr_t (actn_attr_data_id, actn_id, attr_defn_id, attr_val, ver_nbr)
  VALUES ('KS-KRMS-ACTN-ATTR-10002', 'KS-KRMS-ACTN-10000', 'KS-KRMS-ATTR-DEFN-10002', '49500000', 1)
/