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

  //
  //  def apply() = {
  //    Http.collect[Request] {
  //      case req@(Method.GET -> !! / "users") =>
  //        Response.json("{}")
  //    }
  //  }
  //}

}

import zio._
import zio.http._

object HelloWorld extends ZIOAppDefault {

  val app1: App[Any] =
    Http.collect[Request] {
      case Method.GET -> !! / "users" => {
        Response.json(UserRestService.getAllUsers().asJson.toString())
      }
      case Method.GET -> !! / "books" => {
        Response.json(UserRestService.getAllBooks().asJson.toString())
      }
      case Method.GET -> "" /: "books" /: isbn => {
        Response.json(UserRestService.getBooksByIsbn(isbn.toString()).asJson.toString())
      }
    }

  /**
   * Always sucess handler
   */
  val app2 = Handler.succeed(Response.text("Always success handler")).toHttp

  val app3 = Http.collect[Request] {
    case Method.GET -> !! => Response.text("GET ")
    case (Method.HEAD -> !!) => Response.text(s"HEAD")
    case Version.`HTTP/1.1` -> !! =>
      Response.text(s"VERSIOn")
  }

  val app4 = Http.collectZIO[Request] {
    case Method.GET -> !! => {
      val i = Random.nextIntBetween(10, 20)
      i.map(n => Response.text(s"$n"))
    }
  }

  val combineHandlers = app3 ++ app1

  override val run = {
    Server.serve(app4).provide(Server.default)
  }
}