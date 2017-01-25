package nNet;

import java.awt.Point;

public class InputNode extends Node{

	public void setValue(double value) {
		
		this.value = value;
	}
	
	@Override
	void resetValue() throws UnsupportedOperationException{

		throw new UnsupportedOperationException("The value of this node cannot be reset.");
	}
	
	@Override
	void addValue(double amount) throws UnsupportedOperationException {

		throw new UnsupportedOperationException("This node does not accept input from other nodes.");
	}
}
