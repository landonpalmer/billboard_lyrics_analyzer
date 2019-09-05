/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betalyricsanalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author landonpalmer
 */
public class Scraper {
    
    public String initUserAgent() {
        // Initializes a randomly selected user agent
        // This prevents blacklisting and 403 errors
        
        ArrayList<String> userAgentsList = new ArrayList<String>();

        userAgentsList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        userAgentsList.add("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        userAgentsList.add("Mozilla/5.0 (Windows NT 5.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        userAgentsList.add("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        userAgentsList.add("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        userAgentsList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");

        // random number generation
        // max 5 min 0
        int random = 0 + (int) (Math.random() * ((5 - 0) + 1));
        
        return (userAgentsList.get(random));
    }
    
    public String getLyrics(String title, String artist) throws IOException {
        
        // Lyrics provided by lyrics wikia
        // Scraping methods have been tweaked
        // to ensure that servers don't face overload
        
        String lyrics = "";
        
        
        try{
            
            String url = cleanLyricsWikiaURL(artist, title);
            
            String userAgent = initUserAgent(); // or "Mozilla/17.0"
            Document d = Jsoup.connect(url).userAgent(userAgent).maxBodySize(0).get();
            
            lyrics = d.select(".lyricbox").get(0).text();
            
            // Catches error from unavailable lyrics via lyrics wikia
            // Retries using oldielyrics.com
        } catch(IOException | IndexOutOfBoundsException e) {
            /*
            String tryAgainTitle = title;
            String tryAgainArtist = artist;
            //JOptionPane.showMessageDialog(this, "Error connecting to lyrics for selected song. This will be fixed in the future.", "ERROR:", JOptionPane.ERROR_MESSAGE);
            
            String url = cleanOldiesLyricsURL(artist, title);
        
            Document f = Jsoup.connect(url).userAgent(initUserAgent()).maxBodySize(0).get();
            
            lyrics = f.select("#lyrics").get(0).text();
            
            */
            System.out.println("LYRICS UNAVAILABLE");
            
        }
            return lyrics;
        
    }
    
    public void cleanUpLyrics(String lyrics) {
        // TODO: every time there is a capital, start a new line (insert linebreak)
        // Instructions:
        // 1. Chop up every word using scanner
        // 2. If word has a capital letter in the beginning then
        //    start a new line (i.e. insert \n before it).
    }
    
    public String cleanLyricsWikiaURL(String testArtist, String testTitle) {
            // from artist: replace spaces with underscore
            //              and get rid of feature in artist name
            //              as well as capitalize
            //              and switch "and" to "&" (%26)
            testArtist = testArtist.substring(0, 1).toUpperCase() + testArtist.substring(1);
            testArtist = testArtist.replace(" ", "_");
            
            if(testArtist.contains("featuring")) {
                testArtist = testArtist.substring(0, testArtist.indexOf("featuring") - 1 );
            }
            if(testArtist.contains("_and_")) {
                testArtist = testArtist.replace("and", "%26");
            }
            
            // Specific song artist cases -----------------------
            
            // Unknown year
            if(testArtist.equals("Carl_Dobkins_Jr.")) {
                testArtist = "Carl_Dobkins,_Jr.";
            }
            
            // --------------------------------------------------
        
        
            // from title: replace spaces with underscore
            //             and delete surrounding quotes
            testTitle = testTitle.replace(" ", "_");
            testTitle = testTitle.replace("\"", "");
            testTitle = testTitle.replace(" ", "_");
            testTitle = testTitle.replace(",", "");
            
            // Specific song title cases -----------------------
            
            // 1954
            if(testTitle.equals("Oh!_My_Pa-Pa")) {
                testTitle = "Oh!_My_Pa-Pa_(O_Mein_Papa)";
            }
            
            // 2017
            if(testTitle.equals("Look_at_Me!")) {
                testTitle = "Look_at_Me";
            }
            
            if(testTitle.equals("iSpy")) {
                testTitle = "ISpy";
            }
            
            
            if(testTitle.equals("(Till)_I_Kissed_You")) {
                testTitle = "Till_I_Kissed_You";
            }
            
            if(testTitle.equals("Return_to_Me")) {
                testTitle = "Return_To_Me_(Ritorna-Me)";
            }
            
            
            
            // Last step
            String url = "http://lyrics.wikia.com/wiki/" + testArtist + ":" + testTitle;
            System.out.println("URL: " + url + "\n");
        
            return url;
        
        
        
    }
    
    public String cleanOldiesLyricsURL(String artist, String title) {
        
        String tryAgainTitle = title;
        String tryAgainArtist = artist;
        
        // Title formatting:
        // Make it all lower-case
        // Get rid of paranthesis
        // Get rid of quotes
        // Get rid of apostrophes
        
        tryAgainTitle = tryAgainTitle.toLowerCase();
        tryAgainTitle = tryAgainTitle.replace(" ", "_");
        tryAgainTitle = tryAgainTitle.replace("(", "");
        tryAgainTitle = tryAgainTitle.replace(")", "");
        tryAgainTitle = tryAgainTitle.replace("\"", "");
        tryAgainTitle = tryAgainTitle.replace("'", "");

        // Specific song title cases -----------------------

        if(tryAgainTitle.equals("oh_baby_mine_i_get_so_lonely")) {
            tryAgainTitle = "i_get_so_lonely_when_i_dream_about_you";
        }

        if(tryAgainTitle.equals("on_top_of_old_smoky")) {
            tryAgainTitle = "on_top_of_old_smokey";
        }


        // -------------------------------------------------

        // Artist formatting:
        // replace spaces with underscores
        // lowercase it all

        tryAgainArtist = tryAgainArtist.replace(" ", "_");
        tryAgainArtist = tryAgainArtist.toLowerCase();

        if(tryAgainArtist.contains("featuring")) {
            tryAgainArtist = tryAgainArtist.substring(0, tryAgainArtist.indexOf("featuring") - 1 );
        }


        String url = "https://www.oldielyrics.com/lyrics/" + tryAgainArtist + "/" + tryAgainTitle + ".html";
        System.out.println("\n" + "URL: " + url);
        
        return url;
        
        
    }
    
    public ArrayList<String> scrapeSiteFor(String url, String container, String child) {
        // Scrapes a website for an element based on:
        // url, info container, and info child
        // returns a list of such elements
        String userAgent = initUserAgent();

        ArrayList<String> selectedList = new ArrayList<String>();

        try {
            // connector
            // Document variable to connect to specified url
            // userAgent put in for less sketch
            // maxBodySize(0) allows for unlimited body size of elements
            Document d = Jsoup.connect(url).userAgent(userAgent).maxBodySize(0).get();

            // Element class variable for jSoup to go through items
            // d.select will select items with (parent.classname) or (parent#idname)
            Elements temp = d.select(container);

            // Loops through elements within created Elements class (temp)
            // this is the functional part, html data to text
            int i = 0;
            for (Element ele : temp) {
                i++;
                String selected = ele.getElementsByClass(child).first().text();
                selectedList.add(selected);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return selectedList;
    }
    
    public ArrayList<Song> getSongsList(int year) {
        
        // song variable declaration
        ArrayList <Song> songs = new ArrayList();
        String rank = "";
        String songName = "";
        String artistName = "";
        
        // random userAgent to avoid blacklist
        String userAgent = initUserAgent();
        
        // avoids blacklist from server request overload
        waitTime(200);

        // url to scrape for Billboard list
        String url = "https://en.wikipedia.org/wiki/Billboard_Year-End_Hot_100_singles_of_" + year;
        
        // connector
        // Document variable to connect to specified url
        // maxBodySize(0) allows for unlimited body size of elements
        
        try{
            
            Document d = Jsoup.connect(url).userAgent(userAgent).maxBodySize(0).get();
            
            // Loops through elements within created Elements class (temp)
            // This is the functional part: html data to String text
            
            // Conditional applied since some years have two tables
            Element table;
            
            if(year == 1970 || year == 2012 || year == 2013 ) {
                table = d.select("table").get(1); //select the first table.
            }
            
            else {
                table = d.select("table").get(0); //select the first table.
            }
            
            Elements rows = table.select("tr");
    
            // Loops through rows of table to collect data from each one
            Iterator<Element> rowIterator = rows.iterator();
            // Goes past headers
            rowIterator.next();
            
            // testing for 1958
            int rowCounter = 0;
            boolean endScrape = false;
            
            while (rowIterator.hasNext() && !endScrape) {
                
                Element row = rowIterator.next();
                
                Elements cols = row.select("td");
                
                // This portion is done within each row
                // years 1951-1981 and 2018 have rank in table as "td"
                // years 1982-2018 have rank in table as "th"
                
                // year 1958 has one more artist/song row than ranks
                
                if(year > 1981) {
                    Elements rankHead = row.select("th");
                    rank = rankHead.get(0).text();
                    songName = cols.get(0).text();
                    artistName = cols.get(1).text();
                    //Testing
                    //System.out.println("Rank: " + rank + "/" + "Title: " + songName + "/ Artist: " +
                            //artistName);
                }
                
                // the tie case
                else if(year == 1958 && rowCounter == 49) {
                    rank = "50";
                    songName = cols.get(1).text();
                    artistName = cols.get(2).text();
                
                    endScrape = true;
                }
                
                // if before 1981 and not tie case
                else {
                    rank = cols.get(0).text();
                    songName = cols.get(1).text();
                    artistName = cols.get(2).text();
                }
                
                // creates song object (w/ year, rank, title, artist, no lyrics)
                // and adds it to song object list
                
                Song newSong = new Song(year + "", rank, songName, artistName, "");
                songs.add(newSong);
                
                // testing
               // System.out.println(newSong.toString());
               
               rowCounter++;
            }
            
            
           } catch(IOException e) {
            e.printStackTrace();
            }
        
        
        return songs;
        
    }
    
    public void waitRandomTime(int timeMS) {
       
        // wait between scrapes to not overload servers
        Random randomGenerator = new Random();
            int sleepTime = randomGenerator.nextInt(timeMS);
            try{
                Thread.sleep(sleepTime); //sleep for x milliseconds
            }catch(Exception e){
                // only fires if topic is interruped by another process, should never happen
            }
    }
    
    public void waitTime(int timeMS) {
       
        
            int sleepTime = timeMS;
            try{
                Thread.sleep(sleepTime); //sleep for n milliseconds
            }catch(Exception e){
                // only fires if topic is interruped by another process
            }
    }
    
    
} // end of class
