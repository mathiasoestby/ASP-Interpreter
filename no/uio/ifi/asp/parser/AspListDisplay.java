package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspListDisplay extends AspAtom {
  TokenKind leftBracket;

  AspListDisplay(int curLineNum){
    super(curLineNum);
  }

  static AspListDisplay parse(Scanner s){
    enterParser("list display");

    AspListDisplay all = new AspListDisplay(s.curLineNum());
    all.leftBracket = s.curToken().kind;
    s.readNextToken();

    leaveParser("list display");

    return all;
  }

  @Override
  public void prettyPrint(){
    System.out.println("list display");
  }
}
