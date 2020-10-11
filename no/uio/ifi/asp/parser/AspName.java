package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


class AspName extends AspAtom {
  String navn;
  // int curLineNum;

  AspName(int curLineNum){
    super(curLineNum);
  }

  static AspName parse(Scanner s){
    enterParser("name");

    AspName anl = new AspName(s.curLineNum());
    anl.navn = s.curToken().name;
    s.readNextToken();

    leaveParser("name");

    return anl;
  }

  @Override
  public void prettyPrint(){
    System.out.println("name");
  }
}
