package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;


public class AspName extends AspAtom {
  public String navn;
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
    prettyWrite(this.navn);
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeName(this.navn);
  }
}
