package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;

public class ExpCos extends Exp {

  Exp exp;

  public ExpCos(Exp e) {
    exp = e;
  }

  public Exp getExp(){
    return exp;
  }


  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpCos(this, arg);
  }

  @Override
  public String toString() {
    return "cos( " +exp.toString() +" )";
  }
}
