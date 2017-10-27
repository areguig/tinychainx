package io.areguig.minichainx.api

import com.google.gson.Gson
import io.areguig.minichainx.chain.{Block, Chain}
import io.vertx.core.json.Json
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.ext.web.Router

import scala.concurrent.Future

class MinichainxApiVerticle extends ScalaVerticle{

  override def startFuture(): Future[Unit] = {
    println("starting MinichainX Api Verticle")
    val router = Router.router(vertx)
    val port = 8080
    val gson = new Gson()

    router.get("/block").handler(
      _.response().putHeader("content-type", "application/json; charset=utf-8")
        .end(gson.toJson(Chain.get())
    ))

    router.post("/block/:data").handler(
      ctx=> {
        val b= Chain.generateNextBlock(ctx.request().params().get("data").get)
        Chain.addBlock(b)
        ctx.response().end(gson.toJson(b))
      }
    )


    vertx
      .createHttpServer()
      .requestHandler(router.accept _)
      .listenFuture(port, "0.0.0.0")
      .map(_ => ())
  }

  override def stopFuture(): Future[Unit] = {
    println("stopping MinichainxApiVerticle")
    Future.successful(())
  }

}
