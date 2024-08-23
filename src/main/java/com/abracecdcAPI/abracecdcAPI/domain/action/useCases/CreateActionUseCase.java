package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.dto.CreateActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category_action.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;

@Service
public class CreateActionUseCase {

  @Autowired
  private ActionRepository actionRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private OrganizerRepository organizerRepository;

  public ActionEntity execute(CreateActionDTO createActionDTO) {

    Optional<Address> optionalAddress = addressRepository.findById(createActionDTO.getAddressId());
    Optional<CategoryEntity> optionalCategory = categoryRepository.findById(createActionDTO.getCategoryId());
    Optional<OrganizerEntity> optionalOrganizer = organizerRepository.findById(createActionDTO.getOrganizerId());

    if (optionalAddress.isEmpty()) {
      throw new RuntimeException("adress not found");
    }

    if (optionalCategory.isEmpty()) {
      throw new RuntimeException("category not found");
    }

    if (optionalOrganizer.isEmpty()) {
      throw new RuntimeException("organizer not found");
    }

    var actionEntity = ActionEntity.builder()
        .addressEntity(optionalAddress.get())
        .organizerEntity(optionalOrganizer.get())
        .categoryEntity(optionalCategory.get())
        .title(createActionDTO.getTitle())
        .subtitle(createActionDTO.getSubtitle())
        .description(createActionDTO.getDescription())
        .dateTime(createActionDTO.getDateTime())
        .registers(createActionDTO.getRegisters())
        .donationsAction(createActionDTO.getDonationsAction())
        .build();

    return this.actionRepository.save((actionEntity));
  }
}
