package com.globalcharge.ageverification.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utility {

	public static String convertStackTraceToString(Exception e) {
		StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        return exceptionAsString;
	}

	
	
	
}
