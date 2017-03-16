package smpl.syntax;

public class Option {

    Exp pred, con;

    public Option(Exp p, Exp c) {
	   pred = p;
	   con = c;
    }

    public Exp getPred() {
	   return pred;
    }

    public Exp getCon() {
	   return con;
    }

}