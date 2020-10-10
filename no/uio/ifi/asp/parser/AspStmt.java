package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


abstract class AspStmt extends AspSyntax {
  AspStmt(int n){
    super(n);
  }
  static AspStmt parse(Scanner s){
    enterParser("stmt");

    AspStmt a;

    if (s.curToken().kind == TokenKind.forToken) {
      a = AspCompoundStmt.parse(s);
    } else if (s.curToken().kind == TokenKind.ifToken) {
      a = AspCompoundStmt.parse(s);
    } else if (s.curToken().kind == TokenKind.whileToken) {
      a = AspCompoundStmt.parse(s);
    } else if (s.curToken().kind == TokenKind.defToken) {
      a = AspCompoundStmt.parse(s);
    } else {
      a = AspSmallStmtList.parse(s);
    }

    leaveParser("stmt");
    return a;
  }

   @Override
   public void prettyPrint(){
     System.out.println("TEST FOR STMT");
   }
}
