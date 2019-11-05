FROM debian

# update
RUN apt update
RUN apt upgrade -y

# openjdk
RUN apt install openjdk-11-jre-headless -y

# copy application
ADD target/trackweb-2.0.jar /root/

#run application
CMD ["./root/trackweb-2.0.jar", "run"]

# docker build -t ben/trackweb:1 .
# docker run -p 8084:8084 -it --rm --name trackweb -d ben/trackweb:1
# docker exec -it trackweb bash