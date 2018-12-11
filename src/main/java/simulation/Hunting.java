package main.java.simulation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.json.JSONObject;

import main.java.neural.Connection;
import main.java.neural.InputNeuron;
import main.java.neural.Layer;
import main.java.neural.Net;
import main.java.neural.Neuron;
import main.java.people.Group;
import main.java.people.Party;
import main.java.people.Person;
import main.java.people.StarvationException;
import main.java.resource.Resource;

/**
 * This is the main simulation class of the application.
 * @author Kyle McVay
 */
public class Hunting {
	private static int NUM_PEOPLE;
	private static int PARTY_SIZE;
	private static int DAYS_TO_RUN;// Number of days to simulate
	private static int HOURS_TO_RUN;// Number of hours to allow for hunting/gathering each day
	private static int NUM_PARTIES;
	private static int NUM_GENERATIONS;
	private static int NUM_INDIVIDUALS;
	private static boolean MAXIMIZE;
	private static List<Resource> resources;
	private static Group group;
	private static Group bestGroup;
	private static Map<Double, Resource> chancesAnimal;
	private static Map<Double, Resource> chancesPlant;
	private static double totalCalsAnimals;
	private static double totalCalsPlants;

	/**
	 * Main method of application. Reads in configuration file and runs simulation.
	 * @param args - Arguments. args[0] should be name of configuration file.
	 * @throws IOException Thrown if file is read incorrectly.
	 */
	public static void main(String[] args) throws IOException {
		String fileName = args[0];
		File jsonFile = new File(fileName);
		String fileContent = new String(Files.readAllBytes(jsonFile.toPath()));
		JSONObject json = new JSONObject(fileContent);
		
		NUM_PEOPLE = json.getInt("numPeople");
		PARTY_SIZE = json.getInt("partySize");
		DAYS_TO_RUN = json.getInt("daysToRun");
		HOURS_TO_RUN = json.getInt("hoursToRun");
		NUM_PARTIES = json.getInt("numParties");
		NUM_GENERATIONS = json.getInt("numGenerations");
		NUM_INDIVIDUALS = json.getInt("numIndividuals");
		MAXIMIZE = json.getBoolean("maximize");
		Person.MAX_WEIGHT = json.getInt("maxWeight");
		Person.CALS_BURNED_AT_REST = json.getInt("calsBurnedAtRest");
		Person.CALS_BURNED_FORAGING = json.getInt("calsBurnedForaging");
		Person.MEAT_RATIO = json.getDouble("meatRatio");
		
		List<Long> animalArray = new ArrayList<Long>();
		List<Long> plantArray = new ArrayList<Long>();
		
		resources = new ArrayList<Resource>();
		for(Object r: json.getJSONArray("resources")) {
			JSONObject resource = (JSONObject)r;
			resources.add(new Resource(
				resource.getInt("calories"),
				resource.getBoolean("animal"),
				resource.getInt("gatherTime"),
				resource.getInt("processTime"),
				resource.getInt("encounterRate"),
				resource.getDouble("weight"),
				resource.getString("name"),
				resource.getDouble("successRate")
			));
		}
		
		for(Resource resource: resources) {
			if(resource.isAnimal()) {
				animalArray.add(Long.valueOf(resource.getEncounterRate()));
			} else {
				plantArray.add(Long.valueOf(resource.getEncounterRate()));
			}
		}
		
		long commonDenominatorAnimals = lcm(animalArray);
		long commonDenominatorPlants = lcm(plantArray);
		
		chancesAnimal = new HashMap<Double, Resource>();
		chancesPlant = new HashMap<Double, Resource>();
		
		for(Resource resource: resources) {
			if(resource.isAnimal()) {
				chancesAnimal.put(((commonDenominatorAnimals / 1.0) / resource.getEncounterRate()) / commonDenominatorAnimals, resource);
			} else {
				chancesPlant.put(((commonDenominatorPlants / 1.0) / resource.getEncounterRate()) / commonDenominatorPlants, resource);
			}
		}
		
		Net net = generateNet();
		Net bestNet = net;
		double bestScore = 0;
		if(!MAXIMIZE) {
			bestScore = Double.MAX_VALUE;
		}
		bestGroup = new Group();
		while(bestScore == 0 || bestScore == Double.MAX_VALUE) {
			double score = runSimulation(net);
			if(MAXIMIZE) {
				if(score > bestScore) {
					bestNet = net;
					bestScore = score;
					bestGroup = group;
				}
			} else {
				if(score != 0 && score < bestScore) {
					bestNet = net;
					bestScore = score;
					bestGroup = group;
				}
			}
			net = generateNet();
		}
		
		System.out.println("Found intial net.\nScore: " + bestScore + "\nDays Survived: " + bestGroup.getDaysSurvived() + "\nWeightings:");
		for(Neuron neuron: bestNet.getInputLayer().getNeurons()) {
			System.out.println(neuron.getId() + ": " + neuron.getOutputConnections().get(0).getWeight());
		}
		System.out.println("Initial Net hunting values:");
		bestGroup.getTotalGathered().forEach(new BiConsumer<Resource, Integer>(){
			/**
			 * Prints the name of each resource and the number of individuals of each
			 */
			@Override
			public void accept(Resource t, Integer u) {
				System.out.println(t.getName() + ": " + u);
			}
		});
		System.out.println();
		
		for(int j = 0; j < NUM_GENERATIONS; j++) {
			System.out.print("\rCompleted " + j + "/"+ NUM_GENERATIONS + " Generations.");
			List<Net> nets = new ArrayList<Net>();
			for(int i = 0; i < NUM_INDIVIDUALS; i++) {
				nets.add(generateMutation(bestNet));
			}
			for(int i = 0; i < NUM_INDIVIDUALS; i++) {
				nets.add(generateNet());
			}
			
			for(Net testNet: nets) {
				double score = runSimulation(testNet);
				if(MAXIMIZE) {
					if(score > bestScore) {
						bestNet = net;
						bestScore = score;
						bestGroup = group;
					}
				} else {
					if(score != 0 && score < bestScore) {
						bestNet = net;
						bestScore = score;
						bestGroup = group;
					}
				}
			}
			
			nets = null;
		}
		System.out.println();
		System.out.println("Best Score: " + bestScore);
		System.out.println("Days Survived: " + bestGroup.getDaysSurvived());
		System.out.println("Best Net hunting values:");
		bestGroup.getTotalGathered().forEach(new BiConsumer<Resource, Integer>(){
			/**
			 * Adds the calories of each resource to the total calories gathered.
			 */
			@Override
			public void accept(Resource t, Integer u) {
				if(t.isAnimal()) {
					totalCalsAnimals += u * t.getCalories();
				} else {
					totalCalsPlants += u * t.getCalories();
				}
			}
		});
		bestGroup.getTotalGathered().forEach(new BiConsumer<Resource, Integer>(){
			/**
			 * Prints the name of each resource, the number of individuals of each, and the percentage of total kcals each resource represents.
			 */
			@Override
			public void accept(Resource t, Integer u) {
				if(t.isAnimal()) {
					System.out.println(t.getName() + ": " + u + ", Percent of total foraged kcals: " + ((t.getCalories() * u) / totalCalsAnimals) * 100 + "%");
				} else {
					System.out.println(t.getName() + ": " + u + ", Percent of total foraged kcals: " + ((t.getCalories() * u) / totalCalsPlants) * 100 + "%");
				}
			}
		});
	}
	
	/**
	 * Generates a random neural network with the correct input neurons and random weightings.
	 * @return Generated neural network
	 */
	private static Net generateNet() {
		Layer inputLayer = new Layer("input");
		Layer outputLayer = new Layer("output");
		
		Net neuralNet = new Net("network", inputLayer, outputLayer);
		
		inputLayer.getNeurons().add(new InputNeuron(0, "party_size"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_weight"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_cals"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_time"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_resource_calories"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_resource_gatherTime"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_resource_processTime"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_resource_weight"));
		inputLayer.getNeurons().add(new InputNeuron(0, "current_resource_success"));
		
		outputLayer.getNeurons().add(new Neuron("choice"));
		
		inputLayer.getNeuron("party_size").getOutputConnections().add(new Connection(inputLayer.getNeuron("party_size"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_weight").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_weight"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_cals").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_cals"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_time").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_time"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_calories").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_calories"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_gatherTime").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_gatherTime"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_processTime").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_processTime"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_weight").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_weight"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_success").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_success"), outputLayer.getNeuron("choice")));
		
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("party_size").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_weight").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_cals").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_time").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_calories").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_gatherTime").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_processTime").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_weight").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_success").getOutputConnections().get(0));
		
		return neuralNet;
	}
	
	/**
	 * Runs the simulation using the input neural net.
	 * @param decisionNet - Net to use.
	 * @return Score of simulation.
	 */
	private static double runSimulation(Net decisionNet) {
		group = new Group();
		
		for(int i = 0; i < NUM_PEOPLE; i++) {
			group.getMembers().add(new Person());
		}
		
		boolean hunting = true;
		for(int i = 0; i < NUM_PARTIES; i++) {
			group.allocateParty(PARTY_SIZE, hunting);
			hunting = !hunting;
		}
		int i = 0;
		for(; i < DAYS_TO_RUN; i++) {
			
			for(int j = 0; j < HOURS_TO_RUN * 60; j++) {
				
				for(Party party: group.getParties()) {
					if(party.isActive()) {
						party.advanceMinutesForaged();
						if(party.isGathering()) {
							party.setGatherTimeRemaining(party.getGatherTimeRemaining() - 1);
							if(party.getGatherTimeRemaining() == 0) {
								party.setGathering(false);
							}
						} else {
							Resource resource = generateResource(party);
							if(resource != null) {
								//if(resource.getName() != "Super Plant") System.out.println("Party found resource: " + resource.getName());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("party_size")).setValue(party.size());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_weight")).setValue(party.getCurrentWeight());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_cals")).setValue(party.getCurrentCals());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_time")).setValue(j);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_calories")).setValue(resource.getCalories());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_gatherTime")).setValue(resource.getGatherTime());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_processTime")).setValue(resource.getProcessTime());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_weight")).setValue(resource.getProcessTime());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_success")).setValue(resource.getSuccessRate());
								
								double decision = decisionNet.getOutput();// Decision will be 0 for ignore, 1 for hunt, 2 for return to camp
								
								if(decision == 1) {
									party.setGathering(true);
									party.setGatherTimeRemaining(resource.getGatherTime() + resource.getProcessTime());
									if(Math.random() <= resource.getSuccessRate()) {
										party.optimizeResources(resource, resource.getWeight());
									}
								} else if(decision == 2) {
									party.setActive(false);
								}
							} else {
								//System.out.println("Party found no resource");
								((InputNeuron)decisionNet.getInputLayer().getNeuron("party_size")).setValue(party.size());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_weight")).setValue(party.getCurrentWeight());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_cals")).setValue(party.getCurrentCals());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_time")).setValue(j);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_calories")).setValue(0);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_gatherTime")).setValue(0);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_processTime")).setValue(0);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_weight")).setValue(0);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_success")).setValue(0);
								
								double decision = decisionNet.getOutput();// Decision will be 0 for ignore, 1 for hunt, 2 for return to camp
								
								if(decision == 2) {
									party.setActive(false);
								}
							}
						}
					}
				}
			}
			try {
				group.feedGroup();
			} catch(StarvationException e) {
				break;
			}
			
			for(Party party: group.getParties()) {
				party.setActive(true);
				party.setGathering(false);
				party.setGatherTimeRemaining(0);
				party.newDay();
				party.getObtained().forEach(new BiConsumer<Resource, Double>(){
					/**
					 * Adds all gathered resources to the total gathered resources of the group.
					 */
					@Override
					public void accept(Resource arg0, Double arg1) {
						if(!group.getTotalGathered().containsKey(arg0)) {
							group.getTotalGathered().put(arg0, 0);
						}
						group.getTotalGathered().put(arg0, group.getTotalGathered().get(arg0) + (int)Math.ceil(arg1 / arg0.getWeight()));
					}
				});
				party.getObtained().clear();
			}
		}
		
		// If group did not survive, do not score.
		if(i != DAYS_TO_RUN) {
			return 0;
		}
		
		// Grant 1000 points for each day survived
		double score = (i) * 1000;
		group.setDaysSurvived(i);
		
		// Add excess calories to score
		score += group.getExcessMeat() + group.getExcessPlants();
		// Subtract the number of deficient days from the score, multiplied by 1000 for significance
		score -= group.daysDeficient() * 1000;
		
		return score;
	}

	/**
	 * Generates a random resource.
	 * @param party - Party to generate resource for.
	 * @return Animal if party is hunting, Plant if party is gathering, null if no animal is generated.
	 */
	private static Resource generateResource(Party party) {
		double random = Math.random();
		double total = 0;
		if(party.isHunting()) {
			for(Double chance: chancesAnimal.keySet()) {
				total += chance.doubleValue();
				if(random < total) {
					return chancesAnimal.get(chance);
				}
			}
		} else {
			for(Double chance: chancesPlant.keySet()) {
				total += chance.doubleValue();
				if(random < total) {
					return chancesPlant.get(chance);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Generates a new network based on input network with mutations on each weighting of +-20%
	 * @param net - Net to mutate.
	 * @return New net based on input net.
	 */
	private static Net generateMutation(Net net) {
		Net mutation = generateNet();
		
		for(Neuron neuron: net.getInputLayer().getNeurons()) {
			boolean higher = (int)(Math.random() * 2) == 1;
			if(higher) {
				mutation.getInputLayer().getNeuron(neuron.getId()).getOutputConnections().get(0).setWeight(neuron.getOutputConnections().get(0).getWeight() * ((Math.random() * 1.2) + 1));
			}
			else {
				mutation.getInputLayer().getNeuron(neuron.getId()).getOutputConnections().get(0).setWeight(neuron.getOutputConnections().get(0).getWeight() * ((Math.random() * 1.2) + .8));
			}
		}
		
		return mutation;
	}
	
	/**
	 * Calculate the greatest common denominator of two numbers.
	 * @param a - First number.
	 * @param b - Second number.
	 * @return Greatest common denominator.
	 */
	private static long gcd(long a, long b) {
	    while (b > 0) {
	        long temp = b;
	        b = a % b;
	        a = temp;
	    }
	    return a;
	}
	
	/**
	 * Calculate the least common multiple of two numbers.
	 * @param a - First number.
	 * @param b - Second number.
	 * @return Least common multiple.
	 */
	private static long lcm(long a, long b) {
	    return a * (b / gcd(a, b));
	}

	/**
	 * Calculate the least common multiple of a list of numbers.
	 * @param input - List to calculate least common multiple of.
	 * @return Least common multiple of list.
	 */
	private static long lcm(List<Long> input) {
	    long result = input.get(0).longValue();
	    for(int i = 1; i < input.size(); i++) {
	    	result = lcm(result, input.get(i).longValue());
	    }
	    return result;
	}
}
