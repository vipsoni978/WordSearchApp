package com.wordsearch.controllers;


import java.util.Arrays;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordsearch.services.GridService;

@RestController
@CrossOrigin(origins = "http://localhost:1234")
public class WordSearchController {

	@Autowired
	GridService gridService;

	@GetMapping("/wordsearch")
	public String getGrid(@PathParam("gridsize") int gridsize,@PathParam("wordlist") String wordlist) {

		List<String> words = Arrays.asList(wordlist.split(","));
            words.forEach(System.out::println);
		char[][] grid = gridService.fillgrid(gridsize, words);

		String gridtostring = "";

		for (int i = 0; i < gridsize; i++) {
			for (int j = 0; j < gridsize; j++) {
				gridtostring = gridtostring + grid[i][j] + " ";
			}
			gridtostring =  gridtostring + "\n";
		}

		return gridtostring;

	}

}
