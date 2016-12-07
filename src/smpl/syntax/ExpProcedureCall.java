package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;
import java.util.*;

public class ExpProcedureCall extends Exp {

  ArrayList<Exp> args;
  String var;

  public ExpProcedureCall(){
    super();
  }

  public ExpProcedureCall(String var, ArrayList<Exp> args){
    this.args = args;
    this.var = var;
  }

  public ArrayList<Exp> getArguments(){
    return args;
  }

  public String getVar(){
    return var;
  }

  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpProcedureCall(this, arg);
  }

  @Override
  public String toString() {
    return var + "(" + args + ")";
  }
}

