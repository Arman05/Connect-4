
import java.util.Scanner;

class Board {
	int board[][] = new int[6][7];

	//Assigning an empty default board in the constructor
	public Board(){
		
		//Board Initialized
		board = new int[][]{
			{0,0,0,0,0,0,0,},			//0 is used to represent an empty slot in the board
			{0,0,0,0,0,0,0,},
			{0,0,0,0,0,0,0,},
			{0,0,0,0,0,0,0,},
			{0,0,0,0,0,0,0,},
			{0,0,0,0,0,0,0,},
		};
	}

	public boolean checkFilledColumn(int column){
		return board[0][column]==0;                   // Checking weather the column is filled or not
	}

	//Function to insert a disc
	public boolean insertDisc(int column,int playerNumber){

		if(!checkFilledColumn(column)){
			System.out.println("You have inserted the disc in a filled column.");
			return false;
		}

		for(int x=5;x>=0;x--){
			if(board[x][column] == 0){
				board[x][column] = playerNumber;
				return true;
			}
		}
		return false;
	}

	//Function to delete the topmost disc from a column
	public void deleteDisc(int column){
		  for(int x=0;x<=5;x++){
           if(board[x][column] != 0) {
               board[x][column] = 0;
               break;
           }
       }  
	}

	//Printing the board state
   public void printBoard(){
       System.out.println();
       for(int x=0;x<=5;x++){
           for(int y=0;y<=6;y++){
               System.out.print(board[x][y]+"|");
           }
           System.out.println();
       }
       System.out.println();
   }

}

class Connect4{
	private Board b;
	private Scanner s;

	public Connect4(Board b){
		this.b = b;
		s = new Scanner(System.in);
	}

	public static void main(String[] args) {
       Board b = new Board();
       Connect4 C4 = new Connect4(b);  
       C4.runGame();
   }

   

   public void runGame(){
   		int playerMove = -1;
   		Scanner s = new Scanner(System.in);
   		System.out.println("Player 1 gives the first move.\nWould you like to be Player 1 or Player 2?");
   		int playerNumber = s.nextInt();
   		int aiNumber = 1;

   		if(playerNumber==1) {   
    		playerMove(playerNumber);
    		b.printBoard();
    		aiNumber = 2;
        }
        b.insertDisc(3, aiNumber);
        System.out.println("Player "+aiNumber+"(AI) inserted in column 4");
        b.printBoard();
       
        while(true){ 
            playerMove(playerNumber);
            b.printBoard();
           
            int resultNumber = boardState(b,playerNumber);
            if(resultNumber==aiNumber){
        	    System.out.println("AI Wins!("+"Player "+aiNumber+")");
        	    break;
            }
            else if(resultNumber==playerNumber){
        	    System.out.println("You Win!("+"Player "+playerNumber+")");
        	    break;
            }
            else if(resultNumber==0){
        	    System.out.println("Draw!");
        	    break;
            }
           
            b.insertDisc(aiMove(aiNumber), aiNumber);
            b.printBoard();
            resultNumber = boardState(b,playerNumber);
            if(resultNumber==aiNumber){
        	    System.out.println("AI Wins!("+"Player "+aiNumber+")");
        	    break;
            }
            else if(resultNumber==playerNumber){
        	    System.out.println("You Win!("+"Player "+playerNumber+")");
        	    break;
            }
            else if(resultNumber==0){
        	    System.out.println("Draw!");
        	    break;
            }
    	}
        
    }

    public int aiMove(int aiNumber){
	   /* nextMoveLocation = -1;
	    minmax(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
	    return nextMoveLocation;*/

    	System.out.println("Type the column on which you want to place the disc(1-7): ");
		int column = s.nextInt();
		while(column<1 || column > 7){
		    System.out.println("Wrong column number.\n\nType the correct column number between 1-7: "); 
		    column = s.nextInt();
		    if(!b.checkFilledColumn(column-1)){
		    	System.out.println("The column number that you have inserted is already filled.Try somewhere else."); 
		    	column = s.nextInt();
		    }
		}
		System.out.println("Player "+aiNumber+"(AI) inserted in column "+column);
       	return column-1;
        //b.insertDisc(column-1, playerNumber);
   }

   public void playerMove(int playerNumber){
   		System.out.println("Type the column on which you want to place the disc(1-7): ");
        int column = s.nextInt();
        while(column<1 || column > 7){
            System.out.println("Wrong column number.\n\nType the correct column number between 1-7: "); 
            column = s.nextInt();
            if(!b.checkFilledColumn(column-1)){
            	System.out.println("The column number that you have inserted is already filled.Try somewhere else."); 
            	column = s.nextInt();
            }
        }
        
        b.insertDisc(column-1, playerNumber);
        System.out.println("Player "+playerNumber+"(Human) inserted in column "+column);
   }

    public int boardState(Board b,int playerNumber){
       int aiScore = 0;
       int playerScore = 0;
       int aiNumber = 1;

       if(playerNumber==1){
       		aiNumber = 2;
       }

       for(int row=5;row>=0;row--){
          for(int column=0;column<=6;column++){

            //Checking weather there is any disc in the slot
            if(b.board[row][column]==0) {
              continue;
            }
               
           //Checking for a match  horizontally towards right direction
            if(column<=3){
                for(int x=0;x<4;x++){ 
                   if(b.board[row][column+x]==aiNumber) {
                	   aiScore++;
                   }
                   else if(b.board[row][column+x]==playerNumber) {
                	   playerScore++;
                   }
                   else {
                	   break; 
                   }
               }
               if(aiScore==4) {
            	   return aiNumber; 
               }
               else if (playerScore==4) {
            	   return playerNumber;
               }
               aiScore = 0; 
               playerScore = 0;
           } 
               
           //Checking for a match vertically towards up direction
           if(row>=3){
               for(int x=0;x<4;x++){
                       if(b.board[row-x][x]==aiNumber) {
                    	   aiScore++;
                       }
                       else if(b.board[row-x][column]==playerNumber) {
                    	   playerScore++;
                       }
                       else {
                    	   break;
                       }
               }
               if(aiScore==4) {
            	   return aiNumber;
               }
               else if (playerScore==4) {
            	   return playerNumber;
               }

               //Reseting the scores
               aiScore = 0; 
               playerScore = 0;
           } 
           
           //Checking match diagonally towards up-right direction
           if(column<=3 && row>= 3){
               for(int x=0;x<4;x++){
                   if(b.board[row-x][column+x]==aiNumber) {
                	   aiScore++;
                   }
                   else if(b.board[row-column][row+x]==playerNumber) {
                	   playerScore++;
                   }
                   else {
                	   break;
                   }
               }
               if(aiScore==4) {
            	   return aiNumber;
               }
               else if (playerScore==4) {
            	   return playerNumber;
               }
               aiScore = 0;
               playerScore = 0;
           }
           
           //Checking match diagonally up-left direction
           if(column>=3 && row>=3){
               for(int x=0;x<4;x++){
                   if(b.board[row-x][column-x]==aiNumber) {
                	   aiScore++;
                   }
                   else if(b.board[row-x][column-x]==playerNumber) {
                	   playerScore++;
                   }
                   else {
                	   break;
                   }
               } 
               if(aiScore==4) {
            	   return aiNumber;
               }
               else if (playerScore==4) {
            	   return playerNumber;
               }
               aiScore = 0;
               playerScore = 0;
           }  
           }
       }
       
       for(int column=0;column<7;column++){

           //Game has not ended yet
           if(b.board[0][column]==0) {
        	   return -1;
           }
       }
       //Game draw
       return 0;
   }

   
}