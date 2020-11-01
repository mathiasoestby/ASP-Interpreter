package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntegerValue extends RuntimeValue {
    long intValue;

    public RuntimeIntegerValue(long v) {
	intValue = v;
    }


    //Vi skal tydeligvis bruke de metodene vi eventuelt trenger fra RuntimeValue, så det er sikkert noen flere som
    //må implementeres her. Jeg vet bare ikke hvilke ennå
    @Override
    public long getIntValue(String what, AspSyntax where) {
      return intValue;
    }

    @Override
    protected String typeName() {
	return "integer literal";
    }

    @Override
    protected String showInfo(ArrayList<RuntimeValue> inUse, boolean toPrint) {
	return String.valueOf(this.intValue);
    }


}
