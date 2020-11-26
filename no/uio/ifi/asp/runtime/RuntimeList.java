package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.lang.Math;

public class RuntimeList extends RuntimeValue {
    ArrayList<RuntimeValue> listValue;

    public RuntimeList(ArrayList<RuntimeValue> v) {
	    this.listValue = v;
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
      return String.valueOf(this.listValue);
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
      return !(this.listValue.size() == 0);
    }

    @Override
    protected String typeName() {
	    return "list";
    }


    @Override //Metoden henter ut riktig element i lista. Den kaller en feilmelding hvis indeksen er utenfor listas lengde, eller hvis indeksen ikke er et tall.
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        if (v.getIntValue("subscription int", where) >= 0 && v.getIntValue("subscription int", where) < this.listValue.size()) {
          return this.listValue.get(Math.toIntExact(v.getIntValue("subscription int", where)));
        } else {
          runtimeError("Index " + String.valueOf(v.getIntValue("subscription int", where)) + " out of bounds for "+typeName()+"!", where);
        }
      }

      runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
      return null;
    }

    // ------------------------------------------- implementerer diverse variasjoner av metoder som skal brukes for å evaluere lister på forskjellige måter
    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
      if (v instanceof RuntimeIntegerValue) {
        if (v.getIntValue("list multiply", where) < 0) {
          runtimeError("Value error for List multiplication", where);
        }

        ArrayList<RuntimeValue> l = new ArrayList<RuntimeValue>();
        for (int i = 0; i < v.getIntValue("list multiply", where); i++) {
          l.addAll(this.listValue);
        }
        return new RuntimeList(l);
      }
      runtimeError("Type error for List multiplication", where);
      return null;
    }


    @Override
    public RuntimeValue evalNot(AspSyntax where) {
      return new RuntimeBoolValue(this.listValue.size() == 0);  
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(this.listValue.size() == 0);
      }
      runtimeError("Type error for List comparison ==.", where);
      return null;  
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(!(this.listValue.size() == 0));
      }
      runtimeError("Type error for List comparison !=.", where);
      return null;  
    }

}
