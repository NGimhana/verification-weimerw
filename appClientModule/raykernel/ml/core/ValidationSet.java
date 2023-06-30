package raykernel.ml.core;

import java.util.LinkedList;
import java.util.List;

import raykernel.ml.feature.Trainable;

public class ValidationSet
{

	List<Trainable> trainingData;
	List<Trainable> validationData;

	public void addTrainingBlocks(List<Trainable> moreData)
	{
		if (trainingData == null)
		{
			trainingData = new LinkedList<Trainable>();
		}

		trainingData.addAll(moreData);
	}

	public List<Trainable> getTrainingData()
	{
		return trainingData;
	}
	public List<Trainable> getValidationData()
	{
		return validationData;
	}
	public void setTrainingData(List<Trainable> trainingData)
	{
		this.trainingData = trainingData;
	}
	public void setValidationData(List<Trainable> validationData)
	{
		this.validationData = validationData;
	}

}
