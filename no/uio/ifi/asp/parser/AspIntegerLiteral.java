package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspIntegerLiteral extends AspAtom {
  long tall;
  // int curLineNum;

  AspIntegerLiteral(int n){
    super(n);
  }

  static AspIntegerLiteral parse(Scanner s){
    enterParser("integer literal");

    AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
    ail.tall = s.curToken().integerLit;
    s.readNextToken();
    leaveParser("integer literal");

    return ail;
  }

  @Override
  public void prettyPrint(){
    prettyWrite(String.valueOf(this.tall));
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = new RuntimeIntegerValue(this.tall);
    return v;
  }
}
