import java.io.*;
import java.net.*;

class TCPServer {
 public static void main(String argv[]) throws Exception {
  System.out.println("Hello I'm SERVER");

  ServerSocket serverSocket = new ServerSocket(6789);

  while (true) {
   Socket connectionSocket = serverSocket.accept();
   System.out.println("oh! Ho una connessione!!");

   DataInputStream dis=new DataInputStream(connectionSocket.getInputStream());  
   String  str=(String)dis.readUTF();
     
   System.out.println(connectionSocket.getPort() + " message= "+str);  
   
   Tool.pressEnterKeyToContinue("s1 ) premi enter chiudere la connessione con il client ");
   
   connectionSocket.close();
   System.out.println("Ho chiuso la connessione !! ");
   
  }
 }
}
