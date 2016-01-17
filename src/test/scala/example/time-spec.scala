package example

import org.scalatest._
import java.time.{OffsetDateTime, ZoneId, Instant}
import java.time.ZoneOffset.UTC

class TimeSpec extends FreeSpec {

  "time lookup" - {

    "should split a string into names" in {
      assertResult(Set("london", "new-york", "paris")) {
        DateAndTime.names("london new-york  paris")
      }
    }

    "should pick out known names" in {
      assert(DateAndTime.zoneIdOf("London") == Some(ZoneId.of("Europe/London")))
      assert(DateAndTime.zoneIdOf("sydney") == Some(ZoneId.of("Australia/Sydney")))
      assert(DateAndTime.zoneIdOf("wibble") == None)
    }

    "should pick out aliases" in {
      assert(DateAndTime.zoneIdOf("Brighton") == Some(ZoneId.of("Europe/London")))
    }

    "should describe a known place" in {
      val now = OffsetDateTime.of(2016, 1, 17, 21, 49, 0, 0, UTC).toInstant
      assertResult("In Brighton it's 21:49 on Sunday") {
        DateAndTime.describe(now, "Brighton", Some(ZoneId.of("Europe/London")))
      }
      assertResult("In Sydney it's 8:49 on Monday") {
        DateAndTime.describe(now, "Sydney", Some(ZoneId.of("Australia/Sydney")))
      }
    }

  }

}