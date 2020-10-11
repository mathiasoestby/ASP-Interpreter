package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspDictDisplay extends AspAtom {
  ArrayList<AspStringLiteral> aslList = new ArrayList<>();
  ArrayList<AspExpr> exprList = new ArrayList<>();

  AspDictDisplay(int curLineNum){
    super(curLineNum);
  }

  static AspDictDisplay parse(Scanner s){
    enterParser("dict display");

    skip(s, TokenKind.leftBraceToken);
    AspDictDisplay adl = new AspDictDisplay(s.curLineNum());

    if (s.curToken().kind != TokenKind.rightBraceToken){
      adl.aslList.add(AspStringLiteral.parse(s));
      skip(s, TokenKind.colonToken);
      adl.exprList.add(AspExpr.parse(s));
      while (s.curToken().kind == TokenKind.commaToken){
        s.readNextToken();
        adl.aslList.add(AspStringLiteral.parse(s));
        skip(s, TokenKind.colonToken);
        adl.exprList.add(AspExpr.parse(s));
      }
    }

    skip(s, TokenKind.rightBraceToken);
    leaveParser("dict display");

    return adl;
  }

  @Override
  public void prettyPrint(){
    System.out.println("dict display");
  }
}
