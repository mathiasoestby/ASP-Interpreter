package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspComparison extends AspSyntax {
  ArrayList<AspCompOpr> copr = new ArrayList<>();
  ArrayList<AspTerm> term = new ArrayList<>();


  AspComparison(int n) {
    super(n);
  }

  static AspComparison parse(Scanner s) {
    AspComparison ac = AspComparison(s.curLineNum());
    enterParser("comparison")

    ac.term.add(AspTerm.parse(s));

    while(true){
      if (s.curToken().kind == TokenKind.lessToken ||
          s.curToken().kind == TokenKind.greaterToken ||
          s.curToken().kind == TokenKind.doubleEqualToken ||
          s.curToken().kind == TokenKind.greaterEqualToken ||
          s.curToken().kind == TokenKind.lessEqualToken ||
          s.curToken().kind == TokenKind.notEqualToken)
      {
        ac.copr.add(AspCompOpr.parse(s));
        ac.term.add(AspTerm.parse(s));
      } else {
        break;
      }
    }
    leaveParser("comparison")
    return ac;
  }

  @Override
  public void prettyPrint() {
    //-- Must be changed in part 2:
  }
