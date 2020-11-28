package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspPrimary extends AspSyntax {
  AspAtom a;
  ArrayList<AspPrimarySuffix> apsList = new ArrayList<>();

  AspPrimary(int n){
    super(n);
  }

  static AspPrimary parse(Scanner s){
    enterParser("primary");

    AspPrimary as = new AspPrimary(s.curLineNum());

    as.a = AspAtom.parse(s);

    while (s.curToken().kind == TokenKind.leftParToken || s.curToken().kind == TokenKind.leftBracketToken)
      as.apsList.add(AspPrimarySuffix.parse(s));

    leaveParser("primary");

    return as;
  }

  @Override
  public void prettyPrint(){
    a.prettyPrint();
    for (AspPrimarySuffix aps : this.apsList) {
      aps.prettyPrint();
    }
  }

  @Override //Henter ut atom-et sin evaluerte verdi, og sjekker etter potensielle primary suffixer. For øyeblikket tar den bare høyde for subscriptions.
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = this.a.eval(curScope);
    ArrayList<RuntimeValue> rtvList = new ArrayList<>();

    for (int i = 0; i < this.apsList.size(); i++) {
      RuntimeValue aps = this.apsList.get(i).eval(curScope);

      if (this.apsList.get(i) instanceof AspArguments) {
        ArrayList<RuntimeValue> argsList = this.apsList.get(i).eval(curScope).listValue;
        v = curScope.find(v.showInfo());
        v = v.evalFuncCall(argsList, this);

      } else {
        v = v.evalSubscription(aps, this);
      }
    }

    return v;
  }
}
