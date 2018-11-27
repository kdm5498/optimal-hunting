package simulation;

import java.util.ArrayList;
import java.util.List;

import neural.Connection;
import neural.InputNeuron;
import neural.Layer;
import neural.Net;
import neural.Neuron;
import people.Group;
import people.Party;
import people.Person;
import people.StarvationException;
import resource.Resource;

public class Hunting {
	private static final int NUM_PEOPLE = 40;
	private static final int PARTY_SIZE = 5;
	private static final int DAYS_TO_RUN = 365;// Number of days to simulate
	private static final int HOURS_TO_RUN = 8;// Number of hours to allow for hunting/gathering each day
	private static List<Resource> resources;

	public static void main(String[] args) {
		resources = new ArrayList<Resource>();
		resources.add(new Resource(150, true, 10, 10, 30, 2, "Rabbit"));
		
		Net net = generateNet();
		Net bestNet = net;
		double bestScore = 0;
		
		while(bestScore == 0) {
			double score = runSimulation(net);
			if(score > bestScore) {
				bestNet = net;
				bestScore = score;
			}
			net = generateNet();
		}
		
		for(int j = 0; j < 10000; j++) {
			List<Net> nets = new ArrayList<Net>();
			boolean higher = true;
			for(int i = 0; i < 50; i++) {
				nets.add(generateMutation(bestNet, higher));
				higher = !higher;
			}
			for(int i = 0; i < 50; i++) {
				nets.add(generateNet());
			}
			
			for(Net testNet: nets) {
				double score = runSimulation(testNet);
				if(score > bestScore) {
					bestNet = testNet;
					bestScore = score;
				}
			}
		}
		
		System.out.println("Best Score: " + bestScore);
		System.out.println("Best net weightings:");
		for(Neuron neuron: bestNet.getInputLayer().getNeurons()) {
			System.out.println(neuron.getId() + ": " + neuron.getOutputConnections().get(0).getWeight());
		}
	}
	
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
		
		outputLayer.getNeurons().add(new Neuron("choice"));
		
		inputLayer.getNeuron("party_size").getOutputConnections().add(new Connection(inputLayer.getNeuron("party_size"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_weight").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_weight"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_cals").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_cals"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_time").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_time"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_calories").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_calories"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_gatherTime").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_gatherTime"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_processTime").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_processTime"), outputLayer.getNeuron("choice")));
		inputLayer.getNeuron("current_resource_weight").getOutputConnections().add(new Connection(inputLayer.getNeuron("current_resource_weight"), outputLayer.getNeuron("choice")));
		
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("party_size").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_weight").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_cals").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_time").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_calories").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_gatherTime").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_processTime").getOutputConnections().get(0));
		outputLayer.getNeuron("choice").getInputConnections().add(inputLayer.getNeuron("current_resource_weight").getOutputConnections().get(0));
		
		return neuralNet;
	}
	
	private static double runSimulation(Net decisionNet) {
		Group group = new Group();
		
		for(int i = 0; i < NUM_PEOPLE; i++) {
			group.getMembers().add(new Person());
		}
		
		boolean hunting = true;
		for(int i = 0; i < NUM_PEOPLE / PARTY_SIZE; i++) {
			group.allocateParty(PARTY_SIZE, hunting);
			hunting = !hunting;
		}
		int i = 0;
		for(; i < DAYS_TO_RUN; i++) {
			
			for(int j = 0; j < HOURS_TO_RUN * 60; j++) {
				
				for(Party party: group.getParties()) {
					if(party.isActive()) {
						if(party.isGathering()) {
							party.setGatherTimeRemaining(party.getGatherTimeRemaining() - 1);
							if(party.getGatherTimeRemaining() == 0) {
								party.setGathering(false);
							}
						} else {
							Resource resource = generateResource(party.isHunting());
							if(resource != null) {
								((InputNeuron)decisionNet.getInputLayer().getNeuron("party_size")).setValue(party.size());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_weight")).setValue(party.getCurrentWeight());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_cals")).setValue(party.getCurrentCals());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_time")).setValue(j);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_calories")).setValue(resource.getCalories());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_gatherTime")).setValue(resource.getGatherTime());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_processTime")).setValue(resource.getProcessTime());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_weight")).setValue(resource.getProcessTime());
								
								double decision = decisionNet.getOutput();// Decision will be 0 for ignore, 1 for hunt, 2 for return to camp
								
								if(decision == 1) {
									party.setGathering(true);
									party.setGatherTimeRemaining(resource.getGatherTime() + resource.getProcessTime());
									party.optimizeResources(resource, resource.getWeight());
								} else if(decision == 2) {
									party.setActive(false);
								}
							} else {
								((InputNeuron)decisionNet.getInputLayer().getNeuron("party_size")).setValue(party.size());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_weight")).setValue(party.getCurrentWeight());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_cals")).setValue(party.getCurrentCals());
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_time")).setValue(j);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_calories")).setValue(0);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_gatherTime")).setValue(0);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_processTime")).setValue(0);
								((InputNeuron)decisionNet.getInputLayer().getNeuron("current_resource_weight")).setValue(0);
								
								double decision = decisionNet.getOutput();// Decision will be 0 for ignore, 1 for hunt, 2 for return to camp
								
								if(decision == 2) {
									party.setActive(false);
								}
							}
						}
					}
				}
			}
			
			for(Party party: group.getParties()) {
				party.setActive(true);
				party.setGathering(false);
				party.setGatherTimeRemaining(0);
			}
			
			try {
				group.feedGroup();
			} catch(StarvationException e) {
				// This group has starved to death. Break to score the run.
				break;
			}
		}
		
		// Grant 1000 points for each day survived
		double score = i * 1000;
		
		// Add excess calories to score
		score += group.getExcessMeat() + group.getExcessPlants();
		// Subtract the number of deficient days from the score, multiplied by 1000 for significance
		score -= group.getMembers().get(0).getDaysDeficient() * 1000;
		
		return score;
	}

	private static Resource generateResource(boolean animal) {
		return resources.get(0);
	}
	
	private static Net generateMutation(Net net, boolean greater) {
		Net mutation = generateNet();
		
		if(greater) {
			for(Neuron neuron: net.getInputLayer().getNeurons()) {
				mutation.getInputLayer().getNeuron(neuron.getId()).getOutputConnections().get(0).setWeight(neuron.getOutputConnections().get(0).getWeight() * ((Math.random() * 1.2) + 1));
			}
		} else {
			for(Neuron neuron: net.getInputLayer().getNeurons()) {
				mutation.getInputLayer().getNeuron(neuron.getId()).getOutputConnections().get(0).setWeight(neuron.getOutputConnections().get(0).getWeight() * ((Math.random() * 1.2) + .8));
			}
		}
		
		return mutation;
	}
}
