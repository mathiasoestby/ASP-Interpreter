package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeName extends RuntimeValue {
  String name;

  public RuntimeName(String v) {
    this.name = v;
  }
  @Override
  public String typeName(){
    return "name";
  }

  @Override
  protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
    return this.name;
  }

  @Override
  public String getStringValue(String what , AspSyntax where){
    return this.name;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    return !this.name.isEmpty();
  }
}
