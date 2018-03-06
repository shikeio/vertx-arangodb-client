package io.vertx.ext.arangodb.impl;

import java.util.stream.Stream;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.arangodb.ArangoDB;
import io.vertx.ext.arangodb.ArangoDatabase;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class ArangoDBImpl implements ArangoDB {

  private Vertx vertx;
  private JsonObject options;

  private com.arangodb.ArangoDB arangoDB;

  public ArangoDBImpl(Vertx vertx, JsonObject options, Handler<AsyncResult<ArangoDB>> handler) {
    this.vertx = vertx;
    this.options = options;
    vertx.executeBlocking(fut -> {
                            try {
                              com.arangodb.ArangoDB.Builder builder = new com.arangodb.ArangoDB.Builder();
                              Stream.of(options.getString("hosts").split(","))
                                    .map(host -> new JsonObject().put("host", host.split(":")[0]).put("port", Integer.parseInt(host.split(":")[1])))
                                    .forEach(json -> builder.host(json.getString("host"), json.getInteger("port")));
                              if (options.containsKey("user")) {
                                builder.user(options.getString("user"));
                                builder.password(options.getString("password"));
                              }
                              arangoDB = builder.build();
                            } catch (Exception e) {
                              fut.fail(e);
                            }
                            fut.complete(this);
                          },
                          handler);
  }


  @Override
  public ArangoDatabase database(String database) {
    return new ArangoDatabaseImpl(vertx, arangoDB.db(database));
  }
}
