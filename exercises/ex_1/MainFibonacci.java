package exercises;
import aux_tools.*;
import java.util.ArrayList;

class MainFibonacci {
    private static ArrayList<Object> reverseSeq(ArrayList<Object> seq) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = seq.size() - 1; i >= 0; i--) {
            result.add(seq.get(i));
        }

        return result;
    }
        
    private static ArrayList<Object> genFibSeq(int quantity) {
        int a, b;
        ArrayList<Object> result = new ArrayList<Object>();
        a = b = 1;

        for (int i = 0; i < quantity; i++) {
            result.add(a);
            b = a + b;
            a = b - a;
        }

        return result;
    }

    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws MainException {
	    Input input = new Input("Integer", "Please, tell how long sequence must be?");
        int seqLength = (Integer)input.getUserData().get(0);
   
        Output.printList(
            genFibSeq(seqLength)
        );

        Output.println("\n<<<And now the same in backwards<<<", "cyan");
        Output.printList(
            reverseSeq(
                genFibSeq(seqLength)
            )
        ); 
    }
}
 
