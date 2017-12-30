
import java.io.{BufferedReader, InputStreamReader, PrintWriter}
import java.net.Socket
import javax.net.ssl.{HttpsURLConnection, SSLSocketFactory}

case class serverSocketWork(client:Socket) extends Runnable{
  override def run(): Unit = {
    val in=new BufferedReader(new InputStreamReader(client.getInputStream))
    val out=new PrintWriter(client.getOutputStream, true)
    //def getHTTPRequest() = {
   /*   val requestLine = in.readLine()
      val (method, url, version) =
        (requestLine.takeWhile(_ != ' '), requestLine.dropWhile(_ != ' ').takeWhile(_ != ' '),
          requestLine.dropWhile(_ != ' ').dropWhile(_ != ' ').takeWhile(_ != '\r'))
      val headerTitle = in.readLine()
      val (headerFieldName, value)=(headerTitle.takeWhile(_!=' '), headerTitle.dropWhile(_!=' ').takeWhile(_!='\r'))
      var headerBody:String=""
      var line:String=null
      while ((!(line=in.readLine()).equals(headerTitle))) {
        headerBody+=line
      }
      line=in.readLine()
      var entityBody:String=""
      while ((line=in.readLine())!=null) {
          entityBody+=line
      }*/
   // }
    val https=HttpsURLConnection

  }
}
