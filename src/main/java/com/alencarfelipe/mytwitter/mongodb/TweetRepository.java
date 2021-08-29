package com.alencarfelipe.mytwitter.mongodb;

import java.util.ArrayList;
import java.util.List;

import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.repositorio.INException;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;
import com.alencarfelipe.mytwitter.pojos.Tweet;
import com.alencarfelipe.mytwitter.repositorio.UNCException;
import com.alencarfelipe.mytwitter.services.ITweetServices;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;

import static com.mongodb.client.model.Filters.eq;

@Primary
@Component
public class TweetRepository implements ITweetRepository {
    @Value("${database.uri}")
    private String uri;
    
    @Value("${database.name}")
    private String dbName;

    @Autowired
    private ITweetServices tweetServices;

    @Autowired
    private IRepositorioUsuario repositorioUsuario;

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Tweet> tweets;

    private void connect() {
        if(db != null) return;
        
        ConnectionString connectionString = new ConnectionString(uri);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder().automatic(true).build());
        
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            pojoCodecRegistry);
                                             
        MongoClientSettings clientSettings =   
            MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .codecRegistry(codecRegistry)
            .build();

        mongoClient = MongoClients.create(clientSettings);
        db = mongoClient.getDatabase(dbName);

        tweets = db.getCollection("tweets", Tweet.class);    
    }  
    
    @Override
    public void addTweet(Tweet tweet) throws INException {
        connect();

        if(!tweetServices.isTweetValid(tweet)) {
            throw new INException();
        }

        tweets.insertOne(tweet);
    }

    @Override
    public List<Tweet> getPerfilTweets(Perfil perfil) throws UNCException {
        connect();
        
        String usuario = perfil.getUsuario();

        if(repositorioUsuario.buscar(usuario) == null) {
            throw new UNCException();
        }

        ArrayList<Tweet> userTweets = new ArrayList<>();

        MongoCursor<Tweet> cursor =
            tweets.find(eq("usuario", usuario)).iterator();

        while(cursor.hasNext()) {
            userTweets.add(cursor.next());
        }

        cursor.close();

        return userTweets;
    }
    
}