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

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = termList.get(0).eval(curScope);
    RuntimeValue nextv;
    AspCompOpr opr;

    for (int i = 0; i < coList.size(); i++) {
      v = termList.get(i).eval(curScope);
      nextv = termList.get(i+1).eval(curScope);
      opr = coList.get(i);
      if (opr.opr == TokenKind.lessToken) v = v.evalLess(nextv, this);
      else if (opr.opr == TokenKind.greaterToken) v = v.evalGreater(nextv, this);
      else if (opr.opr == TokenKind.doubleEqualToken) v = v.evalEqual(nextv, this);
      else if (opr.opr == TokenKind.greaterEqualToken) v = v.evalGreaterEqual(nextv, this);
      else if (opr.opr == TokenKind.lessEqualToken) v = v.evalLessEqual(nextv, this);
      else if (opr.opr == TokenKind.notEqualToken) v = v.evalNotEqual(nextv, this);

      if (!v.getBoolValue("comp operand", this)) return v;
    }
    return v;
  }
}
