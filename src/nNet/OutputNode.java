package nNet;

import java.awt.Point;
import java.util.Map;

public class OutputNode extends Node {
	
	public double getValue() {
		
		return value;
	}
	
	@Override
	public void outputValue() throws UnsupportedOperationException {

		throw new UnsupportedOperationException("This node doesn't output to other nodes.");
	}
	
	@Override
	public void setConnection(Node node, double value) throws UnsupportedOperationException {

		throw new UnsupportedOperationException("This node doesn't output to other nodes.");
	}
	
	@Override
	public Map<Node, Double> getConnections() throws UnsupportedOperationException {

		throw new UnsupportedOperationException("This node doesn't output to other nodes.");
	}
}
