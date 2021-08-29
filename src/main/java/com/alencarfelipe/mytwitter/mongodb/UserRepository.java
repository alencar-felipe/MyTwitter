package com.alencarfelipe.mytwitter.mongodb;

import com.alencarfelipe.mytwitter.pojos.Perfil;
import com.alencarfelipe.mytwitter.pojos.PerfilDTO;
import com.alencarfelipe.mytwitter.repositorio.IRepositorioUsuario;
import com.alencarfelipe.mytwitter.repositorio.ITweetRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import static com.mongodb.client.model.Filters.eq;

@Primary
@Component
@RequiredArgsConstructor
public class UserRepository implements IRepositorioUsuario {
    @Value("${database.uri}")
    @Setter
    private String uri;
    
    @Value("${database.name}")
    @Setter
    private String dbName;

    @Autowired
    @Setter
    private ITweetRepository tweetRepository;

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

        return perfilDTO.toPerfil(tweetRepository);
    }

    @Override
    public void atualizar(Perfil perfil) throws UNCException {
        connect();

        if(buscar(perfil.getUsuario()) == null) {
            throw new UNCException();
        }

        PerfilDTO perfilDTO = new PerfilDTO(perfil);
        
        perfis.replaceOne(eq("usuario", perfil.getUsuario()), perfilDTO);
    }

    @Override
    public void delete(Perfil perfil) throws UNCException {
        connect();
        
        if(buscar(perfil.getUsuario()) == null) {
            throw new UNCException();
        }

        perfis.deleteOne(eq("usuario", perfil.getUsuario()));
    }
}
