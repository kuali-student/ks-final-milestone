-- KSENROLL-13773
-- Spring 2012, ENGL101, Reg Group 1001, AO = 'BO' -> changing max enroll to 1

update KSEN_LUI set MAX_SEATS=1, MIN_SEATS=1 where ID='4f4417ce-e014-4ba5-9ec3-22b873c85880' and VER_NBR=1
/