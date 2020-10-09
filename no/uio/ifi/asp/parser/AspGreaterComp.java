package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspGreaterOpr extends AspCompOpr {
  TokenKind sign;

  AspGreaterOpr(int n){
    super(n);
  }

  static AspGreaterOpr parse(Scanner s) {
    enterParser("greater opr");

    AspGreaterOpr ago = new AspGreaterOpr(s.curLineNum());
    ago.sign = s.curToken().kind;
    s.readNextToken();

    leaveParser("greater opr");
    return ago;
  }

  @Override
  public void prettyPrint(){
    System.out.println(("hei"));
  }
}
