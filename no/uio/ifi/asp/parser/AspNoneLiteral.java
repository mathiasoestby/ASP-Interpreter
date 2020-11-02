package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspNoneLiteral extends AspAtom {
  TokenKind k;

  AspNoneLiteral(int curLineNum){
    super(curLineNum);
  }

  static AspNoneLiteral parse(Scanner s){
    enterParser("none literal");

    AspNoneLiteral anl = new AspNoneLiteral(s.curLineNum());
    anl.k = s.curToken().kind;
    s.readNextToken();

    leaveParser("none literal");

    return anl;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("None");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
