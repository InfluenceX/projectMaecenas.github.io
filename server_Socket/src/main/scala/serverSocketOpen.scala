import java.io.IOException
import java.net.{InetAddress, ServerSocket, Socket}
import javax.net.ssl.SSLServerSocketFactory

case class serverSocketOpen(port:Int) extends Runnable {
  /// variabile che verifica la status del server: acceso o spento
  protected var isStopped=false

  /// uso il protocollo https
  protected var sock:SSLServerSocketFactory= new SSLServerSocketFactory {
    override def getDefaultCipherSuites: Array[String] = getDefaultCipherSuites

    override def getSupportedCipherSuites: Array[String] = getSupportedCipherSuites

    override def createServerSocket(i: Int): ServerSocket =
    try {
      new ServerSocket(i)
    } catch {
      case e:Exception => throw new RuntimeException("Error createServerSocket("+i+"): "+e.toString)
    }

    override def createServerSocket(i: Int, i1: Int): ServerSocket =
    try {
      new ServerSocket(i, i1)
    } catch {
      case e:Exception => throw new RuntimeException("Error createServerSocket("+i+", "+i1+"): "+e.toString)
    }

    override def createServerSocket(i: Int, i1: Int, inetAddress: InetAddress): ServerSocket =
      try {
        new ServerSocket(i, i1, inetAddress)
      } catch {
        case e:Exception => throw new RuntimeException("Error createServerSocket("+i+", "+i1+", "+inetAddress.toString+"): "+e.toString)
      }
  }
  protected var serverSocket:ServerSocket=_

  override def run(): Unit = {
    ///creo il socket su cui il server ascolterà
    serverSocket = sock.createServerSocket(port)

    /// Accetto connessioni dai client. Il server è multithread quindi lavorerà con i diversi client conteporeamente.
    /// La classe serverSocketWork si occuperà della comunicazione del server con il client
    var client:Socket=null
    while (!FisStopped) {
      try {
        client = serverSocket.accept()
      }
      catch {
        case e: IOException =>
          if (FisStopped) println("Server has Stopped")
          else throw new RuntimeException("Accept failed", e)
      }
      val t = new Thread(serverSocketWork(client))
      t.start()
    }
    println("Server has Stopped")
  }

  /// Chiusura delle connessioni del SeverSocket
  def FisStopped()=synchronized(this.isStopped)
  def stop()={
    def s = {
      isStopped = true
      try {
        serverSocket.close()
      } catch {
        case e: IOException =>
          throw new RuntimeException("Error closing the server", e)
      }
    }
    synchronized(s)
  }
}
