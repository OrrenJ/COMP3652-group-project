package smpl.syntax;

import smpl.semantics.Visitor;
import smpl.sys.SmplException;

public class ExpBitWiseNot extends Exp {

  Exp exp;

  public ExpBitWiseNot(Exp e) {
    exp = e;
  }

  public Exp getExp(){
    return exp;
  }

  @Override
  public <S, T> T visit(Visitor<S, T> v, S arg) throws SmplException {
    return v.visitExpBitWiseNot(this, arg);
  }

  @Override
  public String toString() {
    return "~" + exp.toString();
  }
}

