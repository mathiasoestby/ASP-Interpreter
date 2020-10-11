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
    //-- Must be changed in part 2:
  }
}
