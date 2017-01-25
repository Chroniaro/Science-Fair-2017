package nNet;

import java.awt.Point;
import java.util.List;
import java.util.Random;

public class HiddenLayerNetwork extends NeuroNet
{

	final static int SIZE_OF_HIDDEN_LAYER = 10;

	final static Random rng = new Random();

	private HiddenLayerNetwork(boolean build)
	{
		super();

		for (int x = 0; x < SIZE_OF_HIDDEN_LAYER; x++)
			bodyNodes.add(new Node());

		valuePassingNodes.addAll(inputNodes);
		valuePassingNodes.addAll(bodyNodes);
		valueAcceptingNodes.addAll(bodyNodes);
		valueAcceptingNodes.addAll(outputNodes);

		if(build)
		{
			for (InputNode b : inputNodes)
				for (Node c : bodyNodes)
					b.setConnection(c, rng.nextGaussian());
	
			for (Node b : bodyNodes)
				for (OutputNode c : outputNodes)
					b.setConnection(c, rng.nextGaussian());
		}
	}
	
	public HiddenLayerNetwork()
	{
		this(true);
	}

	public HiddenLayerNetwork mutate()
	{
		return mutate(this);
	}

	public static HiddenLayerNetwork mutate(HiddenLayerNetwork parent)
	{
		HiddenLayerNetwork child = new HiddenLayerNetwork(false); 

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
				child.valuePassingNodes.get(i).setConnection(tNode, val);
			}
		}

		return child;
	}

	public static HiddenLayerNetwork breed(HiddenLayerNetwork[] parents)
	{

		HiddenLayerNetwork child = new HiddenLayerNetwork(false); 

		for (int i = 0; i < parents[0].valuePassingNodes.size(); i++)
		{
			HiddenLayerNetwork parent = parents[rng.nextInt(parents.length)]; 
			Node pNode = parent.valuePassingNodes.get(i);
			
			for(Node oNode : pNode.getConnections().keySet())
			{
				double val = pNode.getConnections().get(oNode);
				val += 0.1 * rng.nextGaussian();
				if(rng.nextDouble() < 0.01)
					val = 0;
				Node tNode = child.valueAcceptingNodes.get(parent.valueAcceptingNodes.indexOf(oNode));
				child.valuePassingNodes.get(i).setConnection(tNode, val);
			}
		}

		return child;
	}
}
