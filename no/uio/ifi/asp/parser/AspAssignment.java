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
     if (asList.isEmpty()) {
       curScope.assign(this.name.eval(curScope).getStringValue("assignment", this), this.expr.eval(curScope));

     } else {
       RuntimeValue list  = curScope.find(this.name.eval(curScope).getStringValue("assignment", this), this);
       for (int i = 0; i < this.asList.size() - 1; ) {
          list = list.evalSubscription(this.asList.get(i).eval(curScope), this);
       }
       list.evalAssignElem(this.asList.get(this.asList.size() - 1).eval(curScope), this.expr.eval(curScope), this);
     }

     return new RuntimeNoneValue();
   }
}
