FROM openjdk:11

WORKDIR /usr

RUN apt-get install wget -y
RUN apt-get install tar -y
RUN wget https://archive.apache.org/dist/kafka/2.8.1/kafka_2.12-2.8.1.tgz
RUN tar -xzf kafka_2.12-2.8.1.tgz
RUN wget https://github.com/aws/aws-msk-iam-auth/releases/download/v1.1.1/aws-msk-iam-auth-1.1.1-all.jar
RUN ls kafka_2.12-2.8.1
RUN mv aws-msk-iam-auth-1.1.1-all.jar kafka_2.12-2.8.1/libs/aws-msk-iam-auth-1.1.1-all.jar
COPY client.properties kafka_2.12-2.8.1/bin
RUN rm kafka_2.12-2.8.1.tgz
RUN mv kafka_2.12-2.8.1 kafka


WORKDIR /usr/kafka/bin

CMD ["/bin/bash"]