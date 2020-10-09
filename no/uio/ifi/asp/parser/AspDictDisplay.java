package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspDictDisplay extends AspAtom {
  TokenKind leftBracket;

  AspDictDisplay(int curLineNum){
    super(curLineNum);
  }

  static AspDictDisplay parse(Scanner s){
    enterParser("dict display");

    AspDictDisplay adl = new AspDictDisplay(s.curLineNum());
    adl.leftBracket = s.curToken().kind;
    s.readNextToken();

    leaveParser("dict display");

    return adl;
  }

  @Override
  public void prettyPrint(){
    System.out.println("dict display");
  }
}
