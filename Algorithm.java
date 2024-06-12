import java.util.*;
import java.util.stream.Collectors;

public class Algorithm {
    private static final int PLUS = 0;
    private static final int MINUS = 1;
    private static final int TIMES = 2;
    private static final int DIVIDE = 3;
    private static final int N = 4;
    ArrayList<Integer> cardList;
    ArrayList<String> pathList;
    ArrayList<String> operator;
    ArrayList<Double> result;
    ArrayList<Card> cardNode;

    public Algorithm() {
        cardList = new ArrayList<Integer>();
        pathList = new ArrayList<String>();
        result = new ArrayList<Double>();
        cardNode = new ArrayList<Card>();
        operator = new ArrayList<String>(Arrays.asList("+","-", "*", "/"));
    }

    public void solve24BruteForce() {
        for (int i = 0; i < N; i++) {
            for (int op1 = 0; op1 < 4; op1++) {
                for (int j = 0; j < N; j++) {
                    if (j != i) {
                        for (int op2 = 0; op2 < 4; op2++) {
                            for (int k = 0; k < N; k++) {
                                if (k != i && k != j) {
                                    for (int op3 = 0; op3 < 4; op3++) {
                                        for (int l = 0; l < N; l++) {
                                            if (l != k && l != i && l != j) {
                                                // (a # b) # (c # d)
                                                if (count(count((double)cardList.get(i), (double)cardList.get(j), op1), count((double)cardList.get(k), (double)cardList.get(l), op2), op3) == 24) {
                                                    String operation = String.format("(%d %s %d) %s (%d %s %d)", cardList.get(i), operator.get(op1), cardList.get(j), operator.get(op3), cardList.get(k), operator.get(op2), cardList.get(l));
                                                    if (!pathList.contains(operation)) {
                                                        pathList.add(operation);
                                                    }
                                                }

                                                // (a # (b # c)) # d
                                                if (count(count((double)cardList.get(i), count((double)cardList.get(j), (double)cardList.get(k), op2), op1), (double)cardList.get(l), op3) == 24) {
                                                    String operation = String.format("(%d %s (%d %s %d)) %s %d", cardList.get(i), operator.get(op1), cardList.get(j), operator.get(op2), cardList.get(k), operator.get(op3), cardList.get(l));
                                                    if (!pathList.contains(operation)) {
                                                        pathList.add(operation);
                                                    }
                                                }
                                                
                                                // ((a # b) # c) # d
                                                if (count(count(count((double)cardList.get(i), (double)cardList.get(j), op1), (double)cardList.get(k), op2), (double)cardList.get(l), op3) == 24) {
                                                    String operation = String.format("((%d %s %d) %s %d) %s %d", cardList.get(i), operator.get(op1), cardList.get(j), operator.get(op2), cardList.get(k), operator.get(op3), cardList.get(l));
                                                    if (!pathList.contains(operation)) {
                                                        pathList.add(String.format("((%d %s %d) %s %d) %s %d", cardList.get(i), operator.get(op1), cardList.get(j), operator.get(op2), cardList.get(k), operator.get(op3), cardList.get(l)));
                                                    }
                                                }

                                                // a # ((b # c) # d)
                                                if (count((double)cardList.get(i), count(count((double)cardList.get(j), (double)cardList.get(k), op2), (double)cardList.get(l), op3), op1) == 24) {
                                                    String operation = String.format("%d %s ((%d %s %d) %s %d)", cardList.get(i), operator.get(op1), cardList.get(j), operator.get(op2), cardList.get(k), operator.get(op3), cardList.get(l));
                                                    if (!pathList.contains(operation)) {
                                                        pathList.add(operation);
                                                    }
                                                }

                                                // a # (b # (c # d))
                                                if (count((double)cardList.get(i), count((double)cardList.get(j), count((double)cardList.get(k), (double)cardList.get(l), op3), op2), op1) == 24) {
                                                    String operation = String.format("%d %s (%d %s (%d %s %d))", cardList.get(i), operator.get(op1), cardList.get(j), operator.get(op2), cardList.get(k), operator.get(op3), cardList.get(l));
                                                    if (!pathList.contains(operation)) {
                                                        pathList.add(operation);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    
                                }
                            }
                        }
                        
                    }
                }
            }
        }
    }

    public void solve24AStar() {
        cardNode.add(new Card(Integer.toString(cardList.get(0)), 3, cardList.get(0)));
        boolean found = false;
        while (!found && cardNode.size() != 0) {
            
        }
    }

    public void cardConverter(ArrayList<String> inputList) {
        for (String numString : inputList) {
            try {
                cardList.add(Integer.parseInt(numString));
            } catch (Exception e) {
                if (numString.equals("A")) {
                    cardList.add(1);
                }
                if (numString.equals("J")) {
                    cardList.add(11);
                } 
                if(numString.equals("Q")) {
                    cardList.add(12);
                }
                if(numString.equals("K")) {
                    cardList.add(13);
                }
            }
        }
    }

    public double count(double num1, double num2, int operation) {

        if (operation == PLUS) {
            return (double)(num1 + num2);
        }
        else if (operation == MINUS) {
            return (double)(num1 - num2);
        }
        else if (operation == TIMES) {
            return (double)(num1 * num2);
        }
        else { // DIVIDE
            return (double)(num1/num2);
        }
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void removeDuplicate() {
        pathList = pathList.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Double> getResult() {
        return result;
    }

    public void clear() {
        cardList = new ArrayList<Integer>();
        pathList = new ArrayList<String>();
        result = new ArrayList<Double>();
    }

    public static void main(String[] args) {
        InputHandler userInput = new InputHandler();
        userInput.input();
        Algorithm algorithm = new Algorithm();
        algorithm.cardConverter(userInput.getInput());
        long startTime = System.currentTimeMillis();
        algorithm.solve24BruteForce();
        long endTime = System.currentTimeMillis();
        for (int i = 0; i < algorithm.getPathList().size(); i++) {
            System.out.println(algorithm.getPathList().get(i));
        }
        if (algorithm.getPathList().isEmpty()) {
            System.out.println("\nSolusi tidak ada!");
        } else {
            System.out.println("\nJumlah solusi: " + algorithm.getPathList().size());
        }
        System.out.println("Time elapsed: " + (endTime-startTime) + " ms");
    }
}
