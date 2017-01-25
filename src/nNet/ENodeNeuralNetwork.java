package nNet;

import java.util.Random;

public class ENodeNeuralNetwork extends NeuroNet
{
	final static int SIZE_OF_HIDDEN_REGION = 10;

	final static Random rng = new Random();

	private ENodeNeuralNetwork(boolean build)
	{
		super();
		
		if(build)
		{
			final int nodes = rng.nextInt(SIZE_OF_HIDDEN_REGION);
			for(int i = 0; i < nodes; i++)
				bodyNodes.add(new Node());
			
			for(Node p : valuePassingNodes)
				for(Node r : valueAcceptingNodes)
					if(rng.nextDouble() < 0.1)
						p.setConnection(r, rng.nextGaussian());
		}
		
		valuePassingNodes.addAll(inputNodes);
		valuePassingNodes.addAll(bodyNodes);
		valueAcceptingNodes.addAll(bodyNodes);
		valueAcceptingNodes.addAll(outputNodes);
	}

	public ENodeNeuralNetwork()
	{
		this(true);
	}
	
	public ENodeNeuralNetwork mutate()
	{
		return mutate(this);
	}

	public static ENodeNeuralNetwork mutate(ENodeNeuralNetwork parent)
	{
		ENodeNeuralNetwork child = new ENodeNeuralNetwork(false); 
		for(Node n : parent.bodyNodes)
			child.bodyNodes.add(new Node()); 

		for (int i = 0; i < parent.valuePassingNodes.size(); i++)
		{
			Node pNode = parent.valuePassingNodes.get(i);
			
			for(Node oNode : pNode.getConnections().keySet())
			{
				double val = pNode.getConnections().get(oNode);
				val += 0.1 * rng.nextGaussian();
				if(rng.nextDouble() < 0.01)
					val = 0;
				Node tNode = child.valueAcceptingNodes.get(parent.valueAcceptingNodes.indexOf(oNode));
				if(rng.nextDouble() < .055)
				{
					Node nNode = new Node();
					child.bodyNodes.add(nNode);
					child.valuePassingNodes.get(i).setConnection(nNode, val);
					nNode.setConnection(tNode, 1 + 0.2 * rng.nextGaussian());
				} 
				else
					child.valuePassingNodes.get(i).setConnection(tNode, val);
			}
		}
		
		if(rng.nextDouble() < 0.1)
			child.bodyNodes.remove(rng.nextInt(child.bodyNodes.size()));

		return child;
	}
}
