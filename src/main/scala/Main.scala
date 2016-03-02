import  twitter4j._
import org.joda.time.DateTime

object Main {
  case class Config(command: String = "", keyword: String = "")

  def main(args: Array[String]): Unit = {
    val parser = new scopt.OptionParser[Config]("scopt") {
      head("twitter-api", "1.0")
      cmd("search") action { (_, c) =>
        c.copy(command = "search")
      } text "search is a command" children (
        arg[String]("keyword") action { (x, c) =>
          c.copy(keyword = x)
        } text "keyword for search"
        )
      cmd("ranking") action { (_, c) =>
        c.copy(command = "ranking")
      } text "ranking is a command" children (
        arg[String]("keyword") action { (x, c) =>
          c.copy(keyword = x)
        } text "keyword for search"
        )
      cmd("stream") action { (_, c) =>
        c.copy(command = "stream")
      } text "stream is a command"
    }

    parser.parse(args, Config()) match {
      case Some(config) =>
        config.command match {
          case "search" =>
            val result = TwitterApi.searchHomeTimeLine(config.keyword)
            println("Hit: " + result.length)
            result.foreach(t => {
              TwitterApi.sendDirectMessage(t.getId + ":" + t.getText)
            })
          case "ranking" =>
            val since = new DateTime(2016, 2, 24, 0, 0)
            TwitterApi
              .search(since, config.keyword)
              .sortWith(_.getFavoriteCount > _.getFavoriteCount)
              .filter(!_.isRetweet)
              .zipWithIndex
              .foreach{v =>
                val s = v._1
                val i = v._2
                println((i+1) + ":" + s.getFavoriteCount + ":" + s.getUser.getName + ":" + s.getText)
              }
          case "stream" =>
            var stream = TwitterApi.streaming(new StatusListener {
              override def onStallWarning(warning: StallWarning): Unit = {
              }

              override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {
              }

              override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {
              }

              override def onStatus(status: Status): Unit = {
              }

              override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {
              }

              override def onException(ex: Exception): Unit = {
              }
            })
            stream.user()
          case _ => parser.showUsage()
        }
      case None =>
        parser.showUsage()
    }
  }
}
