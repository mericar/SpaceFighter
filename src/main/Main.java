package main;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;


import gui.SelectScreen;

/**
 * Created by mirk on 11/13/2015.
 */
public class Main {
    // main
    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient( "localhost", 8002 );
        DB db = mongoClient.getDB( "mydb" );
        System.out.println(db.getName());

        new SelectScreen();
    }

}














