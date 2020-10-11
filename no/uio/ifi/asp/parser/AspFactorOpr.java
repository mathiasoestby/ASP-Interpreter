package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorOpr extends AspSyntax {
  TokenKind kind;

  AspFactorOpr(int n) {
    super(n);
  }

  static AspFactorOpr parse(Scanner s) {
    AspFactorOpr afo = new AspFactorOpr(s.curLineNum());
    enterParser("factor opr");

    switch (s.curToken().kind) {
      case astToken:
        skip(s, TokenKind.astToken);
        afo.kind = TokenKind.astToken;
        break;

      case slashToken:
        skip(s, TokenKind.slashToken);
        afo.kind = TokenKind.slashToken;
        break;

      case percentToken:
        skip(s, TokenKind.percentToken);
        afo.kind = TokenKind.percentToken;
        break;

      case doubleSlashToken:
        skip(s, TokenKind.doubleSlashToken);
        afo.kind = TokenKind.doubleSlashToken;
        break;

      default:
        String build = "Expected " + TokenKind.astToken +
                        " or " + TokenKind.slashToken +
                        " or " + TokenKind.percentToken +
                        " or " + TokenKind.doubleSlashToken +
                        ", but found " + s.curToken().kind;
      //lager feilmelding
        parserError(build, s.curLineNum());
    }

    leaveParser("factor opr");
    return afo;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(this.kind.toString());
  }
}
