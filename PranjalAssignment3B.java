package Assignment3B;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

public class PranjalAssignment3B{

    public static void main(String[] args) {
        PranjalHashMap<String, Integer> statePopulations = new PranjalHashMap<>();
        PranjalHashSet<String> stateNames = new PranjalHashSet<>();

        try {
        	Scanner scanner = new Scanner(new File("src/Assignment3BData.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\t"); // Splitting by tab character
                String state = parts[0].trim();
                int population = Integer.parseInt(parts[1].trim());

                statePopulations.put(state, population);
                stateNames.add(state);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        	System.out.println("File not found: " + e.getMessage());
        }

        // Demonstrate PranjalHashMap functionalities
        System.out.println("----- Pranjal Assignment -----\n");
        System.out.println("-------HashMap Functionalities-------");
        System.out.println("Contians Key 'California': " + statePopulations.containsKey("California"));
        System.out.println("Contains Value 39512223: " + statePopulations.containsValue(39512223)); 
        System.out.println("");
        
        Set<PranjalMap.Entry<String, Integer>> entries = statePopulations.entrySet();
        System.out.println("HashMap [State, Population]\n" + entries);

        // Get value
        System.out.println("\nGet 'California': " + statePopulations.get("California"));

        // Key Set
        Set<String> keys = statePopulations.keySet();
        System.out.println("\nState (Key): " + keys);

        // Values
        Set<Integer> values = statePopulations.values();
        System.out.println("\nPopulation(Values): " + values);

        // Size
        System.out.println("\nSize: " + statePopulations.size());
        
        System.out.println("-------------------------------------");
        
        // Demonstrate PranjalHashSet functionalities
        System.out.println("\n----- HashSet Functionalities -----\n");
        System.out.println("Contains 'California': " + stateNames.contains("California"));

        // Size
        System.out.println("Size: " + stateNames.size());

        // Iterator
        System.out.println("Iterating over HashSet: ");
        for(String state : stateNames) {
            System.out.println(" - " + state);
        }

        // Now demonstrate removal and clearing of the map
        statePopulations.remove("California");
        System.out.println("-------------------------------------");
        System.out.println("\nDemonstrating the removal and clearing the "
        		+ "specific value from the HashMap");
        System.out.println("\nAfter Removing 'California', Contains Key 'California': "
        + statePopulations.containsKey("California"));
        System.out.println("Current Size (after removal): " + statePopulations.size());
        
        statePopulations.clear();
        System.out.println("After Clearing, Is Empty: " + statePopulations.isEmpty());

        // PranjalOutput after repopulating for demonstration
        statePopulations.put("California", 39512223); // Repopulate for PranjalOutput
        System.out.println("\nHashMap after repopulating:");
        statePopulations.PranjalOutput();
        
        // Clearing HashSet for demonstration
        stateNames.clear();
        System.out.println("After Clearing HashSet, Is Empty: " + stateNames.isEmpty());

        
    }
}
