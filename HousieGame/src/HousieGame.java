import java.util.Arrays;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class HousieGame {

	// For Logging purpose
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static void setupLogger() {
		LogManager.getLogManager().reset();
		logger.setLevel(Level.ALL);
		
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.SEVERE);
		logger.addHandler(ch);
		
		try {
			FileHandler fh = new FileHandler("housieGameLog.txt", true);
			fh.setLevel(Level.FINE);
			logger.addHandler(fh);
		}catch (java.io.IOException e) {
			logger.log(Level.SEVERE, "File logger not working.", e);
		}
	}
	
	public static void main(String[] args) {
		HousieGame.setupLogger();
		
		// Generating 10 HousieGame tickets.
		for(int i=0; i<10; i++) {
			System.out.println("**********************************");
			startGame();
		}
	}

	private static void startGame() {

		int game[][] = new int[3][9];
		int occupancyLimit = 15;

		// Rule Checking & assuring only 15 cells to be filled. 
		while(occupancyLimit > 0) {
			int i = getRandomNumber(3);
			int j = getRandomNumber(9);
			int data = validateAndReturnNumber(i,j,game);

			if(data > 0) {
				game[i][j]=data;
				occupancyLimit--;
			}
		}
		System.out.println(Arrays.deepToString(game).replace("],", "]\n"));
	}

	private static int validateAndReturnNumber(int i, int j, int[][] game) {

		// Rule - If the value is already filled so it can not be overridden. 
		if(game[i][j] !=0) {
			return -1;
		}

		int rowCounter = 0;
		for(int r=0; r<3; r++) {
			if(game[r][j] != 0) {
				rowCounter++;
			}
		}

		// Rule - Column can not contains more than 2 values.
		if(rowCounter >= 2) {
			return -1;
		}

		int columnCounter = 0;
		for(int c=0; c<9; c++) {
			if(game[i][c] != 0) {
				columnCounter++;
			}
		}

		// Rule - Rows can not have more than 5 elements.
		if(columnCounter >= 5) {
			return -1;
		}

		int data = 0;
		boolean isValueSet = false;

		do {
			data = getRandomNumberForColumn(j);
			isValueSet = isValueExistsInCol(game,i,j,data);
		}while(isValueSet);

		return data;
	}

	private static boolean isValueExistsInCol(int[][] game, int i, int j, int data) {
		
		// Checking value is present in column or not. 
		boolean status = false;
		for(int k=0; k<3; k++) {
			if(game[k][j] == data) {
				status = true;
				break;
			}
		}
		return status;
	}

	private static int getRandomNumberForColumn(int highestNo) {
		//Checking and generating random no.s not more than 10.  
		if(highestNo == 0) {
			highestNo = 10;
		}
		else {
			highestNo = (highestNo+1)*10;
		}
		int lowestNo = highestNo - 9;
		Random r = new Random();
		return r.nextInt(highestNo-lowestNo)+lowestNo;
	}

	private static int getRandomNumber(int max) {
		return (int)(Math.random()*max);
	}
}
