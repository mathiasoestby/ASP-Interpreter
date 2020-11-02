package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTerm extends AspSyntax {
  ArrayList<AspTermOpr> topr = new ArrayList<>();
  ArrayList<AspFactor> factor = new ArrayList<>();


  AspTerm(int n) {
    super(n);
  }

  static AspTerm parse(Scanner s) {
    AspTerm at = new AspTerm(s.curLineNum());
    enterParser("term");

    at.factor.add(AspFactor.parse(s));
    while (s.curToken().kind == TokenKind.plusToken || s.curToken().kind == TokenKind.minusToken){
        at.topr.add(AspTermOpr.parse(s));
        at.factor.add(AspFactor.parse(s));
    }
    leaveParser("term");
    return at;
  }

  @Override
  public void prettyPrint() {
    for (int i = 0; i < this.factor.size(); i++) {
      if (i >= 1) this.topr.get(i-1).prettyPrint();
      this.factor.get(i).prettyPrint();

    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = this.factor.get(0).eval(curScope);

    for (int i = 1; i < this.factor.size(); i++) {
      TokenKind k = topr.get(i-1).kind;
      switch (k) {
        case minusToken:
          v = v.evalSubtract(factor.get(i).eval(curScope), this); break;
        case plusToken:
          v = v.evalAdd(factor.get(i).eval(curScope), this); break;
        default:
          Main.panic("Illegal term operator: " + k + "!");
      }
    }
    return v;
  }
}
