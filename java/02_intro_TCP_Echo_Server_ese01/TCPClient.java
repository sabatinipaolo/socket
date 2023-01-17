import java.io.*;
import java.net.*;
import java.util.Scanner;


class TCPClient {
 public static void main(String argv[]) throws Exception {
  System.out.println("Hello I'm Client");

  String serverAddres = "127.0.0.1" ; 

  //Tool.pressEnterKeyToContinue("c1 ) premi enter per creare un socket connesso al server");

  Socket clientSocket = new Socket(serverAddres, 6789);
  
  BufferedReader dis = new BufferedReader (
                            new InputStreamReader(
                                    clientSocket.getInputStream()));

  DataOutputStream dout = new DataOutputStream( 
                                    clientSocket.getOutputStream());
                                    

  Scanner scanner = new Scanner(System.in);

  String messaggio,messaggioDalServer;
 
  while ( ! (messaggio =scanner.nextLine()).isEmpty() ){ 
    //traduco : mentre ( la stringa letta non è vuota ,,)
   
    dout.writeUTF(messaggio+"\n"); //TODO : usare print stream ...
    dout.flush();

    messaggioDalServer=dis.readLine();
    System.out.println("echo: " + messaggioDalServer);

    
    if (messaggio.equals("@server bye")) break;

  }
  
  clientSocket.close();

 }
}
