package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.*;

public class RuntimeFunc extends RuntimeValue {
  String name;
  AspFuncDef def;
  RuntimeScope oScope;

  public RuntimeFunc(AspFuncDef def, RuntimeScope oScope) {
    this.def = def;
    this.name = def.defName.navn;
    this.oScope = oScope;
  }

  public RuntimeFunc(String name){
    this.name = name;
    this.oScope = null;
  }

  @Override
  public String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint){
    return String.valueOf(this.name);
  }


  @Override
  public String typeName(){
    return "function";
  }


  @Override
  public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
    if (this.def.argsName.size() != actualParams.size()) {
      runtimeError(this.def.defName + "(): Expected" +
                                this.def.argsName.size() + " arguments (" +
                                actualParams.size() + " given)", where);
    }

    RuntimeScope curScope = new RuntimeScope(this.oScope);
    for (int i = 0; i < this.def.argsName.size(); i++) {
      curScope.assign(def.argsName.get(i).navn, actualParams.get(i));
    }
    try {
      runFunction(curScope);
    } catch(RuntimeReturnValue rrv) {
      return rrv.getReturnValue(where);
    }

    return new RuntimeNoneValue();


  }

  private void runFunction(RuntimeScope curScope) throws RuntimeReturnValue{
    this.def.suite.eval(curScope);
  }


}
