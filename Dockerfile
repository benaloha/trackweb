FROM debian

# update
RUN apt update
RUN apt upgrade -y

# openjdk
RUN apt install openjdk-11-jre-headless -y

# application
ADD target/trackweb-2.1.jar /root/trackweb.jar
ADD application.properties /root/

#run application
#CMD ["./root/trackweb.jar", "run"]

EXPOSE 8084

# docker build -t ben/trackweb:1 .
# docker run -p 8084:8084 -v /var/log/trackweb:/var/log/ -it --rm --name trackweb -d ben/trackweb:1
# docker exec -it trackweb bash