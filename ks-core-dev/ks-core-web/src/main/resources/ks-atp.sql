// ATP Duration types
INSERT INTO KSAP_ATP_DUR_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('TERM', 'Academic Term', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Term')
/

// ATP Seasonal types
INSERT INTO KSAP_ATP_SEASONAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('FALL_TERM', 'Fall Term', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Fall')
/

// ATP TYPES
INSERT INTO KSAP_ATP_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, SEASONAL_TYPE, DUR_TYPE) values ('kuali.atp.undergrad', 'undergraduate calendar', {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'Undergrad', 'FALL_TERM','TERM')
/

// ATP
INSERT INTO KSAP_ATP (VERSIONIND, CREATETIME, CREATEID, UPDATETIME, UPDATEID, ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, TYPE, STATE) values (1, {ts '2009-10-29 08:57:00.0'}, 'KSCORE', {ts '2009-10-29 08:57:00.0'}, 'KSCORE', '1', 'Fall 2009', null, {ts '2009-09-01 00:00:00.0'}, {ts '2009-12-31 00:00:00.0'}, 'kuali.atp.undergrad', '')
/
