package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTermOpr extends AspSyntax {
  TokenKind kind;
  AspTermOpr(int n) {
    super(n);
  }

  static AspTermOpr parse(Scanner s) {
    enterParser("term opr");
    AspTermOpr ato = new AspTermOpr(s.curLineNum());

    switch (s.curToken().kind) {
      case plusToken:
        skip(s, TokenKind.plusToken);
        ato.kind = TokenKind.plusToken;
        break;
      case minusToken:
        skip(s, TokenKind.minusToken);
        ato.kind = TokenKind.minusToken;
        break;
      default:
      //lager feilmelding
        test(s, TokenKind.plusToken, TokenKind.minusToken);
    }

    leaveParser("term opr");
    return ato;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(this.kind.toString());
  }
}
