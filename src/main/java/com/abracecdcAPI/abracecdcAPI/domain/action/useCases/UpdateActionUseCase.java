package com.abracecdcAPI.abracecdcAPI.domain.action.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abracecdcAPI.abracecdcAPI.domain.action.dto.UpdateActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.action.entity.ActionEntity;
import com.abracecdcAPI.abracecdcAPI.domain.action.exceptions.ActionAlredyExistsException;
import com.abracecdcAPI.abracecdcAPI.domain.action.repository.ActionRepository;
import com.abracecdcAPI.abracecdcAPI.domain.address.entity.Address;
import com.abracecdcAPI.abracecdcAPI.domain.address.repository.AddressRepository;
import com.abracecdcAPI.abracecdcAPI.domain.category.entity.CategoryEntity;
import com.abracecdcAPI.abracecdcAPI.domain.category.repository.CategoryRepository;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.entity.OrganizerEntity;
import com.abracecdcAPI.abracecdcAPI.domain.organizer.repository.OrganizerRepository;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.AddressNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.CategoryNotFoundException;
import com.abracecdcAPI.abracecdcAPI.exceptions.OrganizerNotFoundException;

@Service
public class UpdateActionUseCase {

  @Autowired
  private ActionRepository actionRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private OrganizerRepository organizerRepository;

  public ActionEntity execute(UpdateActionDTO updateActionDTO) {
    Optional<ActionEntity> actionOptional = this.actionRepository.findById(updateActionDTO.getId());

    Optional<Address> optionalAddress = addressRepository.findById(updateActionDTO.getAddressId());
    Optional<CategoryEntity> optionalCategory = categoryRepository.findById(updateActionDTO.getCategoryId());
    Optional<OrganizerEntity> optionalOrganizer = organizerRepository.findById(updateActionDTO.getOrganizerId());
    Optional<ActionEntity> actionWithSameTitleAndSubtitleOptional = actionRepository.findByTitleAndSubtitle(
        updateActionDTO.getTitle(),
        updateActionDTO.getSubtitle());

    if (actionOptional.isEmpty()) {
      throw new ActionNotFoundException();
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

    if (actionWithSameTitleAndSubtitleOptional.isPresent()) {
      throw new ActionAlredyExistsException();
    }
    
    var existingAction = actionOptional.get();

    existingAction.setTitle(updateActionDTO.getTitle());
    existingAction.setSubtitle(updateActionDTO.getSubtitle());
    existingAction.setDescription(updateActionDTO.getDescription());
    existingAction.setDuration(updateActionDTO.getDescription());
    existingAction.setDateTime(updateActionDTO.getDateTime());
    existingAction.setAddressEntity(optionalAddress.get());
    existingAction.setCategoryEntity(optionalCategory.get());
    existingAction.setOrganizerEntity(optionalOrganizer.get());
    existingAction.setRegisters(updateActionDTO.getRegisters());

    return this.actionRepository.save(existingAction);

  }
}
