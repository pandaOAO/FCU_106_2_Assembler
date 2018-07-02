package sic;

import java.util.ArrayList;

public class OpTable {

  public ArrayList<Instruction> instructions = new ArrayList<Instruction>();

  public String[] instruction = {
      "ADD",
      "AND",
      "COMP",
      "DIV",
      "HIO",
      "J",
      "JEQ",
      "JGT",
      "JLT",
      "JSUB",
      "LDA",
      "LDCH",
      "LDL",
      "LDX",
      "MUL",
      "OR",
      "RD",
      "RSUB",
      "STA",
      "STCH",
      "STL",
      "STSW",
      "STX",
      "SUB",
      "TD",
      "TIX",
      "WD"
  };
  public String[] opCode = {
      "18",
      "B4",
      "28",
      "24",
      "F4",
      "3C",
      "30",
      "34",
      "38",
      "48",
      "00",
      "50",
      "08",
      "04",
      "20",
      "44",
      "D8",
      "4C",
      "0C",
      "54",
      "14",
      "E8",
      "10",
      "1C",
      "E0",
      "2C",
      "DC"
  };

  public OpTable() {
    // TODO Auto-generated constructor stub

    for (int i = 0; i < 27; i++) {
      instructions.add(new Instruction(instruction[i], opCode[i]));
    }
  }

}

class Instruction {

  String instruction;
  String opCode;

  public Instruction(String instruction, String opCode) {
    super();
    this.instruction = instruction;
    this.opCode = opCode;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public String getOpCode() {
    return opCode;
  }

  public void setOpCode(String opCode) {
    this.opCode = opCode;
  }

}