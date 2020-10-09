package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspReturnStmt extends AspSyntax {
  AspReturnStmt(int n){
    AspExpr expr;
    super(n);
  }
  static parse(Scanner s){
    enterParser("return stmt");

    skip(s, TokenKind.returnToken);
    AspReturnStmt rs;
    rs.expr = AspExpr.parse(s);

    leaveParser("return stmt");
    return rs;
  }

   @Override
   public void prettyPrint(){
     System.out.println("TEST FOR RETURN STMT");
   }
}
