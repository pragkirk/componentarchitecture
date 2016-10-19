package com.kirkk.hello;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.extensiblejava.calculator.math.*;
import com.extensiblejava.calculator.operation.*;
import java.util.Map;
import java.util.HashMap;
import static com.kirkk.hello.JsonUtil.*;
 
public class MathCalc {
    public static void main(String[] args) {
    	port(getHerokuAssignedPort());
        get("/math", "application/json", (req, res) -> {
        	String op1 = req.queryParams("op1");
        	String op2 = req.queryParams("op2");
        	String opType = req.queryParams("opType");  
        	Values v = new Values() {
        		public Map<String, Integer> getValues() {
        			Map<String, Integer> vals = new HashMap<String, Integer>();
        			if (opType.equals("0")) {
						vals.put("type", com.extensiblejava.calculator.math.Math.ADD);
					} else if (opType.equals("1")) {
						vals.put("type", com.extensiblejava.calculator.math.Math.SUBTRACT);
					} else if (opType.equals("2")) {
						vals.put("type", com.extensiblejava.calculator.math.Math.MULTIPLY);
					} else {
						vals.put("type", com.extensiblejava.calculator.math.Math.DIVIDE);
					}
					Integer operand1 = new Integer(op1);
					Integer operand2 = new Integer(op2);
        			vals.put("operand1",operand1);
					vals.put("operand2",operand2);
					
					return vals;
				}
				
				public String getDescriptor() { return "MathUI"; }
        	};	
        	com.extensiblejava.calculator.math.Math math = new com.extensiblejava.calculator.math.Math();
        	math.setValues(v);
        	return new Value().setValue(math.execute());
        }, json());
    }
    
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}