import java.io.*;
import java.net.*;
import java.util.HashMap;


public class ChatServer{

private HashMap<String, Collegamento>  mappaCollegamenti ; 
  

    ChatServer() throws Exception {

        mappaCollegamenti = new HashMap<String, Collegamento>();

        System.out.println("Hello I'm SERVER");
        ServerSocket serverSocket = new ServerSocket(6789);

        while (true) {
           Socket connectionSocket = serverSocket.accept();
           new Collegamento(this , connectionSocket).start(); 
        }
    }

    public static void main(String argv[])throws Exception  {
       new ChatServer() ; 
      }


    void analizza(Collegamento collegamento , String stringaDaAnalizzare){

      if (stringaDaAnalizzare.charAt(0) != '@' ) return ;
      
      String[] splittata =stringaDaAnalizzare.split(" ", 2);
      String comando = splittata[0];
      
      
      String restoStringa ;
      if ( splittata.length == 1 )
          restoStringa ="";
      else
          restoStringa = splittata[1];
          
      switch(comando ){  
        case "@bye":
            collegamento.invia("@bye");
            collegamento.chiudo();
            return ; 
        case "@who":
            collegamento.invia("from server to you : lista fake per ora");
            break;  
        case "@login":
            //TODO: inserire controlli più raffinati
                 if ( collegamento.isLoggato() ) {
                      collegamento.invia("from server to you : sei già loggato");
                      return;
                 }
            else if ( mappaCollegamenti.containsKey(restoStringa) ){
                    collegamento.invia("from server to you : nickName in uso");
                    return;
                 }
            else {
                  mappaCollegamenti.put(restoStringa,collegamento);
                  collegamento.login(restoStringa);
                  collegamento.invia("from server to you : sei loggato come"
                                + restoStringa );
            }
            break;
        case "@all" :
            //TODO: dispatchhare a tutti
            break;
        default:
            //estraggo il primo carattere @ dal comando
            //todo inserire controllo se non rimane niente ..
            // quel che rimane è un nick name ...
            //controllo se esiste
            // ottengo il collegamento dalla mappa e invio il resto...


        }  

      
          
      //System.out.println("resto  "+splittata[1]);
     
      
    }


}

class Collegamento extends Thread {
 
  private ChatServer chatServer;
  private Socket socket ;
  private String fromIP ;
  private int fromPort ;

  private BufferedReader bufferIn ;
  private PrintWriter printOut ;
  private boolean sonoChiuso ;
  private boolean isLoggato;
  private String nickName;


  Collegamento( ChatServer server , Socket s ){
    this.isLoggato=false;
    this.nickName="";
    this.sonoChiuso = false;
    this.chatServer = server;
    this.socket = s;
    this.fromIP = s.getInetAddress().toString();
    this.fromPort = s.getPort();
    try {
      this.bufferIn = new BufferedReader (
                         new InputStreamReader(
                               socket.getInputStream()));
      this.printOut = new PrintWriter(
                               socket.getOutputStream());
     } catch (IOException ex) {  };
     System.out.println( "Connessione da " +fromIP + ":" +fromPort );

  }

  public void run(){

    String  stringaDalClient;
    try {

      while ( (stringaDalClient = bufferIn.readLine()) != null ){
        
          System.out.println( fromIP + ":" +fromPort + " = " 
                            + stringaDalClient );
                            
          chatServer.analizza(this , stringaDalClient);
          if ( sonoChiuso ) break;
      };


    } catch (IOException ex) {  };
 }
  void invia (String messaggio){

      printOut.write(messaggio+"\n");
      printOut.flush();

  }
  
  void chiudo(){
      try {  
          socket.close();
          System.out.println("chiusa connessione con "+fromIP + ":"
                                +fromPort);
          sonoChiuso = true;
      } catch (IOException ex) {  };
  }

  void login ( String nickName ){
    this.isLoggato = true;
    this.nickName = nickName ;
  }

  boolean isLoggato() { return this.isLoggato;}
}
