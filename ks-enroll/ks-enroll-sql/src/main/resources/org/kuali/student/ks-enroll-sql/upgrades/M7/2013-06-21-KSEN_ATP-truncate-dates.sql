-- Truncate the start and end dates of terms and acals since this is messing with calculations and is truncated on save anyway
update KSEN_ATP set END_DT = TRUNC(END_DT),START_DT = TRUNC(START_DT)
/
