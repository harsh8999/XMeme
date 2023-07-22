package com.crio.starter.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeDto;
import com.crio.starter.exchange.MemeRequestDto;
import com.crio.starter.repository.MemeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemeServiceImplementation implements MemeService {

    @Autowired
    private MemeRepository memeRepository;

    @Autowired
    protected ModelMapper modelMapper;

    @Override
    public boolean isDuplicateMeme(MemeRequestDto memeRequestDto) {
        MemeEntity memeEntity = modelMapper.map(memeRequestDto, MemeEntity.class);
        return memeRepository.existsByNameAndUrlAndCaption(memeEntity.getName(), memeEntity.getUrl(), memeEntity.getCaption());
    }

    @Override
    public String addMeme(MemeRequestDto memeRequestDto) {
        MemeEntity memeEntity = modelMapper.map(memeRequestDto, MemeEntity.class);
        memeEntity.setCreatedAt(LocalDateTime.now()); // Set the createdAt value
        MemeEntity savedMeme = memeRepository.save(memeEntity);
        return savedMeme.getId();
    }

    @Override
    public List<MemeDto> getMemes() {
        List<MemeEntity> memeEntities = memeRepository.findTop100ByOrderByCreatedAtDesc();
        return memeEntities.stream()
                .map(e -> modelMapper.map(e, MemeDto.class))
                .collect(Collectors.toList());

        // PageRequest pageRequest = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "_id"));
        // List<MemeEntity> memes = memeRepository.findAll(pageRequest).getContent();
        // return memes.stream()
        //         .map(e -> modelMapper.map(e, MemeDto.class))
        //         .collect(Collectors.toList());
    }

    @Override
    public MemeDto getMemeById(String id) {
        MemeEntity meme = memeRepository.findById(id).orElse(null);
        if(meme == null) {
            return null;
        }
        
        return modelMapper.map(meme, MemeDto.class);
    }
    
}
