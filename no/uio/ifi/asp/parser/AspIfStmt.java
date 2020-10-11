package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspIfStmt extends AspCompoundStmt {
  ArrayList<AspExpr> exprList = new ArrayList<>();
  ArrayList<AspSuite> ifSuiteList = new ArrayList<>();
  AspSuite elseSuite = null;

  AspIfStmt(int n) {
    super(n);
  }


  public static AspIfStmt parse(Scanner s) {
    enterParser("if stmt");

    AspIfStmt is = new AspIfStmt(s.curLineNum());

    skip(s, TokenKind.ifToken);

    while (true) {
      is.exprList.add(AspExpr.parse(s));
      skip(s, TokenKind.colonToken);
      is.ifSuiteList.add(AspSuite.parse(s));
      if (s.curToken().kind != TokenKind.elifToken){
        break;
      } else {
        skip(s, TokenKind.elifToken);
      }
    }

    if (s.curToken().kind == TokenKind.elseToken) {
      skip(s, TokenKind.elseToken);
      skip(s, TokenKind.colonToken);
      is.elseSuite = AspSuite.parse(s);
    }

    leaveParser("if stmt");
    return is;
  }


  @Override
  public void prettyPrint(){
    prettyWrite("if ");
    int nPrinted = 0;
    for (int i = 0; i < this.exprList.size(); i++) {
      this.exprList.get(i).prettyPrint();
      prettyWrite(":");
      prettyIndent();
      this.ifSuiteList.get(i).prettyPrint();
      prettyDedent();
      prettyWrite("elif: ");
    }
    if (this.elseSuite != null) {
      prettyWrite("else:");
      prettyIndent();
      this.elseSuite.prettyPrint();
      prettyDedent();
    }
  }
}
