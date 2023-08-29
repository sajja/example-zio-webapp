package com.example.zio.zlayer.services

import zio.http._
import zio.{Scope, ZIO, ZIOAppArgs, ZLayer}


case class RestServer(tweetService: TweetService) {
  val app = Http.collect[Request] {
    case Method.GET -> !! / "tweets" => Response.text(tweetService.getAll())
  }

  def start() = {
    Server.serve(app).provide(Server.default)
  }
}

object RestServerLayer {
  val layer: ZLayer[TweetService, Nothing, RestServer] = ZLayer {
    for {
      ts <- ZIO.service[TweetService]
    } yield {
      RestServer(ts)
    }
  }
}

object RestAPI extends zio.ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.serviceWithZIO[RestServer](_.start()).provide(RestServerLayer.layer, TweetServiceLive.layer)
    } yield {}
  }
}
