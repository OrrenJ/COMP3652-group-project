/*
 * Adapted from FnPlot
 */

package smpl.values;

import smpl.sys.SmplException;
import static smpl.values.SmplValue.make;

public class SmplBool extends SmplValue<SmplBool> {

	boolean val;

	public SmplBool() {
		this(false);
	}

	public SmplBool(boolean v){
		val = v;
	}

	@Override
	public SmplType getType(){
		return SmplType.BOOLEAN;
	}

	// return literal values

	public boolean boolValue() throws TypeSmplException {
		return val;
	}

	@Override
	public String toString() {
		return val ? "true" : "false";
	}
}