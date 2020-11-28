package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
// For part 4:

public class RuntimeReturnValue extends Exception {
  public int lineNum;
  RuntimeValue value;

  public RuntimeReturnValue(RuntimeValue v, int lNum) {
    value = v;  lineNum = lNum;
  }

  public String showInfo() {
    return value.showInfo();
  }

  public RuntimeValue getReturnValue(AspSyntax where) {
    return value;
  }
}
