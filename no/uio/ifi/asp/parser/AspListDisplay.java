package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspListDisplay extends AspAtom {
  ArrayList<AspExpr> exprList = new ArrayList<>();

  AspListDisplay(int curLineNum){
    super(curLineNum);
  }

  static AspListDisplay parse(Scanner s){
    enterParser("list display");

    skip(s, TokenKind.leftBracketToken);

    AspListDisplay ald = new AspListDisplay(s.curLineNum());
    ald.exprList.add(AspExpr.parse(s));

    while (s.curToken().kind == TokenKind.commaToken)
      ald.exprList.add(AspExpr.parse(s));

    skip(s, TokenKind.rightBracketToken);

    leaveParser("list display");

    return ald;
  }

  @Override
  public void prettyPrint(){
    System.out.println("list display");
  }
}
