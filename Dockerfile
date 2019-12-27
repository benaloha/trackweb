#FROM arm32v7/openjdk:11-jre-slim
FROM openjdk:8-jre-alpine

# application
ADD application.properties /root/
ADD trackweb-2.1.jar /root/trackweb.jar


#run application
CMD ["./root/trackweb.jar", "run"]

EXPOSE 8084

# docker build -t ben/trackweb:1 .
# docker run -p 8084:8084 -v /var/log/trackweb:/var/log/ -it --rm --name trackweb -d ben/trackweb:1
# docker exec -it trackweb bash