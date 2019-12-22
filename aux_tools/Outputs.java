package aux_tools;

public class Outputs { 
    interface View<T> {
        public String render(T data);
    }

    class Row<T> implements View<T> {
        public String render(T data) {
            System.out.println("This is render!");
            return "";
        }
    }

    public static void main(String[] args) {
    
    }
}

