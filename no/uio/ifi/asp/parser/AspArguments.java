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
    if (s.curToken().kind != TokenKind.rightParToken) {

      while (s.curToken().kind != TokenKind.rightParToken) {
        aa.exprList.add(AspExpr.parse(s));
        if (s.curToken().kind != TokenKind.rightParToken){
          skip(s, TokenKind.commaToken);
        }
      }

    }


    skip(s, TokenKind.rightParToken);
    leaveParser("arguments");

    return aa;
  }

  @Override
  public void prettyPrint(){
    System.out.println("arguments");
  }
}
