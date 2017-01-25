package nNet;

import control.NeuroEvolutionSimulator;

public class NeuroNetEvaluator implements Comparable<NeuroNetEvaluator>
{
	public int lastLongestRun;
	public int lastIterations;
	NeuroNet net;
	public double[] runningList;

	public NeuroNetEvaluator(NeuroNet network)
	{
		this.net = network;
	}

	public void evaluate(double[] startingInputs)
	{
		for (InputNode n : net.memoryInputNodes)
		{
			n.setValue(0);
		}

		runningList = new double[startingInputs.length];
		for (int i = 0; i < startingInputs.length; i++)
			runningList[i] = startingInputs[i];

		lastIterations = 0;

		int ind1 = 0;
		int ind2 = 1;

//		System.out.println("xxx");
		
		int correctSwitches = 0;
		
		for (int i = 0; i < NeuroEvolutionSimulator.ITERATIONS_OF_EVALUATION; i++)
		{
			for(InputNode n : net.valueInputNodes)
				n.setValue(0);
			net.valueInputNodes.get(ind1).setValue(1);
			net.valueInputNodes.get(ind2).setValue(1);
			final double val = Math.signum(ind1 - ind2) * Math.signum(runningList[ind1] - runningList[ind2]);
			net.orderNode.setValue(val);
			net.biasNode.setValue(1);
//			System.out.println("[" + ind1 + "]" + runningList[ind1] + " [" + ind2 + "]" + runningList[ind2] + " val=" + val);

			for (int n = 0; n < net.memoryInputNodes.size(); n++)
				net.memoryInputNodes.get(n).setValue(net.memoryOutputNodes.get(n).getValue());

			for (Node node : net.valuePassingNodes)
				node.outputValue();

			if (net.switchNode.getValue() > 0)
			{
				if(val < 0)
					correctSwitches += 1;
				else
					correctSwitches -= 1;
				//System.out.println("Switching [" + ind1 + "]" + runningList[ind1] + " with [" + ind2 + "]" + runningList[ind2]);
				double tmp = runningList[ind1];
				runningList[ind1] = runningList[ind2];
				runningList[ind2] = tmp;
			}
			
			for (int n = 1; n < runningList.length; n++)
				if (runningList[n] < runningList[n - 1])
					break;
				else if(n == runningList.length - 1)
				{
					lastLongestRun = 1000;
					return;
				}

			lastIterations++;

			int max1 = 0;
			int max2 = 1;
			for(int n = 0; n < net.valueOutputNodes.size(); n++)
			{
				if(net.valueOutputNodes.get(n).getValue() > net.outputNodes.get(max1).getValue())
					max1 = n;
				else if(net.valueOutputNodes.get(n).getValue() > net.outputNodes.get(max2).getValue())
					max2 = n;
			}
			
			ind1 = max1;
			ind2 = max2;
		}
		
		int current = 0;
		int biggest = 1;
		int runs = 1;
		for(int i = 0; i < runningList.length - 1;i++)
		{
			if(runningList[i] > runningList[i + 1])
			{
				if(current > biggest)
					biggest = current;
				
				current = 0;
				runs ++;
			}
			else
				current ++;
		}
		
		lastLongestRun = 50 * biggest - 35 * runs + correctSwitches;
	}

	@Override
	public int compareTo(NeuroNetEvaluator o)
	{
//		return (int)Math.signum(o.getOutputNodes().get(0).getValue() - getOutputNodes().get(0).getValue());
		if (lastIterations != o.lastIterations)
			return lastIterations - o.lastIterations;

		return (int) Math.signum(o.lastLongestRun - lastLongestRun);
	}

	public NeuroNetEvaluator mutate()
	{
		if (net instanceof HiddenLayerNetwork)
			return new NeuroNetEvaluator(((HiddenLayerNetwork) this.net).mutate());

		else if(net instanceof ENodeNeuralNetwork)
			return new NeuroNetEvaluator(((ENodeNeuralNetwork) this.net).mutate());
		
		else
			return null;
	}

	public NeuroNetEvaluator breed(NeuroNetEvaluator otherParent)
	{
		if (net instanceof HiddenLayerNetwork && otherParent.net instanceof HiddenLayerNetwork)
			return new NeuroNetEvaluator(HiddenLayerNetwork.breed(new HiddenLayerNetwork[] {(HiddenLayerNetwork)this.net, (HiddenLayerNetwork)otherParent.net}));

		else
			return null;
	}
	
	public java.util.List<OutputNode> getOutputNodes()
	{
		return net.outputNodes;
	}

	@Override
	public String toString()
	{
		return lastIterations + ":" + lastLongestRun;
	}
}