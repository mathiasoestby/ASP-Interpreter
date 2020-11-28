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
    prettyWrite("for ");
    this.name.prettyPrint();
    prettyWrite(" in ");
    this.expr.prettyPrint();
    prettyWrite(": ");
    this.suite.prettyPrint();
    prettyWriteLn();

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue ex = this.expr.eval(curScope);
    if (ex instanceof RuntimeList){
      for (int i = 0; i < ((RuntimeList) ex).listValue.size(); i++) {
        curScope.assign(this.name.navn, ((RuntimeList) ex).listValue.get(i));
        this.suite.eval(curScope);
      }
      return null;
    }
    RuntimeValue.runtimeError("For loop range is not a list!", this);
    return null;
  }
}
