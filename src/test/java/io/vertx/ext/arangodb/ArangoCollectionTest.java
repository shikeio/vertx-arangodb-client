package io.vertx.ext.arangodb;

import org.junit.Test;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class ArangoCollectionTest extends ArangoDatabaseTest {

  @Test
  public void insert(TestContext ctx) {
    Async async = ctx.async();
    client.database("test")
          .collection("test")
          .insert(String.valueOf(System.currentTimeMillis()),
                  new JsonObject().put("time", String.valueOf(System.currentTimeMillis())),
                  insert -> {
                    ctx.assertNotNull(insert.result());
                    client.database("test")
                          .collection("test")
                          .delete(insert.result().getString("key"),
                                  delete -> async.complete());
                  });
    async.await();
  }

  @Test
  public void getThenDelete(TestContext ctx) {
    Async async = ctx.async();
    client.database("test").collection("test").insert("key-1", new JsonObject().put("time", System.currentTimeMillis()), insert -> {
      ctx.assertNotNull(insert.result());
      client.database("test").collection("test").get("key-1", get -> {
        ctx.assertNotNull(get.result());
        client.database("test").collection("test").delete("key-1", delete -> {
          ctx.assertNotNull(delete.result());
          async.complete();
        });
      });
    });
    async.await();
  }

  @Test
  public void update(TestContext ctx) {
    Async async = ctx.async();
    client.database("test").collection("test")
          .insert(new JsonObject().put("time", System.currentTimeMillis()), insert -> {
            ctx.assertNotNull(insert.result());
            client.database("test").collection("test")
                  .update(insert.result().getString("key"), new JsonObject().put("time", System.currentTimeMillis()), update -> {
                    ctx.assertNotNull(update.result());
                    client.database("test").collection("test")
                          .delete(update.result().getString("key"), delete -> {
                            ctx.assertNotNull(delete.result());
                            async.complete();
                          });
                  });
          });
    async.await();
  }

}
