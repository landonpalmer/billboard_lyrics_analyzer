/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package betalyricsanalyzer;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author landonpalmer
 */
public class DataModel implements Serializable{
    private HashMap<String, Song> songObjects;

    public DataModel(HashMap<String, Song> songObjects) {
        this.songObjects = songObjects;
    }
    
    public HashMap<String, Song> getSongObjects() {
        return songObjects;
    }
}
