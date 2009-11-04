// ATP Duration types
INSERT INTO KSAP_ATP_DUR_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('TERM', 'Academic Term', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Term')
INSERT INTO KSAP_ATP_DUR_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('SEMESTER', 'Academic Semester', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Semester')
/

// ATP Seasonal types
INSERT INTO KSAP_ATP_SEASONAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('Fall 2009', 'Fall Term of 2009', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Fall 2009')
INSERT INTO KSAP_ATP_SEASONAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('Fall 2010', 'Fall Term of 2010', {ts '2010-09-01 00:00:00.0'}, {ts '2010-12-31 00:00:00.0'}, 'Fall 2010')
/

// ATP TYPES
INSERT INTO KSAP_ATP_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, SEASONAL_TYPE, DUR_TYPE) values ('kuali.atp.undergrad.fall.2009', 'Undergrad', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Undergrad', 'Fall 2009','TERM')
INSERT INTO KSAP_ATP_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, SEASONAL_TYPE, DUR_TYPE) values ('kuali.atp.medical.fall.2009', 'Medical School', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Medical', 'Fall 2009','SEMESTER')
/

// ATP
INSERT INTO KSAP_ATP (VERSIONIND, CREATETIME, CREATEID, UPDATETIME, UPDATEID, ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, TYPE, STATE) values (1, {ts '2009-10-29 08:57:00.0'}, 'KSCORE', {ts '2009-10-29 08:57:00.0'}, 'KSCORE', '1', 'Fall 2009 - Undergrad', null, {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'kuali.atp.undergrad.fall.2009', '')
INSERT INTO KSAP_ATP (VERSIONIND, CREATETIME, CREATEID, UPDATETIME, UPDATEID, ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, TYPE, STATE) values (2, {ts '2009-10-29 08:57:00.0'}, 'KSCORE', {ts '2009-10-29 08:57:00.0'}, 'KSCORE', '2', 'Fall 2009 - Medical', null, {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'kuali.atp.medical.fall.2009', '')
/

// ATP Seasonal attributes
INSERT INTO KSAP_ATP_SEASONAL_TYPE_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER) values ('1', 'Year', '2009', 'Fall 2009')
INSERT INTO KSAP_ATP_SEASONAL_TYPE_ATTR (ID, ATTR_NAME, ATTR_VALUE, OWNER) values ('2', 'Season', 'Fall', 'Fall 2009')

