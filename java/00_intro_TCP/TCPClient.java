import java.io.*;
import java.net.*;
import java.util.Arrays;

class TCPClient {
 public static void main(String argv[]) throws Exception {
  System.out.println("Hello I'm Client");
  
  Socket clientSocket = new Socket("localhost",6789);
  
  byte[] bytes = new byte[64];
  Arrays.fill( bytes, (byte) 255 );
  
  clientSocket.getOutputStream().write(bytes);
  
  
      
        
        
        
  
  clientSocket.close();

 }
}
