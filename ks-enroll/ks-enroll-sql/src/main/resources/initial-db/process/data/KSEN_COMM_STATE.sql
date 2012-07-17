--  INSERTING into KSEN_COMM_STATE
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.check.state.enabled', 'Enabled', 'kuali.process.check.lifecycle', 'Indicates that this Check is active and should be checked across all Processes.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.check.state.disabled', 'Disabled', 'kuali.process.check.lifecycle', 'Indicates that this Check is disabled across all Processes and should be skipped with a success.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.check.state.inactive', 'Inactive', 'kuali.process.check.lifecycle', 'Indicates that this Check is inactive (out to pasture) across all Processes and should fail.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.instruction.state.enabled', 'Enabled', 'kuali.process.instruction.lifecycle', 'Indicates that this Instruction is active and enabled.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.instruction.state.disabled', 'Disabled', 'kuali.process.instruction.lifecycle', 'Indicates that this Instruction is disabled and should be skipped.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.process.state.enabled', 'Enabled', 'kuali.process.process.lifecycle', 'Indicates that this Process is enabled.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.process.state.disabled', 'Disabled', 'kuali.process.process.lifecycle', 'Indicates that this Process is disabled and should be skipped resulting in success.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.process.process.state.inactive', 'Inactive', 'kuali.process.process.lifecycle', 'Indicates that this Process is inactive because it was put out to pasture. Any checks for this process should fail.', 0)
/
