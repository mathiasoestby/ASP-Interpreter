package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.lang.Math;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double v) {
	    floatValue = v;
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
      return String.valueOf(this.floatValue);
    }

    //Vi skal tydeligvis bruke de metodene vi eventuelt trenger fra RuntimeValue, så det er sikkert noen flere som
    //må implementeres her. Jeg vet bare ikke hvilke ennå
    @Override
    public double getFloatValue(String what, AspSyntax where) {
      return this.floatValue;
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
      return !(this.floatValue == 0);
    }

    @Override
    protected String typeName() {
	    return "float";
    }


    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeFloatValue(this.floatValue + v.getIntValue("float add", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.floatValue + v.getFloatValue("float add", where));
      }
      runtimeError("Type error for Float addition +.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeFloatValue(this.floatValue - v.getIntValue("float subtract", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.floatValue - v.getFloatValue("float subtract", where));
      }
      runtimeError("Type error for Float subtraction -.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeFloatValue(this.floatValue * v.getIntValue("float multiply", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.floatValue * v.getFloatValue("float multiply", where));
      }
      runtimeError("Type error for Float multiplication *.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeFloatValue(this.floatValue / v.getIntValue("float divide", where));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.floatValue / v.getFloatValue("float divide", where));
      }
      runtimeError("Type error for Float divison /.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeFloatValue(Math.floor(this.floatValue / v.getIntValue("float intdivide", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(Math.floor(this.floatValue / v.getFloatValue("float intdivide", where)));
      }
      runtimeError("Type error for Float division //.", where);
      return null ;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeFloatValue(this.floatValue - v.getIntValue("float mod", where) * Math.floor(this.floatValue / v.getIntValue("float mod", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeFloatValue(this.floatValue - v.getFloatValue("float mod", where) * Math.floor(this.floatValue / v.getFloatValue("float mod", where)));
      }
      runtimeError("Type error for Float mod %.", where);
      return null ;  // Required by the compiler!
    }

    public RuntimeValue evalPositive(AspSyntax where) {
      // kan hende dette burde vært et nytt objekt, men forsto det fra kompendiet at vi bare skal returnere
      return this;
    }

    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
      return new RuntimeFloatValue(-this.floatValue);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
      return new RuntimeBoolValue(this.floatValue == 0);  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(this.floatValue == 0);
      }
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.floatValue == (v.getIntValue("float equal", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.floatValue == (v.getFloatValue("float equal", where)));
      }
      runtimeError("Type error for Float comparison ==.", where);
      return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeNoneValue) {
        return new RuntimeBoolValue(!(this.floatValue == 0));
      }
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(!(this.floatValue == (v.getIntValue("float not equal", where))));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(!(this.floatValue == (v.getFloatValue("float not equal", where))));
      }
      runtimeError("Type error for Float comparison !=.", where);
      return null;  // Required by the compiler!
    }

    // !! Dette må gjøres
    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.floatValue > (v.getIntValue("float greater", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.floatValue > (v.getFloatValue("float greater", where)));
      }
      runtimeError("Type error for Float comparison >.", where);
      return null;  // Required by the compiler!
    }

    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.floatValue >= (v.getIntValue("float greater equal", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.floatValue >= (v.getFloatValue("float greater equal", where)));
      }
      runtimeError("Type error for Float comparison >=.", where);
      return null;  // Required by the compiler!
    }

    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.floatValue < (v.getIntValue("float less", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.floatValue < (v.getFloatValue("float less", where)));
      }
      runtimeError("Type error for Float comparison <.", where);
      return null;  // Required by the compiler!
    }

    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
      if (v instanceof RuntimeIntegerValue) {
        return new RuntimeBoolValue(this.floatValue <= (v.getIntValue("float greater equal", where)));
      }
      if (v instanceof RuntimeFloatValue) {
        return new RuntimeBoolValue(this.floatValue <= (v.getFloatValue("float greater equal", where)));
      }
      runtimeError("Type error for Float comparison >.", where);
      return null;  // Required by the compiler!
    }



}
