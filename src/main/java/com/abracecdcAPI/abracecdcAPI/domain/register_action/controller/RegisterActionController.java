package com.abracecdcAPI.abracecdcAPI.domain.register_action.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.register_action.dto.CreateRegisterActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.dto.UpdateRegisterActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.exceptions.RegisterActionNotFoundException;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases.CreateRegisterActionUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases.DeleteRegisterActionUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases.GetRegisterActionByIdUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.register_action.useCases.UpdateRegisterActionUseCase;

@RestController
@RequestMapping("/registers-action")
public class RegisterActionController {

  @Autowired
  private CreateRegisterActionUseCase createRegisterActionUseCase;

  @Autowired
  private GetRegisterActionByIdUseCase getRegisterActionByIdUseCase;

  @Autowired
  private DeleteRegisterActionUseCase deleteRegisterActionUseCase;

  @Autowired
  private UpdateRegisterActionUseCase updateRegisterActionUseCase;
  
  @PostMapping("/create")
  public ResponseEntity<Object> createRegisterAction(@RequestBody CreateRegisterActionDTO createRegisterActionDTO) {
    try {
      var registerAction = this.createRegisterActionUseCase.execute(createRegisterActionDTO);
      return ResponseEntity.ok().body(registerAction);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }    
  }

  @GetMapping("/{registerActionId}")
  public ResponseEntity<Object> getRegisterActionById(@PathVariable UUID registerActionId) {
    try {
      var register = this.getRegisterActionByIdUseCase.execute(registerActionId);
      return ResponseEntity.ok().body(register);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{registerActionId}")
  public ResponseEntity<Object> deleteRegisterActionById(@PathVariable UUID registerActionId) {
    try {
      this.deleteRegisterActionUseCase.execute(registerActionId);
      return ResponseEntity.noContent().build();
    } catch (RegisterActionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
  
  @PutMapping("/update")
  public ResponseEntity<Object> updateRegisterAction(@RequestBody UpdateRegisterActionDTO updateRegisterActionDTO) {
    try {
      var registerAction = this.updateRegisterActionUseCase.execute(updateRegisterActionDTO);
      return ResponseEntity.ok().body(registerAction);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }    
  }
  
}
