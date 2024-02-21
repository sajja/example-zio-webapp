package com.example.zio.di.service.zioservice


case class Tweet(id: String, tweet: String)
/*
  R => Either[E,A]
 */

trait TweetService {
  def getTweet(id: String): Tweet
}

trait TweetRepo {
  def getTweet(id: String): Tweet
}


class TweetRepoImpl extends TweetRepo {
  def getTweet(id: String): Tweet = Tweet("X","Y")
}

class TweetServiceImpl(tweetRepo: TweetRepo) extends TweetService {
  def getTweet(id: String): Tweet = tweetRepo.getTweet(id)
}


//Companion objects
object TweetRepoImpl {
  def apply() = new TweetRepoImpl()
}


object TweetServiceImpl {
  def apply(tweetRepo: TweetRepo): TweetServiceImpl = new TweetServiceImpl(tweetRepo)
}


object TestMe {
  def main(args: Array[String]): Unit = {
    val tweetService = TweetServiceImpl(TweetRepoImpl())
    println(tweetService.getTweet("sfa"))
  }
}