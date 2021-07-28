FROM java:8
MAINTAINER Fisher "715448004@qq.com"
VOLUME /tmp
ADD target/daily-note-0.0.1-SNAPSHOT.jar /daily-note.jar
RUN bash -c 'touch /daily-note-0.0.1-SNAPSHOT.jar'
ENV TZ 'Asia/Shanghai'
EXPOSE 30000

# 暴露调试端口，容器内部
EXPOSE 5005
# 下面的address和上面的EXPOSE一致
ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","/daily-note.jar"]

