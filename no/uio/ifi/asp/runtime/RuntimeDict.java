package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.lang.Math;
import java.util.HashMap;

public class RuntimeDict extends RuntimeValue {
    HashMap<String, RuntimeValue> dictValue;

    public RuntimeDict(HashMap<String, RuntimeValue> v) {
	    this.dictValue = v;
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
      return String.valueOf(this.dictValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
      return !(this.dictValue.size() == 0);
    }

    @Override
    protected String typeName() {
	    return "dictionary";
    }

    @Override //Metoden henter ut riktig nøkkelverdi i ordboka. Den kaller en feilmelding hvis nøkkelen ikke finnes i hashmappet, eller hvis nøkkelen ikke er en tekststreng.
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeStringValue) {
        if (this.dictValue.containsKey(v.getStringValue("Dict key", where))) {
          return this.dictValue.get(v.getStringValue("Dict key", where));
        } else {
          runtimeError("String value " + v.getStringValue("Dict key", where) + " is not a key in "+typeName()+"!", where);
        }
      }
      runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
      return null;
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
      return new RuntimeBoolValue(this.dictValue.size() == 0);
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(this.dictValue.size() == 0);
      }
      runtimeError("Type error for Dict comparison ==.", where);
      return null;
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(!(this.dictValue.size() == 0));
      }
      runtimeError("Type error for Dict comparison !=.", where);
      return null;
    }


    @Override
    public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
      if (inx instanceof RuntimeStringValue) {
        this.dictValue.put(inx.getStringValue("Dict assignment", where), val);
        return;
      }
      runtimeError("Type-error for key in Dictionary assignment", where);
      return;
    }
}
