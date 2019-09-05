/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betalyricsanalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 *
 * @author landonpalmer
 */
public class LyricsAnalyzer {
    
    public int carlinCounter(String year, Sheet ws, boolean isDecade) {
        
        String testYear = year;
        // for decade search
        if(isDecade) 
            testYear = year.substring(0, 3);
        
        // Carlin words
        int carlinCount = 0;
        String[] carlinWords = {"fuck", "shit", "piss", "cunt", "cocksucker", "motherfucker", "tits"};
        
        String lyrics = "";
        
        // loops thru every cell
        // TODO: more efficient
        for(int r = 0; r < 6142; r++) {
            
            // row/cell creation
            XSSFRow row = (XSSFRow) ws.getRow(r);
            // year selection
            if(row.getCell(0).toString().contains(testYear)) {
                XSSFCell cell = row.getCell(4);

                if(cell != null) {
                    lyrics = cell.toString().toLowerCase();
                    lyrics = cleanLyrics(lyrics);
                    // actual analysis
                    for(String carlinWord : carlinWords) {
                        if(lyrics.contains(carlinWord)) {
                            System.out.println(carlinWord);
                            carlinCount++;
                        }
                    }
                }
        
            }
        }
        return carlinCount;
    }
            
    public int gBannedCounter(String year, Sheet ws, boolean isDecade) throws FileNotFoundException {
        
        String BANNED_WORDS_PATH = "/Users/landonpalmer/Desktop/list_badWords.txt";
        String testYear = year;
        // for decade search
        if(isDecade)
            testYear = year.substring(0, 3);
        
        Set gBannedWordsSet = new HashSet();
 
        Scanner chopper = new Scanner(new File(BANNED_WORDS_PATH));
        // adds google-banned words to arraylist
        ArrayList<String> gBannedWords = new ArrayList();
        while(chopper.hasNextLine()) {
            gBannedWords.add(chopper.nextLine());
        }
        // cumulative number
        int gBannedCount = 0;
        String lyrics = "";
        
        // loops thru every cell
        // TODO: more efficient
        for(int r = 0; r < 6242; r++) {
            
            // row/cell creation
            XSSFRow row = (XSSFRow) ws.getRow(r);
            // year selection
            if(row.getCell(0).toString().contains(testYear)) {
                // Lyrics cell
                XSSFCell cell = row.getCell(4);
                if(cell != null) {
                    // assigns and formats lyrics for analysis
                    lyrics = cell.toString();
                    lyrics = cleanLyrics(lyrics);
                    //System.out.println(row.getCell(3).toString() + " - " + row.getCell(2).toString() + ": " + lyrics);
                    // Analysis:
                    // chops thru each word of lyrics, checks to see
                    // if word is in the gBannedWords list
                    // if so, adds word to set whose size is added to overall gBannedCiount
                    Scanner chopLyrics = new Scanner(lyrics);
                    while(chopLyrics.hasNext()) {
                        String word = chopLyrics.next();
                        for(String gBannedWord : gBannedWords) {
                            
                            if(word.equals(gBannedWord)) {
                                // contains matched words
                                //System.out.println("found:: " + word);
                                gBannedWordsSet.add(word);
                            
                            }
                        }
                        
                    }
                    //System.out.println("Set size:: " + gBannedWordsSet.size() + "\n");
                    gBannedCount += gBannedWordsSet.size();
                    // empty set per song
                    gBannedWordsSet.clear();
                }
        
        
            }
        }
        return gBannedCount;
    }
    
    public int gBannedCounterSingle(String title, Sheet ws) throws FileNotFoundException {
        
        String BANNED_WORDS_PATH = "/Users/landonpalmer/Desktop/list_badWords.txt";
        
        // contains matched words
        Set gBannedWordsSet = new HashSet();
 
        Scanner chopper = new Scanner(new File(BANNED_WORDS_PATH));
        // adds google-banned words to arraylist
        ArrayList<String> gBannedWords = new ArrayList();
        while(chopper.hasNextLine()) {
            gBannedWords.add(chopper.nextLine());
        }
        
        String lyrics = "";
        
        // loops thru every cell
        // TODO: more efficient
        for(int r = 0; r < 6242; r++) {
            
            // row/cell creation
            XSSFRow row = (XSSFRow) ws.getRow(r);
            // if title of excel row matches desired title
            if(row.getCell(2).toString().equals(title)) {
                XSSFCell cell = row.getCell(4);
                if(cell != null) {
                    // formats lyrics for analysis
                    lyrics = cell.toString();
                    lyrics = cleanLyrics(lyrics);
                    System.out.println("cleaned: " + lyrics);
                    // Analysis
                    // chops thru each word of lyrics, checks to see
                    // if word is in the gBannedWords list
                    // if so, adds word to set
                    Scanner chopLyrics = new Scanner(lyrics);
                    while(chopLyrics.hasNext()) {
                        String word = chopLyrics.next();
                        for(String gBannedWord : gBannedWords) {
                            if(word.equals(gBannedWord)) {
                                gBannedWordsSet.add(word);
                                System.out.println("Song contains:: " + word);
                            }
                        }
                    }
                    
                }
                // if song is found, no need to continue
                break;
            }
        }
        return gBannedWordsSet.size();
    
        
        
    }
    
    
    
    public String cleanLyrics(String lyrics) {
        lyrics = lyrics.toLowerCase();
        String cleaned = lyrics.replace(",","");
        cleaned = cleaned.replace("'", "");
        cleaned = cleaned.replace("'s", "");
        cleaned = cleaned.replace(";", "");
        cleaned = cleaned.replace(".", "");
        cleaned = cleaned.replace("?", "");
        cleaned = cleaned.replace("!", "");
        cleaned = cleaned.replace("\"", "");
        cleaned = cleaned.replace("(", "");
        cleaned = cleaned.replace(")", "");

        return cleaned;
    }
    
    public boolean isExplicit(String word) {
        // common mistakes:
        String[] mistakes = {"grass", "glass", "button", "basement", "butterflies", "canal", "analy"};
        for(String mistake : mistakes) {
            if(word.contains(mistake))
                return false;
        }
        
        return true;
    }
    
    
    
    
} // end of class
