package pl.cebula.smp.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;

@Getter
public final class MongoDatabaseService {

    private final MongoDatabase mongoDatabase;
    private final MongoClient mongoClient;

    public MongoDatabaseService() {
        MongoClientURI clientURI = new MongoClientURI("mongodb://admin:no4eX7Xv998uX6C@158.174.87.179:55101/");

        this.mongoClient = new MongoClient(clientURI);
        this.mongoDatabase = this.mongoClient.getDatabase("smp_dev");
    }

}
