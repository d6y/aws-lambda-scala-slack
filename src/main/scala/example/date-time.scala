package example

import java.time.{Instant, ZoneId}
import java.time.format.DateTimeFormatter
import scala.collection.JavaConverters._

object DateAndTime {

  def names(text: String): Set[String] =
    text.split("\\s+").toSet

  def nameMatch(lowerCasePlace: String)(zoneId: String): Boolean =
      (zoneId.toLowerCase contains lowerCasePlace) ||
      (zoneId.toLowerCase contains lowerCasePlace.replace('-', '_'))

  def zoneIdOf(place: String): Option[ZoneId] =
    place.toLowerCase match {
      case "brighton" => Some(ZoneId.of("Europe/London"))
      case p          => ZoneId
                          .getAvailableZoneIds.asScala
                          .find(nameMatch(p))
                          .flatMap(n => Option(ZoneId.of(n)))
    }

  def describe(now: Instant, place: String, zoneId: Option[ZoneId]): String =
    zoneId match {
      case None    => s"I don't know the time in $place"
      case Some(z) => DateTimeFormatter
                        .ofPattern(s"'In $place it''s' H:mm 'on' EEEE")
                        .withZone(z)
                        .format(now)
    }


}