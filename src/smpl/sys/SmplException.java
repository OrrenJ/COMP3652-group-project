/*
 * Adapted from FnPlot
 */

package smpl.sys;

public class SmplException extends Exception {

    private static final long serialVersionUID = 1L;

    public SmplException() {
        super("SMPL Error");
    }
    
    public SmplException(String msg) {
        super(msg);
    }
    
    public SmplException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
