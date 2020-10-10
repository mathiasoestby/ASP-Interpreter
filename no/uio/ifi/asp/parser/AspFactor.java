package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactor extends AspSyntax {
  ArrayList<AspTermOpr> fpref = new ArrayList<>();
  ArrayList<AspFactor> prim = new ArrayList<>();
  ArrayList<AspFactor> fopr = new ArrayList<>();


  AspFactor(int n) {
    super(n);
  }

  static AspFactor parse(Scanner s) {
    AspFactor af = AspFactor(s.curLineNum());
    enterParser("factor")

    while(true){
      if (s.curToken().kind == TokenKind.plusToken ||
          s.curToken().kind == TokenKind.minusToken)
      {
        af.fpref.add(AspFactorPrefix.parse(s));
      } else {
        //kan hende dette blir feil,, NULLPOINTEREXCEPTION
        af.fpref.add(null);
      }
      af.prim.add(AspPrimary.parse(s));
      if (s.curToken().kind == TokenKind.astToken ||
          s.curToken().kind == TokenKind.slashToken ||
          s.curToken().kind == TokenKind.percentToken ||
          s.curToken().kind == TokenKind.doubleSlashToken)
      {
        af.fopr.add(AspFactorOpr.parse(s));
      }
      else {
        break;
      }
    }
    leaveParser("factor")
    return af;
  }

  @Override
  public void prettyPrint() {
    //-- Must be changed in part 2:
  }
