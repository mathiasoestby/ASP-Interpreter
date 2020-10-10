package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspNotTest extends AspSyntax {
  Boolean hasnot = false;
  AspComparison comparison;

  AspNotTest(int n) {
    super(n);
  }

  static AspNotTest parse(Scanner s) {
    AspNotTest ant = new AspNotTest(s.curLineNum());
    if (s.curToken().kind == TokenKind.notToken) {
      skip(s, TokenKind.notToken);
      ant.hasnot = true;
    }
    ant.comp = AspComparison.parse(s);

    return ant;
  }
}
