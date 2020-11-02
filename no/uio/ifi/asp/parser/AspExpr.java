package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspExprStmt {
  //-- Must be changed in part 2:
  ArrayList<AspAndTest> andTests = new ArrayList<>();

  AspExpr(int n) {
    super(n);
  }


  public static AspExpr parse(Scanner s) {
    enterParser("expr");

    AspExpr ae = new AspExpr(s.curLineNum());

    ae.andTests.add(AspAndTest.parse(s));

    while (s.curToken().kind == TokenKind.orToken) {
      skip(s, TokenKind.orToken);
      ae.andTests.add(AspAndTest.parse(s));
    }

    leaveParser("expr");
    return ae;
  }


  @Override
  public void prettyPrint(){
    int nPrinted = 0;
    for (AspAndTest at : this.andTests) {
      if (nPrinted > 0) prettyWrite(" or ");
      at.prettyPrint(); nPrinted++;
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
