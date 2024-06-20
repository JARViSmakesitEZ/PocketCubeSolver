import java.util.*;

class Cube{

    public static final int FRONT = 0;
    public static final int BACK = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int UP = 4;
    public static final int DOWN = 5;

    // 3D array to represent the cube's state
    // Each dimension represents a face of the cube
    // Each face contains 4 cubies (for 2x2x2 Pocket Cube)
    private int[][][] state;
    private HashMap<Integer,ArrayList<Integer>> neighbors;

    // Constructor to initialize the cube with a scrambled state
    public Cube() {
        // Initialize the 3D array with the starting configuration
        // For simplicity, let's assume a solved state initially
        state = new int[6][2][2]; // 6 faces, 2x2 grid for each face
        this.neighbors = new HashMap<>();

        // Fill the state with color indices (for visualization)
        // For example, let's assume a solved cube initially
        // Front face (0)
        state[FRONT] = new int[][] {
            {FRONT, FRONT},
            {FRONT, FRONT}
        };

        // Back face (1)
        state[BACK] = new int[][] {
            {BACK, BACK},
            {BACK, BACK}
        };

        // Left face (2)
        state[LEFT] = new int[][] {
            {LEFT, LEFT},
            {LEFT, LEFT}
        };

        // Right face (3)
        state[RIGHT] = new int[][] {
            {RIGHT, RIGHT},
            {RIGHT, RIGHT}
        };

        // Up face (4)
        state[UP] = new int[][] {
            {UP, UP},
            {UP, UP}
        };

        // Down face (5)
        state[DOWN] = new int[][] {
            {DOWN, DOWN},
            {DOWN, DOWN}
        };


        neighbors.put(0,new ArrayList<>(Arrays.asList(2, 3, 4, 5)));
        neighbors.put(1,new ArrayList<>(Arrays.asList(3, 2, 4, 5)));
        neighbors.put(2,new ArrayList<>(Arrays.asList(1, 0, 4, 5)));
        neighbors.put(3,new ArrayList<>(Arrays.asList(0, 1, 4, 5)));
        neighbors.put(4,new ArrayList<>(Arrays.asList(2, 3, 1, 0)));
        neighbors.put(5,new ArrayList<>(Arrays.asList(2, 3, 0, 1)));
    }

    public void rowToCol(int[] a1,int[] a2){
        int face1 = a1[0];
        int row = a1[1];
        int face2 = a2[0];
        int col = a2[1];
        if(row==0 && col==1){
            this.state[face2][col][0] = this.state[face1][row][0];
            this.state[face2][col][1] = this.state[face1][row][1];
        }
        else{
            this.state[face2][col][0] = this.state[face1][row][1];
            this.state[face2][col][1] = this.state[face1][row][0];
        }

    }
    public void colToRow(int[] a1,int[] a2){
        int face1 = a1[0];
        int col = a1[1];
        int face2 = a2[0];
        int row = a2[1];
        if(col==0 && row==1){
            this.state[face2][row][0] = this.state[face1][col][0];
            this.state[face2][row][1] = this.state[face1][col][1];
        }
        else{
            this.state[face2][row][0] = this.state[face1][col][1];
            this.state[face2][row][1] = this.state[face1][col][0];
        }
    }
    public void swapCol(int[] a1,int[] a2){
        int face1 = a1[0];
        int col1 = a1[1];
        int face2 = a2[0];
        int col2 = a2[1];
        int temp = this.state[face1][col1][0];
        this.state[face1][col1][0] = this.state[face2][col2][0];
        this.state[face2][col2][0] = temp;
        temp = this.state[face1][col1][1];
        this.state[face1][col1][1] = this.state[face2][col2][1];
        this.state[face2][col2][1] = temp;
    }
    public void swapRow(int[] a1,int[] a2){
        int face1 = a1[0];
        int row1 = a1[1];
        int face2 = a2[0];
        int row2 = a2[1];
        int temp = this.state[face1][row1][0];
        this.state[face1][row1][0] = this.state[face2][row2][0];
        this.state[face2][row2][0] = temp;
        temp = this.state[face1][row1][1];
        this.state[face1][row1][1] = this.state[face2][row2][1];
        this.state[face2][row2][1] = temp;
    }
    

    public void printCubeState() {
        for (int face = 0; face < 6; face++) {
            System.out.println("Face " + face + ":");
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 2; col++) {
                    System.out.print(state[face][row][col] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    public void rotateClockwise(int face) {
        // Implement rotation logic for the front face clockwise
        ArrayList<Integer> neighborsStates = neighbors.get(face);
        int leftFace = neighborsStates.get(0);
        int rightFace = neighborsStates.get(1);
        int upFace = neighborsStates.get(2);
        int downFace = neighborsStates.get(3);
        System.out.println(leftFace+" "+rightFace+" "+upFace+" "+downFace);
        this.rotateFaceArrayClockwise(this.state[face]);
        int[] upRow = new int[2];
        upRow[0] = this.state[UP][1][0];
        upRow[1] = this.state[UP][1][1];
        colToRow(new int[]{leftFace,1},new int[]{upFace,1});
        rowToCol(new int[]{downFace,0},new int[]{leftFace,1});
        colToRow(new int[]{rightFace,0},new int[]{downFace,0});
        System.out.println(upRow[0]+" "+upRow[1]);
        this.state[rightFace][0][0] = upRow[0];
        this.state[rightFace][1][0] = upRow[1];

    }

    private void rotateFaceArrayClockwise(int[][] face) {
        int temp = face[0][0];
        face[0][0] = face[1][0];
        face[1][0] = face[1][1];
        face[1][1] = face[0][1];
        face[0][1] = temp;
    }

    public void rotateCounterClockwise(int face) {
        // Implement rotation logic for the front face counterclockwise
        ArrayList<Integer> neighborsStates = neighbors.get(face);
        int leftFace = neighborsStates.get(0);
        int rightFace = neighborsStates.get(1);
        int upFace = neighborsStates.get(2);
        int downFace = neighborsStates.get(3);
        int[] upRow = new int[2];
        upRow[0] = this.state[UP][1][0];
        upRow[1] = this.state[UP][1][1];
        colToRow(new int[]{rightFace,0},new int[]{upFace,1});
        rowToCol(new int[]{downFace,0},new int[]{rightFace,0});
        colToRow(new int[]{leftFace,1},new int[]{downFace,0});
        this.state[leftFace][0][1] = upRow[1];
        this.state[leftFace][1][1] = upRow[0];
    }

    public void rotate180(int face) {
        // Implement rotation logic for the front face 180 degrees
        ArrayList<Integer> neighborsStates = neighbors.get(face);
        int leftFace = neighborsStates.get(0);
        int rightFace = neighborsStates.get(1);
        int upFace = neighborsStates.get(2);
        int downFace = neighborsStates.get(3);
        int[] upRow = this.state[UP][1];
        swapRow(new int[]{upFace,1},new int[]{downFace,0});
        swapCol(new int[]{leftFace,1},new int[]{rightFace,0});
    }

    // Method to check if cube is solved
    public boolean isSolved() {
        // Implement logic to check if cube is in solved state
        // Return true or false accordingly
        return false;
    }
    public static void main(String[] args) {
        System.out.println("hello world");
        Cube c = new Cube();
        c.rotateCounterClockwise(3);
        c.printCubeState();
    }
}
