package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompoundStmt {
  AspExpr expr;
  AspSuite suite;

  AspWhileStmt(int n) {
    super(n);
  }


  public static AspWhileStmt parse(Scanner s) {
    enterParser("while stmt");

    AspWhileStmt ws = new AspWhileStmt(s.curLineNum());

    skip(s, TokenKind.whileToken);
    ws.expr = AspExpr.parse(s);
    skip(s, TokenKind.colonToken);
    ws.suite = AspSuite.parse(s);



    leaveParser("while stmt");
    return ws;
  }


  @Override
  public void prettyPrint(){
    prettyWrite("while ");
    this.expr.prettyPrint();
    prettyWrite(": ");
    this.suite.prettyPrint();
  }
}
