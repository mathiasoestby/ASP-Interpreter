package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspSmallStmtList extends AspStmt {
  ArrayList<AspSmallStmt> assList = new ArrayList<>();

  AspSmallStmtList(int n){
    super(n);
  }
  static AspSmallStmtList parse(Scanner s){
    enterParser("small stmt list");

    AspSmallStmtList ssl = new AspSmallStmtList(s.curLineNum());

    ssl.assList.add(AspSmallStmt.parse(s));

    while (s.curToken().kind == TokenKind.semicolonToken) {
      skip(s, TokenKind.semicolonToken);
      if (s.curToken().kind == TokenKind.newLineToken) {
        break;
      }
      ssl.assList.add(AspSmallStmt.parse(s));
    }
    skip(s, TokenKind.newLineToken);

    leaveParser("small stmt list");
    return ssl;
  }

   @Override
   public void prettyPrint(){
     System.out.println("TEST FOR SMALL STMT LIST");
   }
}
