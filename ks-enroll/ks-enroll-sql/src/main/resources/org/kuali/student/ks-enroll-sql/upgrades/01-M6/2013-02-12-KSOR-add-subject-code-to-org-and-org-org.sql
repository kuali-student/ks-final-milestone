-- KSENROLL-4979 Create Org type and Org Org type for Subject Codes

-- Add org type
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.SubjectCode', 'Subject code acting as organization', to_date('2013-02-06', 'YYYY-MM-DD'), to_date('2100-02-06', 'YYYY-MM-DD'), 'Subject Code')
/

-- Add Subject Code Orgs
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-AASP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'African American Studies', 'African American Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'African American Studies', 'AASP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-AAST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Asian American Studies', 'Asian American Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Asian American Studies', 'AAST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-AGNR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Agriculture and Natural Resources', 'Agriculture and Natural Resources', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Agriculture and Natural Resources', 'AGNR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-AMSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Applied Mathematics & Scientific Computation', 'Applied Mathematics & Scientific Computation', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Applied Mathematics & Scientific Computation', 'AMSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-AMST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'American Studies', 'American Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'American Studies', 'AMST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ANSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Animal Science', 'Animal Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Animal Science', 'ANSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ANTH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Anthropology', 'Anthropology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Anthropology', 'ANTH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-AOSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Atmospheric and Oceanic Science', 'Atmospheric and Oceanic Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Atmospheric and Oceanic Science', 'AOSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARAB', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Arabic', 'Arabic', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Arabic', 'ARAB', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARCH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Architecture', 'Architecture', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Architecture', 'ARCH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-AREC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Agricultural and Resource Economics', 'Agricultural and Resource Economics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Agricultural and Resource Economics', 'AREC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARHU', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Arts and Humanities', 'Arts and Humanities', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Arts and Humanities', 'ARHU', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARMY', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Army', 'Army', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Army', 'ARMY', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Air Science', 'Air Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Air Science', 'ARSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARTH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Art History & Archaeology', 'Art History & Archaeology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Art History & Archaeology', 'ARTH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARTM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'The Arts at Maryland', 'The Arts at Maryland', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'The Arts at Maryland', 'ARTM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ARTT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Art Studio', 'Art Studio', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Art Studio', 'ARTT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ASTR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Astronomy', 'Astronomy', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Astronomy', 'ASTR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BCHM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Biochemistry', 'Biochemistry', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Biochemistry', 'BCHM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BEES', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Behavior, Ecology, Evolution and Systematics', 'Behavior, Ecology, Evolution and Systematics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Behavior, Ecology, Evolution and Systematics', 'BEES', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BIOE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Bioengineering', 'Bioengineering', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Bioengineering', 'BIOE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BIOL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Biology', 'Biology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Biology', 'BIOL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BIOM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Biometrics', 'Biometrics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Biometrics', 'BIOM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BIPH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Biophysics', 'Biophysics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Biophysics', 'BIPH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BISI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Biological Sciences', 'Biological Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Biological Sciences', 'BISI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BMGT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Business and Management', 'Business and Management', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Business and Management', 'BMGT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BSCI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Biological Sciences Program', 'Biological Sciences Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Biological Sciences Program', 'BSCI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BSCV', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'CIVICUS', 'CIVICUS', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'CIVICUS', 'BSCV', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BSGC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Global Communities', 'Global Communities', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Global Communities', 'BSGC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BSOS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Behavioral and Social Sciences', 'Behavioral and Social Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Behavioral and Social Sciences', 'BSOS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BSST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Terrorism Studies', 'Terrorism Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Terrorism Studies', 'BSST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BTPT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Physical Therapy', 'Physical Therapy', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Physical Therapy', 'BTPT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BUAC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Accounting and Information Assurance', 'Accounting and Information Assurance', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Accounting and Information Assurance', 'BUAC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BUDT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Decision and Information Technologies', 'Decision and Information Technologies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Decision and Information Technologies', 'BUDT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BUFN', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Finance', 'Finance', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Finance', 'BUFN', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BULM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Logistics, Business, and Public Policy', 'Logistics, Business, and Public Policy', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Logistics, Business, and Public Policy', 'BULM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BUMK', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Marketing', 'Marketing', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Marketing', 'BUMK', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BUMO', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Management and Organization', 'Management and Organization', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Management and Organization', 'BUMO', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-BUSI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'MBA Core and Cross-Functional', 'MBA Core and Cross-Functional', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'MBA Core and Cross-Functional', 'BUSI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CBMG', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Cell Biology & Molecular Genetics', 'Cell Biology & Molecular Genetics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Cell Biology & Molecular Genetics', 'CBMG', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CCJS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Criminology and Criminal Justice', 'Criminology and Criminal Justice', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Criminology and Criminal Justice', 'CCJS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CHBE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Chemical and Biomolecular Engineering', 'Chemical and Biomolecular Engineering', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Chemical and Biomolecular Engineering', 'CHBE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CHEM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Chemistry', 'Chemistry', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Chemistry', 'CHEM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CHIN', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Chinese', 'Chinese', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Chinese', 'CHIN', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CHPH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Chemical Physics', 'Chemical Physics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Chemical Physics', 'CHPH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CLAS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Classics', 'Classics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Classics', 'CLAS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CLFS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Chemical and Life Sciences', 'Chemical and Life Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Chemical and Life Sciences', 'CLFS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CMLT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Comparative Literature', 'Comparative Literature', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Comparative Literature', 'CMLT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CMNS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Computer, Math, and Natural Sciences', 'Computer, Math, and Natural Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Computer, Math, and Natural Sciences', 'CMNS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CMPS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Computer, Mathematical and Physical Sciences', 'Computer, Mathematical and Physical Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Computer, Mathematical and Physical Sciences', 'CMPS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CMSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Computer Science', 'Computer Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Computer Science', 'CMSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-COMM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Communication', 'Communication', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Communication', 'COMM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CONF', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Conferences and Institutes', 'Conferences and Institutes', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Conferences and Institutes', 'CONF', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CONS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Sustainable Development & Conservation Biology', 'Sustainable Development & Conservation Biology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Sustainable Development & Conservation Biology', 'CONS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-COOP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Cooperative Education Program', 'Cooperative Education Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Cooperative Education Program', 'COOP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-COTA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'College of the Air', 'College of the Air', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'College of the Air', 'COTA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-CPSP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'College Park Scholars Program', 'College Park Scholars Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'College Park Scholars Program', 'CPSP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-DANC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Dance', 'Dance', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Dance', 'DANC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EALL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'East Asian Languages and Literatures', 'East Asian Languages and Literatures', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'East Asian Languages and Literatures', 'EALL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ECON', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Economics', 'Economics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Economics', 'ECON', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDCI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Curriculum and Instruction', 'Curriculum and Instruction', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Curriculum and Instruction', 'EDCI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDCP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Education Counseling and Personnel Services', 'Education Counseling and Personnel Services', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Education Counseling and Personnel Services', 'EDCP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDHD', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Education, Human Development', 'Education, Human Development', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Education, Human Development', 'EDHD', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDHI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Education Leadership, Higher Ed and International Ed', 'Education Leadership, Higher Ed and International Ed', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Education Leadership, Higher Ed and International Ed', 'EDHI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDMS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Measurement, Statistics, and Evaluation', 'Measurement, Statistics, and Evaluation', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Measurement, Statistics, and Evaluation', 'EDMS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDPL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Education Policy and Leadership', 'Education Policy and Leadership', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Education Policy and Leadership', 'EDPL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDPS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Education Policy Studies', 'Education Policy Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Education Policy Studies', 'EDPS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDSP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Education, Special', 'Education, Special', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Education, Special', 'EDSP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EDUC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Education', 'Education', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Education', 'EDUC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EMBA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Executive MBA Program', 'Executive MBA Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Executive MBA Program', 'EMBA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENAE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Aerospace', 'Engineering, Aerospace', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Aerospace', 'ENAE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENBE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Biological Resources Engineering', 'Biological Resources Engineering', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Biological Resources Engineering', 'ENBE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENCE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Civil', 'Engineering, Civil', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Civil', 'ENCE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENCH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Chemical', 'Engineering, Chemical', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Chemical', 'ENCH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENCO', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Cooperative Education', 'Engineering, Cooperative Education', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Cooperative Education', 'ENCO', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENEE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Electrical & Computer Engineering', 'Electrical & Computer Engineering', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Electrical & Computer Engineering', 'ENEE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENES', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering Science', 'Engineering Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering Science', 'ENES', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENFP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Fire Protection', 'Engineering, Fire Protection', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Fire Protection', 'ENFP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENGL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'English', 'English', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'English', 'ENGL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENMA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Materials', 'Engineering, Materials', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Materials', 'ENMA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENME', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Mechanical', 'Engineering, Mechanical', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Mechanical', 'ENME', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENNU', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Nuclear', 'Engineering, Nuclear', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Nuclear', 'ENNU', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENPM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering, Professional Masters', 'Engineering, Professional Masters', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering, Professional Masters', 'ENPM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENPP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Engineering and Public Policy', 'Engineering and Public Policy', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Engineering and Public Policy', 'ENPP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENRE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Reliability Engineering', 'Reliability Engineering', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Reliability Engineering', 'ENRE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENSE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Systems Engineering', 'Systems Engineering', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Systems Engineering', 'ENSE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENSP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Environmental Science and Policy', 'Environmental Science and Policy', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Environmental Science and Policy', 'ENSP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Environmental Science and Technology', 'Environmental Science and Technology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Environmental Science and Technology', 'ENST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENTM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Entomology', 'Entomology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Entomology', 'ENTM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ENTS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Telecommunications', 'Telecommunications', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Telecommunications', 'ENTS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EPIB', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Epidemiology and Biostatistics', 'Epidemiology and Biostatistics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Epidemiology and Biostatistics', 'EPIB', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-EXST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Office of Extended Studies', 'Office of Extended Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Office of Extended Studies', 'EXST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-FALL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Fall', 'Fall', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Fall', 'FALL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-FBIO', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'China Medical University food Safety Program', 'China Medical University food Safety Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'China Medical University food Safety Program', 'FBIO', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-FFFF', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Freshman First Program', 'Freshman First Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Freshman First Program', 'FFFF', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-FMSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Family Science', 'Family Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Family Science', 'FMSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-FMST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Family Studies', 'Family Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Family Studies', 'FMST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-FOLA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Foreign Language', 'Foreign Language', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Foreign Language', 'FOLA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-FREN', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'French', 'French', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'French', 'FREN', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GEMS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Gemstone', 'Gemstone', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Gemstone', 'GEMS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GEOG', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Geography', 'Geography', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Geography', 'GEOG', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GEOL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Geology', 'Geology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Geology', 'GEOL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GERM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Germanic Studies', 'Germanic Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Germanic Studies', 'GERM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GNED', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'General Education', 'General Education', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'General Education', 'GNED', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GRAD', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Graduate School', 'Graduate School', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Graduate School', 'GRAD', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GREK', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Greek', 'Greek', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Greek', 'GREK', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-GVPT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Government and Politics', 'Government and Politics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Government and Politics', 'GVPT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HDCC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Digital Cultures and Creativity', 'Digital Cultures and Creativity', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Digital Cultures and Creativity', 'HDCC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HEBR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Hebrew', 'Hebrew', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Hebrew', 'HEBR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HEIP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Entrepreneurship and Innovation', 'Entrepreneurship and Innovation', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Entrepreneurship and Innovation', 'HEIP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HESP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Hearing and Speech Sciences', 'Hearing and Speech Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Hearing and Speech Sciences', 'HESP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HHUM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Honors Humanities', 'Honors Humanities', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Honors Humanities', 'HHUM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HISP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Historic Preservation', 'Historic Preservation', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Historic Preservation', 'HISP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HIST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'History', 'History', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'History', 'HIST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HLHP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Health and Human Performance', 'Health and Human Performance', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Health and Human Performance', 'HLHP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HLSA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Health Services Administration', 'Health Services Administration', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Health Services Administration', 'HLSA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HLSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Integrated Life Sciences', 'Integrated Life Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Integrated Life Sciences', 'HLSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HLTH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Health', 'Health', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Health', 'HLTH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-HONR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Honors', 'Honors', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Honors', 'HONR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-INAG', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Institute of Applied Agriculture', 'Institute of Applied Agriculture', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Institute of Applied Agriculture', 'INAG', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-INFM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Information Management', 'Information Management', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Information Management', 'INFM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-INST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Information Studies', 'Information Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Information Studies', 'INST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ISRL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Israel Studies', 'Israel Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Israel Studies', 'ISRL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ITAL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Italian', 'Italian', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Italian', 'ITAL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-IVSP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Individual Studies Program', 'Individual Studies Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Individual Studies Program', 'IVSP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-JAPN', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Japanese', 'Japanese', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Japanese', 'JAPN', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-JOUR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Journalism', 'Journalism', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Journalism', 'JOUR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-JWST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Jewish Studies', 'Jewish Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Jewish Studies', 'JWST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-KNES', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Kinesiology', 'Kinesiology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Kinesiology', 'KNES', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-KORA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Korean', 'Korean', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Korean', 'KORA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-L&S', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Letters & Sciences', 'Letters & Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Letters & Sciences', 'L&S', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LARC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Landscape Architecture', 'Landscape Architecture', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Landscape Architecture', 'LARC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LASC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Certificate in Latin American Studies', 'Certificate in Latin American Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Certificate in Latin American Studies', 'LASC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LATN', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Latin', 'Latin', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Latin', 'LATN', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LBSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Library Science', 'Library Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Library Science', 'LBSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LFSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Life Sciences', 'Life Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Life Sciences', 'LFSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LGBT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Lesbian Gay Bisexual Transgender Studies', 'Lesbian Gay Bisexual Transgender Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Lesbian Gay Bisexual Transgender Studies', 'LGBT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LING', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Linguistics', 'Linguistics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Linguistics', 'LING', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-LTSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Letters and Sciences', 'Letters and Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Letters and Sciences', 'LTSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MAIT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Masters in the Mathematics of Advanced Industrial Tech', 'Masters in the Mathematics of Advanced Industrial Tech', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Masters in the Mathematics of Advanced Industrial Tech', 'MAIT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MATH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Mathematics', 'Mathematics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Mathematics', 'MATH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MEES', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Marine-Estuarine-Environmental Sciences', 'Marine-Estuarine-Environmental Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Marine-Estuarine-Environmental Sciences', 'MEES', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MIEH', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Maryland Institute for Applied Environmental Health', 'Maryland Institute for Applied Environmental Health', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Maryland Institute for Applied Environmental Health', 'MIEH', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MOCB', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Molecular and Cell Biology', 'Molecular and Cell Biology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Molecular and Cell Biology', 'MOCB', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MUED', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Music Education', 'Music Education', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Music Education', 'MUED', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MUET', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Ethnomusicology', 'Ethnomusicology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Ethnomusicology', 'MUET', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MUSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'School of Music', 'School of Music', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'School of Music', 'MUSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-MUSP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Music Performance', 'Music Performance', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Music Performance', 'MUSP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NACS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Neuroscience & Cognitive Science', 'Neuroscience & Cognitive Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Neuroscience & Cognitive Science', 'NACS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NFSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Nutrition and Food Science', 'Nutrition and Food Science', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Nutrition and Food Science', 'NFSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NIAG', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'National Institute of Aeronautics - Georgia', 'National Institute of Aeronautics - Georgia', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'National Institute of Aeronautics - Georgia', 'NIAG', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NIAP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'National Institute of Aeronautics - Va Tech', 'National Institute of Aeronautics - Va Tech', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'National Institute of Aeronautics - Va Tech', 'NIAP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NIAS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'National Institute of Aeronautics - North Carolina', 'National Institute of Aeronautics - North Carolina', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'National Institute of Aeronautics - North Carolina', 'NIAS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NIAT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'National Institute of Aeronautics - NC AT&T', 'National Institute of Aeronautics - NC AT&T', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'National Institute of Aeronautics - NC AT&T', 'NIAT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NIAV', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'National Institute of Aeronautics - Univ of VA', 'National Institute of Aeronautics - Univ of VA', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'National Institute of Aeronautics - Univ of VA', 'NIAV', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NRMT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Natural Resources Management', 'Natural Resources Management', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Natural Resources Management', 'NRMT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NRSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Natural Resource Sciences', 'Natural Resource Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Natural Resource Sciences', 'NRSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NTUS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'National Technological University', 'National Technological University', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'National Technological University', 'NTUS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-NURS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Nursing', 'Nursing', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Nursing', 'NURS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PERS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Persian', 'Persian', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Persian', 'PERS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PHAR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Pharmacy', 'Pharmacy', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Pharmacy', 'PHAR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PHIL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Philosophy', 'Philosophy', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Philosophy', 'PHIL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PHYS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Physics', 'Physics', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Physics', 'PHYS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PLSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Plant Sciences', 'Plant Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Plant Sciences', 'PLSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PORT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Portuguese', 'Portuguese', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Portuguese', 'PORT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PROQ', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'ProQuest', 'ProQuest', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'ProQuest', 'PROQ', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PSYC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Psychology', 'Psychology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Psychology', 'PSYC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-PUAF', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Public Affairs', 'Public Affairs', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Public Affairs', 'PUAF', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-RDEV', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Real Estate Development', 'Real Estate Development', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Real Estate Development', 'RDEV', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-RELS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Religious Studies', 'Religious Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Religious Studies', 'RELS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-RLES', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Real Estate', 'Real Estate', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Real Estate', 'RLES', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-RUSS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Russian', 'Russian', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Russian', 'RUSS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SAOF', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Study Abroad, Non-Affiliated Programs', 'Study Abroad, Non-Affiliated Programs', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Study Abroad, Non-Affiliated Programs', 'SAOF', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SBEE', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Smith Business Edge', 'Smith Business Edge', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Smith Business Edge', 'SBEE', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SFSR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Summer Freshmen Seminar', 'Summer Freshmen Seminar', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Summer Freshmen Seminar', 'SFSR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SGRV', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Shady Grove', 'Shady Grove', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Shady Grove', 'SGRV', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SLAA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Second Language Acquisition and Application', 'Second Language Acquisition and Application', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Second Language Acquisition and Application', 'SLAA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SLAV', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Slavic', 'Slavic', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Slavic', 'SLAV', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SLLC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'School of Languages, Literatures and Cultures', 'School of Languages, Literatures and Cultures', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'School of Languages, Literatures and Cultures', 'SLLC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SOCY', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Sociology', 'Sociology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Sociology', 'SOCY', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SPAN', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Spanish', 'Spanish', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Spanish', 'SPAN', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SPEC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Special Programs', 'Special Programs', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Special Programs', 'SPEC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SPHL', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Public Health', 'Public Health', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Public Health', 'SPHL', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SPRG', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Spring', 'Spring', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Spring', 'SPRG', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-STAT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Statistics and Probability', 'Statistics and Probability', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Statistics and Probability', 'STAT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SUMM', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Summer', 'Summer', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Summer', 'SUMM', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-SURV', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Survey Methodology', 'Survey Methodology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Survey Methodology', 'SURV', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-TDPS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Theatre, Dance and Performance Studies', 'Theatre, Dance and Performance Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Theatre, Dance and Performance Studies', 'TDPS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-THET', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Theatre', 'Theatre', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Theatre', 'THET', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-TMGT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Management', 'Management', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Management', 'TMGT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-TOXI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Toxicology', 'Toxicology', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Toxicology', 'TOXI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UCCI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Univ College Conf/Inst', 'Univ College Conf/Inst', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Univ College Conf/Inst', 'UCCI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UCGA', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Master of General Admin. -U.C', 'Master of General Admin. -U.C', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Master of General Admin. -U.C', 'UCGA', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UCOU', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Open University', 'Open University', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Open University', 'UCOU', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UCSP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Univ. College Special', 'Univ. College Special', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Univ. College Special', 'UCSP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UGST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Undergraduate Studies', 'Undergraduate Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Undergraduate Studies', 'UGST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMAB', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'University of MD at Baltimore City', 'University of MD at Baltimore City', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'University of MD at Baltimore City', 'UMAB', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMBC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'University of MD Baltimore County', 'University of MD Baltimore County', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'University of MD Baltimore County', 'UMBC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMBS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Bowie State', 'Bowie State', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Bowie State', 'UMBS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMCO', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Coppin State', 'Coppin State', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Coppin State', 'UMCO', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMEI', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Maryland English Institute', 'Maryland English Institute', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Maryland English Institute', 'UMEI', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMES', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'University of MD Eastern Shore', 'University of MD Eastern Shore', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'University of MD Eastern Shore', 'UMES', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMFR', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Frostburg State', 'Frostburg State', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Frostburg State', 'UMFR', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMSG', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Shady Grove', 'Shady Grove', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Shady Grove', 'UMSG', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMSS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Salisbury State', 'Salisbury State', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Salisbury State', 'UMSS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMTS', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Towson State', 'Towson State', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Towson State', 'UMTS', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMUB', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'University of Baltimore', 'University of Baltimore', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'University of Baltimore', 'UMUB', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UMUC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'University College', 'University College', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'University College', 'UMUC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-UNIV', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'University Courses', 'University Courses', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'University Courses', 'UNIV', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-URSP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Urban Studies and Planning', 'Urban Studies and Planning', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Urban Studies and Planning', 'URSP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-USLT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Latina/o Studies', 'Latina/o Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Latina/o Studies', 'USLT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-VMSC', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Veterinary Medical Sciences', 'Veterinary Medical Sciences', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Veterinary Medical Sciences', 'VMSC', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-WINT', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Winterterm Workshops', 'Winterterm Workshops', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Winterterm Workshops', 'WINT', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-WMST', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Women''s Studies', 'Women''s Studies', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Women''s Studies', 'WMST', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-WORK', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'WorkShop - Non Academic', 'WorkShop - Non Academic', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'WorkShop - Non Academic', 'WORK', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-WRLD', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'World Courses', 'World Courses', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'World Courses', 'WRLD', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-YSPP', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'Young Scholars Program', 'Young Scholars Program', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Young Scholars Program', 'YSPP', 'Active', 'kuali.org.SubjectCode')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE)
  values ('ORGID-ZZZZ', 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-08', 'YYYY-MM-DD'), 0, 'D.C. Consortium', 'D.C. Consortium', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'D.C. Consortium', 'ZZZZ', 'Active', 'kuali.org.SubjectCode')
/
-- Add org org type for subject code to org
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, REV_DESCR, REV_NAME, ORG_HIRCHY, VER_NBR) values
('kuali.org.org.relation.type.subjectcode2org', 'Subject code to organization', to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Subject Code 2 Org', '2145ae8d-9b40-4ffd-a1b4-94845da46b94', null, null, null, 0)
/

-- Add org org data
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-AASP-1120083604', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-AASP', '1120083604', 'kuali.org.org.relation.type.subjectcode2org', '71fd3960-24ea-4ad2-bf8b-b31fc88a1bec')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-AAST-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-AAST', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'ac2eda32-a237-4abe-8df3-a8666dad497b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-AGNR-3461671214', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-AGNR', '3461671214', 'kuali.org.org.relation.type.subjectcode2org', 'd65a9332-ce84-4387-9cce-11985234c5a7')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-AMSC-1780579604', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-AMSC', '1780579604', 'kuali.org.org.relation.type.subjectcode2org', '486c069d-4f28-4833-8fbb-eddd871784df')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-AMST-4224519400', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-AMST', '4224519400', 'kuali.org.org.relation.type.subjectcode2org', 'd31c44ee-5b15-4e40-a3d8-60d009d9be76')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ANSC-51021953', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ANSC', '51021953', 'kuali.org.org.relation.type.subjectcode2org', '8ea46e4b-a556-41db-a710-e68651ab5f4f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ANTH-520957158', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ANTH', '520957158', 'kuali.org.org.relation.type.subjectcode2org', 'd0e84098-9c39-4b59-92f6-99e98069b312')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-AOSC-3549948715', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-AOSC', '3549948715', 'kuali.org.org.relation.type.subjectcode2org', '618a0ac9-a230-44f9-9782-674711d3c9a6')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARAB-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARAB', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '501ecede-9390-4347-bf00-b0559a83fd5f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARCH-3331190009', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARCH', '3331190009', 'kuali.org.org.relation.type.subjectcode2org', '87c11c52-fac8-4ff4-87c4-89b49eab8944')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-AREC-173721955', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-AREC', '173721955', 'kuali.org.org.relation.type.subjectcode2org', '74f8edba-ed72-4dea-8953-f1a45d9f051d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARHU-1089010951', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARHU', '1089010951', 'kuali.org.org.relation.type.subjectcode2org', '80b00a32-9380-40bf-9111-1c02eb664681')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARMY-3879036495', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARMY', '3879036495', 'kuali.org.org.relation.type.subjectcode2org', '25331dc9-cf7f-4c65-9155-8c8744ea1335')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARSC-4017478140', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARSC', '4017478140', 'kuali.org.org.relation.type.subjectcode2org', 'c759a8ac-750a-40e3-ae8a-430862637112')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARTH-3796561158', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARTH', '3796561158', 'kuali.org.org.relation.type.subjectcode2org', '75c8f18f-06be-46cd-8450-f0f58c60a1df')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARTM-', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARTM', '', 'kuali.org.org.relation.type.subjectcode2org', '3ffcfce6-fc0f-4264-a185-4f8601a8847a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ARTT-16888782', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ARTT', '16888782', 'kuali.org.org.relation.type.subjectcode2org', '7a76d41f-f0a5-4c37-b6d0-7a0ab84029e1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ASTR-2178639351', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ASTR', '2178639351', 'kuali.org.org.relation.type.subjectcode2org', '55337bbe-6722-4a99-a8a0-180ab0281619')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BCHM-4284516367', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BCHM', '4284516367', 'kuali.org.org.relation.type.subjectcode2org', '350301b8-a108-497a-8de0-95e8fa87a670')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BEES-3851349380', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BEES', '3851349380', 'kuali.org.org.relation.type.subjectcode2org', 'd87bd12e-9275-48c9-bf67-88f2023f83aa')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BIOE-2428559079', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BIOE', '2428559079', 'kuali.org.org.relation.type.subjectcode2org', 'c32299bc-8b36-4a96-ab9f-dcc93b994645')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BIOL-3851349380', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BIOL', '3851349380', 'kuali.org.org.relation.type.subjectcode2org', 'f2339ed2-4c24-411a-9de5-354df6f0e4db')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BIOL-4269648307', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BIOL', '4269648307', 'kuali.org.org.relation.type.subjectcode2org', 'd32d1a1f-9f7d-4a79-ae14-77becf795a6e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BIOM-51021953', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BIOM', '51021953', 'kuali.org.org.relation.type.subjectcode2org', '01f0484b-fcd6-42a4-8e22-267edf8d07e5')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BIPH-1497225962', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BIPH', '1497225962', 'kuali.org.org.relation.type.subjectcode2org', '3e86da3c-19fe-498c-8133-a9d66568bc83')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BISI-4269648307', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BISI', '4269648307', 'kuali.org.org.relation.type.subjectcode2org', '56a1c943-efdf-49ab-9130-999c56e9e62c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BMGT-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BMGT', '782679694', 'kuali.org.org.relation.type.subjectcode2org', 'a367f86d-a41b-4085-b4a9-c7b97759c7dc')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BSCI-576639460', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BSCI', '576639460', 'kuali.org.org.relation.type.subjectcode2org', '1ddeb3d0-72f7-4e2c-87a8-33dfb65f9723')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BSCV-3599714293', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BSCV', '3599714293', 'kuali.org.org.relation.type.subjectcode2org', 'dcfb05e0-d43a-4b6c-84d5-29493d0f5b6f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BSGC-3599714293', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BSGC', '3599714293', 'kuali.org.org.relation.type.subjectcode2org', '31598c51-aecd-425f-b539-fdf2021e5b4e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BSOS-3599714293', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BSOS', '3599714293', 'kuali.org.org.relation.type.subjectcode2org', '2ab557bf-2fac-447d-a1e2-6fdf95026029')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BSST-3599714293', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BSST', '3599714293', 'kuali.org.org.relation.type.subjectcode2org', 'e9680c86-9457-4781-9954-3195981b18c0')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BTPT-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BTPT', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'd503eba7-4ef6-4fe2-86d4-122b85ee62eb')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BUAC-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BUAC', '782679694', 'kuali.org.org.relation.type.subjectcode2org', '12fecc0a-7e3a-4ab3-8ea2-110a6e01a8f1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BUDT-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BUDT', '782679694', 'kuali.org.org.relation.type.subjectcode2org', 'ac454100-79ed-4356-b4d9-c6d11e28e490')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BUFN-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BUFN', '782679694', 'kuali.org.org.relation.type.subjectcode2org', '0ccc9410-04c6-4d13-ae69-2d50486ae48e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BULM-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BULM', '782679694', 'kuali.org.org.relation.type.subjectcode2org', '2f08e610-45c4-444d-b96a-3bfcf1242b85')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BUMK-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BUMK', '782679694', 'kuali.org.org.relation.type.subjectcode2org', '0615a496-af6b-4a51-ae20-9c2222e43733')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BUMO-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BUMO', '782679694', 'kuali.org.org.relation.type.subjectcode2org', 'ee795b97-9fb8-461e-98f7-6dcfa453595f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-BUSI-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-BUSI', '782679694', 'kuali.org.org.relation.type.subjectcode2org', '5ba95274-cf3a-41f8-9524-f547170d1b30')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CBMG-2924712098', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CBMG', '2924712098', 'kuali.org.org.relation.type.subjectcode2org', '208f4e54-ad75-4de2-8aab-f353191e8df7')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CCJS-546589949', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CCJS', '546589949', 'kuali.org.org.relation.type.subjectcode2org', '50b90471-542c-4f99-8139-0b5d50dac6a1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CHBE-4109255486', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CHBE', '4109255486', 'kuali.org.org.relation.type.subjectcode2org', '8e46188b-9264-44dc-8d74-1d674ea2a3b0')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CHEM-4284516367', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CHEM', '4284516367', 'kuali.org.org.relation.type.subjectcode2org', '3b1a67b5-aa7b-4b1f-a2a1-b9342a8e7f2e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CHIN-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CHIN', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '1cf99270-09d1-4be0-a187-24287b14e501')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CHPH-626941539', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CHPH', '626941539', 'kuali.org.org.relation.type.subjectcode2org', 'adabe6d5-7ea2-43d8-84e1-9cdcb513bfcd')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CHPH-2920718521', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CHPH', '2920718521', 'kuali.org.org.relation.type.subjectcode2org', 'b9d4ee02-0cf2-4eab-8753-e8a9d290b35d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CHPH-4284516367', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CHPH', '4284516367', 'kuali.org.org.relation.type.subjectcode2org', 'cd5840a9-65ba-450b-8713-df01d21029db')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CLAS-3097072228', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CLAS', '3097072228', 'kuali.org.org.relation.type.subjectcode2org', '78ec740a-ec93-4989-b2ef-a14965e30c48')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CLFS-3851349380', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CLFS', '3851349380', 'kuali.org.org.relation.type.subjectcode2org', '639a6591-b1bb-4847-a779-128457b19a4e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CMLT-2677933260', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CMLT', '2677933260', 'kuali.org.org.relation.type.subjectcode2org', '76e496ac-4605-4f0c-bf57-9f6b8e6bf584')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CMNS-1497225962', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CMNS', '1497225962', 'kuali.org.org.relation.type.subjectcode2org', 'c676b4c9-9e37-49c8-af3b-13e369d49b4c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CMPS-1497225962', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CMPS', '1497225962', 'kuali.org.org.relation.type.subjectcode2org', '655b9d0d-c608-448e-9576-fd60d2a1c12e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CMSC-3220936919', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CMSC', '3220936919', 'kuali.org.org.relation.type.subjectcode2org', '8a691d44-70c4-42cb-bdf5-5725bb5389da')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-COMM-674215562', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-COMM', '674215562', 'kuali.org.org.relation.type.subjectcode2org', 'ca11784c-6955-4df6-a03d-c267623cbf8e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CONF-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CONF', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '800265b7-0d0a-49ee-aaab-dd7fdbf9c6b2')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CONS-4269648307', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CONS', '4269648307', 'kuali.org.org.relation.type.subjectcode2org', '179d1874-9e72-47ae-9a99-0f25e418f2a7')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-COOP-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-COOP', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'e4541c20-289e-4c90-a5f8-32a855955650')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-COTA-4017478140', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-COTA', '4017478140', 'kuali.org.org.relation.type.subjectcode2org', '7c49f09d-a5d7-456a-82e6-16072d169424')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-CPSP-356639116', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-CPSP', '356639116', 'kuali.org.org.relation.type.subjectcode2org', 'f91ee875-38f1-42bd-8386-9100560cf6aa')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-DANC-859250509', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-DANC', '859250509', 'kuali.org.org.relation.type.subjectcode2org', 'a7cefd57-690d-4c91-a9af-6c5a6ae163fa')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EALL-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EALL', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '0c0047fd-79ff-42fa-a918-7ee6750d2f68')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ECON-95634162', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ECON', '95634162', 'kuali.org.org.relation.type.subjectcode2org', 'e610796b-d8a0-454a-a07e-67de0bd0f5f2')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDCI-1562099342', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDCI', '1562099342', 'kuali.org.org.relation.type.subjectcode2org', 'd41f7ac3-99f2-4702-84f8-45ba34e9e8e9')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDCP-3817025636', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDCP', '3817025636', 'kuali.org.org.relation.type.subjectcode2org', '722f7225-8164-41d5-85a6-08b40ec662e6')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDHD-2724430094', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDHD', '2724430094', 'kuali.org.org.relation.type.subjectcode2org', 'c486d797-bf05-4c4c-96b9-c511951dbd65')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDHI-3817025636', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDHI', '3817025636', 'kuali.org.org.relation.type.subjectcode2org', '39947870-05b7-4612-89c4-2a2d8d0ecd1e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDMS-2724430094', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDMS', '2724430094', 'kuali.org.org.relation.type.subjectcode2org', '51407f27-6bfd-41a3-9042-e5ce31806fbc')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDPL-1474328662', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDPL', '1474328662', 'kuali.org.org.relation.type.subjectcode2org', '3eba5f18-8c2b-4a59-a739-2344f19514dc')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDPL-1562099342', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDPL', '1562099342', 'kuali.org.org.relation.type.subjectcode2org', 'c89ca19d-0607-4e6c-97df-5d522306058b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDPS-1562099342', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDPS', '1562099342', 'kuali.org.org.relation.type.subjectcode2org', 'c518ed5d-01ec-4861-93f5-7774ec3e6619')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDSP-3817025636', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDSP', '3817025636', 'kuali.org.org.relation.type.subjectcode2org', 'dc54058f-442b-424c-b877-64d2cff552ee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EDUC-608473555', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EDUC', '608473555', 'kuali.org.org.relation.type.subjectcode2org', '4ba931bf-b9dc-4a2b-9d98-0b0b22c52bcd')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EMBA-782679694', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EMBA', '782679694', 'kuali.org.org.relation.type.subjectcode2org', '0ea72fca-6066-4f14-baf4-264e662f6422')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENAE-4094704439', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENAE', '4094704439', 'kuali.org.org.relation.type.subjectcode2org', 'ea5b0a41-1b4e-4af1-a7e2-7a356bb2495d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENBE-170848482', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENBE', '170848482', 'kuali.org.org.relation.type.subjectcode2org', 'b8732720-7ad8-4393-a8c7-80456c1fcfc6')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENCE-2881090036', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENCE', '2881090036', 'kuali.org.org.relation.type.subjectcode2org', 'eddf91fa-beba-4659-8984-02388fb4da5b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENCH-4109255486', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENCH', '4109255486', 'kuali.org.org.relation.type.subjectcode2org', '17cab8d7-baa9-4577-86f7-af29343b85c9')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENCO-2410586615', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENCO', '2410586615', 'kuali.org.org.relation.type.subjectcode2org', '39f51bc5-d17f-4089-9562-677f486d5634')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENEE-2920718521', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENEE', '2920718521', 'kuali.org.org.relation.type.subjectcode2org', 'c48cc964-e3ed-4041-8754-4c7a11870a4f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENES-4112787133', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENES', '4112787133', 'kuali.org.org.relation.type.subjectcode2org', 'e7abd7f1-8cab-460c-a42f-39cc30740cc9')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENFP-1937207595', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENFP', '1937207595', 'kuali.org.org.relation.type.subjectcode2org', '9de2c965-75d2-4faf-9ace-71b6ff2086f5')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENGL-2677933260', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENGL', '2677933260', 'kuali.org.org.relation.type.subjectcode2org', 'b16f2858-321e-496f-86dc-26bd2b6b42e5')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENMA-1526119078', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENMA', '1526119078', 'kuali.org.org.relation.type.subjectcode2org', '7f118af1-1f76-4a0f-bfb3-bce73eac99a3')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENME-2971760996', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENME', '2971760996', 'kuali.org.org.relation.type.subjectcode2org', '03080381-ece6-4ced-84ba-bbce22eb4357')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENNU-1526119078', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENNU', '1526119078', 'kuali.org.org.relation.type.subjectcode2org', '77cced1a-e414-4290-94d8-7fe0924d10e1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENPM-2261340226', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENPM', '2261340226', 'kuali.org.org.relation.type.subjectcode2org', '16ecd68d-761a-432a-b367-54e0bd4540ce')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENPP-4112787133', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENPP', '4112787133', 'kuali.org.org.relation.type.subjectcode2org', '960c4207-0adc-434d-87b2-ebe99aa55979')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENPP-2850820400', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENPP', '2850820400', 'kuali.org.org.relation.type.subjectcode2org', 'f1d263c0-7460-4783-983c-2968b067fed8')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENRE-1526119078', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENRE', '1526119078', 'kuali.org.org.relation.type.subjectcode2org', '138d148c-a54b-49a5-a87b-a32885391bbf')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENSE-2535524611', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENSE', '2535524611', 'kuali.org.org.relation.type.subjectcode2org', '5d753aba-6a0e-4037-b3e6-e35f094ba598')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENSP-170848482', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENSP', '170848482', 'kuali.org.org.relation.type.subjectcode2org', '247f7068-7cb6-4537-980a-1093953cf761')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENST-170848482', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENST', '170848482', 'kuali.org.org.relation.type.subjectcode2org', '62b3c849-e050-4e30-a6e1-3a519a25736c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENTM-2319174391', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENTM', '2319174391', 'kuali.org.org.relation.type.subjectcode2org', 'aa0912f8-aebf-47f0-92fb-ad8275dffd88')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ENTS-2920718521', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ENTS', '2920718521', 'kuali.org.org.relation.type.subjectcode2org', 'beb02440-00ee-457d-86e3-9859df935312')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EPIB-2766774548', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EPIB', '2766774548', 'kuali.org.org.relation.type.subjectcode2org', '9b9bacdb-10ef-4e55-b988-0ff00a45de85')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-EXST-624412996', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-EXST', '624412996', 'kuali.org.org.relation.type.subjectcode2org', '8a7730fb-c743-4c87-8880-074337423d15')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-FALL-3449772954', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-FALL', '3449772954', 'kuali.org.org.relation.type.subjectcode2org', 'b88c92c1-164b-4df3-859d-d5b5c5e22e6a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-FBIO-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-FBIO', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '5ba38c9e-9734-4e82-99b6-9acf184ffa53')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-FFFF-3449772954', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-FFFF', '3449772954', 'kuali.org.org.relation.type.subjectcode2org', 'e3ef8710-44af-4596-a165-0a981d6b9bf0')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-FMSC-543215077', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-FMSC', '543215077', 'kuali.org.org.relation.type.subjectcode2org', '6da9d2bd-bc80-4aa1-8696-a9b559916576')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-FMST-543215077', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-FMST', '543215077', 'kuali.org.org.relation.type.subjectcode2org', '487063b0-057e-4018-8480-0e6afcd52304')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-FOLA-1089010951', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-FOLA', '1089010951', 'kuali.org.org.relation.type.subjectcode2org', '53976f05-4e43-45ce-8c72-933d352f1097')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-FREN-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-FREN', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', 'de1e5297-6312-4d8f-bd22-2392b7a5fb8a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GEMS-3308998069', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GEMS', '3308998069', 'kuali.org.org.relation.type.subjectcode2org', '07fab575-accf-4504-befa-e103698e31dc')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GEOG-3415707385', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GEOG', '3415707385', 'kuali.org.org.relation.type.subjectcode2org', '8ea637a9-dae4-404e-808a-39df2061d389')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GEOL-1666941564', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GEOL', '1666941564', 'kuali.org.org.relation.type.subjectcode2org', '79ada3ce-5900-4ab2-9be7-9a1e905be6dc')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GERM-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GERM', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '513dfd7e-3dc9-4de3-b5cc-3b15f934fbc4')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GNED-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GNED', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '40b5886a-5073-4a63-810d-6c183bebae90')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GRAD-3429664812', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GRAD', '3429664812', 'kuali.org.org.relation.type.subjectcode2org', '0e791764-26cc-4338-ba04-4e4a3238fd5d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GREK-3097072228', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GREK', '3097072228', 'kuali.org.org.relation.type.subjectcode2org', '4b31f6d7-e731-4597-b7be-62a726644107')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-GVPT-4113665405', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-GVPT', '4113665405', 'kuali.org.org.relation.type.subjectcode2org', 'a3451a0a-f9e0-4f12-9341-bb8bc2214fd6')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HDCC-1480306587', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HDCC', '1480306587', 'kuali.org.org.relation.type.subjectcode2org', '27bfd341-cf08-4ed5-9213-bb3ad9b4de1b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HEBR-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HEBR', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '9a83f856-de78-47e5-ba20-140717df0d29')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HEIP-1129765915', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HEIP', '1129765915', 'kuali.org.org.relation.type.subjectcode2org', 'd0f62fd5-be07-4f9e-9082-978e665eff6c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HESP-3684279603', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HESP', '3684279603', 'kuali.org.org.relation.type.subjectcode2org', '549f3e2f-adea-4ccd-bb91-bbf0749acf4a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HHUM-3179168204', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HHUM', '3179168204', 'kuali.org.org.relation.type.subjectcode2org', 'a5c7dc92-1783-4f3f-afa7-3faa6d3a8d2c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HISP-1430480157', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HISP', '1430480157', 'kuali.org.org.relation.type.subjectcode2org', 'fef40a28-f247-4367-978f-4a48f3d80809')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HIST-3213375036', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HIST', '3213375036', 'kuali.org.org.relation.type.subjectcode2org', '7fc1adcc-74ef-4397-91e8-509c0fac8010')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HLHP-1829095850', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HLHP', '1829095850', 'kuali.org.org.relation.type.subjectcode2org', '6dac8f0b-8a16-4dd6-a303-42c9ad7484d5')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HLSA-2228992216', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HLSA', '2228992216', 'kuali.org.org.relation.type.subjectcode2org', 'c272c02d-943c-4545-9a63-204ed6777995')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HLSC-1225939484', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HLSC', '1225939484', 'kuali.org.org.relation.type.subjectcode2org', 'ef9c57bc-d0d1-4aaa-a0aa-e4c188ceee20')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HLTH-3962055787', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HLTH', '3962055787', 'kuali.org.org.relation.type.subjectcode2org', 'd50398c2-e863-4a8d-89eb-d3ae80816b8c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-HONR-951553754', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-HONR', '951553754', 'kuali.org.org.relation.type.subjectcode2org', 'dd1f7bb3-2c00-40d4-b835-32b3d635ad1a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-INAG-4219981095', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-INAG', '4219981095', 'kuali.org.org.relation.type.subjectcode2org', 'f7d9bd6b-3bee-4ccf-9443-8487ac59a016')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-INFM-2376331455', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-INFM', '2376331455', 'kuali.org.org.relation.type.subjectcode2org', '54ce2482-c4e0-4cba-bab7-25cb3e423049')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-INST-2376331455', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-INST', '2376331455', 'kuali.org.org.relation.type.subjectcode2org', '9473fb47-f1f1-452f-84a9-e961703b0503')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ISRL-3392144313', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ISRL', '3392144313', 'kuali.org.org.relation.type.subjectcode2org', '011b500f-3cea-4901-a13f-99c32fbd004a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ITAL-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ITAL', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '00ce149e-e218-48ce-a3d4-17941df39831')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-IVSP-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-IVSP', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'f42caf84-9c11-4108-a8dc-4520eb0fa4cd')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-JAPN-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-JAPN', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', 'df4c5584-3d63-4a27-b78b-168015053335')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-JOUR-4236812358', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-JOUR', '4236812358', 'kuali.org.org.relation.type.subjectcode2org', '7f2034c0-70a8-4da9-b981-b3899c3a587b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-JWST-3392144313', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-JWST', '3392144313', 'kuali.org.org.relation.type.subjectcode2org', '493a9ce3-f8c2-4bb8-848f-1ac2c2dc076b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-KNES-3395864112', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-KNES', '3395864112', 'kuali.org.org.relation.type.subjectcode2org', '715bbeae-d101-4bf2-912b-5eff882e7e3b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-KORA-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-KORA', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '95bf707d-ea81-4987-bb90-f920a0c9f799')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-L&S-2192541427', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-L&S', '2192541427', 'kuali.org.org.relation.type.subjectcode2org', 'b7d6ea67-2caa-412d-9174-26f0ef0a71f4')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LARC-2591189105', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LARC', '2591189105', 'kuali.org.org.relation.type.subjectcode2org', 'e5a3daf9-3a6e-4cc1-8ccc-7ab61c4f06cb')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LASC-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LASC', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '084f75f2-8b5a-4dab-bd12-ba4152522aba')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LATN-3097072228', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LATN', '3097072228', 'kuali.org.org.relation.type.subjectcode2org', 'b92ca025-f04f-43b5-a97d-580f52c373df')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LBSC-2376331455', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LBSC', '2376331455', 'kuali.org.org.relation.type.subjectcode2org', '950e8089-4b01-426e-aaa7-f5d62c0b8476')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LFSC-3851349380', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LFSC', '3851349380', 'kuali.org.org.relation.type.subjectcode2org', '13271eb6-afea-4fa4-a8f8-f3c9a9d9808b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LGBT-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LGBT', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'f0ac2e96-b23e-4f1f-a395-04977c7a0434')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LING-2774695149', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LING', '2774695149', 'kuali.org.org.relation.type.subjectcode2org', '0ec208bd-0867-41e7-b97e-7a20a89eaa76')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-LTSC-2192541427', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-LTSC', '2192541427', 'kuali.org.org.relation.type.subjectcode2org', 'ac050cf7-7ed4-4299-8d6a-2acd3bc0a758')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MAIT-4124609032', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MAIT', '4124609032', 'kuali.org.org.relation.type.subjectcode2org', '3d8f423b-d70c-4634-8b9d-e19bb5535bcc')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MATH-4124609032', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MATH', '4124609032', 'kuali.org.org.relation.type.subjectcode2org', '64b4dbbd-f026-43dd-8ba5-0ad9e87c4d39')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MEES-261786706', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MEES', '261786706', 'kuali.org.org.relation.type.subjectcode2org', 'd016fafa-fd7f-4112-836d-007e7f733d6b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MIEH-754311689', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MIEH', '754311689', 'kuali.org.org.relation.type.subjectcode2org', '513f566f-3a7d-400b-a96a-7b8a1799ed29')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MOCB-4284516367', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MOCB', '4284516367', 'kuali.org.org.relation.type.subjectcode2org', '78bef40e-8011-485c-bf6c-21f476e066f3')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MUED-1273172872', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MUED', '1273172872', 'kuali.org.org.relation.type.subjectcode2org', 'a1931116-f5cf-4fbe-a8b7-52df7e6a9d5c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MUET-1273172872', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MUET', '1273172872', 'kuali.org.org.relation.type.subjectcode2org', '15226043-cd3f-4a8f-8e6d-21e213e0e8bb')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MUSC-1273172872', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MUSC', '1273172872', 'kuali.org.org.relation.type.subjectcode2org', 'ef07ad2e-0458-43c0-ac22-e2bc84aecd2b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-MUSP-1273172872', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-MUSP', '1273172872', 'kuali.org.org.relation.type.subjectcode2org', '619e31b5-9226-47b3-9f50-bd22dc0792a7')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NACS-3840536872', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NACS', '3840536872', 'kuali.org.org.relation.type.subjectcode2org', 'ad28ee0e-334c-4557-bae9-b86d4219cb66')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NFSC-1187532553', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NFSC', '1187532553', 'kuali.org.org.relation.type.subjectcode2org', 'bcd749b8-f9c3-4931-aa28-ad6ee4439247')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NIAG-4094704439', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NIAG', '4094704439', 'kuali.org.org.relation.type.subjectcode2org', '8dd3ca0d-d35d-4c4a-b017-ac2d4c2ec5ac')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NIAP-4094704439', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NIAP', '4094704439', 'kuali.org.org.relation.type.subjectcode2org', 'c444e025-ad14-4444-b308-b83e24256a2e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NIAS-4094704439', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NIAS', '4094704439', 'kuali.org.org.relation.type.subjectcode2org', '6dc1ae22-fc22-40da-b530-d239a3db0fb1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NIAT-4094704439', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NIAT', '4094704439', 'kuali.org.org.relation.type.subjectcode2org', '33b2446e-d148-407c-a1e5-bfae452915b3')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NIAV-4094704439', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NIAV', '4094704439', 'kuali.org.org.relation.type.subjectcode2org', '0b49c7f2-2d8c-4a0b-b4c0-1a27f47ee28f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NRMT-170848482', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NRMT', '170848482', 'kuali.org.org.relation.type.subjectcode2org', 'c06d82f5-a8e9-4e86-966c-4327513bffba')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NRSC-2591189105', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NRSC', '2591189105', 'kuali.org.org.relation.type.subjectcode2org', 'c1d57a52-c9ee-47cf-ba16-6dd536c6e9ae')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NTUS-3449772954', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NTUS', '3449772954', 'kuali.org.org.relation.type.subjectcode2org', '1e0f87cf-739f-4064-879e-f584bbc27d71')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-NURS-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-NURS', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '89597e3a-8469-4542-8794-8f940fbd9d32')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PERS-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PERS', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '0a979c9a-626f-4cdf-8787-dac67013a706')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PHAR-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PHAR', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'be4d73a9-33a4-49bc-901d-4195976a38c2')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PHIL-859826089', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PHIL', '859826089', 'kuali.org.org.relation.type.subjectcode2org', 'e08d8aa8-038a-4916-ba45-677f7d4bc5db')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PHYS-9012742', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PHYS', '9012742', 'kuali.org.org.relation.type.subjectcode2org', '027b564a-5a05-4f2f-a5df-18a1fa81d0ee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PLSC-2591189105', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PLSC', '2591189105', 'kuali.org.org.relation.type.subjectcode2org', '12c20b2a-943e-45f1-b1bf-767e581c49f0')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PORT-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PORT', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '254ab84f-b79e-47c5-ba21-accc95e6e288')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PROQ-3429664812', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PROQ', '3429664812', 'kuali.org.org.relation.type.subjectcode2org', '5ba41c8c-f7c8-4d3b-a45c-164cab5fd25f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PSYC-4270148696', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PSYC', '4270148696', 'kuali.org.org.relation.type.subjectcode2org', '7e51886b-a4a8-4ade-8694-ee4bef9326a1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-PUAF-2850820400', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-PUAF', '2850820400', 'kuali.org.org.relation.type.subjectcode2org', '829d8c06-703a-4bc5-aeed-699257854d7a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-RDEV-3331190009', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-RDEV', '3331190009', 'kuali.org.org.relation.type.subjectcode2org', 'fd1e15a7-c642-472c-83a6-aef558e4a3b0')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-RELS-3392144313', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-RELS', '3392144313', 'kuali.org.org.relation.type.subjectcode2org', '932d5ba6-9e4d-4430-81db-b615f4aa82d4')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-RLES-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-RLES', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '276c964b-0ded-41c7-b025-a43ff48ce390')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-RUSS-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-RUSS', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '598869d2-66db-4bbc-bbd0-013514639f26')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SAOF-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SAOF', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'bf4c6ce0-9402-43f3-adbe-33f1d349bd76')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SBEE-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SBEE', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '40bbe78d-e98e-4a0e-a3ee-3424ff9fdb8a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SFSR-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SFSR', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '5b29b8da-71e6-4831-91c2-7fa7ab49a9b1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SGRV-4069181374', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SGRV', '4069181374', 'kuali.org.org.relation.type.subjectcode2org', 'c5e3c99a-6616-4760-b943-a6629215b54c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SLAA-1089010951', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SLAA', '1089010951', 'kuali.org.org.relation.type.subjectcode2org', 'f635069c-fbef-4703-bbe0-466bcc27c1f1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SLAV-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SLAV', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '646a596c-35c8-4073-b996-e4b00bae4105')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SLLC-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SLLC', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', '056d1a0f-64b8-4cf1-9bec-1d6036d2c200')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SOCY-503260996', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SOCY', '503260996', 'kuali.org.org.relation.type.subjectcode2org', 'b47478dd-99ca-4582-b6de-530f8d671e12')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SPAN-1391456348', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SPAN', '1391456348', 'kuali.org.org.relation.type.subjectcode2org', 'e66360e6-0735-4874-a1e8-eed40841ebc2')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SPEC-3449772954', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SPEC', '3449772954', 'kuali.org.org.relation.type.subjectcode2org', '72627687-a936-4dc1-93a2-1c4cd9c7ba8c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SPHL-1829095850', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SPHL', '1829095850', 'kuali.org.org.relation.type.subjectcode2org', '62b49ab3-10bf-4e77-bb34-68c2987e613e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SPRG-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SPRG', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '6830bf3d-9e0c-44b7-b097-323130e6c952')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-STAT-4124609032', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-STAT', '4124609032', 'kuali.org.org.relation.type.subjectcode2org', 'f1a00337-f741-4141-aed7-8aeeeec5e241')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SUMM-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SUMM', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'c854e358-819f-4596-a2d9-a4766a0cf40e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-SURV-2141542868', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-SURV', '2141542868', 'kuali.org.org.relation.type.subjectcode2org', 'bd98d944-db3f-408c-8d61-ce0a6828689c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-TDPS-859250509', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-TDPS', '859250509', 'kuali.org.org.relation.type.subjectcode2org', 'c28b9768-bf09-4518-ba16-42322090cd7a')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-THET-859250509', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-THET', '859250509', 'kuali.org.org.relation.type.subjectcode2org', '37e60dd5-527f-4ebd-8bce-26af9f415ce6')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-TMGT-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-TMGT', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'a336c4d1-5770-4d40-96f2-efc713d0826f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-TOXI-3851349380', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-TOXI', '3851349380', 'kuali.org.org.relation.type.subjectcode2org', '6f617411-0899-43ba-91ff-b0d3156cca37')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UCCI-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UCCI', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'ffdb615c-32db-468c-9eae-6e40b2e893f6')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UCGA-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UCGA', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '89ecb48a-4ef2-4f9b-bedd-0102702ef71d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UCOU-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UCOU', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'dccac6bf-803c-40db-9db5-fefd22d677ab')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UCSP-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UCSP', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'd787f933-7964-4ba5-91aa-f6c52e87da4d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UGST-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UGST', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'f4968895-6eab-4753-a248-8c5dc2333bb7')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMAB-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMAB', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'c11c9152-062a-4e30-9ad4-26bf1066a05c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMBC-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMBC', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '93704a51-107f-4339-8236-4b5532f1fcca')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMBS-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMBS', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '7e8e0e79-b840-48f9-a973-f86ee0fcdfe1')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMCO-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMCO', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'd2401281-0504-429e-b4a7-18baf99b0c27')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMEI-489819113', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMEI', '489819113', 'kuali.org.org.relation.type.subjectcode2org', 'b528382f-87b1-4157-bff5-e8774abaeeee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMES-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMES', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '48e73b51-7b24-45bb-8604-243c3e31c7b6')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMFR-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMFR', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '20c30dfe-7939-473d-91bb-4fff1e6f36f8')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMSG-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMSG', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'cd0ed1eb-8b96-49c5-89dc-7ca97fcd7d13')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMSS-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMSS', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '07ce6693-d17e-4b4c-bc56-fdd7e268946f')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMTS-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMTS', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'ecfb46c6-351d-42b2-9b61-f9a88f35ee3c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMUB-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMUB', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', 'c70a84c0-037f-474e-a3bc-edb8347477ce')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UMUC-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UMUC', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '727aa505-fd3c-4032-8762-5736bb7f765e')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-UNIV-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-UNIV', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '3f9e407a-b061-4fdb-94ee-28f8146bd4b4')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-URSP-131359314', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-URSP', '131359314', 'kuali.org.org.relation.type.subjectcode2org', 'bd8a41d2-4c03-4116-962d-5136c8da0b64')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-USLT-4224519400', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-USLT', '4224519400', 'kuali.org.org.relation.type.subjectcode2org', '444bf463-a9ef-49eb-8139-5992c07f9b98')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-VMSC-2682248900', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-VMSC', '2682248900', 'kuali.org.org.relation.type.subjectcode2org', 'e7f4fa42-8c5d-4583-8553-cee00ab303c3')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-WINT-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-WINT', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '6a65fc68-6efb-4086-8047-7b673fefed1b')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-WMST-4014660630', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-WMST', '4014660630', 'kuali.org.org.relation.type.subjectcode2org', '9cbca0b0-e593-4771-83d5-1377bd80e15c')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-WORK-3449772954', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-WORK', '3449772954', 'kuali.org.org.relation.type.subjectcode2org', '161843cd-6a83-44e6-a095-e7a7f700fc8d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-WRLD-2686750159', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-WRLD', '2686750159', 'kuali.org.org.relation.type.subjectcode2org', '50152222-6a3a-44c1-bfaf-c75371566092')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-YSPP-3449772954', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-YSPP', '3449772954', 'kuali.org.org.relation.type.subjectcode2org', '7f246ab1-68d4-4855-ba32-94e56dead679')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values
('ORGORGID-ZZZZ-3449772954', 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 'UMDENRREFDATALOAD', to_date('2013-02-11', 'YYYY-MM-DD'), 0, to_date('1990-01-01', 'YYYY-MM-DD'), null, 'Active', 'ORGID-ZZZZ', '3449772954', 'kuali.org.org.relation.type.subjectcode2org', 'cec6550c-15a6-468c-aa8d-416145a436c1')
/
