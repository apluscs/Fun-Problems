package verifyNumber;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class templateReplace {

  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("template.html"));
    PrintWriter out =
        new PrintWriter(new BufferedWriter(new FileWriter("templateChanged.html")));
    for (int i = 0; i != 7; i++)
      in.readLine();
    while (in.ready()) {
      String post = modify(in.readLine());
      out.println(post);
    }
    in.close();
    out.close();
  }

  public static String modify(String pre) {
    StringBuilder sb = new StringBuilder(pre);
    int ind = pre.indexOf("%%END%%"); // leng 7
    while (ind != -1) {
      String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
      // System.out.println(date);
      sb.replace(ind, ind + 7, date);
      ind = pre.indexOf(pre, ind + 7); // shortened search
    }
    ind = pre.indexOf("%%START%%"); // leng 9
    while (ind != -1) {
      LocalDateTime ldt = LocalDateTime.now().plusDays(-2);
      String formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt);
      sb.replace(ind, ind + 9, formatter);
      ind = pre.indexOf(pre, ind + 9);
    }
    return sb.toString();
  }
}
