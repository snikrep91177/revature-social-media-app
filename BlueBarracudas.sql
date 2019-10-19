/*******************************************************************************
   BlueBarracudas Database - Version 1.4
   Script: BlueBarracudas.sql
   Description: Creates and initializes the Blue Barracudas database.
   DB Server: Oracle
   Author: Michael Perkins
   ********************************************************************************/
/*******************************************************************************
   Drop database if it exists
********************************************************************************
DROP USER mgperkins1 CASCADE;
********************************************************************************
   Create database
********************************************************************************/
CREATE USER blueBarracuda
IDENTIFIED BY p4ssw0rd
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;
GRANT connect to blueBarracuda;
GRANT resource to blueBarracuda;
GRANT create session TO blueBarracuda;
GRANT create table TO blueBarracuda;
GRANT create view TO blueBarracuda;

CREATE USER blueBarracuda IDENTIFIED BY p4ssw0rd;
GRANT CONNECT, RESOURCE TO blueBarracuda;
GRANT DBA TO blueBarracuda WITH ADMIN OPTION;


DROP USER blueBarracuda CASCADE;


--Sequence that starts at 1 and increments ers_user by 1 for each new entry 
CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1;
    
DROP SEQUENCE USERS_SEQ;    


--HASHING FUNCTION THAT COMBINES USERNAME, PASSWORD AND A SPECIAL WORD  
CREATE OR REPLACE FUNCTION GET_USER_HASH(USER_NAME VARCHAR2, PASSWORD VARCHAR2) RETURN VARCHAR2
IS
EXTRA VARCHAR2(10) := 'SALT';
BEGIN
  RETURN TO_CHAR(DBMS_OBFUSCATION_TOOLKIT.MD5(
  INPUT => UTL_I18N.STRING_TO_RAW(DATA => USER_NAME || PASSWORD || EXTRA)));
END;
/

----TRIGGER THAT GETS NEXT SEQUENCE VALUE FOR ID AND HASHES BLANK PASSWORD
CREATE OR REPLACE TRIGGER USER_INSERT
BEFORE INSERT
ON USERS
FOR EACH ROW
BEGIN

--INCREASE THE SEQUENCE 
--  IF :NEW.user_id IS NULL THEN
--    SELECT users_seq.NEXTVAL INTO :NEW.user_id FROM DUAL;
--  END IF; 
  
  /* SAVE HASH INSTEAD OF PASSWORD */
  SELECT GET_USER_HASH(:NEW.user_name,:NEW.password) INTO :NEW.password FROM DUAL;
END;
/

CREATE OR REPLACE TRIGGER update_user

BEFORE update ON users

for each row

declare

pragma autonomous_transaction;

temp varchar(500);

begin

select password into temp from users where USER_NAME = :new.USER_NAME;

if :new.password!= temp then

    select GET_USER_HASH(:new.USER_NAME,:new.password) into :new.password from dual;

end if;

end;
/

