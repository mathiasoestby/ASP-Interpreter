package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspSmallStmtList extends AspStmt {
  AspSmallStmtList(int n){
    super(n);
  }
  static parse(Scanner s){
    enterParser("small stmt list");

    AspSmallStmtList ssl;

    while (curToken().kind != TokenKind.newLineToken) {
      ssl = AspSmallStmt.parse(s);
    }

    leaveParser("small stmt list");
    return ssl;
  }

   @Override
   public void prettyPrint(){
     System.out.println("TEST FOR SMALL STMT LIST");
   }
}
