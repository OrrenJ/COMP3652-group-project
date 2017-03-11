package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;

public class ExpCosec extends Exp {

  Exp exp;

  public ExpCosec(Exp e) {
    exp = e;
  }

  public Exp getExp(){
    return exp;
  }


  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpCosec(this, arg);
  }

  @Override
  public String toString() {
    return "cosec( " +exp.toString() +" )";
  }
}