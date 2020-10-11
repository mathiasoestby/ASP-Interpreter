package no.uio.ifi.asp.parser;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


abstract class AspSmallStmt extends AspSyntax {


  AspSmallStmt(int n){
    super(n);
  }
  static AspSmallStmt parse(Scanner s){
    enterParser("small stmt");

    AspSmallStmt ss;

    if (s.curToken().kind == TokenKind.returnToken) {
      ss = AspReturnStmt.parse(s);
    } else if (s.curToken().kind == TokenKind.passToken) {
      ss = AspPassStmt.parse(s);
    } else if (s.anyEqualToken()) {
      ss = AspAssignment.parse(s);
    } else {
      ss = AspExprStmt.parse(s);
    }

    leaveParser("small stmt");
    return ss;
  }

}
