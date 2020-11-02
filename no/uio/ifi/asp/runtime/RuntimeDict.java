package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.lang.Math;
import java.util.HashMap;

public class RuntimeDict extends RuntimeValue {
    HashMap<RuntimeValue, RuntimeValue> dictValue;

    public RuntimeDict(HashMap<RuntimeValue, RuntimeValue> v) {
	    this.dictValue = v;
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
      return String.valueOf(this.dictValue);
    }

    // @Override
    // public ArrayList<RuntimeValue> getListValue(String what, AspSyntax where) {
    //   return this.listValue;
    // }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
      return !(this.dictValue.size() == 0);
    }

    @Override
    protected String typeName() {
	    return "dictionary";
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
      return new RuntimeBoolValue(this.dictValue.size() == 0);  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(this.dictValue.size() == 0);
      }
      runtimeError("Type error for Dict comparison ==.", where);
      return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(!(this.dictValue.size() == 0));
      }
      runtimeError("Type error for Dict comparison !=.", where);
      return null;  // Required by the compiler!
    }

}
