package io.areguig.minichainx

import io.areguig.minichainx.api.MinichainxApiVerticle
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.core.Vertx

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object MinichainxRunner {

  def main(args: Array[String]): Unit = {
    val vertx = Vertx.vertx()
    val startFuture = vertx.deployVerticleFuture(ScalaVerticle.nameForVerticle[MinichainxApiVerticle])
    startFuture.onComplete{
      case Success(result) => {
        println(s"Deployment id is: ${result}")
      }
      case Failure(cause) => {
        println(s"$cause")
      }
    }
  }

}
