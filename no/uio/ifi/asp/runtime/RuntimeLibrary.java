package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {
	//-- Must be changed in part 4:
  // inneholder de predefinerte funksjonene
      // len
      assign("len", new RuntimeFunc("len") {
        @Override
        public RuntimeValue evalFuncCall(
          ArrayList<RuntimeValue> actualParams,
          AspSyntax where) {
            checkNumParams(actualParams, 1, "len", where);
            return actualParams.get(0).evalLen(where);
        }});

      // exit
      assign("exit", new RuntimeFunc("exit") {
        @Override
        public RuntimeValue evalFuncCall(
          ArrayList<RuntimeValue> actualParams,
          AspSyntax where) {
          checkNumParams(actualParams, 1, "exit", where);

          if (actualParams.get(0) instanceof RuntimeIntegerValue)
            System.exit((int)actualParams.get(0).getIntValue("exit", where));

          RuntimeValue.runtimeError("Function call <exit>: Expected Integer," +
                                    " but found " +
                                     actualParams.get(0).typeName() + ".", where);
          return null;
        }
      });

      // float
      assign("float", new RuntimeFunc("float") {
        @Override
        public RuntimeValue evalFuncCall(
          ArrayList<RuntimeValue> actualParams,
          AspSyntax where) {
          checkNumParams(actualParams, 1, "float", where);

          if (actualParams.get(0) instanceof RuntimeIntegerValue)
            return new RuntimeFloatValue(
              (double) actualParams.get(0).getIntValue("float convert", where));

          if (actualParams.get(0) instanceof RuntimeFloatValue)
            return new RuntimeFloatValue(
              actualParams.get(0).getFloatValue("float convert", where));

              if (actualParams.get(0) instanceof RuntimeStringValue){
                try {
                  return new RuntimeFloatValue(
                    Float.parseFloat(actualParams
                                     .get(0)
                                     .getStringValue("float convert", where)));
                } catch(NumberFormatException | NullPointerException ne) {
                  RuntimeValue.runtimeError("Could not convert string to float:"
                    + actualParams.get(0).getStringValue("float convert",
                                                         where), where);
                }
              }

          RuntimeValue.runtimeError("Function call <float>: Argument type-error, " +
                                     actualParams.get(0).typeName() + ".", where);
          return null;
        }
      });

      // input
      assign("input", new RuntimeFunc("input") {
              @Override
              public RuntimeValue evalFuncCall(
                ArrayList<RuntimeValue> actualParams,
                AspSyntax where) {
                  checkNumParams(actualParams, 1, "input", where);

                  if (actualParams.get(0) instanceof RuntimeStringValue){
                    System.out.println(actualParams.get(0).getStringValue("input", where));
                    Scanner sc = new Scanner(System.in);
                    return new RuntimeStringValue(sc.nextLine());
                  }

                  RuntimeValue.runtimeError("Function call <input>: Expected" +
"String argument, but found " + actualParams.get(0).typeName(), where);

                  return null;
                }
              });
      // int
      assign("int", new RuntimeFunc("int") {
        @Override
        public RuntimeValue evalFuncCall(
          ArrayList<RuntimeValue> actualParams,
          AspSyntax where) {
          checkNumParams(actualParams, 1, "int", where);

          if (actualParams.get(0) instanceof RuntimeIntegerValue)
            return new RuntimeIntegerValue(
              actualParams.get(0).getIntValue("int convert", where)
              );

          if (actualParams.get(0) instanceof RuntimeFloatValue)
            return new RuntimeIntegerValue(
              (int) Math.round(
                actualParams.get(0).getFloatValue("int convert", where))
              );

              if (actualParams.get(0) instanceof RuntimeStringValue){
                try {
                  return new RuntimeIntegerValue(
                    Integer.parseInt(
                      actualParams.get(0).getStringValue("int convert", where))
                      );
                } catch(NumberFormatException | NullPointerException ne) {
                  RuntimeValue.runtimeError("Could not convert string to Integer:" +
                                            actualParams.get(0).getStringValue("int convert", where), where);
                }
              }

          RuntimeValue.runtimeError("Function call <int>: Argument type-error, " +
                                     actualParams.get(0).typeName() + ".", where);
          return null;
        }
      });

      // print
      assign("print", new RuntimeFunc("print") {
              @Override
              public RuntimeValue evalFuncCall(
                ArrayList<RuntimeValue> actualParams,
                AspSyntax where) {
                  String build = "";
                  for (RuntimeValue val: actualParams) {
                    if (! build.equals("")) build += " ";
                    build += val.showInfo();
                  }
                  System.out.println(build);
                  return new RuntimeNoneValue();
                }
              });

      // range
      assign("range", new RuntimeFunc("range") {
        @Override
        public RuntimeValue evalFuncCall(
          ArrayList<RuntimeValue> actualParams,
          AspSyntax where) {
            checkNumParams(actualParams, 2, "range", where);

            if ((actualParams.get(0) instanceof RuntimeIntegerValue) &&
              (actualParams.get(1) instanceof RuntimeIntegerValue))
            {
              ArrayList<RuntimeValue> list = new ArrayList<RuntimeValue>();

              for (int i = (int) actualParams.get(0).getIntValue("range", where);
                   i < (int)actualParams.get(1).getIntValue("range", where);
                   i++)
              {
                list.add(new RuntimeIntegerValue(i));
              }
              return new RuntimeList(list);
            }

            RuntimeValue.runtimeError("Function call <range>: Arguments must be Integers.", where);
            return null;

        }});

      //str
      assign("str", new RuntimeFunc("str") {
              @Override
              public RuntimeValue evalFuncCall(
                ArrayList<RuntimeValue> actualParams,
                AspSyntax where) {
                  checkNumParams(actualParams, 1, "str", where);

                  return new RuntimeStringValue(actualParams.get(0).showInfo());
                }
              });


    }


    private void checkNumParams(ArrayList<RuntimeValue> actArgs,
				int nCorrect, String id, AspSyntax where) {
	      if (actArgs.size() != nCorrect)
	        RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
