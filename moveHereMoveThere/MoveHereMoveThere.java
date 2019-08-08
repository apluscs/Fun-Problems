package myPuzzles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MoveHereMoveThere {
  static int N, M, numSet, numBlocks;
  static Block[] blocks;    //for user to play with
  static Block srcBlock;
  static Block[][] grid;
  static int[] target = new int[2];
  static int[] source = new int[2];
  static HashSet<Integer> vis;
  static boolean quit;

  public static void main(String[] args) throws IOException {
    readFromUser();
    //still need to track closed cells - isFinal will be true, dx and dy will be 0

    for (Block b : blocks) {
      if (quit)
        break;
      dfs(source[0], source[1], b);
    }
    //    printGrid();
    //    System.out.println(Arrays.toString(blocks));

  }


  static void dfs(int r, int c, Block curr) {

    if (quit || r >= N || c >= M || r < 0 || c < 0) //|| off the grid
      return;
    System.out.println(r + ", " + c + ", numBlocks: " + vis.size() + " " + curr);
    if (r == target[0] && c == target[1]) {
      System.out.println("Returned early cuz target");
      if (vis.size() == numBlocks) {    //we found THE solution!!
        printGrid();
        System.out.println("SOLVED!!!");
        quit = true;
      }
      return;
    }


    if (grid[r][c] != null) {
      vis.add(grid[r][c].id);   //mark even final blocks as visited
      System.out.println("There's someone here");
      if (grid[r][c].isFinal) {
        System.out.println("redirecting");
        dfs(r + grid[r][c].dy, c + grid[r][c].dx, curr);  //final blocks should only redirect you
        return;
      }
      //      printGrid();
      //      int nr = grid[r][c].dy + r, nc = grid[r][c].dx + c;
      //      while (nr != r || nc != c) {
      //        System.out.println("clearing " + nr + ", " + nc);
      //        vis.remove(grid[nr][nc].id);
      //        int dx = grid[nr][nc].dx, dy = grid[nr][nc].dy;
      //        if (!grid[nr][nc].isFinal) {
      //          grid[nr][nc] = null;
      //        }
      //        nr += dy;
      //        nc += dx;
      //      }
      return; //already visited this cell
    }
    if (vis.contains(curr.id)) {  //this block has already been placed 
      System.out.println("vis contains curr");
      return;
    }
    if (out(r, c, curr))
      return;
    if (vis.size() == numBlocks) {  //no more blocks to place here, ended on an empty cell || invalid directions
      System.out.println("Returned early");
      return;
    }


    int nr = r + curr.dy, nc = c + curr.dx;
    vis.add(curr.id);
    grid[r][c] = curr;
    for (Block b : blocks)
      dfs(nr, nc, b);
    grid[r][c] = null;
    vis.remove(curr.id);
  }

  static void readFromUser() throws IOException {
    Scanner sc = new Scanner(System.in);

    System.out.println(
        "Welcome! This will solve your dilemnas arising from the game Move Here Move There, which can be found at: https://www.coolmathgames.com/0-move-here-move-there .");
    System.out.print(
        "Enter 0 if you'd like to enter input through the terminal and 1 if you'd prefer it through a file. ");
    int choice = sc.nextInt();
    if (choice == 1) {
      System.out.print(
          "The file you choose must be written in a specific format. Are you sure you are ready to run the program? Take a look at example.in for the specifications. Proceed whenever you are ready. ");

      System.out.print("Enter the file name: ");
      String file = sc.next();
      readFromFile(file);
      return;
    }
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("movehere.in")));

    System.out.print(
        "Your input will be recorded into \"movehere.in\". \nEnter the number of rows in the board: ");
    out.print(sc.nextInt() + " ");
    System.out.print("Enter the number of columns in the board: ");
    M = sc.nextInt();
    //    System.out.println("M: " + M);
    out.println(M);

    System.out.println(
        "Please enter the following in 0-based format. Please enter any coordinates as 'x y' and any vectors as 'x-component y-component'. The top-left corner is (0,0), and the bottom-right corner is (N-1, M-1). Up is a negative y-direction. Down is a positive y-direction. Left is a negative x-direction. Right is a positive x-direction. ");

    System.out.print("Enter the location of the target (x y): ");
    out.print(sc.nextInt() + " " + sc.nextInt() + '\n');

    System.out.print("Enter the location of the source (x y): ");
    out.println(sc.nextInt() + " " + sc.nextInt());

    System.out.print("Enter the number of commands in the source cell: ");
    int numCmds = sc.nextInt();
    out.println(numCmds);
    for (int i = 0; i != numCmds; i++) {
      System.out.print("Enter the vector components of the command (x-dir y-dir): ");
      out.println(sc.nextInt() + " " + sc.nextInt());
    }

    System.out.print("Enter the number of preset blocks: ");
    numBlocks = sc.nextInt();
    out.println(numBlocks);
    for (int i = 0; i != numBlocks; i++) {
      System.out.print("Enter the location of the preset block (x y): ");
      int r = sc.nextInt();
      int c = sc.nextInt();
      out.println(r + " " + c);
      System.out.print("Enter the number of commands in this block: ");
      numCmds = sc.nextInt();
      out.println(numCmds);
      for (int j = 0; j != numCmds; j++) {
        System.out.print("Enter the vector components of the command (x-dir y-dir): ");
        out.println(sc.nextInt() + " " + sc.nextInt());
      }
    }

    System.out.print("Enter the number of moveable blocks: ");
    int newBlocks = sc.nextInt();
    out.println(newBlocks);
    for (int i = 0; i != newBlocks; i++) {
      System.out.print("Enter the number of commands in this block: ");
      numCmds = sc.nextInt();
      out.println(numCmds);
      for (int j = 0; j != numCmds; j++) {
        System.out.print("Enter the vector components of the command (x-dir y-dir): ");
        out.println(sc.nextInt() + " " + sc.nextInt());
      }
    }
    out.flush();
    System.out.print(
        "Make sure \"movehere.in\" has all the correct information. Then press enter to solve your puzzle. ");
    sc.next();
    readFromFile("movehere.in");
  }

  static void readFromFile(String file) throws IOException {
    try (BufferedReader in = new BufferedReader(new FileReader(file));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dummy.out")));) {
      StringTokenizer st = new StringTokenizer(in.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      grid = new Block[N][M];
      vis = new HashSet<>();
      quit = false;

      st = new StringTokenizer(in.readLine());
      target[0] = Integer.parseInt(st.nextToken());
      target[1] = Integer.parseInt(st.nextToken());

      st = new StringTokenizer(in.readLine());
      source[0] = Integer.parseInt(st.nextToken());
      source[1] = Integer.parseInt(st.nextToken());
      st = new StringTokenizer(in.readLine());
      int numCmds = Integer.parseInt(st.nextToken());
      int[][] commands = new int[numCmds][2];
      for (int i = 0; i != numCmds; i++) {
        st = new StringTokenizer(in.readLine());
        commands[i][0] = Integer.parseInt(st.nextToken());
        commands[i][1] = Integer.parseInt(st.nextToken());
      }
      srcBlock = new Block(commands, true, 0);
      System.out.println(srcBlock);
      grid[source[0]][source[1]] = srcBlock;

      st = new StringTokenizer(in.readLine());
      numBlocks = Integer.parseInt(st.nextToken());   //plus source block

      for (int i = 0; i != numBlocks; i++) {
        st = new StringTokenizer(in.readLine());
        int r = Integer.parseInt(st.nextToken());
        System.out.println(r);
        int c = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());
        numCmds = Integer.parseInt(st.nextToken());
        commands = new int[numCmds][2];
        for (int j = 0; j != numCmds; j++) {
          st = new StringTokenizer(in.readLine());
          commands[j][0] = Integer.parseInt(st.nextToken());
          commands[j][1] = Integer.parseInt(st.nextToken());
        }
        grid[r][c] = new Block(commands, true, i + 1);
      }

      st = new StringTokenizer(in.readLine());
      int newBlocks = Integer.parseInt(st.nextToken());
      numBlocks += newBlocks + 1;    //total # blocks, set and unset 
      blocks = new Block[newBlocks];
      for (int i = 0; i != newBlocks; i++) {
        st = new StringTokenizer(in.readLine());
        numCmds = Integer.parseInt(st.nextToken());
        commands = new int[numCmds][2];
        for (int j = 0; j != numCmds; j++) {
          st = new StringTokenizer(in.readLine());
          commands[j][0] = Integer.parseInt(st.nextToken());
          commands[j][1] = Integer.parseInt(st.nextToken());
        }
        blocks[i] = new Block(commands, false, numBlocks - newBlocks + i + 1);
      }
    }
  }

  static boolean out(int r, int c, Block curr) {    //if commands from curr will move user off grid anytime during execution
    int[] bnds = curr.bounds;
    return !(r + bnds[0] < N && c + bnds[1] < M && r + bnds[2] > -1 && c + bnds[3] > -1);
  }

  static void printGrid() {
    for (int i = 0; i != N; i++)
      for (int j = 0; j != M; j++)
        if (grid[i][j] != null)
          System.out.println("i: " + i + ", j: " + j + '\t' + grid[i][j]);
  }

  static class Block {
    boolean isFinal;    //if it's hard set
    int dx = 0, dy = 0, id;
    int[] bounds = new int[4];   //how much "space" this block needs SENW

    public Block(int[][] commands, boolean isFinal, int id) {
      this.isFinal = isFinal;
      this.id = id;
      for (int[] cmd : commands) {
        dx += cmd[0];
        dy += cmd[1];   //opposite: up is negative, down is positive
        bounds[0] = Math.max(bounds[0], dy);    //negative r
        bounds[1] = Math.max(bounds[1], dx);    //positive c
        bounds[2] = Math.min(bounds[2], dy);    //positive r
        bounds[3] = Math.min(bounds[3], dx);    //negative c
      }
    }

    @Override
    public String toString() {
      String res = "Final: " + isFinal + ", dx: " + dx + ", dy: " + dy + ", id: " + id + '\t';
      return res + Arrays.toString(bounds);
      //      return res;
    }
  }

}
