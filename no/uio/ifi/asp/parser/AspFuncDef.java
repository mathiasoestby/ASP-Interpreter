package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompoundStmt{
  AspName defName;
  ArrayList<AspName> argsName = new ArrayList<>();
  AspSuite suite;

  AspFuncDef(int n) {
    super(n);
  }


  public static AspFuncDef parse(Scanner s) {
    enterParser("func def");

    AspFuncDef fd = new AspFuncDef(s.curLineNum());

    skip(s, TokenKind.defToken);
    fd.defName = AspName.parse(s);
    skip(s, TokenKind.leftParToken);

    if (s.curToken().kind == TokenKind.nameToken){
      fd.argsName.add(AspName.parse(s));
      while (s.curToken().kind == TokenKind.commaToken) {
        s.readNextToken();
        fd.argsName.add(AspName.parse(s));
      }
    }

    skip(s, TokenKind.rightParToken);
    skip(s, TokenKind.colonToken);

    fd.suite = AspSuite.parse(s);

    leaveParser("func def");
    return fd;
  }


  @Override
  public void prettyPrint(){
    System.out.println("hei");
  }
}
