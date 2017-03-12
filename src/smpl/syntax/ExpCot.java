package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;

public class ExpCot extends Exp {

  Exp exp;

  public ExpCot(Exp e) {
    exp = e;
  }

  public Exp getExp(){
    return exp;
  }


  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpCot(this, arg);
  }

  @Override
  public String toString() {
    return "cot( " +exp.toString() +" )";
  }
}