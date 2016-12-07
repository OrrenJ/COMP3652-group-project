package smpl.values;

import smpl.sys.SmplException;
import static smpl.values.SmplValue.make;
import java.util.*;

public class SmplEmptyList extends SmplList {

	public SmplEmptyList(){
		super(null, null);
	}

	@Override
	public SmplType getType(){
		return SmplType.EMPTYLIST;
	}

	@Override
	public SmplValue<?> eq(SmplValue<?> arg) throws SmplException {
		return make(getType() == arg.getType());
	}

	@Override
	public SmplValue<?> neq(SmplValue<?> arg) throws SmplException {
		return make(getType() != arg.getType());
	}

	@Override
	public String toString() {
		return "nil";
	}
}