package com.wordsearch.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import com.wordsearch.entity.Coordinate;

import org.springframework.stereotype.Service;



@Service
public class GridService {
	
	private enum Direction {
		HORIZONTAL,
		VERTICAL,
		DIAGONAL,
		HORIZONTAL_INVERSE,
		VERTICAL_INVERSE,
		DIAGONAL_INVERSE
	}


	public char[][] fillgrid(int gridSize,List<String> words) {
		List<Coordinate> coordinates = new ArrayList<>();
		List<Direction> directions = Arrays.asList(Direction.values());
		char[][] contents = new char[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				coordinates.add(new Coordinate(i, j));
				contents[i][j] = '_';
			}

		}
		Collections.shuffle(coordinates);
		for (String word : words) {
			for (Coordinate coordinate : coordinates) {
				int x = coordinate.getX();
				int y = coordinate.getY();
				Direction selecteddirection = getdirectiontofit(word, coordinate,directions,contents);
				if(selecteddirection==null) continue;
				
				switch(selecteddirection)
				{
				
				case HORIZONTAL:
					for (char c : word.toCharArray()) {
					contents[x][y++] = c;
				}break;
				case VERTICAL:
					for (char c : word.toCharArray()) {
						contents[x++][y] = c;
					}break;
				case DIAGONAL:
					for (char c : word.toCharArray()) {
						contents[x++][y++] = c;
					}break;
					
				case HORIZONTAL_INVERSE:
					for (char c : word.toCharArray()) {
					contents[x][y--] = c;
				}break;
				case VERTICAL_INVERSE:
					for (char c : word.toCharArray()) {
						contents[x--][y] = c;
					}break;
				case DIAGONAL_INVERSE:
					for (char c : word.toCharArray()) {
						contents[x--][y--] = c;
					}break;
				
				
				}break;
				
			}
		}
		randomfillgrid(contents);
		return contents;
	}

	private Direction getdirectiontofit(String word, Coordinate coordinate,List<Direction> directions,char[][] contents) {
		

		Collections.shuffle(directions);

		for (Direction direction : directions) {
			boolean foo = check(coordinate, word, direction,contents);
			if(foo==true) return direction;
		}

		return null;
	}

	private boolean check(Coordinate coordinate, String word, Direction direction,char[][] contents) {

		int gridSize = contents[0].length;
		switch (direction) {

		case HORIZONTAL: 
			if (coordinate.getY() + word.length() >= gridSize)
				return false;
			for (int i = 0; i < word.length(); i++) {
				if (contents[coordinate.getX()][coordinate.getY() + i] != '_')
					return false;
			}
			break;
			
		case VERTICAL: 
			if (coordinate.getX() + word.length() >= gridSize)
				return false;
			for (int i = 0; i < word.length(); i++) {
				if (contents[coordinate.getX()+i][coordinate.getY()] != '_')
					return false;
			}
			break;
		   

		case DIAGONAL: 
			if (coordinate.getY() + word.length() >= gridSize || coordinate.getX() + word.length() >= gridSize)
				return false;
			for (int i = 0; i < word.length(); i++) {
				if (contents[coordinate.getX()+i][coordinate.getY()+i] != '_')
					return false;
			}
			break;
			
			
		case HORIZONTAL_INVERSE: 
			if (coordinate.getY()<word.length())
				return false;
			for (int i = 0; i < word.length(); i++) {
				if (contents[coordinate.getX()][coordinate.getY() - i] != '_')
					return false;
			}
			break;
			
		case VERTICAL_INVERSE: 
			if (coordinate.getX()<word.length())
				return false;
			for (int i = 0; i < word.length(); i++) {
				if (contents[coordinate.getX()-i][coordinate.getY()] != '_')
					return false;
			}
			break;
		   

		case DIAGONAL_INVERSE: 
			if (coordinate.getY()<word.length() || coordinate.getX() < word.length())
				return false;
			for (int i = 0; i < word.length(); i++) {
				if (contents[coordinate.getX()-i][coordinate.getY()-i] != '_')
					return false;
			}
			break;
			
		}
		return true;
	}

	public void displaygrid(char[][] contents) {

		int gridSize = contents.length;
		for (int i = 0; i < gridSize; i++) {

			for (int j = 0; j < gridSize; j++) {
				System.out.print(contents[i][j]+ " ");
			}
			System.out.println("");
		}

	}
	
	public void randomfillgrid(char[][] contents) {
		
		int gridSize = contents[0].length;
		String randomalpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if(contents[i][j] == '_') {
					int randindex = ThreadLocalRandom.current().nextInt(0, 25);
				contents[i][j] = randomalpha.charAt(randindex);
				}
			}

		}
		
		
	}

	
	
	
	
	
	
	

}
