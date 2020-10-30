package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorPrefix extends AspSyntax {
  TokenKind kind;

  AspFactorPrefix(int n) {
    super(n);
  }

  static AspFactorPrefix parse(Scanner s) {
    AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());
    enterParser("factor prefix");

    afp.kind = s.curToken().kind;
    if (s.curToken().kind == TokenKind.plusToken) {
      skip(s, TokenKind.plusToken);
    } else{
      skip(s, TokenKind.minusToken);
    }

    leaveParser("factor prefix");
    return afp;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(String.valueOf(this.kind));
  }
}
