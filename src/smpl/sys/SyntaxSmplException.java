/*
 * Adapted from FnPlot
 */

package smpl.sys;

public class SyntaxSmplException extends SmplException {

    public SyntaxSmplException() {
        super("Syntax Error:");
    }
    
    public SyntaxSmplException(String msg) {
        super(msg);
    }    
    
    public SyntaxSmplException(String msg, Throwable t) {
        super(msg, t);
    }
}
