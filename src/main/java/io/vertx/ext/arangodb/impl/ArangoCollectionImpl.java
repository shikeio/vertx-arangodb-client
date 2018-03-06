package io.vertx.ext.arangodb.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arangodb.ArangoCollection;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class ArangoCollectionImpl implements ArangoCollection {

  private Vertx vertx;
  private com.arangodb.ArangoCollection collection;

  public ArangoCollectionImpl(Vertx vertx, com.arangodb.ArangoCollection collection) {
    this.vertx = vertx;
    this.collection = collection;
  }

  @Override
  public ArangoCollection insert(JsonObject document, Handler<AsyncResult<JsonObject>> handler) {
    vertx.executeBlocking(fut -> fut.complete(new JsonObject(Json.encode(collection.insertDocument(document.encode())))), handler);
    return this;
  }

  @Override
  public ArangoCollection insert(String key, JsonObject document, Handler<AsyncResult<JsonObject>> handler) {
    vertx.executeBlocking(fut -> fut.complete(new JsonObject(Json.encode(collection.insertDocument(document.put("_key", key).encode())))), handler);
    return this;
  }

  @Override
  public ArangoCollection get(String key, JsonObject readOptions, Handler<AsyncResult<JsonObject>> handler) {
    vertx.executeBlocking(fut -> fut.complete(new JsonObject(collection.getDocument(key, String.class))), handler);
    return this;
  }

  @Override
  public ArangoCollection delete(String key, Handler<AsyncResult<JsonObject>> handler) {
    vertx.executeBlocking(fut -> fut.complete(new JsonObject(Json.encode(collection.deleteDocument(key)))), handler);
    return this;
  }

  @Override
  public ArangoCollection update(String key, JsonObject document, Handler<AsyncResult<JsonObject>> handler) {
    vertx.executeBlocking(fut -> fut.complete(new JsonObject(Json.encode(collection.updateDocument(key, document.encode())))), handler);
    return this;
  }

  @Override
  public ArangoCollection count(Handler<AsyncResult<JsonObject>> handler) {
    vertx.executeBlocking(fut -> fut.complete(new JsonObject(Json.encode(collection.count()))), handler);
    return this;
  }

  @Override
  public String name() {
    return collection.name();
  }
}
