package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspSubscription extends AspPrimarySuffix {
  AspExpr e;

  AspSubscription(int n){
    super(n);
  }

  static AspSubscription parse(Scanner s){
    enterParser("subscription");

    AspSubscription as = new AspSubscription(s.curLineNum());

    skip(s, TokenKind.leftBracketToken);
    as.e = AspExpr.parse(s);
    skip(s, TokenKind.rightBracketToken);
    leaveParser("subscription");

    return as;
  }

  @Override
  public void prettyPrint(){
    System.out.println("integer literal");
  }
}
