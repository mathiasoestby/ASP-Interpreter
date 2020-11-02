package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspArguments extends AspPrimarySuffix {
  ArrayList<AspExpr> exprList = new ArrayList<>();

  AspArguments(int n){
    super(n);
  }

  static AspArguments parse(Scanner s){
    enterParser("arguments");

    AspArguments aa = new AspArguments(s.curLineNum());
    skip(s, TokenKind.leftParToken);

    while(s.curToken().kind != TokenKind.rightParToken) {
      aa.exprList.add(AspExpr.parse(s));

      if (s.curToken().kind != TokenKind.rightParToken) {
        skip(s, TokenKind.commaToken);

        if (s.curToken().kind == TokenKind.rightParToken) {
          parserError("Expected value or expression, but found '" + String.valueOf(s.curToken().kind) + "'!", s.curLineNum());
        }
      }
    }

    skip(s, TokenKind.rightParToken);
    leaveParser("arguments");

    return aa;
  }

  @Override
  public void prettyPrint(){

    int nPrinted = 0;
    prettyWrite("(");

    for (AspExpr expr : this.exprList) {
      if (nPrinted > 0)
        prettyWrite(", ");
      expr.prettyPrint(); nPrinted++;
    }

    prettyWrite(")");
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
