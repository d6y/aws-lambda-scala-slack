package example

import java.time.Instant
import java.io.{InputStream, OutputStream}
import java.nio.charset.StandardCharsets.UTF_8
import cats.data.Xor._
import io.circe.generic.auto._, io.circe.parse.decode, io.circe.syntax._
import DateAndTime._

case class SlashCommand(
  token        : String,
  team_id      : String,
  team_domain  : String,
  channel_id   : String,
  channel_name : String,
  user_id      : String,
  user_name    : String,
  command      : String,
  text         : String,
  response_url : String
)

case class LongResponse(
  text          : String,
  response_type : String = "in_channel"
)

class Slack {

  def time(in: InputStream, out: OutputStream): Unit = {

    val payload = scala.io.Source.fromInputStream(in).mkString("")

    val reply = decode[SlashCommand](payload) match {
      case Right(cmd) if cmd.text.isEmpty => s"Usage: /time brighon sydney new-york"
      case Left(err)  => s"Sorry, I don't understand that: $err"
      case Right(cmd) =>
        val now = Instant.now
        val places = names(cmd.text)
        val descriptions = places.map(name => describe(now, name, zoneIdOf(name)))
        LongResponse(descriptions.mkString("\n")).asJson.noSpaces
    }
    out.write(reply.getBytes(UTF_8))
  }

}