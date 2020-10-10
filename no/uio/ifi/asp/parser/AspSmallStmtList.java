package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;
import java.util.ArrayList;


class AspSmallStmtList extends AspStmt {
  ArrayList<AspSmallStmt> list = new ArrayList<>();

  AspSmallStmtList(int n){
    super(n);
  }

  static parse(Scanner s){
    enterParser("small stmt list");

    AspSmallStmtList ssl = AspSmallStmtList(s.curLineNum());

    ssl.list.add(AspSmallStmt.parse(s));

    while (s.curToken().kind == TokenKind.semicolonToken) {
      skip(s, TokenKind.semicolonToken);
      if (s.curToken().kind == TokenKind.newLineToken) {
        break;
      }
      ssl.list.add(AspSmallStmt.parse(s));
    }
    skip(s, TokenKind.newLineToken)

    leaveParser("small stmt list");
    return ssl;
  }

   @Override
   public void prettyPrint(){
     System.out.println("TEST FOR SMALL STMT LIST");
   }
}
