CREATE DATABASE app;
CREATE USER app WITH PASSWORD 'app';
GRANT ALL PRIVILEGES ON DATABASE app TO app;
\connect app

CREATE TABLE public.users
(
    id serial NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    expiration_time timestamp without time zone,
    account_locked boolean NOT NULL DEFAULT false,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_username_key UNIQUE (username)
);
ALTER TABLE public.users
    OWNER to app;
	
CREATE TABLE dep(
   ID INT PRIMARY KEY      NOT NULL,
   DEPT           CHAR(50) NOT NULL,
   EMP_ID         INT      NOT NULL
);
ALTER TABLE public.dep
    OWNER to app;


CREATE TABLE public."user_details"
(
    id bigint NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;
ALTER TABLE public.user_details
    OWNER to app;