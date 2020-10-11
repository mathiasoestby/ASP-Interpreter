package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspComparison extends AspSyntax {
  ArrayList<AspCompOpr> coList= new ArrayList<>();
  ArrayList<AspTerm> termList = new ArrayList<>();


  AspComparison(int n) {
    super(n);
  }

  private static Boolean isTermOperator(Scanner s){
    TokenKind t = s.curToken().kind;
    return (t == TokenKind.lessToken || t == TokenKind.greaterToken || t == TokenKind.doubleEqualToken || t == TokenKind.greaterEqualToken || t == TokenKind.lessEqualToken || t == TokenKind.notEqualToken);
  }

  public static AspComparison parse(Scanner s) {
    enterParser("comparison");

    AspComparison ac = new AspComparison(s.curLineNum());
    ac.termList.add(AspTerm.parse(s));
    TokenKind t = s.curToken().kind;

    while (t == TokenKind.lessToken || t == TokenKind.greaterToken || t == TokenKind.doubleEqualToken || t == TokenKind.greaterEqualToken || t == TokenKind.lessEqualToken || t == TokenKind.notEqualToken ) {
      ac.coList.add(AspCompOpr.parse(s));
      ac.termList.add(AspTerm.parse(s));
      t = s.curToken().kind;
    }


    leaveParser("comparison");
    return ac;
  }


  @Override
  public void prettyPrint(){
    for (int i = 0; i < this.termList.size(); i++) {
      if (i >= 1) this.coList.get(i-1).prettyPrint();
      this.termList.get(i).prettyPrint();

    }
  }
}
