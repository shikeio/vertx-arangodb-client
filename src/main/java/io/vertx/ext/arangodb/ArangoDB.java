package io.vertx.ext.arangodb;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arangodb.impl.ArangoDBImpl;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public interface ArangoDB {

  static void create(Vertx vertx, JsonObject options, Handler<AsyncResult<ArangoDB>> handler) {
    new ArangoDBImpl(vertx, options, handler);
  }

  ArangoDatabase database(String database);

  default void close() {
    //do nothing
  }
}
