package Do.cs146.project3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Rahul Krishan & Anthony Do
 * The program is tested using the following Tests to ensure that the program is running as expected
 * and to test and compare the mazes.
 *
 */

class MazeTests {

	/**
	 * the following Test creates a random maze of size 26, it then saves that maze to a file
	 * the saved maze is then read from a file and a maze is generated from the data in the file
	 * the two are compared to check if the mazes are being stored into file correctly and also
	 * being read from files correctly.
	 * @throws IOException is thrown if the reading file or writing file is not open successfully
	 */
	@Test
	void readStoreToFile() throws IOException {
		File file = new File("output.txt");
		file.delete(); // first delete the file so that there is no possibility of data in the file from previous test
		Maze maze = new Maze(26);
		maze.printMaze();
		
		String expectedMaze = "";
		String outputMaze = "";
		Scanner scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine(); //skips the first line of file
		
		while(scan.hasNextLine()) //keep getting the data from file until end of file
		{
			expectedMaze +=scan.nextLine();
		}
		Maze maze2 = new Maze(file);
		maze2.printMaze();
		scan.close();
		
		scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine();
		while(scan.hasNextLine())
		{
			outputMaze += scan.nextLine();
		}
		scan.close();
		assertEquals(expectedMaze,outputMaze);
	}
	/**
	 * the following test reads a maze that was provided to testing purposes, the program reads this maze
	 * replicates it, and outputs the newly created maze to a file. then the mazes in the files are
	 * compared to see if the program is reading, replicating and storing mazes correctly.
	 * @throws IOException is thrown if the reading file or writing file is not open successfully
	 */
	@Test
	void readFile() throws IOException {
		File file = new File("maze4.txt");
		Maze maze = new Maze(new File("maze4.txt"));
		maze.printMaze();
		String expectedMaze = "";
		String outputMaze = "";
		
		Scanner scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine();
		while(scan.hasNextLine())
		{
			expectedMaze +=scan.nextLine();
		}
		file = new File("output.txt");
		scan.close();
		
		scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine();
		while(scan.hasNextLine())
		{
			outputMaze += scan.nextLine();
		}
		scan.close();
		assertEquals(expectedMaze,outputMaze);
	}

	/**
	 * the following test reads a maze that was provided to testing purposes, the program reads this maze
	 * replicates it, and outputs the newly created maze to a file. then the mazes in the files are
	 * compared to see if the program is reading, replicating and storing mazes correctly.
	 * @throws IOException is thrown if the reading file or writing file is not open successfully
	 */
	@Test
	void readFile2() throws IOException {
		File file = new File("maze20.txt");
		Maze maze = new Maze(new File("maze20.txt"));
		maze.printMaze();
		
		String expectedMaze = "";
		String outputMaze = "";
		
		Scanner scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine();
		while(scan.hasNextLine())
		{
			expectedMaze +=scan.nextLine();
		}
		file = new File("output.txt");
		scan.close();
		
		scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine();
		while(scan.hasNextLine())
		{
			outputMaze += scan.nextLine();
		}
		scan.close();
		assertEquals(expectedMaze,outputMaze);
	}
	
	/**
	 * the test runs BFS and DFS algorithm to solve the maze, it then compares the path lengths of 
	 * each algorithm. if equal, it is an indicator the both algorithm should have gotten the same solution
	 * hence, running as expected.  
	 * @throws IOException is thrown if the reading file or writing file is not open successfully
	 */
	@Test
	void comparePathLength() throws IOException
	{
		Maze maze = new Maze(10);
		DFS.DFSearch(maze);
		int dfsPathLength = maze.pathLength;
		BFS.BFSearch(maze);
		int bfsPathLength = maze.pathLength;
		assertEquals(dfsPathLength,bfsPathLength);
	}

	
	/**
	 * The following test runs BFS and DFS algorithms of the maze and them compares the path
	 * printed out by these algorithms. if the path from both algorithms is equal, it ensures 
	 * that both algorithms are correctly producing the same solution
	 * @throws IOException is thrown if the reading file or writing file is not open successfully
	 */
	
	@Test
	void pathCompare() throws IOException
	{
		Maze maze = new Maze(6);
		maze.printMaze();
		DFS.DFSearch(maze);
		maze.printPath();
		BFS.BFSearch(maze);
		maze.printPath();
		
		String dfsPath = "";
		String bfsPath = "";
		File file = new File("output.txt");
		Scanner scan = new Scanner(file);
		int size = 0;
		
		if(scan.hasNextInt()) size = scan.nextInt();
		scan.nextLine();
		int lineCounter = 0;
		while(scan.hasNextLine() && lineCounter <= size*2 )
		{
			System.out.println(scan.nextLine());
			lineCounter++;
		}
		
		if(scan.hasNextLine()) scan.nextLine();
		lineCounter = 0;
		while(scan.hasNextLine()  && lineCounter <= size*2 )
		{
			dfsPath += scan.nextLine();
			lineCounter++;
		}
		
		if(scan.hasNextLine()) scan.nextLine();
		lineCounter = 0;
		while(scan.hasNextLine()  && lineCounter <= size*2 )
		
		{
			bfsPath += scan.nextLine();	
			lineCounter++;
		}
		scan.close();
		assertEquals(bfsPath,dfsPath);
	}
	
	/**
	 * this test reads a maze and DFS solution provided for testing purposes and it then creates a
	 * maze based on the provided maze. The newly created maze is them solved, and the solution of 
	 * the new created maze is compared to the provided solution to check if the code is executing 
	 * properly.
	 * @throws IOException is thrown if the reading file or writing file is not open successfully
	 */
	@Test
	void filePathCompareDFS() throws IOException
	{
		File file = new File("output.txt");
		file.delete();
		file = new File("maze8.txt");
		
		Maze maze = new Maze(file);
		DFS.DFSearch(maze);
		maze.printPath();
		
		String expectedPath = "";
		String outputPath = "";
		file = new File("mazeSolution8.txt");
		
		Scanner scan = new Scanner(file);
		while(scan.hasNextLine())
		{
			expectedPath +=scan.nextLine();
		}
		scan.close();
		file = new File("output.txt");
		
		scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine();
		while(scan.hasNextLine())
		{
			outputPath += scan.nextLine();
		}
		scan.close();
		assertEquals(expectedPath,outputPath);
	}

	/**
	 * this test reads a maze and BFS solution provided for testing purposes and it then creates a
	 * maze based on the provided maze. The newly created maze is them solved, and the solution of 
	 * the new created maze is compared to the provided solution to check if the code is executing 
	 * properly.
	 * @throws IOException is thrown if the reading file or writing file is not open successfully
	 */
	@Test
	void filePathCompareBFS() throws IOException
	{
		File file = new File("output.txt");
		file.delete();
		file = new File("maze4.txt");
		
		Maze maze = new Maze(file);
		DFS.DFSearch(maze);
		maze.printPath();
		
		String expectedPath = "";
		String outputPath = "";
		file = new File("mazeSolution4.txt");
		Scanner scan = new Scanner(file);
		
		while(scan.hasNextLine())
		{
			expectedPath +=scan.nextLine();
		}
		scan.close();
		file = new File("output.txt");
		
		scan = new Scanner(file);
		if(scan.hasNextLine()) scan.nextLine();
		while(scan.hasNextLine())
		{
			outputPath += scan.nextLine() ;
		}
		scan.close();
		assertEquals(expectedPath,outputPath);
	}
	
	
}
