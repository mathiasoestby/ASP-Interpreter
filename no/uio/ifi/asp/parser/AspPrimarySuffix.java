package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


abstract class AspPrimarySuffix extends AspAtom {

  AspPrimarySuffix(int n){
    super(n);
  }

  static AspPrimarySuffix parse(Scanner s){
    enterParser("primary suffix");

    AspPrimarySuffix aps = new AspArguments(s.curLineNum());

    if (s.curToken().kind == TokenKind.leftParToken)
      aps = AspArguments.parse(s);
    else if (s.curToken().kind == TokenKind.leftBracketToken)
      aps = AspSubscription.parse(s);
    else
      parserError("Expected operator, but found " + String.valueOf(s.curToken().kind), s.curLineNum());



    leaveParser("primary suffix");

    return aps;
  }

  @Override
  public void prettyPrint(){
    System.out.println("primary suffix");
  }
}
