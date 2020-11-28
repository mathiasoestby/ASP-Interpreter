package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
  String stringValue;

  public RuntimeStringValue(String v) {
    this.stringValue = v;
  }
  @Override
  public String typeName(){
    return "String";
  }

  @Override
  protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
    return "'" + this.stringValue + "'";
  }

  @Override
  public String getStringValue(String what, AspSyntax where) {
    return this.stringValue;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    return !this.stringValue.isEmpty();
  }
  // ------------------------------------------- implementerer diverse variasjoner av metoder som skal brukes for å evaluere Strings på forskjellige måter

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeStringValue) {
      return new RuntimeStringValue(this.stringValue + v.getStringValue("string add", where));
    }
    runtimeError("Type error for string concatination.", where);
    return null ;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
    if (v instanceof RuntimeIntegerValue) {
      if (v.getIntValue("string multiply", where) < 0) {
        runtimeError("Value error for string multiplication", where);
      }
      String s = "";
      for (int i = 0; i < v.getIntValue("string multiply", where); i++) {
        s += this.stringValue;
      }
      return new RuntimeStringValue(s);
    }
    runtimeError("Type error for string multiplication", where);
    return null;
  }

  @Override
  public RuntimeValue evalLen(AspSyntax where) {
    return new RuntimeIntegerValue(this.stringValue.length());
  }

  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    return new RuntimeBoolValue(this.stringValue.isEmpty());

  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(this.stringValue.isEmpty());
    }
    if (v instanceof RuntimeStringValue) {
      return new RuntimeBoolValue(this.stringValue.equals(v.getStringValue("string equal", where)));
    }
    runtimeError("Type error for string comparison ==.", where);
    return null;
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(!this.stringValue.isEmpty());
    }
    if (v instanceof RuntimeStringValue) {
      return new RuntimeBoolValue(!(this.stringValue.equals(v.getStringValue("string not equal", where))));
    }
    runtimeError("Type error for string comparison !=.", where);
    return null;
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeStringValue) {
      return new RuntimeBoolValue(this.stringValue.compareTo(v.getStringValue("string less", where)) < 0);
    }
    runtimeError("Type error for string comparison <.", where);
    return null;
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeStringValue) {
      return new RuntimeBoolValue(this.stringValue.compareTo(v.getStringValue("string lessEqual", where)) <= 0);
    }
    runtimeError("Type error for string comparison <=.", where);
    return null;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeStringValue) {
      return new RuntimeBoolValue(this.stringValue.compareTo(v.getStringValue("string Greater", where)) > 0);
    }
    runtimeError("Type error for string comparison >.", where);
    return null;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeStringValue) {
      return new RuntimeBoolValue(this.stringValue.compareTo(v.getStringValue("string not equal", where)) >= 0);
    }
    runtimeError("Type error for string comparison >=.", where);
    return null;
  }

  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntegerValue) {
      if (v.getIntValue("subscription int", where) >= 0 && v.getIntValue("subscription int", where) < this.stringValue.length()) {
        return new RuntimeStringValue(String.valueOf(this.stringValue.charAt((int)v.getIntValue("subscription int", where))));
      } else {
        runtimeError("Index " + String.valueOf(v.getIntValue("subscription int", where)) + " out of bounds for "+typeName()+"!", where);
      }
    }

    runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler!
  }


}
