import java.util.Scanner;
public class Tool {

  public static void pressEnterKeyToContinue(String str )
  { 
          System.out.println(str);
          Scanner s = new Scanner(System.in);
          s.nextLine();
  }
}

