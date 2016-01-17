package example

import org.scalatest._

class DecodeSpec extends FreeSpec {

  import io.circe._, io.circe.generic.auto._, io.circe.parse._, io.circe.syntax._

  "A response" - {
    "should encode into a set of attachments" in {
      val expected =
        """{"text":"Abc","response_type":"in_channel"}"""
      assertResult(expected) {
        LongResponse("Abc").asJson.noSpaces
      }
    }
  }

}