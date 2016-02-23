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

  def main(args: Array[String]) = {
    val keyword = "猫"
    val result = timeline
      .getHomeTimeline(new Paging(1, 200))
      .filter(s => s.getText.contains(keyword))
    result.map(_.getText).foreach(println(_))
  }
}
