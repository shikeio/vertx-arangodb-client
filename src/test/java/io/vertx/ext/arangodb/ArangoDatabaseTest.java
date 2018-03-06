package io.vertx.ext.arangodb;

import org.junit.Test;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
public class ArangoDatabaseTest extends ArangoClientTest {

  @Test
  public void collection(TestContext ctx) {
    ctx.assertNotNull(client.database("test").collection("test"));
  }

  @Test
  public void aql(TestContext ctx) {
    Async async = ctx.async();
    client.database("test").aql("FOR t in test return t", null, aql -> {
      ctx.assertTrue(aql.succeeded());
      System.out.println(aql.result().encodePrettily());
      async.complete();
    });
    async.await();
  }

}
