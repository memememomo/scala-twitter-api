

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
    }

    parser.parse(args, Config()) match {
      case Some(config) =>
        config.command match {
          case "search" =>
            val result = TwitterApi.searchHomeTimeLine(config.keyword)
            result.map(_.getText).foreach(println(_))
          case _ => parser.showUsage()
        }
      case None =>
        parser.showUsage()
    }
  }
}
