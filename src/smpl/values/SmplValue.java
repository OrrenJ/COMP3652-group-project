/*
 * Adapted from FnPlot
 */

package smpl.values;

import smpl.sys.SmplException;

public abstract class SmplValue<T extends SmplValue<T>> {

	private static final long serialVersionID = 1L;

	// integers
	public static SmplInt make(Integer v) {
		return new SmplInt(v);
	}

	// real numbers
	public static SmplReal make(Double v) {
		return new SmplReal(v);
	}

	// booleans
	public static SmplBool make(boolean v) {
		return new SmplBool(v);
	}

	public static SmplChar make(char v){
		return new SmplChar(v);
	}

	public static SmplChar makeChar(String v){
		if(v.length()==4){
			return new SmplChar(Integer.parseInt(v, 16));
		} else if(v.equals("\\n")){
			return new SmplChar('\n');
		} else if(v.equals("\\t")){
			return new SmplChar('\t');
		} else if(v.equals("\\\\")){
			return new SmplChar('\\');
		} else {
			return new SmplChar(v.charAt(0));
		}
	}

	public static SmplString makeStr(String v){
		return new SmplString(v);
	}

	public static SmplString makeStrEscaped(String v){
		return new SmplString(SmplString.escape(v));
	}

	// return the type of a value
	public abstract SmplType getType();

	public boolean isInteger() {
        return getType() == SmplType.INTEGER;
    }

	// arithmetic operations

	public SmplValue<?> add(SmplValue<?> arg) throws SmplException {
		throw new TypeSmplException("Cannot add non-numeric values");
	}

	public SmplValue<?> sub(SmplValue<?> arg) throws SmplException {
		throw new TypeSmplException("Cannot subtract non-numeric values");
	}

	public SmplValue<?> mul(SmplValue<?> arg) throws SmplException {
		throw new TypeSmplException("Cannot multiply non-numeric values");
	}

	public SmplValue<?> div(SmplValue<?> arg) throws SmplException {
		throw new TypeSmplException("Cannot divide non-numeric values");
	}

	public SmplValue<?> mod(SmplValue<?> arg) throws SmplException {
		throw new TypeSmplException("Cannot perform modulo on non-numeric values");
	}

	public SmplValue<?> pow(SmplValue<?> arg) throws SmplException {
		throw new TypeSmplException("Cannot rise powers of non-numeric values");
	}


	// return literal values

	public int intValue() throws TypeSmplException {
		throw new TypeSmplException(SmplType.INTEGER, getType());
	}

	public double doubleValue() throws TypeSmplException {
		throw new TypeSmplException(SmplType.REAL, getType());
	}

	public boolean boolValue() throws TypeSmplException {
		throw new TypeSmplException(SmplType.BOOLEAN, getType());
	}

	public char charValue() throws TypeSmplException {
		throw new TypeSmplException(SmplType.CHARACTER, getType());
	}

	public String stringValue() {
		return toString();
	}
}