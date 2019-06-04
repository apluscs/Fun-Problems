package plead;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
public class combineStats {

  public static void main(String[] args) throws IOException {

    double[] hrs = seeStudent("claireyang");
    System.out.println("Project Lead Hours: " + hrs[0] + ", PVSA Hours: " + hrs[1] + ", over 20?"
        + (hrs[0] + hrs[1] >= 20) + ", paid dues? " + (hrs[2] == 1)
        + ", total Project Lead points: "
        + (hrs[3] + hrs[0]));
  }

  public static double[] seeStudent(String name) throws IOException {
    int[] res = new int[5];
    double pldHrs = 0, pvsaHrs = 0, dues = 0, extra = 0; // extra project lead pts
    URL oracle = new URL("https://www.thsprojectlead.com/" + name + ".html");
    try (BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));) {
      for (int i = 1; i < 345; i++) { // first 344 lines
        in.readLine();
      }
      String inputLine = in.readLine(); //345th 
      System.out.println(inputLine);
      if (inputLine.indexOf("es") != -1) // "Y/y es"
        dues = 1;
      for (int i = 346; i < 351; i++) {
        inputLine = in.readLine();
        if (i == 348)
          continue;
        System.out.println(inputLine);
        extra += extraPoints(inputLine);
      }
      System.out.println("-----------------------------------");
      for (int i = 351; i < 486; i++) { // end limit is where i ends after loop
        in.readLine();
        // System.out.println(
      }
      inputLine = in.readLine(); // 486th line
      System.out.println(inputLine);
      pldHrs = getHrs(inputLine);
      for (int i = 487; i < 490; i++)
        in.readLine();
      inputLine = in.readLine(); // 490th line
      if (inputLine.length() > 30
          && inputLine.substring(0, 31).equals("<div class=\"paragraph\"><ul><li>")) {
        System.out.println(inputLine);
        pvsaHrs = getHrs(inputLine);
      }
    }
    return new double[] {pldHrs, pvsaHrs, dues, extra};
  }

  public static double extraPoints(String s) {
    String n = "";
    int i = 50;
    while (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.') {
      // System.out.println(n);
      n = s.charAt(i) + n;
      i++;
    }
    if (n.length() == 0)
      return 0; // empty box
    double num = Double.parseDouble(n);
    return num;
  }

  public static double getHrs(String s) {
    double sum = 0;
    int ind = s.indexOf("ours");
    while (ind != -1) {
      String n = "";
      int i = ind - 3;
      while (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.') {
        // System.out.println(n);
        n = s.charAt(i) + n;
        i--;
      }
      double num = Double.parseDouble(n);
      System.out.println(num);
      sum += num;
      s = s.substring(ind + 4);
      ind = s.indexOf("ours");
    }
    return sum;
  }
}
