import java.io.*;
import java.net.*;

class TCPServer {
  public static void main(String argv[]) throws Exception {
    System.out.println("Hello I'm SERVER");

    ServerSocket serverSocket = new ServerSocket(6789);

    while (true) {

     Socket connectionSocket = serverSocket.accept();
     new connectionConUnClient(connectionSocket).start(); 
  
    }
  }
}


class connectionConUnClient extends Thread{
  private Socket connectionSocket ;
    
  public  connectionConUnClient( Socket s ){
    this.connectionSocket = s;
  };

  public void run(){

    System.out.println("oh! Ho una connessione!!" +
                       connectionSocket.getInetAddress().toString()+ ":"
                         + connectionSocket.getPort());  

    try {
      //DataInputStream dis=new DataInputStream(connectionSocket.getInputStream());
      // per migliore cinversione dei stringhe meglio usare la classe Buffered ..
      // in quanto il metodo readline di DataInputStream è deprecato ...
      // vedi https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/io/DataInputStream.html#readLine()

      BufferedReader dis = new BufferedReader (
                              new InputStreamReader(
                                  connectionSocket.getInputStream()));
      DataOutputStream dout=new DataOutputStream(
                                  connectionSocket.getOutputStream());

      String  stringaDalClient;


      while ( (stringaDalClient = dis.readLine()) != null ){
        
        System.out.println( connectionSocket.getInetAddress().toString()
                            + ":" + connectionSocket.getPort()
                            + ">>" + stringaDalClient);

        if ( stringaDalClient=="@server bye" ) break;

        String s = "il server dice : ho ricevuto" + stringaDalClient;
        dout.writeUTF(s);
        dout.flush();
      }

      connectionSocket.close();
      System.out.println("Ho chiuso la connessione !! con "+connectionSocket.getInetAddress().toString()+ ":" + connectionSocket.getPort() ) ;

     } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
     }
  }


  
}
