package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactor extends AspSyntax {
  ArrayList<AspFactorPrefix> fpref = new ArrayList<>();
  ArrayList<AspPrimary> prim = new ArrayList<>();
  ArrayList<AspFactorOpr> fopr = new ArrayList<>();


  AspFactor(int n) {
    super(n);
  }

  static AspFactor parse(Scanner s) {
    AspFactor af = new AspFactor(s.curLineNum());
    enterParser("factor");

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
    leaveParser("factor");
    return af;
  }

  @Override
  public void prettyPrint() {

    for (int i = 0; i < this.prim.size(); i++) {
      if (i >= 1) this.fopr.get(i-1).prettyPrint();
      if (this.fpref.get(i) != null) this.fpref.get(i).prettyPrint();
      this.prim.get(i).prettyPrint();
    }
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = this.prim.get(0).eval(curScope);
    if (this.fpref.get(0) != null) {
      switch (this.fpref.get(0).kind) {
        case plusToken:
          v = v.evalPositive(this.fpref.get(0)); break;
        case minusToken:
          v = v.evalNegate(this.fpref.get(0)); break;
        default:
          Main.panic("Illegal factor operator: " + this.fpref.get(0).kind + "!");
      }
    }

    for (int i = 1; i < this.prim.size(); i++) {
      RuntimeValue v2 = this.prim.get(i).eval(curScope);
      if (this.fpref.get(i) != null) {
        switch (this.fpref.get(0).kind) {
          case plusToken:
            v = v.evalPositive(this.fpref.get(0)); break;
          case minusToken:
            v = v.evalNegate(this.fpref.get(0)); break;
          default:
            Main.panic("Illegal factor operator: " + this.fpref.get(0).kind + "!");
        }
      }
      switch (this.fopr.get(i-1).kind) {
        case astToken:
          v = v.evalMultiply(v2, this.fopr.get(i-1)); break;
        case slashToken:
          v = v.evalDivide(v2, this.fopr.get(i-1)); break;
        case percentToken:
          v = v.evalModulo(v2, this.fopr.get(i-1)); break;
        case doubleSlashToken:
          v = v.evalIntDivide(v2, this.fopr.get(i-1)); break;
      }
    }

    return v;
  }
}
