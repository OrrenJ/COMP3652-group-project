/*
 * Adapted from FnPlot
 */

package smpl.sys;

import smpl.semantics.Environment;
import smpl.semantics.Evaluator;
import smpl.syntax.SmplProgram;
import smpl.syntax.SmplLexer;
import smpl.syntax.SmplParser;

import java.io.*;

public class Repl {

    public static final String PROMPT = "SMPL>";

    public static void main(String args[]) {
    	if (args.length == 0){
			repl(new Environment());
    	} else {
    		repl(new Environment(), args[0]);
    	}
    }

    public static void repl(Environment env) {
		InputStreamReader reader = new InputStreamReader(System.in);
		while (true) {
		    parseEvalShow(reader, env);
		}
    }

    public static void repl(Environment env, String filename){
    	try {
    		File file = new File(filename);
    		FileReader reader = new FileReader(file);
    		parseEvalShow(reader, env);
    	} catch(FileNotFoundException e) {
    		System.out.println("Could not find " + filename);
    	}
    }

    public static void parseEvalShow(Reader reader, Environment env) {
		SmplParser parser;
		SmplProgram program = null;
		Evaluator interp = new Evaluator();
		System.out.print(PROMPT);
		try {
		    parser = new SmplParser(new SmplLexer(reader));
		    program = (SmplProgram) parser.parse().value;
		} catch (Exception e) {
		    System.out.println("Parse Error: " + e.getMessage());
		    // e.printStackTrace();
		}

		if (program != null)
		    try {
		    	Object result;
				result = program.visit(interp, env);
				System.out.println("\nResult: " + result);
		    } catch (SmplException e) {
				System.out.println(e.getMessage());
		    }
    }

}
