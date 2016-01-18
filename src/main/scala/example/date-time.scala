package example

import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter
import scala.collection.JavaConverters._

object DateAndTime {

  lazy val allZones = ZoneId.getAvailableZoneIds.asScala

  val uk = ZoneId.of("Europe/London")
  val eastCoast = ZoneId.of("America/New_York")
  val westCoast = ZoneId.of("America/Los_Angeles")

  val aliases = Map(
    "brighton"     -> uk,
    "portland"     -> westCoast,
    "philadelphia" -> eastCoast,
    "philly"       -> eastCoast
  )



  def names(text: String): Set[String] =
    text.split("\\s+").toSet

  def nameMatch(lowerCasePlace: String)(zoneId: String): Boolean =
      (zoneId.toLowerCase contains lowerCasePlace) ||
      (zoneId.toLowerCase contains lowerCasePlace.replace('-', '_'))

  def zoneIdOf(lowerCasePlace: String): Option[ZoneId] =
    aliases.get(lowerCasePlace) orElse {
      allZones
        .find(nameMatch(lowerCasePlace))
        .flatMap(n => Option(ZoneId.of(n)))
      }

  def describe(now: Instant, place: String, zoneId: Option[ZoneId]): String =
    zoneId match {
      case None    => s"I don't know the time in $place"
      case Some(z) => DateTimeFormatter
                        .ofPattern(s"'In $place it''s' HH:mm 'on' EEEE")
                        .withZone(z)
                        .format(now)
    }

}