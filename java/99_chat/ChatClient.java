import java.io.*;
import java.net.*;
import java.util.Scanner;


class ChatClient {
    public static void main(String argv[]) throws Exception {
      System.out.println("Hello I'm Client");

      //TODO: chiedere in input l'indirizzo del server
      String serverAddres = "127.0.0.1";

      Socket socket = new Socket(serverAddres, 6789);
  
      GestoreMessaggiInInput gmi = new GestoreMessaggiInInput( socket );
      gmi.start();

      PrintWriter pouts = new PrintWriter(
                                socket.getOutputStream()
                              );
  
      Scanner scanner = new Scanner(System.in);
      String messaggio="" ;
      while (!(messaggio.equals("@bye"))){   //TODO: il compito richiede inizia con ...
          messaggio = scanner.nextLine();     
          //if(messaggio != null && !messaggio.trim().isEmpty())
              //continue;

          pouts.write(messaggio + "\n"); 
          pouts.flush();
          
          
      };
       
      socket.close();
      gmi.ferma();

    }
}


class GestoreMessaggiInInput extends Thread {

private Socket socket ;
private BufferedReader lettoreBufferMessaggi ;
private boolean nonStoppato;

    GestoreMessaggiInInput( Socket socket ){
        this.nonStoppato = true;
        this.socket = socket;
        try {
            this.lettoreBufferMessaggi = new BufferedReader(
                                          new InputStreamReader(
                                           socket.getInputStream() ));
        }
        catch ( IOException e ){ e.printStackTrace(System.out);};
        
    }
    
    public void run(){
        System.out.println("Sono pronto a ricevere messaggi");

        String  messaggioDalServer="" ;
        while ( nonStoppato ) {
            try {
                messaggioDalServer =
                      lettoreBufferMessaggi.readLine();
                System.out.println ( messaggioDalServer );
            }
            catch ( IOException e ){ };
         }
    }
    void ferma(){
        nonStoppato = false;
    }
}
