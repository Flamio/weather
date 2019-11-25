FROM tomcat

ADD backend/target/weather.war /usr/local/tomcat/webapps
RUN mkdir /usr/local/tomcat/webapps/weather
ADD frontend/build/static /usr/local/tomcat/webapps/weather/static
ADD frontend/build/static/*.* /usr/local/tomcat/webapps/weather
