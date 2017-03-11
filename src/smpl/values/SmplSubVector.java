package smpl.values;

import smpl.sys.SmplException;
import static smpl.values.SmplValue.make;
import java.util.*;

public class SmplSubVector extends SmplValue<SmplSubVector> {

	SmplInt size;
	SmplProcedure proc;

	public SmplSubVector(SmplInt size, SmplProcedure proc){
		this.size = size;
		this.proc = proc;
	}

	public SmplSubVector(int size, SmplProcedure proc){
		this.size = make(size);
		this.proc = proc;
	}

	@Override
	public SmplType getType(){
		return SmplType.SUBVECTOR;
	}

	public SmplInt getSize(){
		return size;
	}

	public int getSizeInt() throws TypeSmplException {
		return size.intValue();
	}

	public SmplProcedure getProcedure(){
		return proc;
	}

	// check if pairs have equivalent values
	public SmplValue<?> eq(SmplValue<?> arg) throws SmplException {
		if(arg.getType() == SmplType.SUBVECTOR){
			boolean eq = size.eq(((SmplSubVector)arg).getSize()).boolValue() && proc.eq(((SmplSubVector)arg).getProcedure()).boolValue();
			return make(eq);
		} else {
			return make(false);
		}
	}

	public SmplValue<?> neq(SmplValue<?> arg) throws SmplException {
		if(arg.getType() == SmplType.SUBVECTOR){
			boolean eq = size.eq(((SmplSubVector)arg).getSize()).boolValue() && proc.eq(((SmplSubVector)arg).getProcedure()).boolValue();
			return make(!eq);
		} else {
			return make(true);
		}
	}

	@Override
	public String toString() {
		return size.toString() + " : " + proc.toString();
	}
}