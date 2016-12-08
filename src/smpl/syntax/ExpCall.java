package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;
import java.util.*;
import smpl.values.*;

public class ExpCall extends Exp {

  ExpProcedure exp;
  ExpList lst;
  String id = " ";

  public ExpCall(ExpProcedure e, Exp l) {
    exp = e;
    lst = (ExpList)l;
  }

  public ExpCall(ExpProcedure e, String i) {
    exp = e;
    id = i;
  }


  public ExpProcedure getExp(){
    return exp;
  }

  public ExpList getExpList(){
    return lst;
  }

  public String getId(){
    return id;
  }

  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpCall(this, arg);
  }

  @Override
  public String toString() {
    return "call(" + exp.toString() + ", " + lst.toString() + ")";
  }
}