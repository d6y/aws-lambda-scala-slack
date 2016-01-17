package example

import org.scalatest._

class JsonSpec extends FreeSpec {

  import io.circe._, io.circe.generic.auto._, io.circe.parse.decode, io.circe.syntax._
  import cats.data.Xor

   def payload(text: String) = s"""
     {
      "token" : "tokenvalue",
      "team_id" : "T123AB4CD",
      "team_domain" : "underscoreio",
      "channel_id" : "G1ABCD2EF",
      "channel_name" : "privategroup",
      "user_id" : "U012U3ABC",
      "user_name" : "richard",
      "command" : "/time",
      "text" : "$text",
      "response_url" : "https://hooks.slack.com/commands/T123AB4CD/18701940688/0foo"
    }
   """

  "A lambda payload" - {

    "should decode into a command" in {
      val expected = SlashCommand(
        "tokenvalue",
        "T123AB4CD",
        "underscoreio",
        "G1ABCD2EF",
        "privategroup",
        "U012U3ABC",
        "richard",
        "/time",
        "sydney",
        "https://hooks.slack.com/commands/T123AB4CD/18701940688/0foo"
      )

      assertResult(Xor.right(expected)) {
        decode[SlashCommand](payload("sydney"))
      }
    }

    "should decode into a command with no text " in {
      val expected = SlashCommand(
        "tokenvalue",
        "T123AB4CD",
        "underscoreio",
        "G1ABCD2EF",
        "privategroup",
        "U012U3ABC",
        "richard",
        "/time",
        "",
        "https://hooks.slack.com/commands/T123AB4CD/18701940688/0foo"
      )

      assertResult(Xor.right(expected)) {
        decode[SlashCommand](payload(""))
      }
    }
  }

}