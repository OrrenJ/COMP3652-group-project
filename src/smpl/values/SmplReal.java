/*
 * Adapted from FnPlot
 */

package smpl.values;

import smpl.sys.SmplException;
import static smpl.values.SmplValue.make;
import java.lang.Math;

public class SmplReal extends SmplValue<SmplReal> {

	double val;

	public SmplReal() {
		this(0D);
	}

	public SmplReal(Double v){
		val = v;
	}

	@Override
	public SmplType getType(){
		return SmplType.REAL;
	}
	

	// arithmetic operations

	@Override
	public SmplValue<?> add(SmplValue<?> arg) throws SmplException {
		return make(val + arg.doubleValue());
	}

	public SmplValue<?> sub(SmplValue<?> arg) throws SmplException {
		return make(val - arg.doubleValue());
	}

	public SmplValue<?> mul(SmplValue<?> arg) throws SmplException {
		return make(val * arg.doubleValue());
	}

	public SmplValue<?> div(SmplValue<?> arg) throws SmplException {
		return make(val / arg.doubleValue());
	}

	public SmplValue<?> mod(SmplValue<?> arg) throws SmplException {
		if(arg.isInteger())
			return make(val % arg.intValue());
		else
			return make(val % arg.doubleValue());
	}

	public SmplValue<?> pow(SmplValue<?> arg) throws SmplException {
		if(arg.isInteger()) 
			return make( Math.pow( val, arg.intValue() ) );
		else
			return make( Math.pow( val, arg.doubleValue() ) );
	}


	public SmplValue<?> eq(SmplValue<?> arg) throws SmplException {
		SmplType type = arg.getType();
		if(type == SmplType.INTEGER ||
			type == SmplType.CHARACTER ||
			type == SmplType.REAL)
			return make(val == arg.doubleValue());
		else
			return make(false);
	}

	public SmplValue<?> gt(SmplValue<?> arg) throws SmplException {
		SmplType type = arg.getType();
		if(type == SmplType.INTEGER ||
			type == SmplType.CHARACTER ||
			type == SmplType.REAL)
			return make(val > arg.intValue());
		else
			throw new TypeSmplException(SmplType.REAL, arg.getType());
	}

	public SmplValue<?> lt(SmplValue<?> arg) throws SmplException {
		SmplType type = arg.getType();
		if(type == SmplType.INTEGER ||
			type == SmplType.CHARACTER ||
			type == SmplType.REAL)
			return make(val < arg.doubleValue());
		else
			throw new TypeSmplException(SmplType.REAL, arg.getType());
	}

	public SmplValue<?> le(SmplValue<?> arg) throws SmplException {
		SmplType type = arg.getType();
		if(type == SmplType.INTEGER ||
			type == SmplType.CHARACTER ||
			type == SmplType.REAL)
			return make(val <= arg.doubleValue());
		else
			throw new TypeSmplException(SmplType.REAL, arg.getType());
	}

	public SmplValue<?> ge(SmplValue<?> arg) throws SmplException {
		SmplType type = arg.getType();
		if(type == SmplType.INTEGER ||
			type == SmplType.CHARACTER ||
			type == SmplType.REAL)
			return make(val >= arg.doubleValue());
		else
			throw new TypeSmplException(SmplType.REAL, arg.getType());
	}

	public SmplValue<?> neq(SmplValue<?> arg) throws SmplException {
		SmplType type = arg.getType();
		if(type == SmplType.INTEGER ||
			type == SmplType.CHARACTER ||
			type == SmplType.REAL)
			return make(val != arg.doubleValue());
		else
			return make(true);
	}

	// return literal values

	public int intValue() throws TypeSmplException {
		return (int) val;
	}

	public double doubleValue() throws TypeSmplException {
		return val;
	}

	public char charValue() throws TypeSmplException {
		return (char) val;
	}

	@Override
	public String toString() {
		return String.valueOf(val);
	}
}