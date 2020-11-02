package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspBooleanLiteral extends AspAtom {
  Boolean bool;

  AspBooleanLiteral(int curLineNum){
    super(curLineNum);
  }

  static AspBooleanLiteral parse(Scanner s){
    enterParser("boolean literal");

    AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
    if (s.curToken().kind == TokenKind.trueToken) {
      skip(s, TokenKind.trueToken);
      abl.bool = true;
    } else {
      skip(s, TokenKind.falseToken);
      abl.bool = false;
    }

    leaveParser("boolean literal");

    return abl;
  }

  @Override
  public void prettyPrint(){
    prettyWrite(String.valueOf(this.bool));
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeBoolValue(bool);
  }

}
