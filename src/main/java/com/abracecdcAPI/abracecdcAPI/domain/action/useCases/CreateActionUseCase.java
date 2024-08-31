package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.dto.CreateActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.exceptions.ActionAlredyExistsException;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

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
    Optional<ActionEntity> actionOptional = actionRepository.findByTitleAndSubtitle(createActionDTO.getTitle(),
        createActionDTO.getSubtitle());

    if (actionOptional.isPresent()) {
      throw new ActionAlredyExistsException();
    }

    if (optionalAddress.isEmpty()) {
      throw new AddressNotFoundException();
    }

    if (optionalCategory.isEmpty()) {
      throw new CategoryNotFoundException();
    }

    if (optionalOrganizer.isEmpty()) {
      throw new OrganizerNotFoundException();
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
