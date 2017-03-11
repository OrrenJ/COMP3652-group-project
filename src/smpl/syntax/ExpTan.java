package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;

public class ExpTan extends Exp {

  Exp exp;

  public ExpTan(Exp e) {
    exp = e;
  }

  public Exp getExp(){
    return exp;
  }


  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpTan(this, arg);
  }

  @Override
  public String toString() {
    return "tan( " +exp.toString() +" )";
  }
}