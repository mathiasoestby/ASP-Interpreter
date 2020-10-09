package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


abstract class AspCompOpr extends AspSyntax {
  AspCompOpr(int n){
    super(n);
  }

  static AspCompOpr parse(Scanner s) {
    enterParser("comp opr");

    AspCompOpr sco;

    if (s.curToken().kind == TokenKind.lessToken)
      sco = AspLessOpr.parse(s);
    else
      sco = AspLessOpr.parse(s);


    leaveParser("comp opr");
    return sco;
  }

  // @Override
  // public void prettyPrint(){
  //   System.out.println(("hei"));
  // }
}
