package org.example.eventspotlightback.service.description;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.eventspotlightback.dto.internal.description.CreateDescriptionDto;
import org.example.eventspotlightback.dto.internal.description.DescriptionDto;
import org.example.eventspotlightback.exception.EntityNotFoundException;
import org.example.eventspotlightback.mapper.DescriptionMapper;
import org.example.eventspotlightback.model.Description;
import org.example.eventspotlightback.repository.DescriptionRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DescriptionServiceImpl implements DescriptionService {
    private final DescriptionRepository descriptionRepository;
    private final DescriptionMapper descriptionMapper;

    @Override
    public DescriptionDto add(CreateDescriptionDto descriptionDto) {
        Description description = descriptionMapper.toModel(descriptionDto);
        Description savedDescription = descriptionRepository.save(description);
        return descriptionMapper.toDto(savedDescription);
    }

    @Override
    public DescriptionDto update(Long id, CreateDescriptionDto descriptionDto) {
        Description newDescription = descriptionMapper.toModel(descriptionDto);
        newDescription.setId(id);
        descriptionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Description with id " + id + " not found")
        );
        Description savedDescription = descriptionRepository.save(newDescription);
        return descriptionMapper.toDto(savedDescription);
    }

    @Override
    public void deleteById(Long id) {
        descriptionRepository.deleteById(id);
    }

    @Override
    public List<DescriptionDto> findAll() {
        return descriptionMapper.toDto(descriptionRepository.findAll());
    }

    @Override
    public DescriptionDto findById(Long id) {
        Description description = descriptionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Description with id " + id + " not found")
        );
        return descriptionMapper.toDto(description);
    }
}
