import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ChatServer{

    HashMap<String, Collegamento>  mappaCollegamenti =
                                new HashMap<String, Collegamento>();

    private int portaAscolto = 6789 ;

    ChatServer()  throws Exception {

        ServerSocket serverSocket = new ServerSocket(portaAscolto);
        Logger.logga("Server partito in ascolto sulla porta : "
                      + portaAscolto);

        while (true) {
           Socket connectionSocket = serverSocket.accept();
           new Collegamento(this , connectionSocket).start(); 
        }
    }

    public static void main(String argv[])throws Exception  {
       new ChatServer() ; 
      }

}

class Logger {

  static void logga(String stringa){
      System.out.println(stringa); //TODO inserire timestamp
    }
}

class Collegamento extends Thread {

    ChatServer chatServer;
    private Socket socket ;
    private BufferedReader bufferIn ;
    private PrintWriter printOut ;
    private boolean sonoChiuso = false ;
    private boolean isLoggato = false;
    private String nickName ="";


    Collegamento( ChatServer chatServer , Socket socket ){

        this.chatServer = chatServer;
        this.socket = socket;
        try {
            this.bufferIn = new BufferedReader (
                              new InputStreamReader(
                               socket.getInputStream()));
            this.printOut = new PrintWriter(
                               socket.getOutputStream());
        } catch (IOException ex) {  };
        Logger.logga( "Connessione da " + toStringa() );
    }

    public void run(){  

        String  stringaDalClient="";
        while ( nonSonoChiuso()){
            try {
                  stringaDalClient = bufferIn.readLine();
            } catch (IOException ex) {  };
            
            Logger.logga( "Messaggio da " + toStringa()
                                      + stringaDalClient );
                              
            GestoreProtocollo.analizza(this , stringaDalClient);
            if ( sonoChiuso ) break;
        };


    }

    String toStringa(){
        return (socket.getInetAddress().toString()
                + ":" + socket.getPort() );
    }

    void invia (String messaggio){

        printOut.write(messaggio+"\n");
        printOut.flush();
        Logger.logga( "invio a "+ toStringa()+" "+ messaggio) ;
    }

    boolean nonSonoChiuso(){
        return ( !sonoChiuso );
    }

    void chiudo(){
        try {  
            socket.close();
            Logger.logga("chiusa connessione " + toStringa() );
            sonoChiuso = true;
        } catch (IOException ex) {  };
    }

    void login ( String nickName ){
      this.isLoggato = true;
      this.nickName = nickName ;
    }

    boolean isLoggato() {
        return this.isLoggato;
    }
}

class GestoreProtocollo{
    static void analizza(Collegamento collegamento , String stringaDaAnalizzare){

        if (stringaDaAnalizzare.charAt(0) != '@' ) return ;
            
        String[] splittata =stringaDaAnalizzare.split(" ", 2);
        String comando = splittata[0];

        String restoStringa
                  = ( splittata.length > 1 )  ?  splittata[1]  : "";
              
        switch(comando ){  
            case "@bye":
                esegueBye( collegamento );
                break;
            case "@who":
                esegueWho( collegamento );
                break;  
            case "@login":
                esegueLogin ( collegamento , restoStringa );
                break;
            case "@all" :
                esegueAll( collegamento , restoStringa );
                break;
            default:
                esegueAtNick ( collegamento, comando , restoStringa);
           }
      }

    private static void esegueBye (Collegamento collegamento ){
        collegamento.invia("@bye");
        collegamento.chiudo();
            //TODO: rimuore nickname .....
               
      
    }

    private static void esegueWho (Collegamento collegamento ){
       String elencoLogin = "";
       for(String key: collegamento.chatServer.mappaCollegamenti.keySet()){
           elencoLogin += key + " , " ;
       }
       collegamento.invia("from server to you : "+ elencoLogin);
       //TODO: inserire logger.logga ...
    }

    private static void esegueLogin(Collegamento collegamento , String restoStringa ) {
        //TODO: inserire controlli più raffinati
        if ( collegamento.isLoggato() ) {
                collegamento.invia("from server to you : sei già loggato");
             }
        else if ( collegamento.chatServer.mappaCollegamenti.containsKey(restoStringa) ){
                collegamento.invia("from server to you : nickName in uso");
             }
        else {
                  collegamento.chatServer.mappaCollegamenti.put(restoStringa,collegamento);
                  //TODO: controllare se resto stringa è vuota 
                  collegamento.login(restoStringa); //TODO controllare se valori multipli
                  collegamento.invia("from server to you : sei loggato come "
                                + restoStringa );
        }
}

    private static void esegueAll (Collegamento collegamento , String restoStringa){
                  //TODO: dispatchhare a tutti
    }

    private static void esegueAtNick (Collegamento collegamento , String comando , String restoStringa){
      
            //estraggo il primo carattere @ dal comando
            // quel che rimane è un nick name ...
            String nickName = comando.substring(1);
            //todo inserire controllo se non rimane niente ..
            //controllo se esiste
            
            // ottengo il collegamento dalla mappa e invio il resto...
            if ( collegamento.chatServer.mappaCollegamenti.containsKey(nickName) ) {
                collegamento.chatServer.mappaCollegamenti.get( nickName ).invia(
                  "from "+nickName+ " to you : " + restoStringa );
            }
    }
}
