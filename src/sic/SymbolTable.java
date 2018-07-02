package sic;

import java.util.ArrayList;

public class SymbolTable {

  public ArrayList<Symbol> symbols;

  public SymbolTable() {
    // TODO Auto-generated constructor stub
    symbols = new ArrayList<Symbol>();

  }

}

class Symbol {

  String symbol;
  int symbolAdd;

  public Symbol(String symbol, int symbolAdd) {
    super();
    this.symbol = symbol;
    this.symbolAdd = symbolAdd;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public int getSymbolAdd() {
    return symbolAdd;
  }

  public void setSymbolAdd(int symbolAdd) {
    this.symbolAdd = symbolAdd;
  }

}