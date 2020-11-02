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

    if (s.curToken().kind != TokenKind.rightBracketToken){
      ald.exprList.add(AspExpr.parse(s));
      while (s.curToken().kind == TokenKind.commaToken){
        s.readNextToken();
        ald.exprList.add(AspExpr.parse(s));
      }
    }




    skip(s, TokenKind.rightBracketToken);

    leaveParser("list display");

    return ald;
  }

  @Override
  public void prettyPrint(){

    int nPrinted = 0;
    prettyWrite("[");

    for (AspExpr expr : this.exprList) {
      if (nPrinted > 0)
        prettyWrite(", ");
      expr.prettyPrint(); nPrinted++;
    }

    prettyWrite("]");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
