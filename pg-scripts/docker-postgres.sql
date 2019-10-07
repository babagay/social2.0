-- start a postgres instance
-- docker run --rm --publish 127.0.0.1:5432:5432 --env POSTGRES_PASSWORD=mysecretpassword --name some-postgres --detach postgres:11.1-alpine


 --rm
 -- automatically remove the container when it exits
 
  --publish
  -- publish a container's port to the host

  --env
  -- set environment variables
 
  --name
  -- assign a name to the container
 
  --detach
  -- detached mode: run the container in the background
 
 

-- connect
psql postgresql:admin:secret@localhost:5432app

-- create user
CREATE USER vinicius WITH PASSWORD 'admin';

-- optional: make the user a super user so they can create extensions, etc
ALTER USER vinicius WITH SUPERUSER;

-- create database
CREATE DATABASE poc WITH OWNER vinicius;

-- connect with new user
psql postgresql:vinicius:admin@localhost:5432poc

-- show current user
SELECT current_user;


  -- Sources:
 
  -- Postgres - Docker Hub
  -- https:hub.docker.com_postgres
 
  -- How To Remove Docker Images, Containers, and Volumes
  -- https:www.digitalocean.comcommunitytutorialshow-to-remove-docker-images-containers-and-volumes
 
  -- Postgres Cheatsheet
  -- https:gist.github.comapolloclarkea5466d5929e63043dcf
 
  -- Role Membership
  -- https:www.postgresql.orgdocs11role-membership.html
 
  -- Connecting to Postgresql in a docker container from outside
  -- https:stackoverflow.coma51687432
 
  -- PostgreSQL: Comments within SQL
  -- https:www.techonthenet.compostgresqlcomments.php
 
  -- postgres: upgrade a user to be a superuser?
  -- https:stackoverflow.coma10757486
 
 
