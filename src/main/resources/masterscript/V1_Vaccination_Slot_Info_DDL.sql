/* slot info */

DROP TABLE IF EXISTS covid.slot_info;
DROP SEQUENCE IF EXISTS covid.slot_info_seq;
CREATE SEQUENCE covid.slot_info_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 999999
    START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE covid.slot_info
(
    slotInfoId       int4        NOT NULL DEFAULT nextval('covid.slot_info_seq'::regclass),
    location    int2        NOT NULL,
    slotNo    int2        NOT NULL,
    createdon  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    createdby  varchar(255) NULL,
    modifiedon timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    modifiedby varchar(255) NULL,
    isactive bool NOT NULL DEFAULT true,
    CONSTRAINT slotInfoId_pk PRIMARY KEY (slotInfoId)

);
grant all ON SEQUENCE covid.slot_info_seq TO vacadmin;
GRANT all ON table covid.slot_info TO vacadmin;


/*Audit */

DROP TABLE IF EXISTS covid.slot_info_aud;

CREATE TABLE covid.slot_info_aud
(

    slotInfoId       int4         NULL DEFAULT nextval('covid.slot_info_seq'::regclass),
    location    int2         NULL,
    slotNo    int2         NULL,
    rev               int4 NULL,
    revtype           int2,
    createdon  timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    createdby  varchar(255) NULL,
    modifiedon timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    modifiedby varchar(255) NULL,
    isactive bool  NULL DEFAULT true,
    CONSTRAINT slotInfoId_aud_pk PRIMARY KEY (slotInfoId)
);
grant all ON table covid.slot_info_aud TO vacadmin;