//Abilita la configurazione con MongoDB

package com.pietanze_microservice.pietanzemicroservice;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication(
        exclude = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class,
                MongoReactiveDataAutoConfiguration.class,
                EmbeddedMongoAutoConfiguration.class
        }
)
@AutoConfigureAfter({EmbeddedMongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoAutoConfiguration.class})
public class ApplicationConfiguration extends AbstractMongoClientConfiguration {

    @Value(value = "${MONGODB_HOSTNAME}")
    private String host;

    /*@Value(value="${MONGO_USER}")
    private String mongoUser;
    @Value(value="${MONGO_PASS}")
    private String mongoPass;
    @Value(value="${MONGO_AUTH_DB}")
    private String mongoAuthDb;*/

    @Value(value="${MONGODB_PORT}")
    private String port;

    @Value(value="${MONGO_DB_NAME}")
    private String dbname;

    public ApplicationConfiguration() {

    }

    @Override
    @Bean
    public MongoClient mongoClient() {

        //String s = String.format("mongodb://%s:%s@%s:%s/%s", user, pass, host, port, dbname);
        String s = String.format("mongodb://%s:%s/%s", host, port, dbname);
        return MongoClients.create(s);
    }

    @Override
    protected String getDatabaseName() {
        return dbname;
    }
}