package nNet;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import control.NeuroEvolutionSimulator;

public abstract class NeuroNet
{
	public static final int SIZE_OF_MEMORY = 0;
	static Random rng = new Random();
	ArrayList<InputNode> inputNodes;
	ArrayList<Node> bodyNodes;
	ArrayList<OutputNode> outputNodes;
	List<OutputNode> memoryOutputNodes;
	List<OutputNode> valueOutputNodes;
	List<InputNode> memoryInputNodes;
	List<InputNode> valueInputNodes;
	List<Node> valuePassingNodes;
	List<Node> valueAcceptingNodes;
	InputNode countNode;
	InputNode biasNode;
	InputNode orderNode;
	OutputNode switchNode;

	public NeuroNet()
	{
		inputNodes = new ArrayList<InputNode>();
		bodyNodes = new ArrayList<Node>();
		outputNodes = new ArrayList<OutputNode>();
		memoryOutputNodes = new ArrayList<OutputNode>();
		valueOutputNodes = new ArrayList<OutputNode>();
		memoryInputNodes = new ArrayList<InputNode>();
		valueInputNodes = new ArrayList<InputNode>();
		valuePassingNodes = new ArrayList<Node>();
		valueAcceptingNodes = new ArrayList<Node>();

		for (int i = 0; i < NeuroEvolutionSimulator.SIZE_OF_LIST; i++)
		{
			InputNode inpNode = new InputNode();
			inputNodes.add(inpNode);
			valueInputNodes.add(inpNode);
		}
		
		orderNode = new InputNode();
		inputNodes.add(orderNode);
		
//		countNode = new InputNode(1 + NeuroEvolutionSimulator.SIZE_OF_LIST + SIZE_OF_MEMORY);
//		inputNodes.add(countNode);

		biasNode = new InputNode();
		inputNodes.add(biasNode);

		for (int i = 0; i < NeuroEvolutionSimulator.SIZE_OF_LIST; i++)
		{
			OutputNode outpNode = new OutputNode();
			outputNodes.add(outpNode);
			valueOutputNodes.add(outpNode);
		}
		
		switchNode = new OutputNode();
		outputNodes.add(switchNode);
		
		for (int i = 0; i < SIZE_OF_MEMORY; i++)
		{
			InputNode inpNode = new InputNode();
			OutputNode outpNode = new OutputNode();

			inputNodes.add(inpNode);
			memoryInputNodes.add(inpNode);
			outputNodes.add(outpNode);
			memoryOutputNodes.add(outpNode);
		}
	}
}