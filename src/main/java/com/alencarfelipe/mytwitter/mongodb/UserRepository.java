package com.alencarfelipe.mytwitter.mongodb;

import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.repositorio.UJCException;
import com.alencarfelipe.mytwitter.repositorio.UNCException;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import static com.mongodb.client.model.Filters.eq;

@Primary
@Component
public class UserRepository implements IRepositorioUsuario {
    @Value("${database.uri}")
    private String uri;
    
    @Value("${database.name}")
    private String dbName;

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<PerfilDTO> perfis;

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

        perfis = db.getCollection("perfis", PerfilDTO.class);    
    }  
    
    @Override
    public void cadastrar(Perfil usuario) throws UJCException {
        connect();

        if(buscar(usuario.getUsuario()) != null) {
            throw new UJCException();
        }
        
        perfis.insertOne(new PerfilDTO(usuario));
    }

    @Override
    public Perfil buscar(String usuario) {
        connect();

        PerfilDTO perfilDTO = perfis.find(eq("usuario", usuario)).first();

        if(perfilDTO == null) {
            return null;
        }

        return perfilDTO.toPerfil();
    }

    @Override
    public void atualizar(Perfil usuario) throws UNCException {
        connect();

        if(buscar(usuario.getUsuario()) == null) {
            throw new UNCException();
        }

        PerfilDTO perfil = new PerfilDTO(usuario);
        
        perfis.replaceOne(eq("usuario", perfil.getUsuario()), perfil);
    }
    
}
