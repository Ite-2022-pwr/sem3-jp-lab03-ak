package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.server.web.dto.ControllerDto;
import ite.jp.ak.lab03.server.web.services.ControllerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/controller")
@RequiredArgsConstructor
public class ControllerController {

    private final ControllerService controllerService;

    @GetMapping("/{controllerId}")
    public ResponseEntity<ControllerDto> getById(@PathVariable UUID controllerId) {
        return ResponseEntity.ok(controllerService.getById(controllerId));
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<ControllerDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(controllerService.getByUsername(username));
    }

    @GetMapping("/getByPesel/{pesel}")
    public ResponseEntity<ControllerDto> getByPesel(@PathVariable String pesel) {
        return ResponseEntity.ok(controllerService.getByPesel(pesel));
    }

    @PostMapping("/new")
    public ResponseEntity<ControllerDto> create(@RequestBody ControllerDto controllerDto) {
        return ResponseEntity.ok(controllerService.createDto(controllerService.createAndSave(controllerDto)));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ControllerDto>> getAll() {
        return ResponseEntity.ok(controllerService.getAll());
    }
}
