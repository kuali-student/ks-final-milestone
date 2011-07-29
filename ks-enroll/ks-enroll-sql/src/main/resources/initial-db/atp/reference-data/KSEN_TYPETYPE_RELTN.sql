INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-0', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.precedes', 'kuali.atp.type.Fall', 'kuali.atp.type.Spring', 0, 'Fall precedes Spring')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Fall', 0, 'Academic year contains semester')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Spring', 1, 'Academic year contains semester')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Summer', 2, 'Academic year contains semester')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.SpringBreak' , 0, 'Spring break is a holiday')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.Thanksgiving' , 0, 'Thanksgiving is a holiday')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Fall' , 0, 'kuali.atp.type.Fall is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall1' , 0, 'kuali.atp.type.HalfFall1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall2' , 0, 'kuali.atp.type.HalfFall2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring1' , 0, 'kuali.atp.type.HalfSpring1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring2' , 0, 'kuali.atp.type.HalfSpring2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.6', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1A' , 0, 'kuali.atp.type.Mini-mester1A is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.7', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1B' , 0, 'kuali.atp.type.Mini-mester1B is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.8', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2C' , 0, 'kuali.atp.type.Mini-mester2C is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.9', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2D' , 0, 'kuali.atp.type.Mini-mester2D is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.10', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session1' , 0, 'kuali.atp.type.Session1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.11', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session2' , 0, 'kuali.atp.type.Session2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.12', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG1' , 0, 'kuali.atp.type.SessionG1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.13', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG2' , 0, 'kuali.atp.type.SessionG2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.14', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Spring' , 0, 'kuali.atp.type.Spring is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.15', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SpringBreak' , 0, 'kuali.atp.type.SpringBreak is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.16', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Summer' , 0, 'kuali.atp.type.Summer is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.17', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SummerEve' , 0, 'kuali.atp.type.SummerEve is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.18', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Winter' , 0, 'kuali.atp.type.Winter is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.19', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Adhoc' , 0, 'kuali.atp.type.Adhoc is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.FallSpring', 1, 'AcademicCalendar can contain FallSpring')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Fall', 2, 'AcademicCalendar can contain Fall')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Fall', 'kuali.atp.type.HalfFall1', 1, 'Fall can contain HalfFall1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Fall', 'kuali.atp.type.HalfFall2', 2, 'Fall can contain HalfFall2')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Winter', 3, 'AcademicCalendar can contain Winter')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.6', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Spring', 4, 'AcademicCalendar can contain Spring')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.7', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.HalfSpring1', 1, 'Spring can contain HalfSpring1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.8', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.SpringBreak', 2, 'Spring can contain SpringBreak')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.9', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.HalfSpring2', 3, 'Spring can contain HalfSpring2')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.10', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Session1', 5, 'AcademicCalendar can contain Session1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.11', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session1', 'kuali.atp.type.Mini-mester1A', 1, 'Session1 can contain Mini-mester1A')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.12', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session1', 'kuali.atp.type.Mini-mester1B', 2, 'Session1 can contain Mini-mester1B')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.13', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Summer', 'kuali.atp.type.Session2', 2, 'Summer can contain Session2')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.14', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session2', 'kuali.atp.type.Mini-mester2C', 1, 'Session2 can contain Mini-mester2C')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.15', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session2', 'kuali.atp.type.Mini-mester2D', 2, 'Session2 can contain Mini-mester2D')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.16', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.SummerEve', 6, 'AcademicCalendar can contain SummerEve')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.17', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.SummerEve', 'kuali.atp.type.SessionG1', 1, 'SummerEve can contain SessionG1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.18', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.SummerEve', 'kuali.atp.type.SessionG2', 2, 'SummerEve can contain SessionG2')
/

