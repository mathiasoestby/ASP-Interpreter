package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspCompoundStmt extends AspSyntax {
  AspCompoundStmt(int n){
    super(n)
  }
  static parse(Scanner s){
    skip(s, TokenKind.)

  }
}
