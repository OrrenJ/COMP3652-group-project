package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;
import java.util.*;

public class ExpLimit extends Exp {

  Exp function;
  Exp approach;
  String var, funcVar;
  Boolean form;

  public ExpLimit(){
    super();
  }

  public ExpLimit(Exp p, String v, Exp a){
    function = p;
    approach = a;
    var = v;
    form = true;
  }

  public ExpLimit(String funcVar, String v,  Exp a){

    this.funcVar = funcVar;
    var = v;
    approach = a;
    form = false;
  }

  public Exp getFunction(){
    return function;
  }

  public String getVar(){
    return var;
  }

  public String getFuncVar(){
    return funcVar;
  }

  public Exp getApproach(){
    return approach;
  }

  public Boolean getForm(){
    return form;
  }

 


  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpLimit(this, arg);
  }

  //TODO
  @Override
  public String toString() {
    return " ";
  }
}

