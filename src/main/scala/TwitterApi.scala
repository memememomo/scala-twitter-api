import twitter4j._
import twitter4j.conf._
import scala.collection.JavaConversions._
import com.typesafe.config.ConfigFactory

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
