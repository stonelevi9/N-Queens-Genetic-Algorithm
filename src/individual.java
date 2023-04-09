// This is my individual class that represents an individual in my population
// We give an individual some variables such as values (its current binary string of values)
// fitness (it's current fitness score) and moved (a boolean that says if it has been moved to a new generation or not)
// Lastly, we have an overloaded constructor that allows use to initialize all of these variables when creating a new individual
public class individual {
	String values;
	int fitness;
	boolean moved;
	public individual() {
		
	}
	public individual(String values, int fitness, boolean moved) {
		this.values = values;
		this.moved = moved;
		this.fitness = fitness;
	}

}
