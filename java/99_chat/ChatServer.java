import java.io.*;
import java.net.*;


public class ChatServer{

public static void main(String argv[]) throws Exception {

    System.out.println("Hello I'm SERVER");
    ServerSocket serverSocket = new ServerSocket(6789);

    while (true) {
     Socket connectionSocket = serverSocket.accept();
     new Collegamento(connectionSocket).start(); 
    }
  }
}

class Collegamento extends Thread {

  private Socket socket ;
  private String fromIP ;
  private int fromPort ;

  private BufferedReader bufferIn ;
  private PrintWriter printOut ;

  
  Collegamento( Socket s ){
    this.socket = s;
    this.fromIP = s.getInetAddress().toString();
    this.fromPort = s.getPort();

  }

  public void run(){

    String  stringaDalClient;
    try {
      BufferedReader bufferIn = new BufferedReader (
                         new InputStreamReader(
                               socket.getInputStream()));
      PrintWriter printOut = new PrintWriter(
                               socket.getOutputStream());

      while ( (stringaDalClient = bufferIn.readLine()) != null ){
        
          System.out.println( fromIP + ":" +fromPort + " = " 
                            + stringaDalClient );

          if ( stringaDalClient=="@server bye" ) break;
      };

      socket.close();
      System.out.println("chiusa connessione con "+fromIP + ":"
                            +fromPort);
    } catch (IOException ex) {  };
 }


}
