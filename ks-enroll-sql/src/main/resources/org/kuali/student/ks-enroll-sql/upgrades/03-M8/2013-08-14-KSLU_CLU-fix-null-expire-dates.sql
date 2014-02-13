UPDATE kslu_clu c
SET expir_dt=
  (SELECT a.end_dt FROM ksen_atp a WHERE a.id=c.last_atp )
WHERE c.expir_dt IS NULL
AND c.last_atp   IS NOT NULL
/