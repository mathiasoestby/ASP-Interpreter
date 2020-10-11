package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspCompOpr extends AspSyntax {
  TokenKind opr;

  AspCompOpr(int n){
    super(n);
  }

  static AspCompOpr parse(Scanner s) {
    enterParser("comp opr");

    AspCompOpr sco = new AspCompOpr(s.curLineNum());

    TokenKind t = s.curToken().kind;

    if (t == TokenKind.lessToken || t == TokenKind.greaterToken || t == TokenKind.doubleEqualToken || t == TokenKind.greaterEqualToken || t == TokenKind.lessEqualToken || t == TokenKind.notEqualToken)
      sco.opr = t;
    else
      parserError("Expected operator, but found none", s.curLineNum());

    s.readNextToken();

    leaveParser("comp opr");
    return sco;
  }

  @Override
  public void prettyPrint(){
    System.out.println(("hei"));
  }
}
