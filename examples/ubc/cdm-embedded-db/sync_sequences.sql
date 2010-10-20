-- Description: a procedure to sync Rice sequences to latest id values in the Rice tables
-- Usage: execute this as a script in your Oracle environment (sqlplus, TOAD, etc)
DECLARE
  curr_id   number(19);
  curr_seq_id  number(19);
  seq_curr_val_not_set EXCEPTION;
  PRAGMA EXCEPTION_INIT(seq_curr_val_not_set, -8002);
BEGIN
  -- KREW_DOC_HDR_S
  select max(doc_typ_id) into curr_id
  from KREW_DOC_TYP_T;
  
  BEGIN
    select KREW_DOC_HDR_S.currval into curr_seq_id
    from dual;
  EXCEPTION
    WHEN seq_curr_val_not_set THEN
      select KREW_DOC_HDR_S.nextval into curr_seq_id
      from dual;
  END;
  
  dbms_output.put_line('KREW_DOC_HDR_S: current val:' || curr_id || ' seq_val:' || curr_seq_id);
  
  WHILE curr_seq_id < curr_id LOOP
    select KREW_DOC_HDR_S.nextval into curr_seq_id
    from dual;
  dbms_output.put_line('KREW_DOC_HDR_S: current val:' || curr_id || ' seq_val:' || curr_seq_id);    
  END LOOP;
  
  -- KREW_RTE_NODE_S
  select max(rte_node_id) into curr_id
  from KREW_RTE_NODE_T;
  
  BEGIN
    select KREW_RTE_NODE_S.currval into curr_seq_id
    from dual;
  EXCEPTION
    WHEN seq_curr_val_not_set THEN
    select KREW_RTE_NODE_S.nextval into curr_seq_id
    from dual;    
  END;
  
  dbms_output.put_line('KREW_RTE_NODE_S: current val:' || curr_id || ' seq_val:' || curr_seq_id);
      
  WHILE curr_seq_id < curr_id LOOP
    select KREW_RTE_NODE_S.nextval into curr_seq_id
    from dual;
  dbms_output.put_line('KREW_RTE_NODE_S: current val:' || curr_id || ' seq_val:' || curr_seq_id);    
  END LOOP;  
  
  -- KREW_RTE_NODE_CFG_PARM_S
  select max(rte_node_cfg_parm_id) into curr_id
  from KREW_RTE_NODE_CFG_PARM_T;

  BEGIN
    select KREW_RTE_NODE_CFG_PARM_S.currval into curr_seq_id
    from dual;
  EXCEPTION
    WHEN seq_curr_val_not_set THEN
      select KREW_RTE_NODE_CFG_PARM_S.nextval into curr_seq_id
      from dual;
  END; 
  
  dbms_output.put_line('KREW_RTE_NODE_CFG_PARM_S: current val:' || curr_id || ' seq_val:' || curr_seq_id);  
  
  WHILE curr_seq_id < curr_id LOOP
    select KREW_RTE_NODE_CFG_PARM_S.nextval into curr_seq_id
    from dual;
  dbms_output.put_line('KREW_RTE_NODE_CFG_PARM_S: current val:' || curr_id || ' seq_val:' || curr_seq_id);    
  END LOOP;
 
END;


