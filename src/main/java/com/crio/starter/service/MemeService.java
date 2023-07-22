package com.crio.starter.service;

import java.util.List;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.exchange.MemeRequestDto;

public interface MemeService {
    /**
     * Checks if a meme with the same content already exists.
     * 
     * @param memeRequestDto the MemeRequestDto object containing the meme details
     * @return true if a duplicate meme is found, false otherwise
     */
    boolean isDuplicateMeme(MemeRequestDto memeRequestDto);
    
    /**
     * Adds a new meme to the collection.
     * 
     * @param memeRequestDto the MemeRequestDto object containing the meme details
     * @return the ID of the newly added meme
     */
    String addMeme(MemeRequestDto memeRequestDto);
    
    /**
     * Retrieves all the memes.
     * 
     * @return a list of MemeDto objects representing the memes
     */
    List<MemeDto> getMemes();
    
    /**
     * Retrieves a meme by its ID.
     * 
     * @param id the ID of the meme to retrieve
     * @return the MemeDto object representing the meme with the specified ID, or null if not found
     */
    MemeDto getMemeById(String id);
}
