package io.vertx.ext.arangodb.impl;


import java.util.Map;
import java.util.stream.Collectors;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arangodb.ArangoCollection;
import io.vertx.ext.arangodb.ArangoDatabase;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class ArangoDatabaseImpl implements ArangoDatabase {

  private Vertx vertx;
  private com.arangodb.ArangoDatabase database;

  public ArangoDatabaseImpl(Vertx vertx, com.arangodb.ArangoDatabase database) {
    this.vertx = vertx;
    this.database = database;
  }

  @Override
  public ArangoCollection collection(String collection) {
    return new ArangoCollectionImpl(vertx, database.collection(collection));
  }

  @Override
  public ArangoDatabase aql(String aql, Map<String, Object> bindVars, Handler<AsyncResult<JsonArray>> handler) {
    vertx.executeBlocking(fut -> fut.complete(new JsonArray(database.query(aql, bindVars, null, String.class)
                                                                    .asListRemaining()
                                                                    .stream()
                                                                    .map(JsonObject::new)
                                                                    .collect(Collectors.toList()))),
                          handler);
    return this;
  }

  @Override
  public String name() {
    return database.name();
  }

}
