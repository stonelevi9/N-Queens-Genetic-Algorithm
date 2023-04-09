// This is my population class that represents a current generation's population
// We give a population some variables such as popSize (current size of the population)
// indiv (and array of it's individuals) and target (a binary string of the solution it is looking for)
// Lastly, we have an overloaded constructor that allows use to initialize all of these variables when creating a new population

public class population {
	int popSize;
	individual[] indiv = new individual[30];
	String target;
	public population() {
		
	}
	public population(int popSize, individual[] indiv, String target) {
		this.popSize = popSize;
		this.indiv = indiv;
		this.target = target;
	}
	
	
	

}
