package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.lang.Math;

public class RuntimeIntegerValue extends RuntimeValue {
    long intValue;

    public RuntimeIntegerValue(long v) {
	    intValue = v;
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
      return String.valueOf(this.intValue);
    }

    //Vi skal tydeligvis bruke de metodene vi eventuelt trenger fra RuntimeValue, så det er sikkert noen flere som
    //må implementeres her. Jeg vet bare ikke hvilke ennå
    @Override
    public long getIntValue(String what, AspSyntax where) {
      return intValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
      return !(this.intValue == 0);
    }

    @Override
    protected String typeName() {
	    return "integer";
    }


    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeIntegerValue(this.intValue + v.getIntValue("int add", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.intValue + v.getFloatValue("int add", where));
      }
      runtimeError("Type error for Integer addition +.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeIntegerValue(this.intValue - v.getIntValue("int subtract", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.intValue - v.getFloatValue("int subtract", where));
      }
      runtimeError("Type error for Integer subtraction -.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeIntegerValue(this.intValue * v.getIntValue("int multiply", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.intValue * v.getFloatValue("int multiply", where));
      }
      runtimeError("Type error for Integer multiplication *.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeIntegerValue(this.intValue / v.getIntValue("int divide", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.intValue / v.getFloatValue("int divide", where));
      }
      runtimeError("Type error for Integer division /.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeIntegerValue(Math.floorDiv(this.intValue, v.getIntValue("int integer divide", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(Math.floor(this.intValue / v.getFloatValue("int integer divide", where)));
      }
      runtimeError("Type error for Integer division //.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeIntegerValue(Math.floorMod(this.intValue, v.getIntValue("int mod", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue((this.intValue - v.getFloatValue("int mod", where)) * Math.floor(this.intValue / v.getFloatValue("int mod", where)));
      }
      runtimeError("Type error for Integer mod %.", where);
      return null ;  // Required by the compiler!
    }

    public RuntimeValue evalPositive(AspSyntax where) {
      // kan hende dette burde vært et nytt objekt, men forsto det fra kompendiet at vi bare skal returnere
      return this;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
      return new RuntimeIntegerValue(-this.intValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
      return new RuntimeBoolValue(this.intValue == 0);  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(this.intValue == 0);
      }
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.intValue == (v.getIntValue("Integer equal", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.intValue == (v.getFloatValue("Integer equal", where)));
      }
      runtimeError("Type error for Integer comparison ==.", where);
      return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(!(this.intValue == 0));
      }
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(!(this.intValue == (v.getIntValue("Integer not equal", where))));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(!(this.intValue == (v.getFloatValue("Integer not equal", where))));
      }
      runtimeError("Type error for Integer comparison !=.", where);
      return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.intValue > (v.getIntValue("Integer greater", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.intValue > (v.getFloatValue("Integer greater", where)));
      }
      runtimeError("Type error for Integer comparison >.", where);
      return null;  // Required by the compiler!
    }

    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.intValue >= (v.getIntValue("Integer greater equal", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.intValue >= (v.getFloatValue("Integer greater equal", where)));
      }
      runtimeError("Type error for Integer comparison >=.", where);
      return null;  // Required by the compiler!
    }

    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.intValue < (v.getIntValue("Integer less", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.intValue < (v.getFloatValue("Integer less", where)));
      }
      runtimeError("Type error for Integer comparison <.", where);
      return null;  // Required by the compiler!
    }

    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.intValue <= (v.getIntValue("Integer less equal", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.intValue <= (v.getFloatValue("Integer less equal", where)));
      }
      runtimeError("Type error for Integer comparison <=.", where);
      return null;  // Required by the compiler!
    }



}
