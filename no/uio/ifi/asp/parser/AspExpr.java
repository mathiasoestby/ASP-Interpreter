package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspExprStmt {
  //-- Must be changed in part 2:
  ArrayList<AspAndTest> andTests = new ArrayList<>();

  AspExpr(int n) {
    super(n);
  }


  public static AspExpr parse(Scanner s) {
    enterParser("expr");

    AspExpr ae = new AspExpr(s.curLineNum());

    ae.andTests.add(AspAndTest.parse(s));

    while (s.curToken().kind == TokenKind.orToken) {
      skip(s, TokenKind.orToken);
      ae.andTests.add(AspAndTest.parse(s));
    }

    leaveParser("expr");
    return ae;
  }


  @Override
  public void prettyPrint(){
    int nPrinted = 0;
    for (AspAndTest at : this.andTests) {
      if (nPrinted > 0) prettyWrite(" or ");
      at.prettyPrint(); nPrinted++;
    }
  }

  @Override // lager metoden som er vårt utgangspunkt for del 3. Går gjennom and-operatorene og returnerer verdien
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = andTests.get(0).eval(curScope);
    for (int i = 1; i < andTests.size(); i++ ) {
      // I tilfellet det faktisk skulle være and- og or-operatorer i uttrykket: Når vi har funnet en or-operand, betyr det at vi må ha evaluert en hel kjede med and-operatorer til å være true, og vi kan avslutte tidlig.
      if (v.getBoolValue("or operand", this)) return v;
      v = andTests.get(i).eval(curScope);
    }
    trace(v.showInfo());
    return v;
  }
}
