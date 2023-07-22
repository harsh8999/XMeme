package com.crio.starter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.exchange.MemeRequestDto;
import com.crio.starter.exchange.MemeResponseDto;
import com.crio.starter.service.MemeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("Meme Controller Test")
public class MemeControllerTest {

    @Mock
    private MemeServiceImplementation memeService;

    @InjectMocks
    private MemeController memeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addMemeTest() {
        MemeRequestDto memeRequestDto = new MemeRequestDto();
        String memeId = "1";
        MemeResponseDto expectedResponseDto = new MemeResponseDto("1");

        when(memeService.addMeme(any(MemeRequestDto.class))).thenReturn(memeId);

        ResponseEntity<MemeResponseDto> actualResponseEntity = memeController.addMeme(memeRequestDto);

        assertEquals(HttpStatus.CREATED, actualResponseEntity.getStatusCode());
        assertEquals(expectedResponseDto, actualResponseEntity.getBody());
    }

    @Test
    public void addMeme_WithDuplicatePayload_ShouldReturnConflict() {
        // Create a sample meme with the same payload
        MemeRequestDto memeRequestDto = new MemeRequestDto();
        memeRequestDto.setName("Test Meme");
        memeRequestDto.setCaption("Funny meme");
        memeRequestDto.setUrl("https://example.com/meme.jpg");

        // Mock the repository to return true for duplicate check
        when(memeService.isDuplicateMeme(memeRequestDto)).thenReturn(true);

        // Perform the POST request
        ResponseEntity<MemeResponseDto> response = memeController.addMeme(memeRequestDto);

        // Assert that the response has 409 status code
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void getMemesTest() {
        MemeDto memeDto1 = new MemeDto();
        MemeDto memeDto2 = new MemeDto();
        List<MemeDto> expectedMemes = new ArrayList<>();
        expectedMemes.add(memeDto1);
        expectedMemes.add(memeDto2);
        
        when(memeService.getMemes()).thenReturn(expectedMemes);

        ResponseEntity<List<MemeDto>> actuaResponseEntity= memeController.getMemes();

        assertEquals(HttpStatus.OK, actuaResponseEntity.getStatusCode());
        assertEquals(expectedMemes, actuaResponseEntity.getBody());

    }

    @Test
    @DisplayName("Get Meme by Valid Id")
    public void getMemeByIdValidTest() {
        MemeDto expMemeDto = new MemeDto();
        String memeId = "1";
        
        when(memeService.getMemeById(memeId)).thenReturn(expMemeDto);

        ResponseEntity<MemeDto> responseEntity = memeController.getMemeById(memeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expMemeDto, responseEntity.getBody());
    }

    @Test
    @DisplayName("Get Meme by Valid Id")
    public void getMemeByIdInValidTest() {
        String memeId = "100";
        
        when(memeService.getMemeById(memeId)).thenReturn(null);

        ResponseEntity<MemeDto> actualResponseEntity = memeController.getMemeById(memeId);

        assertEquals(HttpStatus.NOT_FOUND, actualResponseEntity.getStatusCode());
        assertEquals(null, actualResponseEntity.getBody());
    }
}
