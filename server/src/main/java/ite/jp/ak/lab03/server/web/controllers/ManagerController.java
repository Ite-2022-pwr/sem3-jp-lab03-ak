package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.server.web.dto.ManagerDto;
import ite.jp.ak.lab03.server.web.services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/{managerId}")
    public ResponseEntity<ManagerDto> getById(@PathVariable UUID managerId) {
        return ResponseEntity.ok(managerService.getById(managerId));
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<ManagerDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(managerService.getByUsername(username));
    }

    @GetMapping("/getByPesel/{pesel}")
    public ResponseEntity<ManagerDto> getByPesel(@PathVariable String pesel) {
        return ResponseEntity.ok(managerService.getByPesel(pesel));
    }

    @PostMapping("/new")
    public ResponseEntity<ManagerDto> create(@RequestBody ManagerDto managerDto) {
        return ResponseEntity.ok(managerService.createDto(managerService.createAndSave(managerDto)));
    }


}
