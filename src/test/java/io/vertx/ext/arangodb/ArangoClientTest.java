package io.vertx.ext.arangodb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * @author Ranger Tsao(https://github.com/boliza)
 */
@RunWith(VertxUnitRunner.class)
public class ArangoClientTest {

  Vertx vertx;
  ArangoDB client;

  @Before
  public void setUp() throws Exception {
    CountDownLatch latch = new CountDownLatch(1);
    vertx = Vertx.vertx();
    ArangoDB.create(vertx, new JsonObject().put("hosts", "127.0.0.1:8529").put("user", "root").put("password", "admin"), ir -> {
      if (ir.succeeded()) {
        client = ir.result();
      } else {
        throw new RuntimeException(ir.cause());
      }
      latch.countDown();
    });
    latch.await();
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void arangoDatabase(TestContext ctx) {
    ctx.assertNotNull(client.database("test"));
  }

}
