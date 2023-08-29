package com.example.zio.zlayer.services

import zio.{ZIO, ZLayer}

case class Tweet(id: Int, description: String)

trait TweetService {
  def findTweetById(id: Int): Tweet
  def getAll():String
}

class TweetServiceImpl extends TweetService {
  override def findTweetById(id: Int): Tweet = Tweet(1, "Brad")

  override def getAll(): String = "hello from tweet service"
}

object TweetServiceLive {
  val layer: ZLayer[Any, Any, TweetService] = ZLayer {
    ZIO.succeed(new TweetServiceImpl())
  }
}