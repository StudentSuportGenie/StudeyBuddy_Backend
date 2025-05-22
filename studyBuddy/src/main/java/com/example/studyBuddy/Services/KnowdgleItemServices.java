package com.example.studyBuddy.Services;

import com.example.studyBuddy.DTO.KnowledgeableDTO;
import com.example.studyBuddy.Models.knowdgleItems;
import com.example.studyBuddy.Repo.knowdgleItemRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class KnowdgleItemServices {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private knowdgleItemRepo knowdgleItemRepo;

    public KnowledgeableDTO addKnowdgleItem(KnowledgeableDTO knowdgleItemDTO) {
        knowdgleItems findknowgdleItem =  knowdgleItemRepo.findByKnowdgleItemTitle(knowdgleItemDTO.getKnowdgleItemTitle()).orElse(null);
        if (Objects.equals(findknowgdleItem.getKnowdgleItemTitle(), knowdgleItemDTO.getKnowdgleItemTitle()) && Objects.equals(knowdgleItemDTO.getKnowdgleItemtype(),findknowgdleItem.getKnowdgleItemtype()) ){
            throw new IllegalStateException("Knowdgle item already exists");
        }
        knowdgleItemRepo.save(findknowgdleItem);
        return modelMapper.map(knowdgleItemDTO, KnowledgeableDTO.class);
    }

    public List<KnowledgeableDTO> getAllKnowdgleItems() {
        List<knowdgleItems> semimetallic = knowdgleItemRepo.findAll();

        if(semimetallic.isEmpty()) {
            throw new IllegalStateException("No knowledge items found");
        }
        return semimetallic.stream()
                .map(item -> modelMapper.map(item, KnowledgeableDTO.class))
                .collect(Collectors.toList());
    }

    public KnowledgeableDTO deleteKnowdgleItem(int knowledgeItemId) {
        knowdgleItems find_item = knowdgleItemRepo.findById(knowledgeItemId).orElse(null);
        if (find_item != null) {
            knowdgleItemRepo.delete(find_item);
            return modelMapper.map(find_item, KnowledgeableDTO.class);
        }
        throw new IllegalStateException("Knowledge item not found");
    }

    public KnowledgeableDTO updateKnowdgleItem(KnowledgeableDTO knowledgeItemDTO) {
        knowdgleItems find_Update = knowdgleItemRepo.findById(knowledgeItemDTO.getKnowdgleItemId()).orElse(null);
        if(find_Update == null) {
          throw new IllegalStateException("Knowledge item not found");
        }
        find_Update.setKnowdgleItemDescription(knowledgeItemDTO.getKnowdgleItemDescription());
        find_Update.setKnowdgleItemTitle(knowledgeItemDTO.getKnowdgleItemTitle());
        knowdgleItemRepo.save(find_Update);
        return modelMapper.map(find_Update, KnowledgeableDTO.class);
    }
}
