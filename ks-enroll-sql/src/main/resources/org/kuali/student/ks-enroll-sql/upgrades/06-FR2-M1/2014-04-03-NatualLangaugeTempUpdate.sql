--KSENROLL-10691 Adding a new NL usage type matrix and updating the templates accordingly.
--================================================================
--Creating the Matrix
--================================================================
INSERT INTO KRMS_NL_USAGE_T
  (DESC_TXT, NL_USAGE_ID, NM, NMSPC_CD)
VALUES
  ('Kuali Rule Matrix',
   'KS-KRMS-NL-USAGE-1007',
   'kuali.krms.matrix',
   'KS-SYS')
   /
--================================================================
--Updating the existing catalog Template Types to point to Matrix
--================================================================
UPDATE KRMS_NL_TMPL_T T
   SET T.NL_USAGE_ID = 'KS-KRMS-NL-USAGE-1007'
 WHERE T.TYP_ID IN ('KS-KRMS-TYP-55669',
                    'KS-KRMS-TYP-55666',
                    'KS-KRMS-TYP-55670',
                    'KS-KRMS-TYP-55667',
                    'KS-KRMS-TYP-55668',
                    'KS-KRMS-TYP-55671',
                    'KS-KRMS-TYP-55672',
                    'KS-KRMS-TYP-55748')
   AND T.NL_USAGE_ID = 'KS-KRMS-NL-USAGE-1005'
   /
--================================================================
--Creating the different Templates for Matrix and Catalog
--================================================================
--MUST MEET <ONE> FOR CATALOG
INSERT INTO KRMS_NL_TMPL_T
  (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID)
VALUES
  ('en',
   'KS-KRMS-NL-TMPL-55754',
   'KS-KRMS-NL-USAGE-1005',
   'kuali.krms.nl.template.blank',
   '10077')
   /
--MUST MEET <ALL> FOR CATALOG
INSERT INTO KRMS_NL_TMPL_T
  (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID)
VALUES
  ('en',
   'KS-KRMS-NL-TMPL-55755',
   'KS-KRMS-NL-USAGE-1005',
   'kuali.krms.nl.template.blank',
   '10076')
   /
--MUST MEET <ONE> FOR MATRIX
INSERT INTO KRMS_NL_TMPL_T
  (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID)
VALUES
  ('en',
   'KS-KRMS-NL-TMPL-55756',
   'KS-KRMS-NL-USAGE-1007',
   'kuali.krms.nl.template.blank',
   '10077')
   /
--MUST MEET <ALL> FOR MATRIX
INSERT INTO KRMS_NL_TMPL_T
  (LANG_CD, NL_TMPL_ID, NL_USAGE_ID, TMPL, TYP_ID)
VALUES
  ('en',
   'KS-KRMS-NL-TMPL-55757',
   'KS-KRMS-NL-USAGE-1007',
   'kuali.krms.nl.template.blank',
   '10076')
   /
--================================================================
--Creating the attribute
--================================================================
INSERT INTO KRMS_ATTR_DEFN_T
  (ATTR_DEFN_ID, DESC_TXT, LBL, NM, NMSPC_CD)
VALUES
  ('KS-KRMS-ATTR-DERN-10007',
   'operator',
   'Operator',
   'kuali.krms.nl.template.attribute.operator',
   'KS-SYS')
   /
--================================================================
--LINKING the operators
--================================================================
--adding plain AND operator
INSERT INTO KRMS_NL_TMPL_ATTR_T
  (ATTR_DEFN_ID, ATTR_VAL, NL_TMPL_ATTR_ID, NL_TMPL_ID)
VALUES
  ('KS-KRMS-ATTR-DERN-10007',
   'AND',
   'KS-KRMS-NL-TMPL-ATTR-10000',
   'KS-KRMS-NL-TMPL-55755')
   /
--adding break AND operator
INSERT INTO KRMS_NL_TMPL_ATTR_T
  (ATTR_DEFN_ID, ATTR_VAL, NL_TMPL_ATTR_ID, NL_TMPL_ID)
VALUES
  ('KS-KRMS-ATTR-DERN-10007',
   'AND <br>',
   'KS-KRMS-NL-TMPL-ATTR-10001',
   'KS-KRMS-NL-TMPL-55757')
   /
--adding plain OR operator
INSERT INTO KRMS_NL_TMPL_ATTR_T
  (ATTR_DEFN_ID, ATTR_VAL, NL_TMPL_ATTR_ID, NL_TMPL_ID)
VALUES
  ('KS-KRMS-ATTR-DERN-10007',
   'OR',
   'KS-KRMS-NL-TMPL-ATTR-10002',
   'KS-KRMS-NL-TMPL-55754')
   /
--adding break OR operator
INSERT INTO KRMS_NL_TMPL_ATTR_T
  (ATTR_DEFN_ID, ATTR_VAL, NL_TMPL_ATTR_ID, NL_TMPL_ID)
VALUES
  ('KS-KRMS-ATTR-DERN-10007',
   'OR <br>',
   'KS-KRMS-NL-TMPL-ATTR-10003',
   'KS-KRMS-NL-TMPL-55756')
   /di