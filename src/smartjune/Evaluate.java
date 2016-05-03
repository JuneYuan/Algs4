package smartjune;

import java.util.Scanner;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// 算术表达式求值
// 条件：表达式严格加括号，数字与符号之间都以空格分隔
public class Evaluate {

	public static void main(String[] args) {

		StackI<String> ops = new LinkedListStack<>();
		StackI<Double> vals = new LinkedListStack<>();
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			if (s.equals("("))	;
			else if (s.equals("+"))		ops.push(s);
			else if (s.equals("-"))		ops.push(s);
			else if (s.equals("*"))		ops.push(s);
			else if (s.equals("/"))		ops.push(s);
			else if (s.equals("sqrt"))	ops.push(s);
			else if (s.equals(")")) {
				String op = ops.pop();
				double v = vals.pop();
				if (op.equals("+"))			v = vals.pop() + v;
				else if (op.equals("-"))	v = vals.pop() - v;
				else if (op.equals("*"))	v = vals.pop() * v;
				else if (op.equals("/"))	v = vals.pop() / v;
				else if (op.equals("sqrt"))	v = Math.sqrt(v);
				vals.push(v);				
			}
			else	vals.push(Double.parseDouble(s));
				
		}
		
		StdOut.println(vals.pop());
	}
}
