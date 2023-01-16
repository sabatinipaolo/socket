import java.io.*;
import java.net.*;


class TCPClient {
 public static void main(String argv[]) throws Exception {
  System.out.println("Hello I'm Client");

  String serverAddres = "127.0.0.1" ; 

  Tool.pressEnterKeyToContinue("c1 ) premi enter per creare un socket connesso al server");

  Socket clientSocket = new Socket(serverAddres, 6789);


  Tool.pressEnterKeyToContinue("c2 ) premi enter per inviare \"ciao mondo!!\" al server");

  String messaggio="ciao mondo!!\n";
  DataOutputStream dout = new DataOutputStream( clientSocket.getOutputStream());
  dout.writeUTF(messaggio);
  dout.flush();

  Tool.pressEnterKeyToContinue("c3 ) premi enter chiudere la connessione ");
  clientSocket.close();

 }
}
