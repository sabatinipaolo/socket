import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPClient {
  public static void main(String argv[]) throws Exception {
    System.out.println("Hello I'm Client");

    String serverAddres = "127.0.0.1";

    // Tool.pressEnterKeyToContinue("c1 ) premi enter per creare un socket connesso
    // al server");

    Socket clientSocket = new Socket(serverAddres, 6789);

    BufferedReader bins = new BufferedReader(
        new InputStreamReader(
            clientSocket.getInputStream()));

    PrintWriter pouts = new PrintWriter(
        clientSocket.getOutputStream());

    Scanner scanner = new Scanner(System.in);

    String messaggio="", messaggioDalServer;

    while (!(messaggio = scanner.nextLine()).equals("@server bye")) {
      // traduco : mentre ( la stringa letta non è vuota ,,)

      pouts.write(messaggio + "\n"); 
      pouts.flush();

      messaggioDalServer = bins.readLine();
      System.out.println(messaggioDalServer);

      if (messaggio.equals("@bye")) //TODO: il compito richiede:"che inizia con ..."
        break;

    }

    clientSocket.close();

  }
}
