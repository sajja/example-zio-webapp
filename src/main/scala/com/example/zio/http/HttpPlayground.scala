package com.example.zio.http

import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._
import zio.http.Version

case class User(id: Int, firstName: String, lastName: String)

case class Book(name: String, isbn: String)

object UserRestService {
  implicit val userEncoder: Encoder[User] = deriveEncoder[User]
  implicit val booksEncoder: Encoder[Book] = deriveEncoder[Book]
  val users = Seq(
    User(1, "Hello", "world"),
    User(2, "Cable", "Guy"),
    User(3, "Drowned", "God")
  )
  val books = Seq(
    Book("Hardy Boys", "1"),
    Book("Mist", "2"),
    Book("Wonky", "3")
  )

  def getAllUsers() = users.asJson

  def getUsersById(id: String) = users.filter(_.id == id).head.asJson

  def getAllBooks() = books.asJson

  def getBooksByIsbn(id: String) = {
    books.filter(_.isbn == id).head.asJson
  }
}

import zio._
import zio.http._

object HelloWorld extends ZIOAppDefault {

  override val run = {
    for {
      i <- ZIO.succeed(42)
      _ <- Console.printLine(i)
    } yield ()
  }
}