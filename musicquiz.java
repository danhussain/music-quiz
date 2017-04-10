/*******************************************************************************************
AUTHOR: Syed Danyel Hussain

DATE: 9th December 2014

This program is a multiple choice music quiz.

This program asks the participants of this quiz a series of questions.
For each correct answer, the participants receives appraise and vice-versa
for each incorrect answer. At the end of each participants series of questions
the participants receives a score. After each participant has answered all of the questions,
the scores are displayed from highest to lowest in a leaderboard fashion.
The scores are saved into a file which can be viewed later, if the participants wish it,
at the start of the next run of the program.
*******************************************************************************************/

import java.io.*; // Imports the java input/output library.
import javax.swing.*; // Imports the swing library for I/O.
import java.util.Random; // Imports the random library.

class musicquiz
{
    public static void main (String[] param)
    {		
		printmessage("Welcome to The Music Quiz!"); // A window appears with this welcome message.

		int ans = JOptionPane.showConfirmDialog(null, "Would you like to display the scores from last time?", "OLD SCORES", JOptionPane.YES_NO_OPTION); 

		if(ans == JOptionPane.YES_OPTION)
		{
			askForOldScore();
		}
		
		String[] questions = new String[6]; // An array to store the questions of the music quiz.
		questions[0] = "Who was known as The King of Pop?\na) Michael Jackson\nb) Kurt Cobain\nc) Ray Charles";
		questions[1] = "Who was nicknamed The Man in Black?\na) Jerry Lee Lewis\nb) Will Smith\nc)Johnny Cash";
		questions[2] = "Who was referred to as The King of Rock and Roll?\na) Barbara Streisand\nb) Elvis Presley\nc) Kurt Cobain";
		questions[3] = "In Bloom, was a song by which rock band?\na) Pantera\nb) One Direction\nc) Nirvana";
		questions[4] = "My Heart Will Go On, featured in the film Titanic, is sung by which artist?\na) Miley Cyrus\nb) Celine Dion\nc) Cher";
		questions[5] = "Wicked Games, is a song by which artist?\na) The Weeknd\nb) Kanye West\nc) Jay Z";

		String[] fullanswers = new String[6]; // An array to store the correct full answers of the music quiz.
		fullanswers[0] = "a) Michael Jackson";
		fullanswers[1] = "c) Johnny Cash";
		fullanswers[2] = "b) Elvis Presley";
		fullanswers[3] = "c) Nirvana";
		fullanswers[4] = "b) Celine Dion";
		fullanswers[5] = "a) The Weeknd";

		String[] letteranswers = new String[6]; // An array to store the correct letter answers of the music quiz.
		letteranswers[0] = "a";
		letteranswers[1] = "c";
		letteranswers[2] = "b";
		letteranswers[3] = "c";
		letteranswers[4] = "b";
		letteranswers[5] = "a";

		int num = numberofplayers();
	
		String[] playernames = namesofplayers(num);

		int[] scores = quiz(playernames, questions, letteranswers, fullanswers);

		sort(scores, playernames);

		leaderboard(playernames, scores);

		saveScoresToFile(playernames, scores);

		printmessage("Thank you for playing!"); // A window appears with this goodbye message.

		System.exit(0); // Ends the program and exits.
    } // END main


/* *******************************************************************
The method below asks the user for the number of participants.
The value is then returned and passed as an argument to the method, namesofplayers,
and is used as the length of the array, playernames. 
*********************************************************************/

    public static int numberofplayers ()
    {
		String numberofplayers = askquestion("Please enter the number of players.");
		int numofplayers = string_to_int(numberofplayers);
		
		if(numofplayers < 1)
		{
			printmessage("Error! Please enter a number greater than or equal to 1");
		}
		
		return numofplayers;
    } // END numberofplayers


/* *******************************************************************
The method below asks the user to input each participants' name.
A for loop is used to store each participants' name in an array named, playernames.
The array is then returned and passed to many methods.
*********************************************************************/

    public static String[] namesofplayers (int numofplayers)
    {							    
		printmessage("Please enter the name of each player.");

		String[] playernames = new String[numofplayers];

		for(int i = 0; i < playernames.length; i++)
		{
			playernames[i] = askquestion("Player " + (i+1) + ":");
		}

		return playernames;
    } // END namesofplayers


/* *******************************************************************
The method below is the skeletal structure of the quiz.
The method initialises an array named, scores, to store the score of each
participant. A for loops is used to dictacte who's turn it is and within
this for loops is another for loop which asks the question to the participants,
checks the answer given (by passing the answer as an argument to the method, answercheck)
and returns a window with a message declaring whether the participant gave the correct answer or not.
After all questions have been answered, a score is calculated in the method, scorecalculator,
which is then stored in an array and displayed to the participant.
*********************************************************************/

    public static int[] quiz(String[] names, String[] questions, String[] letteranswers, String[] fullanswers)
    {
	int[] scores = new int[names.length];
	for(int x = 0; x < names.length; x++)
	{
		printmessage(names[x] + "'s turn!");

		for(int i = 0; i < 6; i++)
		{
			String ans = askquestion(questions[i]);
			answercheck(ans, letteranswers[i], fullanswers[i]);
		}

		scores[x] = scorecalculator();
		printmessage("Your score is " + scores[x] + ".");
	}

	return scores;
    } // END quiz


/* *******************************************************************
The method below compares the answer given by the user with the correct answer and, in turn, 
return a message declaring whether the participant got the question correct or not.
If not, then the correct answer is displayed.
*********************************************************************/

    public static void answercheck (String answer, String letteranswers, String fullanswers)
    {
		if(answer.equalsIgnoreCase(letteranswers))
		{
			printmessage("Correct!");
		}
		else
		{
			printmessage("Incorrect! The correct answer is " + fullanswers + ".");
		}
    } // END answercheck


/* *******************************************************************
The method below bubblesorts the scores of the players and their respective names,
(if need be) into descending order.
*********************************************************************/

    public static void sort(int[] scores, String[] names)
    {
	final int ONE = 1;
	for(int pass = ONE; pass <= scores.length - ONE; pass++)
	{
		for(int i = 0; i < scores.length - ONE; i++)
		{
			if(scores[i] < scores[i+1])
			{
				swap_scores(scores, i, i+1);
				swap_names(names, i, i+1);
			}
		}
	}
    } // END sort


/* *******************************************************************
The method below swaps the indexes of the array, scores, if the score
in the index selected is lower than the score in the index adjacent to it.
*********************************************************************/

    public static void swap_scores(int[] scores, int i, int z)
    {
		int tmp = scores[i];
		scores[i] = scores [z];
		scores[z] = tmp;
    } // END swap_scores


/* *******************************************************************
The method below swaps the indexes of the array, names, if the score
in the index selected is lower than the score in the index adjacent to it.
*********************************************************************/

    public static void swap_names(String[] names, int i, int z)
    {
		String tmp = names[i];
		names[i] = names[z];
		names[z] = tmp;
    } // END swap_names


/* *******************************************************************
The method below prints the names of the participants' and their respective score,
in descending order. It then displays a congratulatory message for the winner.
*********************************************************************/

    public static void leaderboard (String[] names, int[] scores)
    {
		printmessage("Each player and their respective scores from highest to lowest:");
		for(int i = 0; i < names.length; i++)
		{
			if(scores[i] == 1)
			{
				printmessage(names[i] + ": " + scores[i] + " point.");
			}
			else if(scores[i] == -1)
			{
				printmessage(names[i] + ": " + scores[i] + " point.");
			}
			else
			{
				printmessage(names[i] + ": " + scores[i] + " points.");
			}
		}

		printmessage("Congratulations " + names[0] + "! You are the winner!");
    } // END leaderboard


/* *******************************************************************
A method to calculate the score given to a player 
generated from a random number generator.
*********************************************************************/

    public static int scorecalculator ()
    {
		Random dice = new Random();
		int diceroll = dice.nextInt(100) + 1;		
		return diceroll;
    } // END scorecalculator


    public static void printmessage (String message) // A method to print a given message in an appearing window.
    {
		JOptionPane.showMessageDialog(null, message);
		return;
    } // END printmessage


    public static String askquestion (String userinput) // A method to allow the user to input a string.
    {
		return JOptionPane.showInputDialog(userinput);
    } // END askquestion


    public static int string_to_int (String strng) // A method to convert a string value to an integer value.
    {
		return Integer.parseInt(strng);
    } // END sting_to_int


/* *******************************************************************
The method below saves the scores in the quiz to a file which can
be accessed later if the user wishes.
*********************************************************************/

    public static void saveScoresToFile(String[] names, int[] scores)
    {
    	try
        {
    		PrintWriter writer = new PrintWriter("scores.txt", "UTF-8"); // Makes a new file writer that saves into scores.txt and encodes in UTF-8.

    		for(int k=0; k<names.length; k++)
		{
    			writer.println(names[k]);
    			writer.println("Has scored: " + scores[k]);
    			writer.println();

    			if(k==names.length-1)
			{
    				writer.close();
    			}
    		} // Ends the for loop with the closing of the writer at the end of the file.

    	}
 
	catch(IOException e)
	{
    		JOptionPane.showMessageDialog(null, "Oops, something went wrong with the writing! \n" + e.getMessage()); // Displays an error 
	}														// should the file writer encouter a problem.
    } // END saveScoresToFile


/* *******************************************************************
The method below reads in the scores from the last run of the program
and displays it if the user selected, yes, when asked at the beginning
of the program. If the user selected no, this method will not be called.
If the user selects yes in the beginning, but there were no results from the
previous game then, an error message will be displayed and the program will continue.
*********************************************************************/

    public static void askForOldScore()
    {
    	String oldScore = "";

    	try
        {
    		BufferedReader read = new BufferedReader(new FileReader("scores.txt"));

    		while(read.ready())
		{
    			oldScore = oldScore + read.readLine() + "\n";
    		}

    		read.close();

    		JOptionPane.showMessageDialog(null, oldScore);

    	} 

	catch(IOException e)
	{
    		JOptionPane.showMessageDialog(null, "Oops, something went wrong with the reading! \n" + e.getMessage());
    	}
    } // END askForOldScore

} // END class musicquiz
