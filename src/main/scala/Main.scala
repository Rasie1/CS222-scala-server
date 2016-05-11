import java.io._
import java.net.{ServerSocket, Socket}

object Main {
  class ProcessClient(socket : Socket) extends Runnable {
    def run() = {
      val out = new PrintWriter(socket.getOutputStream(), true)
      val in = new BufferedReader(new InputStreamReader(socket.getInputStream()))

      var inputLine = in.readLine()
      while (inputLine != null && inputLine != "\n\n")
      {
        val answer = "HTTP/1.1 200 OK\nServer: super fast mexmat server\nContent-Type: text/html\nContent-Length: 1\n\n"
        out.println(answer + inputLine.charAt(7))
        out.flush()
        inputLine = in.readLine()
      }
      socket.close()
    }
  }

  def main(args: Array[String]): Unit = {
    val server = new ServerSocket(19001)
    while (true) {
      new Thread(new ProcessClient(server.accept())).start()
    }
  }
}
