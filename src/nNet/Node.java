package nNet;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Node
{

	HashMap<Node, Double> outputs;
	double value;

	public Node()
	{
		outputs = new HashMap<Node, Double>();
		value = 0;
	}

	public void setConnection(Node node, double value)
	{
		outputs.put(node, value);
	}

	void resetValue()
	{
		value = 0;
	}

	void addValue(double amount)
	{
		double n = logistical(amount);
		if(n != n)
			throw new Error("NaN val node");
		else
			value += n;
	}

	public void outputValue()
	{
		for (Node outNode : outputs.keySet())
		{
			outNode.addValue(value * outputs.get(outNode));
		}
	}

	public Map<Node, Double> getConnections()
	{
		return outputs;
	}

	public void clearConnections()
	{
		outputs = new HashMap<Node, Double>();
	}
	
	public static double logistical(double val)
	{ 
		return 2 / (1 + Math.pow(Math.E, -val)) - 1;
	}
}