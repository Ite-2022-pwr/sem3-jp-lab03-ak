package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.server.web.dto.CitizenDto;
import ite.jp.ak.lab03.server.web.services.CitizenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/citizen")
@RequiredArgsConstructor
public class CitizenController {

    private final CitizenService citizenService;

    @GetMapping("/{citizenId}")
    public ResponseEntity<CitizenDto> getById(@PathVariable UUID citizenId) {
        return ResponseEntity.ok(citizenService.getById(citizenId));
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<CitizenDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(citizenService.getByUsername(username));
    }

    @GetMapping("/getByPesel/{pesel}")
    public ResponseEntity<CitizenDto> getByPesel(@PathVariable String pesel) {
        return ResponseEntity.ok(citizenService.getByPesel(pesel));
    }

    @PostMapping("/new")
    public ResponseEntity<CitizenDto> create(@RequestBody CitizenDto citizenDto) {
        return ResponseEntity.ok(citizenService.createDto(citizenService.createAndSave(citizenDto)));
    }
}
