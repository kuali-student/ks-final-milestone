--This removes all the cluset joins to non-existing clus
delete from KSLU_CLU_SET_JN_CLU csj
 where csj.id in (Select csj.id
  from KSLU_CLU_SET_JN_CLU csj,KSLU_CLU clu
 where csj.clu_ver_ind_id = clu.ver_ind_id(+)
 and clu.id is null)
 /