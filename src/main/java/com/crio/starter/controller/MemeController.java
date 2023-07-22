package com.crio.starter.controller;

import java.util.List;
import javax.validation.Valid;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.exchange.MemeRequestDto;
import com.crio.starter.exchange.MemeResponseDto;
import com.crio.starter.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemeController {

    @Autowired
    private MemeService memeService;

    @PostMapping("/memes")
    public ResponseEntity<MemeResponseDto> addMeme(@Valid @RequestBody MemeRequestDto memeRequestDto) {
        if(memeService.isDuplicateMeme(memeRequestDto)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        String newMemeId = memeService.addMeme(memeRequestDto);
        return new ResponseEntity<>(new MemeResponseDto(newMemeId), HttpStatus.CREATED);
    }


    // @PostMapping("/memes/multi")
    // public ResponseEntity<List<MemeResponseDto>> addMeme(@RequestBody List<MemeDto> memeDto) {
    //     List<MemeResponseDto> responseDto = new ArrayList<>();
    //     for(MemeDto m: memeDto) {
    //         responseDto.add(new MemeResponseDto(memeService.addMeme(m)));
    //     }
    //     return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    // } 

    @GetMapping("/memes")
    public ResponseEntity<List<MemeDto>> getMemes() {
        return new ResponseEntity<>(memeService.getMemes(), HttpStatus.OK);
    }

    @GetMapping("/memes/{id}")
    public ResponseEntity<MemeDto> getMemeById(@PathVariable String id) {
        MemeDto meme = memeService.getMemeById(id);
        return meme != null ? new ResponseEntity<>(meme, HttpStatus.OK) 
                            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
