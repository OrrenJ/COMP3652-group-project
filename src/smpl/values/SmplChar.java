package smpl.values;

import smpl.sys.SmplException;
import static smpl.values.SmplValue.makeChar;

public class SmplChar extends SmplValue<SmplChar> {

	char val;

	public SmplChar() {
		this(0x20);
	}

	public SmplChar(char v){
		val = v;
	}

	public SmplChar(int v){
		val = (char) v;
	}

	@Override
	public SmplType getType(){
		return SmplType.CHARACTER;
	}

	@Override
	public SmplValue<?> add(SmplValue<?> arg) throws SmplException {
		return makeChar("" + (char)(val + arg.intValue()));
	}


	// return literal values

	public int intValue() throws TypeSmplException {
		return (int) val;
	}

	public double doubleValue() throws TypeSmplException {
		return (double) val;
	}

	public char charValue() throws TypeSmplException {
		return val;
	}

	@Override
	public String toString() {
		return ""+val;
	}
}