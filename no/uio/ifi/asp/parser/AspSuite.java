package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSuite extends AspSyntax {
  ArrayList<AspStmt> stmtList = new ArrayList<>();
  AspSmallStmtList ssl;

  AspSuite(int n) {
    super(n);
  }


  public static AspSuite parse(Scanner s) {
    enterParser("suite");

    AspSuite as = new AspSuite(s.curLineNum());

    if (s.curToken().kind == TokenKind.newLineToken) {
      skip(s, TokenKind.newLineToken);
      skip(s, TokenKind.indentToken);
      while (s.curToken().kind != TokenKind.dedentToken) {
        as.stmtList.add(AspStmt.parse(s));
        if (s.curToken().kind == TokenKind.newLineToken){
          s.readNextToken();
        }
      }
      skip(s, TokenKind.dedentToken);
    } else {
      as.ssl = AspSmallStmtList.parse(s);
    }

    leaveParser("suite");
    return as;
  }


  @Override
  public void prettyPrint(){
    if (this.stmtList.isEmpty()){
      this.ssl.prettyPrint();
    } else {
      prettyWriteLn();
      prettyIndent();
      for (AspStmt stmt : this.stmtList)
        stmt.prettyPrint();
      prettyDedent();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    if (this.ssl != null){
      this.ssl.eval(curScope);
    } else {
      for (AspSyntax val : this.stmtList) {
        val.eval(curScope);
      }
    }
    return null;
  }
}
