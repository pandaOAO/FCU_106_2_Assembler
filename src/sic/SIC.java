package sic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SIC {

  // public static final String stringCommentOut = " .";
  public static int startAdd;
  public static int location;
  public static String instructionNow;

  public static void main(String[] args) throws IOException {
    // TODO Auto-generated constructor stub

    OpTable opTable = new OpTable();
    SymbolTable symbolTable = new SymbolTable();

    /*
     * 指令名稱、opcode成功放進OpTable for (int i = 0; i < opTable.instructions.size(); i++)
     * { System.out.println("No." + i + "\t" +
     * opTable.instructions.get(i).getInstruction() + ";\t" +
     * opTable.instructions.get(i).getOpCode()); }
     */

    FileReader fr = new FileReader("SIC.txt");
    BufferedReader br = new BufferedReader(fr);

    FileWriter fw = new FileWriter("SIC_mid.txt");

    String stringBuffer;

    stringBuffer = br.readLine();

    fw.write("位置\t\t原始敘述\t\t\t目的碼\n");
    fw.write("------------------------------------------------\n");

    if (stringBuffer.substring(8, 13).equals("START") == false) {
      // 如果沒有START
      fw.write("！ \t 程式須由虛擬指令START開頭 \t ！");
    }
    // 處理第一行
    startAdd = Integer.parseInt(stringBuffer.substring(16, 20).trim(), 16);
    location = startAdd;
    for (int i = 0; i < symbolTable.symbols.size(); i++) {
      if (stringBuffer.substring(0, 6).trim().equals(symbolTable.symbols.get(i).getSymbol())) {
        fw.write("！ \t symbol重複宣告 \t ！");
      }
    }
    symbolTable.symbols.add(new Symbol(stringBuffer.substring(0, 6).trim(), location));
    /*
     * 可以讀取到第一筆Symbol System.out.println("第一個Symbol: " +
     * symbolTable.symbols.get(0).symbol +
     * Integer.toHexString(symbolTable.symbols.get(0).getSymbolAdd()));
     */

    System.out.print(Integer.toHexString(startAdd).toUpperCase() + "\t" + stringBuffer + "\n");
    fw.write(Integer.toHexString(startAdd).toUpperCase() + "\t" + stringBuffer + "\n");
    while (br.ready()) {
      // 可以成功讀取檔案
      // System.out.println(br.readLine());

      stringBuffer = br.readLine();

      boolean IsInstruction = false;

      // 如果註解掉就只留一點
      // 如果是程式就處理
      if (stringBuffer.indexOf(".") != -1) {
        System.out.println(".");
        fw.write("\t.\n");
        continue;
      } else {
        instructionNow = stringBuffer.substring(8, 12).trim();
        // 如果有symbol, 把location跟symbol加入symbolTable
        if (stringBuffer.startsWith(" ") == false) {
          symbolTable.symbols.add(new Symbol(stringBuffer.substring(0, 6).trim(), location));
        }
        // 寫出location
        System.out.print(Integer.toHexString(location).toUpperCase() + "\t");
        System.out.println(stringBuffer);
        // stringBuffer = String.format("%1$30s", stringBuffer);
        while (stringBuffer.length() < 24) {
          stringBuffer = stringBuffer + " ";
        }
        fw.write(Integer.toHexString(location).toUpperCase() + "\t" + stringBuffer + "\t");
      }

      for (int i = 0; i < opTable.instructions.size(); i++) {
        if (instructionNow.equals(opTable.instructions.get(i).getInstruction())) {
          location = location + 3;

          fw.write(opTable.instructions.get(i).getOpCode() + "\n");
          IsInstruction = true;
          break;
        }
      }

      if (IsInstruction == false) {

        if (instructionNow.equals("BYTE")) {
          String[] array = stringBuffer.substring(16).trim().split("'");
          if (array[0].equals("C")) {
            // BYTE C'
            location = location + array[1].length();

            for (int i = 0; i < array[1].length(); i++) {
              fw.write(Integer.toHexString((int) array[1].charAt(i)).toUpperCase());
            }
            fw.write("\n");

          } else {
            // BYTE X'
            location = location + array[1].length() / 2;
            fw.write(array[1] + "\n");

          }
        } else if (instructionNow.equals("WORD")) {
          location = location + 3;
          int wordInt = Integer.parseInt(stringBuffer.substring(16).trim());
          String wordString = String.format("%06x", wordInt);
          fw.write(wordString + "\n");

        } else if (instructionNow.equals("RESB")) {
          location = location + Integer.parseInt(stringBuffer.substring(16, 20).trim());
          fw.write("\n");
        } else if (instructionNow.equals("RESW")) {
          location = location + Integer.parseInt(stringBuffer.substring(16, 20).trim()) * 3;
          fw.write("\n");
        }

      }

      // 可以成功寫檔
      // fw.write(br.readLine() + "\n");

      // 每次讀寫檔結束
    }

    if (stringBuffer.substring(8, 11).trim().equals("END") == false) {
      // 如果沒有END
      fw.write("！ \t 程式須由虛擬指令END結尾 \t ！\n");
    }

    fw.flush();
    fw.close();

    fr.close();

    /*
     * symbolTable是正確的 System.out.println(""); System.out.println("");
     * System.out.println("-----Symbol Table-----"); for (int i = 0; i <
     * symbolTable.symbols.size(); i++) { System.out.println(
     * symbolTable.symbols.get(i).symbol + "\t" +
     * Integer.toHexString(symbolTable.symbols.get(i).getSymbolAdd()).toUpperCase())
     * ; }
     */

    // 寫出final.txt
    fr = new FileReader("SIC_mid.txt");
    br = new BufferedReader(fr);
    fw = new FileWriter("SIC_final.txt");

    stringBuffer = br.readLine();
    fw.write(stringBuffer + "\n");
    stringBuffer = br.readLine();
    fw.write(stringBuffer + "\n");

    while (br.ready()) {
      stringBuffer = br.readLine();
      if (stringBuffer.length() > 31 && stringBuffer.length() < 36
          && stringBuffer.contains("BYTE") == false) {
        if (stringBuffer.charAt(21) == ' ') {
          fw.write(stringBuffer +
              "0000" +
              "\n");
        }
        if (stringBuffer.substring(21, 29).contains(",X")) {
          // fw.write("接收到有,x的訊息\n");
          String[] array = stringBuffer.substring(21, 29).split(",");
          // fw.write("array[0] = " + array[0] + "\n");
          for (int i = 0; i < symbolTable.symbols.size(); i++) {
            if (array[0].equals(symbolTable.symbols.get(i).getSymbol())) {
              String x = "8000";
              int xInt = Integer.parseInt(x, 16);
              int add = xInt + symbolTable.symbols.get(i).getSymbolAdd();
              fw.write(stringBuffer +
                  Integer.toHexString(add).toUpperCase() +
                  "\n");
            }
          }
          continue;
        }
        for (int i = 0; i < symbolTable.symbols.size(); i++) {
          if (stringBuffer.substring(21, 27).trim().equals(symbolTable.symbols.get(i).getSymbol())) {
            fw.write(stringBuffer +
                Integer.toHexString(symbolTable.symbols.get(i).getSymbolAdd()).toUpperCase() +
                "\n");
            break;
          }
        }
      } else {
        fw.write(stringBuffer + "\n");
      }
    }

    fw.close();
    fr.close();

  }

}
