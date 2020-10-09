package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


abstract class AspAtom extends AspSyntax {
  AspAtom(int n){
    super(n);
  }

  static AspAtom parse(Scanner s){
    enterParser("atom");

    AspAtom a = new AspName(s.curLineNum());

    if (s.curToken().kind == TokenKind.nameToken)
      a = AspName.parse(s);
    else if (s.curToken().kind == TokenKind.integerToken)
      a = AspIntegerLiteral.parse(s);
    else if (s.curToken().kind == TokenKind.floatToken)
      a = AspFloatLiteral.parse(s);
    else if (s.curToken().kind == TokenKind.stringToken)
      a = AspStringLiteral.parse(s);
    else if (s.curToken().kind == TokenKind.noneToken)
      a = AspNoneLiteral.parse(s);
    else if (s.curToken().kind == TokenKind.leftBracketToken)
      a = AspListDisplay.parse(s);
    else if (s.curToken().kind == TokenKind.leftBraceToken)
      a = AspDictDisplay.parse(s);
    else
      parserError("Expected value or expression, but found none", s.curLineNum());

    leaveParser("atom");
    return a;

  }
}
