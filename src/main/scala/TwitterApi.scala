import twitter4j._
import twitter4j.conf._
import scala.collection.JavaConversions._
import com.typesafe.config.ConfigFactory

object  TwitterApi
{
  def buildConfig = {
    val config = ConfigFactory.load()
    val cb = new ConfigurationBuilder
    cb.setOAuthConsumerKey(config.getString("consumerKey"))
      .setOAuthConsumerSecret(config.getString("consumerSecret"))
      .setOAuthAccessToken(config.getString("accessToken"))
      .setOAuthAccessTokenSecret(config.getString("accessTokenSecret"))
    cb.build
  }

  def getTweet: ResponseList[Status] = {
    val twitterFactory = new TwitterFactory(buildConfig)
    val tt = twitterFactory.getInstance

    val timeLine = tt.timelines
    timeLine.getHomeTimeline(new Paging(1, 200))
  }

  def searchTweet(keyword: String): List[Status] = {
    getTweet.filter(s => s.getText.contains(keyword)).toList
  }

  def main(args: Array[String]) = {
    searchTweet("猫").foreach(s => println(s.getText))
  }
}
