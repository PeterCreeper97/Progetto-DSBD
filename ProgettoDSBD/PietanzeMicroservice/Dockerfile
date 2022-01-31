#builder
FROM maven:3-jdk-8 AS builder
WORKDIR /project
COPY . .
RUN mvn package


FROM openjdk:8-jdk-alpine

WORKDIR /app
#viene copiato l'artefatto costruito dal builder nell'immagine docker
COPY --from=builder /project/target/PietanzeMicroservice-0.0.1-SNAPSHOT.jar ./PietanzeMicroservice.jar
ENTRYPOINT ["/bin/sh", "-c"]
CMD [ "java -jar PietanzeMicroservice.jar" ]