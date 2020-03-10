CREATE DATABASE app;
CREATE USER app WITH PASSWORD 'app';
GRANT ALL PRIVILEGES ON DATABASE app TO app;
\connect app

CREATE SEQUENCE public.users_id_seq
    INCREMENT 1
    START 12
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.users_id_seq
    OWNER TO app;

CREATE TABLE public."user"
(
    id integer NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    username text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    expiration_time timestamp without time zone,
    account_locked boolean DEFAULT false,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_username_key UNIQUE (username)
,
    CONSTRAINT "username.length" CHECK (length(username) > 3) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user"
    OWNER to app;

ALTER TABLE public.user RENAME TO users;
ALTER TABLE users ALTER COLUMN id TYPE BIGINT;
-- все ID ставим BIGINT

-- тестовые таблицы
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

-- Roles
CREATE SEQUENCE public.roles_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.roles_id_seq
    OWNER TO app;

CREATE TABLE public.roles
(
    title character varying(255) COLLATE pg_catalog."default",
    id bigint NOT NULL DEFAULT nextval('roles_id_seq'::regclass),
    CONSTRAINT roles_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."roles"
    OWNER to app;

INSERT INTO public.roles(title)
VALUES
('ADMIN'),
 ('USER'),
 ('SUPERVISOR');

CREATE UNIQUE INDEX title_idx
    ON public.roles USING btree(title)
    TABLESPACE pg_default;

ALTER TABLE roles
	ADD CONSTRAINT unique_title
	UNIQUE USING INDEX title_idx;

-- повторно создать индекс, т.к. после 	ADD CONSTRAINT он исчезает

-- DROP INDEX public.roles_title;
CREATE UNIQUE INDEX title_idx
    ON public.roles USING btree
    (title COLLATE pg_catalog."default")
    TABLESPACE pg_default;

-- User To Roles
CREATE SEQUENCE public.user_roles_id_seq
    INCREMENT 1
    START 12
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.user_roles_id_seq
    OWNER TO app;

CREATE TABLE public.user_to_role
(
    id bigint NOT NULL DEFAULT nextval('user_roles_id_seq'::regclass),
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_to_role_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user_to_role"
    OWNER to app;

ALTER TABLE public.user_to_role DROP CONSTRAINT  IF EXISTS constraint_user_id_fkey;
ALTER TABLE public.user_to_role
ADD CONSTRAINT constraint_user_id_fkey
FOREIGN KEY (user_id)
REFERENCES public.users(id)
ON DELETE CASCADE;

ALTER TABLE public.user_to_role DROP CONSTRAINT IF EXISTS constraint_role_id_fkey;
ALTER TABLE public.user_to_role
ADD CONSTRAINT constraint_role_id_fkey
FOREIGN KEY (role_id)
REFERENCES public.roles(role_id)
ON DELETE CASCADE;

-- Назначить роли базовым юзерам
-- Надо ли админу создавать вторую роль - супервизор?
INSERT INTO public.user_to_role
	(user_id, role_id)
    VALUES
    (1, 1),
    (1, 3),
    (2, 2);

-- User Details
CREATE SEQUENCE public.user_details_id_seq
    INCREMENT 1
    START 12
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.user_details_id_seq
    OWNER TO app;

CREATE TABLE public.user_details
(
    id bigint NOT NULL DEFAULT nextval('user_details_id_seq'::regclass),
    user_id bigint NOT NULL,
    first_name VARCHAR (255) NOT NULL,
    last_name VARCHAR (255) NOT NULL,
    CONSTRAINT user_details_pk PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."user_details"
    OWNER to app;

-- Добавляем ограничение на уникальность поля user_id для реализации отношения 1 to 1
-- При попытке добавить запись с user_id, который уже есть,
-- получим собщение: duplicate key value violates unique constraint "unique_user_id"
ALTER TABLE user_details
	ADD CONSTRAINT unique_user_id
	UNIQUE USING INDEX details_user_id;

-- Добавить ограничение в виде FK по полю user_id
-- При попытке вставить запись с user_id, которого нет в т. users,
-- получим сообщение с пояснением Key (user_id)=(138) is not present in table "users".
ALTER TABLE public.user_details
	ADD CONSTRAINT constraint_user_id_fkey
	FOREIGN KEY (user_id)
	REFERENCES users(id)
	ON DELETE CASCADE;

-- Создать уникальный индекс
-- [!] добавлять именно в конце, после создания ограничений
CREATE UNIQUE INDEX CONCURRENTLY details_user_id ON user_details (user_id);

-- Добавить парочку юзеров
INSERT INTO public.user_details
    (user_id, first_name, last_name)
    VALUES
    (1, 'Alexandr', 'Makedoni'),
     (2, 'Testo', 'Tiesto');

-- Альтерация: переименовать userDetails в userInfo
alter table if exists user_details
rename to user_info;


