package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspStringLiteral extends AspAtom {
  String tekst;
  // int curLineNum;

  AspStringLiteral(int curLineNum){
    super(curLineNum);
  }

  static AspStringLiteral parse(Scanner s){
    enterParser("string literal");

    AspStringLiteral asl = new AspStringLiteral(s.curLineNum());
    asl.tekst = s.curToken().stringLit;
    s.readNextToken();

    leaveParser("string literal");

    return asl;
  }

  @Override
  public void prettyPrint(){
    System.out.println("string literal");
  }
}
