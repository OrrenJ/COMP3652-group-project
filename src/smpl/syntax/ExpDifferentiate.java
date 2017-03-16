package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;
import java.util.*;

public class ExpDifferentiate extends Exp {

  ExpProcedure function;
  String var;
  Boolean form;

  public ExpDifferentiate(){
    super();
  }

  public ExpDifferentiate(ExpProcedure p){
    function = p;
    form = true;
  }

  public ExpDifferentiate(String v){
    var = v;
    form = false;
  }

  public ExpProcedure getFunction(){
    return function;
  }

  public String getVar(){
    return var;
  }
  public Boolean getForm(){
    return form;
  }

 

  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpDifferentiate(this, arg);
  }

  @Override
  public String toString() {
    return " ";
  }
}

