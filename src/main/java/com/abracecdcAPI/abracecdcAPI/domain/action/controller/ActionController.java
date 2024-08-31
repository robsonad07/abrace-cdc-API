package com.abracecdcAPI.abracecdcAPI.domain.action.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abracecdcAPI.abracecdcAPI.domain.action.dto.CreateActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.action.dto.UpdateActionDTO;
import com.abracecdcAPI.abracecdcAPI.domain.action.useCases.CreateActionUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.action.useCases.DeleteActionUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.action.useCases.GetActionByIdUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.action.useCases.ListAllActionsByFilterUseCase;
import com.abracecdcAPI.abracecdcAPI.domain.action.useCases.UpdateActionUseCase;
import com.abracecdcAPI.abracecdcAPI.exceptions.ActionNotFoundException;

@RestController
@RequestMapping("/action")
public class ActionController {

  @Autowired
  private CreateActionUseCase createActionUseCase;

  @Autowired
  private UpdateActionUseCase updateActionUseCase;

  @Autowired
  private DeleteActionUseCase deleteActionUseCase;

  @Autowired
  private GetActionByIdUseCase getActionByIdUseCase;

  @Autowired 
  private ListAllActionsByFilterUseCase listAllActionsByFilterUseCase;

  @PostMapping("/create")
  public ResponseEntity<Object> createAction(@RequestBody CreateActionDTO createActionDTO) {
    try {
      var res = this.createActionUseCase.execute(createActionDTO);
      return ResponseEntity.ok().body(res);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{actionId}")
  public ResponseEntity<Object> getActionById(@PathVariable UUID actionId) {
    try {
      var action = this.getActionByIdUseCase.execute(actionId);
      return ResponseEntity.ok().body(action);
    } catch (ActionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @GetMapping("/list")
  public ResponseEntity<Object> getActionByTitle(@RequestParam String filter) {
    try {
      var result = listAllActionsByFilterUseCase.execute(filter);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/update")
  public ResponseEntity<Object> updateAction(@RequestBody UpdateActionDTO updateActionDTO) {
    try {
      var res = this.updateActionUseCase.execute(updateActionDTO);
      return ResponseEntity.ok().body(res);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/delete/{actionIdToDelete}")
  public ResponseEntity<Object> deleteActionById(@PathVariable UUID actionIdToDelete) {
    try {
      deleteActionUseCase.execute(actionIdToDelete);
      return ResponseEntity.noContent().build();
    } catch (ActionNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
