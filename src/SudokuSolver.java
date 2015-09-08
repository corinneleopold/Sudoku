/* Corinne Leopold
 * 
 * This program uses the Naked Singles and Hidden Singles strategies
 * to solve easy-medium Sudoku puzzles. 
 */

public class SudokuSolver {
	    int row;
	    int column;
	    int[][] board = new int[9][9];
	    
	    //creates an initially empty board
	    public SudokuSolver() {
	        for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board.length; j++) {
	                board[i][j] = 0;
	            }
	        }
	    }
	    
	    //creates an initial board defined by board[r][c]. Use 0 to represent an empty cell.
	    public SudokuSolver(int[][] board) { 
	        int[][] copy = new int[9][9];
	        for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board.length; j++) {
	                copy[i][j] = board[i][j];
	            }
	        }
	        this.board = copy;
	    }
	    
	    //returns a copy of the current state of the board
	    public int[][] board() {
	        int[][] copy = new int[9][9];
	        for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board.length; j++) {
	                copy[i][j] = board[i][j];
	            }
	        }
	        return copy;
	    }
	    
	    //returns the list of candidates for the specified cell
	    public boolean[] candidates(int row, int column) {
	        boolean[] box = new boolean[10];
	        if (board[row][column] != 0) {
	            for (int i = 0; i < box.length; i++) {
	                box[i] = false;    
	            }
	            return box;
	        }
	        for (int i = 0; i < box.length; i++)
	            box[i] = true;
	        for (int i = getRow(row); i < getRow(row) + 3; i++) {
	            for (int j = getColumn(column); j < getColumn(column) + 3; j++) {
	                if (board[i][j] != 0) {
	                    int temp = board[i][j];
	                    box[temp] = false;
	                }
	            }
	        }
	        for (int i = 0; i < board.length; i++) {
	            if (board[i][column] != 0) {
	                int temp = board[i][column];
	                box[temp] = false;
	            }
	        }    
	        for (int i = 0; i < board.length; i++) {
	            if (board[row][i] != 0) {
	                int temp = board[row][i];
	                box[temp] = false;
	            }
	        }
	        box[0] = false;
	        return box;
	    }
	    
	    public int getRow(int row) {        
	        if (row < 3) 
	            return 0;    
	        else if (row < 6) 
	            return 3;   
	        else 
	            return 6;
	    }    
	    
	    public int getColumn(int column) {   
	        if (column < 3)
	            return 0;       
	        else if (column < 6)
	            return 3;    
	        else
	            return 6;        
	    }
	    
	    //returns true if the board is in a solved state
	    public boolean isSolved() {
	        String bit;
	        String s = board.toString();
	        for (int i = 0; i < board.length; i++) {
	            for (int j = 0; j < board.length; j++) {
	                if (board[i][j] == 0) 
	                    return false; 
	                else if (i == 8 || i == 17 || i == 26 || i == 35 || i == 44 || i == 53 || i == 62 || i == 71 || i == 80) {
	                    if (i == 8)
	                        bit = s.substring(0, i);
	                    else
	                        bit = s.substring(i - 9, i);
	                    if (!bit.matches(".*[1-9].*"))
	                        return false;
	                }
	            }
	        }        
	        return true;
	    } 
	    
	    //attempts to solve the board. Exits when it is solved or no updates were made to the board.
	    public void solve() {
	        while (!isSolved() && (nakedSingles() || hiddenSingles())) {
	        }
	    }
	    
	    //strategy to find cells with only 1 possible candidate
	    public boolean nakedSingles() {      
	        for (int i = 0; i < 9; i++) {   
	            for (int j = 0; j < 9; j++) {  
	                boolean[] check = candidates(i, j);
	                int count = 0;           
	                for (int x = 0; x < check.length; x++) {
	                    if (check[x] == true) {
	                        count++; 
	                    }
	                }
	                if (count == 1) {
	                    for (int y = 0; y < check.length; y++) {                       
	                        if (check[y] == true) {                      
	                            board[i][j] = y;
	                            return true; 
	                        }
	                    }
	                }
	            }
	        }
	        return false;       
	    }   
	    
	    //strategy to find cells that have a unique candidate for their subunit (row, column, or box)
	    public boolean hiddenSingles() {
	        for(int i=0; i < board.length; i++) {
	            for(int j = 0; j < board[i].length; j++) {
	                boolean[] compare = candidates(i,j);
	                if(checkBox(compare, i, j)) 
	                    return true;
	                else if(checkCol(compare,i,j))
	                    return true;
	                else if(checkRow(compare,i,j))
	                    return true;
	            }
	        }
	        return false;
	    }
	    
	    public boolean determineHiddenSingles(boolean[] hold, int i, int j) {    	
	    	int count = 0;
	    	int temp = 0;
	    	for (int x = 0; x < hold.length; x++) {
	    		if (hold[x] == true) {
	    			count++;
	    			temp = x;
	    		}
	    	}           	        
	        if (count == 1) {
	        	board[i][j] = temp;
	        	return true;
	        }
	        return false;
	    }          
	    
	    public boolean checkRow(boolean[] compare, int i, int j){	        
	        boolean[] hold = new boolean[10];
	        boolean[] compare2;
	        for (int x = 0; x < hold.length; x++)
	            hold[x] = compare[x];
	        for (int x = 0; x < board.length; x++) {
	            compare2 = candidates(i,x);	            
	            if (x != j) { 
	            	for (int z = 0; z < compare2.length; z++) { 
	                    if (hold[z] == true){
	                        if (compare2[z]==true && compare[z] == true)
	                            hold[z] = false;
	                        if (compare2[z] == false && compare[z] == false)
	                            hold[z] = false;
	                        if (compare2[z] == false && compare[z] == true)
	                            hold[z] = true;
	                        if (compare2[z] == true && compare[z] == false)
	                            hold[z] = false;
	                    }
	                } 
	            } 
	        }
	        return determineHiddenSingles(hold, i, j);
	    }
	    
	    public boolean checkCol(boolean[] compare, int i, int j){        
	        boolean[] hold = new boolean[10];
	        boolean[] compare2;
	        for (int x = 0; x < hold.length; x++) 
	            hold[x] = compare[x];
	        for (int x = 0; x < board.length; x++) {
	            compare2 = candidates(x,j);
	            if (x != i){ 
	                for (int z = 0; z < compare2.length; z++){                
	                    if (hold[z] == true){
	                        if (compare2[z] == true && compare[z] == true)
	                            hold[z] = false;
	                        if (compare2[z] == false && compare[z] == false)
	                            hold[z] = false;
	                        if (compare2[z] == false && compare[z] == true)
	                            hold[z] = true;
	                        if (compare2[z] == true && compare[z] == false)
	                            hold[z] = false;
	                    }
	                }
	            } 
	        }
	        return determineHiddenSingles(hold, i, j);
	    }
	       
	    public boolean checkBox(boolean[] compare, int i, int j) {
	        if (board[i][j] != 0)
	            return false;
	        int tracker = 0;
	        boolean[] temp;
	        boolean[] hold = compare; 
	        for (int x = getRow(i); x < getRow(i)+3; x++) {
	            for (int y = getColumn(j); y < getColumn(j)+3; y++) {
	                temp = candidates(i,j);          
	                for (int z = 0; z < temp.length; z++) {
	                    if (temp[z] == false && compare[z] == true)
	                        tracker++;  
	                    if (temp[z] == false && compare[z] == false) {
	                        if (temp[z]==true && compare[z]==true)                        
	                        	hold[z]=false;   
	                    }      
	                }
	                if (tracker == 1){
	                    for (int u = 0; u < hold.length; u++){
	                        if (hold[u] == true) {
	                            board[i][j] = u;
	                            return true;
	                        }
	                    }    
	                }
	            }
	        }
	        return false;
	    }
	    
	    public void draw() {
	        char a = 'A';
	        System.out.println("   1 2 3   4 5 6   7 8 9");
	        System.out.println("  +------+-------+-------+");	        
	        for (int i = 0; i < 9; ++i) {
	            if (i % 3 == 0 && i != 0)
	                System.out.println("  ------------------------");
	            for (int j = 0; j < 9; ++j) {
	                if (j == 0) {
	                    System.out.print(a);
	                    a++;
	                }
	                if (j % 3 == 0) 
	                	System.out.print("| ");
	                System.out.print(board[i][j] == 0 ? " " : Integer.toString(board[i][j]));
	                System.out.print(" ");
	            }
	            System.out.println("|");
	        }
	        System.out.println("  ------------------------");       
	    }
}
