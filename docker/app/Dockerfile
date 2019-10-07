# Базовый образ, "наследуемся" от него
FROM tomcat:9-jre8-alpine

# Выдает ошибку: The command '/bin/sh -c apt-get update && apt-get -y upgrade' returned a non-zero code: 127
#RUN apt-get update && apt-get -y upgrade

# Удалить все файлы в папке webapps
# RUN rm -rf /usr/local/tomcat/webapps/*

# Копируем из Build Context'а собранный web archive в каталог томката
# COPY build/libs/social2.0.war /usr/local/tomcat/webapps/app.war
# [!] Если собирать контейнер через docker build .\docker\app\ -t test,
# варник нужно поместить в корне проекта и изменить путь на social2.0.war
# Далее, docker run <image_name>
COPY docker/app/social2.0.war /usr/local/tomcat/webapps/social2.0.war

# Меняем рабочий каталог на томкатовский
WORKDIR /usr/local/tomcat

# Открываем порт 8080 для контейнера, т.к. его слушает томкат
EXPOSE 8080

# взять файл согласно первого аргумента и положить внутрь контейнера по пути указанному во втором аргументе.
# (В первом аргументе может быть абсолютный путь, относительный путь, http или ftp url,
# а также, этот путь может указывать на папку или архив. В случае архива он будет распакован)
# Нужно ли это здесь?
# ADD *.war /app.war
