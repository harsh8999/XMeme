package com.crio.starter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.exchange.MemeRequestDto;
import com.crio.starter.repository.MemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

@DisplayName("Meme Service Test")
public class MemeServiceTest {
    
    @Mock
    private MemeRepository memeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private MemeServiceImplementation memeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        memeService.modelMapper = new ModelMapper();
    }    

    @Test
    public void addMemeTest() {
        MemeRequestDto memeRequestDto = new MemeRequestDto();
        MemeEntity savedMemeEntity = new MemeEntity();
        savedMemeEntity.setId("1");

        when(memeRepository.save(any(MemeEntity.class))).thenReturn(savedMemeEntity);

        String savedId = memeService.addMeme(memeRequestDto);

        assertEquals(savedMemeEntity.getId(), savedId);

    }

    @Test
    public void testGetMemes() {
        MemeEntity memeEntity1 = new MemeEntity();
        memeEntity1.setId("1");
        MemeEntity memeEntity2 = new MemeEntity();
        memeEntity2.setId("2");
        List<MemeEntity> memeEntities = Arrays.asList(memeEntity1, memeEntity2);

        when(memeRepository.findAll()).thenReturn(memeEntities);

        List<MemeDto> memeDtos = memeService.getMemes();

        assertEquals(memeEntities.size(), memeDtos.size());
    }

    @Test
    public void testGetMemeById() {
        String id = "1";
        MemeEntity memeEntity = new MemeEntity();
        memeEntity.setId(id);

        when(memeRepository.findById(id)).thenReturn(Optional.of(memeEntity));

        MemeDto memeDto = memeService.getMemeById(id);

        assertNotNull(memeDto);
    }

    @Test
    public void testGetMemeById_InvalidId() {
        String id = "1";

        when(memeRepository.findById(id)).thenReturn(Optional.empty());

        MemeDto memeDto = memeService.getMemeById(id);

        assertNull(memeDto);
    }
}
