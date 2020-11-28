package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspReturnStmt extends AspSmallStmt {
  AspExpr expr;

  AspReturnStmt(int n){
    super(n);
  }
  static AspReturnStmt parse(Scanner s){
    enterParser("return stmt");

    skip(s, TokenKind.returnToken);
    AspReturnStmt rs = new AspReturnStmt(s.curLineNum());
    rs.expr = AspExpr.parse(s);

    leaveParser("return stmt");
    return rs;
  }

   @Override
   public void prettyPrint(){
     prettyWrite("return ");
     this.expr.prettyPrint();
   }

   @Override
   public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
     RuntimeValue v = expr.eval(curScope);
     trace(v.showInfo());
     throw new RuntimeReturnValue(v, lineNum);
   }
}
