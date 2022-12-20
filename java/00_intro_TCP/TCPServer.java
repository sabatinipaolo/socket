import java.io.*;
import java.net.*;

class TCPServer {
 public static void main(String argv[]) throws Exception {
  System.out.println("Hello I'm SERVER");

  ServerSocket serverSocket = new ServerSocket(6789);

  while (true) {
   Socket connectionSocket = serverSocket.accept();
   System.out.println("oh! Ho una connessione!!");
   
   Tool.pressEnterKeyToContinue("s1 ) premi enter chiudere la connessione con il client ");
   
   connectionSocket.close();
   System.out.println("I had close thid connection!! ");
   
  }
 }
}
