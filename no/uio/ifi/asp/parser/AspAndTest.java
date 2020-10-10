package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAndTest extends AspSyntax {
  ArrayList<AspNotTest> notTests = new ArrayList<>();

  AspAndTest(int n) {
    super(n);
  }

  static AspAndTest parse(Scanner s) {
    enterParser("and test")

    AspAndTest aat = new AspAndTest(s.curLineNum());

    while (true) {
      aat.notTests.add(AspNotTest.parse(s));

      if (s.curToken().kind != andToken) break;
      skip(s, andToken);
    }

    leaveParser("and test")
    return aat;
  }

  @Override
  public void prettyPrint() {
    //-- Must be changed in part 2:
  }
