/*
 * Adapted from FnPlot
 */

package smpl.sys;

public class RuntimeSmplException extends SmplException {

    public RuntimeSmplException() {
        super("Smpl Runtime Error");
    }

    public RuntimeSmplException(String msg) {
        super(msg);
    }

    public RuntimeSmplException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
