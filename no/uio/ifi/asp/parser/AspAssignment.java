package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


class AspAssignment extends AspSmallStmt{
  AspName name;
  ArrayList<AspSubscription> asList = new ArrayList<>();
  AspExpr expr;

  AspAssignment(int n){
    super(n);
  }
  static AspAssignment parse(Scanner s){
    enterParser("assignment");
    AspAssignment aa = new AspAssignment(s.curLineNum());

    aa.name = AspName.parse(s);

    while (s.curToken().kind == TokenKind.leftBracketToken)
      aa.asList.add(AspSubscription.parse(s));

    skip(s, TokenKind.equalToken);

    aa.expr = AspExpr.parse(s);


    leaveParser("assignment");
    return aa;
  }

  @Override
  public void prettyPrint(){

    this.name.prettyPrint();

    for (AspSubscription as : this.asList) {
      as.prettyPrint();
    }

    prettyWrite(" = ");
    this.expr.prettyPrint();

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue exprEval = this.expr.eval(curScope);

    if (asList.isEmpty()) {
      curScope.assign(this.name.navn, exprEval);
      if (exprEval instanceof RuntimeStringValue)
        trace(this.name.navn + " = '" + exprEval.toString() + "'");
      else trace(this.name.navn + " = " + exprEval.toString());

    } else {
      RuntimeValue list  = this.name.eval(curScope);
      String logStr = this.name.navn;
      for (int i = 0; i < this.asList.size() - 1; ) {
        RuntimeValue subsc = this.asList.get(i).eval(curScope);

        if (exprEval instanceof RuntimeStringValue)
          logStr += "['" + subsc.toString() + "']";
        else logStr += "[" + subsc.toString() + "]";

        list = list.evalSubscription(this.asList.get(i).eval(curScope), this);
      }
      RuntimeValue lastsub = this.asList.get(this.asList.size() - 1).eval(curScope);

      if (lastsub instanceof RuntimeStringValue)
        logStr += "['" + lastsub.toString() + "'] = ";
      else logStr += "[" + lastsub.toString() + "] = ";

      if (exprEval instanceof RuntimeStringValue)
        logStr += "'" + exprEval.toString() + "'";
      else logStr += exprEval.toString();
      trace(logStr);

      list.evalAssignElem(lastsub, exprEval, this);
    }
    return null;
  }
}
