package com.kirkk.hello;

import com.extensiblejava.calculator.operation.Operation;
import com.extensiblejava.calculator.operation.Values;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MathProxy implements Operation {
	public final static Integer ADD = new Integer(0);
	public final static Integer SUBTRACT = new Integer(1);
	public final static Integer MULTIPLY = new Integer(2);
	public final static Integer DIVIDE = new Integer(3);
	private int val1;
	private int val2;
	private Integer type;

	public void setValues(Values values) { 
		Map<String,Integer> map = values.getValues();
		this.val1 = map.get("operand1").intValue();
		this.val2 = map.get("operand2").intValue();
		this.type = map.get("type");
	}
	
	public String execute() throws RuntimeException {
		try {
			//URL url = new URL("http://localhost:4567/audit?amount=" + auditable.getAmount().setScale(2, BigDecimal.ROUND_UP).toString());
			URL url = new URL("http://localhost:4567/math?op1=" + val1 + "&op2=" + val2 + "&opType=" + type);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output = br.readLine();
			int begLoc = output.indexOf(":");
			int endLoc = output.indexOf("}");
			String newString = output.substring(begLoc+2,endLoc-1);
			
			//System.out.println(output);
			//System.out.println("---- " + newString + "----");

			conn.disconnect();
			
			return newString;

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public String getDescriptor() { return "Math"; }
}