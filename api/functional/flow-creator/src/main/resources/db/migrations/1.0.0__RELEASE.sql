--
-- PostgreSQL database dump
--

-- Dumped from database version 10.13
-- Dumped by pg_dump version 10.13

-- Started on 2020-08-14 22:31:25

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2892 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

-- public.revisions definition

-- Drop table

-- DROP TABLE public.revisions;

CREATE TABLE public.revisions (
	id bigserial NOT NULL,
	created_by varchar(45) NOT NULL,
	created_on timestamp NOT NULL,
	CONSTRAINT revisions_pkey PRIMARY KEY (id)
);


-- public.unity definition

-- Drop table

-- DROP TABLE public.unity;

CREATE TABLE public.unity (
	id bigserial NOT NULL,
	created_on timestamp NOT NULL,
	updated_on timestamp NULL,
	"name" varchar(150) NOT NULL,
	CONSTRAINT uk_huxwy7bxiessnish8hbrrx4p2 UNIQUE (name),
	CONSTRAINT unity_pkey PRIMARY KEY (id)
);


-- public.alternative definition

-- Drop table

-- DROP TABLE public.alternative;

CREATE TABLE public.alternative (
	id bigserial NOT NULL,
	created_on timestamp NOT NULL,
	updated_on timestamp NULL,
	message_to_next varchar(255) NOT NULL,
	old_choice_id int8 NULL,
	CONSTRAINT alternative_pkey PRIMARY KEY (id),
	CONSTRAINT fk_alternative_alternative FOREIGN KEY (old_choice_id) REFERENCES public.alternative(id)
);


-- public.alternative_audit definition

-- Drop table

-- DROP TABLE public.alternative_audit;

CREATE TABLE public.alternative_audit (
	id int8 NOT NULL,
	revision int8 NOT NULL,
	revision_type int2 NULL,
	message_to_next varchar(255) NULL,
	old_choice_id int8 NULL,
	CONSTRAINT alternative_audit_pkey PRIMARY KEY (id, revision),
	CONSTRAINT fk_alternative_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id)
);


-- public.unity_audit definition

-- Drop table

-- DROP TABLE public.unity_audit;

CREATE TABLE public.unity_audit (
	id int8 NOT NULL,
	revision int8 NOT NULL,
	revision_type int2 NULL,
	"name" varchar(150) NULL,
	CONSTRAINT unity_audit_pkey PRIMARY KEY (id, revision),
	CONSTRAINT fk_unity_audit_revisions FOREIGN KEY (revision) REFERENCES public.revisions(id)
);