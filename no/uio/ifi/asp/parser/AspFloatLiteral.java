package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspFloatLiteral extends AspAtom {
  double tall;

  AspFloatLiteral(int n){
    super(n);
  }

  static AspFloatLiteral parse(Scanner s){
    enterParser("float literal");

    AspFloatLiteral ail = new AspFloatLiteral(s.curLineNum());
    ail.tall = s.curToken().floatLit;
    s.readNextToken();

    leaveParser("float literal");

    return ail;
  }

  @Override
  public void prettyPrint(){
    System.out.println("float literal");
  }
}
