package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;

public class ExpSin extends Exp {

  Exp exp;

  public ExpSin(Exp e) {
    exp = e;
  }

  public Exp getExp(){
    return exp;
  }


  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpSin(this, arg);
  }

  @Override
  public String toString() {
    return "sin( " +exp.toString() +" )";
  }
}
