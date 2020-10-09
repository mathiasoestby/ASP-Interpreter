package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspLessOpr extends AspCompOpr {
  TokenKind sign;

  AspLessOpr(int n){
    super(n);
  }

  static AspLessOpr parse(Scanner s) {
    enterParser("less opr");

    AspLessOpr alo = new AspLessOpr(s.curLineNum());
    alo.sign = s.curToken().kind;
    s.readNextToken();

    leaveParser("less opr");
    return alo;
  }

  @Override
  public void prettyPrint(){
    System.out.println(("hei"));
  }
}
