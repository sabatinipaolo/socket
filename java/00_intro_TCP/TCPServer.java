import java.io.*;
import java.net.*;

class TCPServer {
 public static void main(String argv[]) throws Exception {
  System.out.println("Hello I'm SERVER");

  ServerSocket welcomeSocket = new ServerSocket(6789);

  while (true) {
   Socket connectionSocket = welcomeSocket.accept();
   System.out.println("I have a connection !!");
  }
 }
}
