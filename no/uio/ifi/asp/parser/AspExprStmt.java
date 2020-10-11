package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


abstract class AspExprStmt extends AspSmallStmt {

  AspExprStmt(int n){
    super(n);
  }
  static AspExprStmt parse(Scanner s){
    enterParser("expr stmt");

    AspExprStmt aes = AspExpr.parse(s);


    leaveParser("expr stmt");
    return aes;
  }

   @Override
   public void prettyPrint(){
     System.out.println("TEST FOR STMT");
   }
}
