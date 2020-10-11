package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt {
  AspName name;
  AspExpr expr;
  AspSuite suite;

  AspForStmt(int n) {
    super(n);
  }


  public static AspForStmt parse(Scanner s) {
    enterParser("for stmt");

    AspForStmt fs = new AspForStmt(s.curLineNum());

    skip(s, TokenKind.forToken);
    fs.name = AspName.parse(s);
    skip(s, TokenKind.inToken);
    fs.expr = AspExpr.parse(s);
    skip(s, TokenKind.colonToken);
    fs.suite = AspSuite.parse(s);



    leaveParser("for stmt");
    return fs;
  }


  @Override
  public void prettyPrint(){
    System.out.println("hei");
  }
}
