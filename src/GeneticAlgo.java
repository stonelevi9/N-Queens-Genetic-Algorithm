
import java.util.Random;

public class GeneticAlgo {
		// This is my main method. It first creates a population p1, our target binary string, a generationCounter, an int called fit, and a boolean for running our while loop.
		// Next we initialize p1 by calling initializePop. Then we start our while loop that runs while run == true
		// Inside it first we have a for loop that iterates through our current population and finds the fitness of each individual in the population
		// We next check if our fit == 24, which means we have our target and if it does we print out the solution and end the while loop
		// If we did not find our target, we then create a new population by calling newPop and iterate our generationcount up
		public static void main (String args[]) {
			String target = "000100111101010110001011";
			population p1 = new population();
			p1 = initializePop(p1, 30, target);
			int generationcount = 1;
			int fit;
			boolean run = true;
			while (run == true) {
				for(int i = 0; i<p1.popSize; i++) {
					fit =fitnessTest(p1.indiv[i], target);
					if (fit == 24) {
						System.out.println("Correct solution found at index " + i + " of generation " + generationcount );
						int[] solution =stringParser(p1.indiv[i].values);
						char[][] board = createBoard(solution);
						toString(board);
						run = false;
					}
				}
				if (run == true) {
					p1 = newPop(p1, target);
					generationcount++;
				}
			}
		}
		// This is my fitnessTest method. It is responsible for determining the fitness of an individual by going through each charAt an index i
		// in both our target string and our individual's values string and iterating the fitness up by one if the chars match.
		public static int fitnessTest(individual individual, String target) {
			int fitness = 0;
			for(int i = 0; i<individual.values.length(); i++) {
				if(individual.values.charAt(i) == target.charAt(i)) {
					fitness++;
				}
			}
			return fitness;
		}
		// This is my initializePop method. It is responsible for creating our first initial population
		// It does this by creating a random binary string of the same length as our target string and
		// then creating an individual in our population with this random binary string until our population is 
		// up to the size we want and then lastly it return the population.
		public static population initializePop(population p1, int size, String target) {
			Random rand = new Random();
			for (int i =0; i < size; i++) {
				String values = "";
				for(int j = 0; j<target.length(); j++) {
					int next = rand.nextInt(2);
					values += next;
				}
				p1.indiv[i] = new individual(values, 0, false);
				int fit = fitnessTest(p1.indiv[i], target);
				p1.indiv[i].fitness = fit;
			}
			p1.popSize = size;
			p1.target = target;
			return p1;
		}
		// This is my newPop method and it is responsible for creating a new generation for our population
		// It does this by first finding the 3 best candidates in our current population and adds them to our new generation
		// Next we initialize the rest of our new generation by getting two of our parents and applying crossover to them to create an offspring
		// Next we take our next generation and randomly apply mutations to them at around a 1% rate
		// Lastly, we return our new Population
		public static population newPop(population p1, String target) {
			population p2 = new population();
			Random rand = new Random();
			p2.popSize=0;
			p2.target = target;
			int currentIndex = 0;
			int maxFitness = 0;
			for(int i =0; i<p1.popSize/10; i++) {
				for (int j = 0; j<p1.popSize; j++ ) {
					if (p1.indiv[j].fitness >= maxFitness && p1.indiv[j].moved == false) {
						currentIndex = j;
						maxFitness = p1.indiv[j].fitness;
					}
				}
				p2.indiv[i] = p1.indiv[currentIndex];
				p2.indiv[i].fitness = maxFitness;
				p2.popSize++;
				p1.indiv[currentIndex].moved = true;
				maxFitness = 0;
				currentIndex =0;
				
			}
			for(int i =p2.popSize; i<p2.popSize*10; i++) {
				int i1 = rand.nextInt(p2.popSize);
				int i2 = rand.nextInt(p2.popSize);
				if(!p2.indiv[i1].values.isEmpty()&& !p2.indiv[i2].values.isEmpty()) {
				p2.indiv[i] = crossOver(p2.indiv[i1],p2.indiv[i2], target);
				}
				else {
					i--;
				}
			}
			p2.popSize = p2.popSize*10;
			int mutationrate = 1;
			for (int i =0; i<p2.popSize; i++) {
			int mutation = rand.nextInt(100)+1;
			if (mutation == mutationrate) {
				p2.indiv[i].values= mutation(p2.indiv[i]);
				
			}
			}
			for (int i =0; i<p2.popSize; i++) {
				p2.indiv[i].moved = false;
			}
			return p2;
		}
		// This is our crossOver method and it is responsible for creating an offspring from two parent individuals
		// It starts by creating some variables such as strings to hold cross sections of our parents
		// Next we run a while loop that first gets two random index and assigns the bigger one to endIndex and the smaller to beginIndex
		// Next we iterate through a for loop that takes value between our beginIndex and endIndex and adds them to a string
		// Next we check to make sure our endIndex is not our last value in our values string and if it is not, we add everything after endIndex to 
		// a new string. We then check to see that beginIndex != 0 or our first value in values string and if it is not we add everything before beginIndex
		// to a new string. Lastly, we combine these strings in order in our values string and create a newIndividual with these values and return it
		public static individual crossOver(individual i1, individual i2, String target) {
			individual newIndiv = new individual();
			Random rand = new Random();
			String values = "";
			String p1 = "";
			String p2past = "";
			String p2first = "";
			boolean runner = false;
			int beginIndex=0;
			int endIndex=0;
			while (runner != true) {
			beginIndex = rand.nextInt(Math.abs(i1.values.length()));
			endIndex = rand.nextInt(Math.abs(i1.values.length()));
			int temp;
			if (endIndex < beginIndex) {
				temp = endIndex;
				endIndex = beginIndex;
				beginIndex = temp;
				runner = true;
			}
			else if (endIndex == beginIndex) {
				runner = false;
			}
			else {
				runner = true;
			}
			}
			for (int i = beginIndex; i<endIndex; i++) {
				p1+= i1.values.charAt(i);
			}
			if (endIndex < i2.values.length()) {
			for (int i = endIndex; i<i2.values.length(); i++ ) {
				p2past += i2.values.charAt(i);
			}
			
		}
			if (beginIndex != 0) {
				for(int i = 0; i<beginIndex; i++) {
					p2first+= i2.values.charAt(i);
					
				}
			}
			values +=p2first;
			values +=p1;
			values+= p2past;
			newIndiv.values = values;
			newIndiv.fitness = fitnessTest(newIndiv, target);
			return newIndiv;
		}
		// This method is our stringParser method and it is responsible for parsing our binary string and turning it into an array of integers
		// where each integer corresponds to the column position on the board of a queen and the index corresponds to the row
		// Lastly, it returns this array
		public static int[] stringParser(String s1) {
			int endEx = 3;
			int startIn = 0;
			int [] parsed = new int[s1.length()/3];
			for(int i = 0; i<8; i++) {
				parsed[i] = Integer.parseInt(s1.substring(startIn, endEx), 2);
				startIn = endEx;
				endEx +=3;
			}
			return parsed;
		}
		// This method is our createBoard method and it is responsible for creating a 2D char array that represents our board based on the
		// int array that we get from calling stringparser.
		public static char[][] createBoard(int[] parsed){
			char[][] board = new char [parsed.length][parsed.length];
			for (int i = 0; i < parsed.length; i ++) {
				board [i][parsed[i]] = 'Q';
			}
			return board;
		}
		
		// This method is my toString method and it is responsible for printing out our board once we find it so that the user can see the solution
		public static void toString(char[][] board) {
			System.out.println("");
			for(int i =0; i< board.length; i++) {
				System.out.println("_________________");
				System.out.print("|");
				for(int j = 0; j<board.length; j++) {
					System.out.print(board[i][j] + "|");
				}
				System.out.println("");
			}
			System.out.println("");
		}
		

		// This method is my mutation method and it is responsible for handling mutations on our generations when it is called
		// It works by first finding a random number of values in our individual's values string and flipping them to the opposite number
		// and then returning the individual back as a mutated individual
		public static String mutation(individual i1) {
			
			Random rand = new Random();
			boolean runner = true;
			int num = 0;
			while (runner == true) {
			if (!i1.values.isEmpty()) {
			num = rand.nextInt(Math.abs(i1.values.length()));
			runner = false;
			}
			else {
				
			}
			}
			String newvalues ="";
			for (int i =0; i<num; i++) {
				int bit = rand.nextInt(i1.values.length());
				if (i1.values.charAt(bit) == '0') {
					if(bit ==0) {
						newvalues = "1" + i1.values.substring(bit+1);
					}
					else if (bit == i1.values.length()) {
						newvalues = i1.values.substring(0, i1.values.length()-1) + "1";
					}
						else{
					newvalues = i1.values.substring(0, bit) + "1" + i1.values.substring(bit+1);
					
					}
				}
				else {
					if(bit ==0) {
						
						newvalues = "0" + i1.values.substring(bit+1);
					}
					else if (bit == i1.values.length()) {
						newvalues = i1.values.substring(0, i1.values.length()-1) + "0";
					}
						else{
					newvalues = i1.values.substring(0, bit) + "0" + i1.values.substring(bit+1);
					
					}
		
				}
			}
			return newvalues;
		}
		
		}
	


