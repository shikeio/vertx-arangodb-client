package io.vertx.ext.arangodb;


import java.util.Map;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public interface ArangoDatabase {

  ArangoCollection collection(String collection);

  ArangoDatabase aql(String aql, Map<String, Object> bindVars, Handler<AsyncResult<JsonArray>> handler);

  String name();

}
