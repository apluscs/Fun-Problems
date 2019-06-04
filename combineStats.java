/*
  Designed to compile information (aka save human labor) about all of Troy High Project LEAD's members into an easy-to-read
  spreadsheet
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Note: cannot have the spreadsheet open while the program is running
public class combineStats {

  public static void main(String[] args) throws IOException {
    double start = System.currentTimeMillis();
    XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(new File("Project LEAD 18-19.xlsx")));
    System.out.println(System.currentTimeMillis() - start);
    XSSFSheet st = wb.getSheetAt(0);
    for (int r = 2; r <= 264; r++) {
      XSSFRow row = (XSSFRow) st.getRow(r - 1);
      for (int i = 3; i != 8; i++) // so we don't get a null pointer exception randomly
        row.createCell(i);
      String first = row.getCell(0).toString();
      String second = row.getCell(1).toString();
      String name = getName(first) + getName(second);
      double[] hrs = seeStudent(name);
      if (hrs.length == 0) { // website not found
        changeCol(row.getCell(0), IndexedColors.GOLD.getIndex(), wb);
        continue;
      }
      row.getCell(3).setCellValue((hrs[3] + hrs[0] - hrs[4])); // PL points
      if (row.getCell(3).getNumericCellValue() >= 30) {
        changeCol(row.getCell(3), IndexedColors.LAVENDER.getIndex(), wb);
      }
      row.getCell(4).setCellValue((hrs[1] + hrs[0])); // PVSA total hours
      if (hrs[5] < 3) {
        row.getCell(5).setCellValue(false);
      } else if (hrs[5] < 6) { // program counts 3-5 diff events=double check manually
        row.getCell(5).setCellValue("?");
        changeCol(row.getCell(5), IndexedColors.LIME.getIndex(), wb);
      } else {
        row.getCell(5).setCellValue(true);
      }
      row.getCell(6).setCellValue((hrs[0] + hrs[1]) >= 20);
      row.getCell(7).setCellValue(hrs[2] == 1);
      System.out.println(name);
    }
    // double[] hrs = seeStudent("shaunawang");
    // System.out.println(Arrays.toString(hrs));
    // System.out.println("Project Lead Hours: " + hrs[0] + ", PVSA Hours: " + hrs[1] + ", over 20?"
    // + (hrs[0] + hrs[1] >= 20) + ", paid dues? " + (hrs[2] == 1)
    // + ", total Project Lead points: " + (hrs[3] + hrs[0]) + ", negative points: " + hrs[4]);
    System.out.println(System.currentTimeMillis() - start);
    FileOutputStream outFile = new FileOutputStream(new File("Project LEAD 18-19.xlsx"));
    wb.write(outFile);
    System.out.println(System.currentTimeMillis() - start);
    outFile.close();
  }

  public static void changeCol(Cell cell, short id, XSSFWorkbook wb) {
    CellStyle style = wb.createCellStyle();
    style.setFillForegroundColor(id);
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setAlignment(HorizontalAlignment.CENTER);
    cell.setCellStyle(style);
  }
  public static String getName(String name) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i != name.length(); i++) {
      char c = name.charAt(i);
      if (c >= 'a' && c <= 'z')
        sb.append(c);
      else if (c >= 'A' && c <= 'Z')
        sb.append((char) (c + ('a' - 'A')));
    }
    return sb.toString();
  }

  public static double[] seeStudent(String name) throws IOException {
    double pldHrs = 0, pvsaHrs = 0, dues = 0, extra = 0, neg = 0, thrAct = 0; // extra project lead
                                                                              // pts
    URL oracle = new URL("https://www.thsprojectlead.com/" + name + ".html");
    try (BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));) {
      for (int i = 1; i < 345; i++) { // first 344 lines
        in.readLine();
      }
      String inputLine = in.readLine(); // 345th
      // System.out.println(inputLine);
      if (inputLine.indexOf("es") != -1) // "Y/y es"
        dues = 1;
      for (int i = 346; i < 351; i++) {
        inputLine = in.readLine();
        if (i == 348)
          continue;
        // System.out.println(inputLine);
        double e = extraPoints(inputLine);
        if (e == -1)
          return new double[] {};
        extra += e;
      }
      for (int i = 351; i < 486; i++) { // end limit is where i ends after loop
        in.readLine();
        // System.out.println(
      }
      inputLine = in.readLine(); // 486th line
      // System.out.println(inputLine);
      pldHrs = getHrs(inputLine);
      neg = negPoints(inputLine);
      thrAct = threeActivities(inputLine);
      for (int i = 487; i < 490; i++)
        in.readLine();
      inputLine = in.readLine(); // 490th line
      if (inputLine.length() > 30
          && inputLine.substring(0, 31).equals("<div class=\"paragraph\"><ul><li>")) {
        // System.out.println(inputLine);
        pvsaHrs = getHrs(inputLine);
      }
    } catch (IOException e) {
      System.out.println("Website not found for " + name);
      return new double[] {};
    }
    return new double[] {pldHrs, pvsaHrs, dues, extra, neg, thrAct};
  }

  public static int threeActivities(String s) {
    int i = s.indexOf(':'), j = s.indexOf('-');
    HashSet<String> set = new HashSet<>();
    while (i != -1 && j != -1) {
      if (j > i) {
        String event = s.substring(i + 1, j).trim();
        set.add(event);
      }
      s = s.substring(j + 1); // neg sign interferes, cut it out and move on
      i = s.indexOf(':');
      j = s.indexOf('-');
    }
    // System.out.println(set);
    return set.size();
  }
  public static double negPoints(String s) {
    int ind = s.indexOf("not show");
    double res = 0;
    while (ind != -1) {
      while (s.charAt(ind) != '-') {
        ind++;
      }
      ind++; // now on actual number, without '-'
      String n = "";
      while (s.charAt(ind) >= '0' && s.charAt(ind) <= '9' || s.charAt(ind) == '.') {
        n += s.charAt(ind);
        ind++;
      }
      res += Double.parseDouble(n);
      s = s.substring(ind + 4);
      ind = s.indexOf("not show");
    }
    return res;
  }

  public static double extraPoints(String s) {
    String n = "";
    int i = 50;
    if (s.charAt(i) == '&') // blank space and then number
      i += 6;
    while (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.') {
      n += s.charAt(i);
      // System.out.println(n);
      i++;
    }
    // System.out.println(n);
    if (n.length() == 0)
      return 0; // empty box
    return Double.parseDouble(n);
  }

  public static double getHrs(String s) {
    double sum = 0;
    int ind = s.indexOf("our");
    while (ind != -1) {
      String n = "";
      int i = ind - 3;
      while (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.') {
        n = s.charAt(i) + n;
        // System.out.println(n);
        i--;
      }
      // System.out.print(n + " ");
      if (n.length() == 0 && s.charAt(ind - 7) == '&') { // in case of nbsp
        i = ind - 8;
        while (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.') {
          n = s.charAt(i) + n;
          // System.out.println(n);
          i--;
        }
      }
      if (n.length() != 0) { // ex. program finds "house"
      double num = Double.parseDouble(n);
        // System.out.println(num);
      sum += num;
      }
      s = s.substring(ind + 4);
      ind = s.indexOf("our");
    }
    return sum;
  }
}
