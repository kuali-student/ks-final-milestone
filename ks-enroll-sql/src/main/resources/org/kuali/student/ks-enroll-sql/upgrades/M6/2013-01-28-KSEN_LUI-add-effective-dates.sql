--KSENROLL-4998 - Add in effective dates from the lui's atp start date
update KSEN_LUI l set l.EFF_DT=(Select a.START_DT from KSEN_ATP a where a.id=l.ATP_ID) where l.EFF_DT is null
/
