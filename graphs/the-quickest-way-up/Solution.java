import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Solution {
    private static Map<Integer, Integer> ladders = new HashMap<Integer, Integer>();
    private static Map<Integer, Integer> snakes = new HashMap<Integer, Integer>();
    private static Set<Integer> visited = new HashSet<Integer>();

    public static void main(String[] args) throws IOException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        
        while(t>0){
            --t;
            /**
            Create empty set to track visited paths
            **/
            visited=new HashSet<Integer>();
            
            /**
            Load ladders
            **/
            int numLadders = Integer.parseInt(br.readLine());
            ladders = new HashMap<Integer, Integer>();
            for(int i = 0; i<numLadders; i++){
                int[] coords=Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                ladders.put(coords[0], coords[1]);
            }
            //System.out.println(ladders.toString());
            
            /**
            Load snakes
            **/
            int numSnakes = Integer.parseInt(br.readLine());
            snakes = new HashMap<Integer, Integer>();
            for(int i = 0; i<numSnakes; i++){
                int[] coords=Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                snakes.put(coords[0], coords[1]);
            }
            //System.out.println(snakes.toString());
            
            /**
            Start traversal from beginning
            **/
            Set<Integer> currentSet = new HashSet<Integer>();
            currentSet.add(1);
            int dieRolls=0;
            //System.out.println("currentSet start: "+currentSet.toString());
            
            /**
            Traverse graph (breadth first search for shortest path), ends on first visit to 100
            **/
            while(!currentSet.contains(100) && currentSet.size()>0){
                currentSet=currentSet.stream().flatMap(node -> breadthFirstSearch(node)).collect(Collectors.toSet());
                ++dieRolls;
               //System.out.println("current set for dieRolls: "+dieRolls+". currentSet: "+currentSet.stream().sorted((i1, i2) -> Integer.compare(i1,i2)).collect(Collectors.toList()).toString());
            }
            System.out.println(currentSet.size()!=0?dieRolls:-1);
        }
    }
    
    public static Stream<Integer> breadthFirstSearch(Integer node){
        Set<Integer> set = new HashSet<Integer>();
            if(!visited.contains(node)){
            visited.add(node);
            for(int i = 1; i<7; i++){
                if(node+i==100){
                    set.add(100);
                    return set.stream();
                }
                //System.out.println(" is node+i ("+(node+i)+") in ladders?: "+(ladders.get(node+i)!=null));
                //System.out.println(" is node+i ("+(node+i)+") in snakes?: "+(snakes.get(node+i)!=null));
                if(ladders.get(node+i)!=null){
                    set.add(ladders.get(node+i));
                }
                else if(snakes.get(node+i)!=null){
                    set.add(snakes.get(node+i));
                }
                else {
                    set.add(node+i);
                }

            }
        }
        return set.stream();
    }
}
