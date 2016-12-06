/*
 * Adapted from FnPlot
 */

package smpl.values;

public enum SmplType {
	INTEGER("int"),
	REAL("real"),
	BOOLEAN("bool"),
	CHARACTER("char"),
	STRING("string"),
	PAIR("pair"),
	FUNCTION("user function");

	private final String docString;

	SmplType(String docString){
		this.docString = docString;
	}
}