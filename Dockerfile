FROM tomcat
 
RUN apt update && apt install postgresql

ADD target/weather.war /usr/local/tomcat/webapps
