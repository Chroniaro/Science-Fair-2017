package control;

import java.util.Random;
import nNet.*;

public class NeuroEvolutionSimulator
{

	final static Random rng = new Random();

	public final static int POPULATION_SIZE = 15, ITERATIONS_OF_EVALUATION = 252, SIZE_OF_LIST = 5;

	NeuroNetEvaluator[] population;

	public NeuroEvolutionSimulator()
	{

		population = new NeuroNetEvaluator[POPULATION_SIZE]; 

		for (int n = 0; n < POPULATION_SIZE; n++)
			population[n] = new NeuroNetEvaluator(new HiddenLayerNetwork());
//			population[n] = new NeuroNetEvaluator(new ENodeNeuralNetwork());
	}

	public void evaluateGeneration(boolean show)
	{

		double[] startingList = getUnsortedList();

		Double[] sortedInputs = new Double[startingList.length];
		for (int i = 0; i < startingList.length; i++)
			sortedInputs[i] = startingList[i];
		sort(sortedInputs);

		for (int o = 0; o < POPULATION_SIZE; o++)
			population[o].evaluate(startingList);

		sort(population);

		if (show)
		{

			System.out.print("Starting Inputs: {");
			for (double d : startingList)
				System.out.print(d + ", ");
			System.out.println("}");

			System.out.print("Target Outputs: {");
			for (Double d : sortedInputs)
				System.out.print(d + ", ");
			System.out.println("}");

			System.out.print("Best Outputs: {");
			for (Double d : population[0].runningList)
				System.out.print(d + ", ");
			System.out.println("}");
			System.out.println(
					"Accuracy: " + population[0].lastLongestRun + " Iterations: " + population[0].lastIterations);
		
			System.out.print("Worst Outputs: {");
			for (Double d : population[population.length - 1].runningList)
				System.out.print(d + ", ");
			System.out.println("}");
			System.out.println("Accuracy: " + population[population.length - 1].lastLongestRun + " Iterations: "
					+ population[population.length - 1].lastIterations);

			System.out.print("Average Accuracy: ");
			double sum = 0;
			for(NeuroNetEvaluator net : population)
				sum += net.lastLongestRun;
			System.out.println(sum / population.length);
			
			System.out.println();
		}

//		int c = 0;
//		for (int i = 2; i >= 0; i--)
//			for (int n = i; n < 3; n++)
//				population[POPULATION_SIZE - (c += 1)] = population[i].mutate();
		
		for(int i = 0; i < 3; i++)
			population[POPULATION_SIZE - i - 1] = population[0].breed(population[1]);
		for(int i = 0; i < 2; i++)
			population[POPULATION_SIZE - i - 4] = population[0].breed(population[2]);
		population[POPULATION_SIZE - 6] = population[1].breed(population[2]);
	}

	double[] getUnsortedList()
	{
		double[] list = new double[SIZE_OF_LIST];

		for (int n = 0; n < SIZE_OF_LIST; n++)
		{

			list[n] = rng.nextInt(300);
		}

		return list;
	}

	public static <F extends Comparable<? super F>> void sort(F[] array)
	{

		if (array.length < 2)
			return;

		quicksort(array, 0, array.length - 1);
	}

	private static <F extends Comparable<? super F>> void quicksort(F[] array, int lowIndex, int highIndex)
	{

		if (lowIndex >= highIndex)
			return;

		F pivotValue = array[(lowIndex + highIndex) / 2];

		int bot = lowIndex;
		int top = highIndex;

		while (top > bot)
		{

			if (array[bot].compareTo(pivotValue) > 0)
			{

				swap(array, bot, top);
				top--;

			}
			else if (array[bot].compareTo(pivotValue) < 0 || array[top].compareTo(pivotValue) == 0)
				bot++;

			if (array[top].compareTo(pivotValue) < 0)
			{

				swap(array, bot, top);
				bot++;

			}
			else if (array[top].compareTo(pivotValue) > 0 || array[bot].compareTo(pivotValue) == 0)
				top--;
		}

		quicksort(array, lowIndex, bot - 1);
		quicksort(array, top + 1, highIndex);
	}

	private static <F> void swap(F[] array, int index1, int index2)
	{

		F n = array[index1];
		array[index1] = array[index2];
		array[index2] = n;
	}
}