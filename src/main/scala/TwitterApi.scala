import org.joda.time.format.DateTimeFormat
import twitter4j._
import twitter4j.conf._
import scala.collection.JavaConversions._
import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime

object  TwitterApi
{
  def config = {
    val config = ConfigFactory.load()
    val cb = new ConfigurationBuilder
    cb.setOAuthConsumerKey(config.getString("consumerKey"))
      .setOAuthConsumerSecret(config.getString("consumerSecret"))
      .setOAuthAccessToken(config.getString("accessToken"))
      .setOAuthAccessTokenSecret(config.getString("accessTokenSecret"))
    cb.build
  }

  def twitter = new TwitterFactory(config).getInstance

  def timeline = twitter.timelines

  def search(since: DateTime, keyword: String): List[Status] = {
    val fmt = DateTimeFormat.forPattern("yyyy-MM-dd")
    val query = new Query()
    query.setSince(fmt.print(since))
    query.setUntil(fmt.print(since.plusDays(1)))
    query.setQuery(keyword)
    query.setCount(1500)
    search(query)
  }

  def search(query: Query): List[Status] = {
    val result = twitter.search(query)
    if (result.hasNext) {
      result.getTweets.toList ++ search(result.nextQuery())
    } else {
      result.getTweets.toList
    }
  }

  def searchHomeTimeLine(keyword: String) = {
    timeline
      .getHomeTimeline(new Paging(1, 200))
      .filter(s => s.getText.contains(keyword))
  }

  def sendDirectMessage(text: String) = {
    val config = ConfigFactory.load()
    twitter.sendDirectMessage(config.getString("toDirectMessage"), text)
  }

  def streaming(listener: StatusListener) = {
    val twitterStream = new TwitterStreamFactory(config).getInstance
    twitterStream.addListener(listener)
    twitterStream
  }
}
