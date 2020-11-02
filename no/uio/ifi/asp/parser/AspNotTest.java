package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspNotTest extends AspSyntax {
  Boolean hasnot = false;
  AspComparison comp;

  AspNotTest(int n) {
    super(n);
  }

  static AspNotTest parse(Scanner s) {
    enterParser("not test");

    AspNotTest ant = new AspNotTest(s.curLineNum());
    if (s.curToken().kind == TokenKind.notToken) {
      skip(s, TokenKind.notToken);
      ant.hasnot = true;
    }
    ant.comp = AspComparison.parse(s);

    leaveParser("not test");
    return ant;
  }

  @Override
  public void prettyPrint(){
    if (this.hasnot) prettyWrite("not ");
    this.comp.prettyPrint();
  }

  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = comp.eval(curScope);

    if (this.hasnot) v = v.evalNot(this);
    return v;

  }
}
