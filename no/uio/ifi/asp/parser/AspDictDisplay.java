package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import java.util.HashMap;

class AspDictDisplay extends AspAtom {
  ArrayList<AspStringLiteral> aslList = new ArrayList<>();
  ArrayList<AspExpr> exprList = new ArrayList<>();

  AspDictDisplay(int curLineNum){
    super(curLineNum);
  }

  static AspDictDisplay parse(Scanner s){
    enterParser("dict display");

    skip(s, TokenKind.leftBraceToken);
    AspDictDisplay adl = new AspDictDisplay(s.curLineNum());

    if (s.curToken().kind != TokenKind.rightBraceToken){
      adl.aslList.add(AspStringLiteral.parse(s));
      skip(s, TokenKind.colonToken);
      adl.exprList.add(AspExpr.parse(s));
      while (s.curToken().kind == TokenKind.commaToken){
        s.readNextToken();
        adl.aslList.add(AspStringLiteral.parse(s));
        skip(s, TokenKind.colonToken);
        adl.exprList.add(AspExpr.parse(s));
      }
    }

    skip(s, TokenKind.rightBraceToken);
    leaveParser("dict display");

    return adl;
  }

  @Override
  public void prettyPrint(){

    prettyWrite("{");
    if (!this.aslList.isEmpty()){
      prettyWriteLn();
      prettyIndent();
      for (int i = 0; i < this.aslList.size(); i++) {
        if (i > 0)
          prettyWriteLn(", ");

        this.aslList.get(i).prettyPrint();
        prettyWrite(" : ");
        this.exprList.get(i).prettyPrint();
      }
      prettyDedent();
      prettyWriteLn();
    }

    prettyWrite("}");
  }

  // oppretter en RuntimeDict. Vi lager en hashmap som vi sender til RuntimeDict-objektet.
  // Vi må opprette en ny hashmap siden den vi ønsker å sende til RuntimeDict må basere seg på RuntimeValue-objekter. 
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    HashMap<String, RuntimeValue> dict = new HashMap<String, RuntimeValue>();

    for (int i = 0; i < aslList.size(); i++ ) {
      dict.put(aslList.get(i).tekst, exprList.get(i).eval(curScope));
    }
    return new RuntimeDict(dict);
  }
}
