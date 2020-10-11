package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspPassStmt extends AspSmallStmt{

  AspPassStmt(int n){
    super(n);
  }
  static AspPassStmt parse(Scanner s){
    enterParser("pass stmt");

    skip(s, TokenKind.passToken);

    leaveParser("pass stmt");
    return new AspPassStmt(s.curLineNum());

  }

   @Override
   public void prettyPrint(){
     prettyWrite("pass");
   }
}
