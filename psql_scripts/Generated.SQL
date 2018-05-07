CREATE SCHEMA "public" AUTHORIZATION "card-vault";

CREATE SEQUENCE "public"."hibernate_sequence"
 INCREMENT BY 1
 NO MAXVALUE
 NO MINVALUE
 CACHE 1
;

CREATE TABLE public.card_master
(
  card_id integer NOT NULL DEFAULT nextval('card_master_id_seq'::regclass),
  card_token character varying(255) NOT NULL,
  card_unique_id character varying(255) NOT NULL,
  card_enc_details text,
  masked_card_number character varying(255),
  card_type character varying(255),
  card_issued_by character varying(255),
  card_status character varying(10),
  created_by character varying(255),
  created_at character varying(255),
  updated_by character varying(255),
  updated_at character varying(255),
  user_id character varying(255),
  CONSTRAINT id_pk PRIMARY KEY (card_id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE public.cckey_master
(
  cckey_id integer NOT NULL DEFAULT nextval('cckey_master_cckey_id_seq'::regclass),
  card_token character varying(255),
  card_unique_id character varying(255),
  cckey_part1 character varying(255),
  cckey_status character varying(10),
  user_id character varying(255),
  card_id bigint,
  created_by character varying(255),
  created_at character varying(255),
  updated_by character varying(255),
  updated_at character varying(255),
  CONSTRAINT cckey_id_pk PRIMARY KEY (cckey_id),
  CONSTRAINT card_id_fk FOREIGN KEY (card_id)
      REFERENCES public.card_master (card_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

