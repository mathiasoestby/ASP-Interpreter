package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspInnerExpr extends AspAtom {
  AspExpr e;


  AspInnerExpr(int n) {
    super(n);
  }


  public static AspInnerExpr parse(Scanner s) {
    enterParser("inner expr");

    //-- Must be changed in part 2:
    skip(s, TokenKind.leftParToken);
    AspInnerExpr aie = new AspInnerExpr(s.curLineNum());

    aie.e = AspExpr.parse(s);

    skip(s, TokenKind.rightParToken);
    leaveParser("inner expr");
    return aie;
  }


  @Override
  public void prettyPrint(){
    prettyWrite("(");
    this.e.prettyPrint();
    prettyWrite(")");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
