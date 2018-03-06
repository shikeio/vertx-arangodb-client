package io.vertx.ext.arangodb;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public interface ArangoCollection {

  ArangoCollection insert(JsonObject document, Handler<AsyncResult<JsonObject>> handler);

  ArangoCollection insert(String key, JsonObject document, Handler<AsyncResult<JsonObject>> handler);

  default ArangoCollection get(String key, Handler<AsyncResult<JsonObject>> handler) {
    return get(key, null, handler);
  }

  ArangoCollection get(String key, JsonObject readOptions, Handler<AsyncResult<JsonObject>> handler);

  ArangoCollection delete(String key, Handler<AsyncResult<JsonObject>> handler);

  ArangoCollection update(String key, JsonObject document, Handler<AsyncResult<JsonObject>> handler);

  default ArangoCollection replace(String key, JsonObject document, Handler<AsyncResult<JsonObject>> handler) {
    return this;
  }

  ArangoCollection count(Handler<AsyncResult<JsonObject>> handler);

  String name();

}
