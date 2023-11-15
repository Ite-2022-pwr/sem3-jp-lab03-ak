package ite.jp.ak.lab03.server.web.controllers;

import ite.jp.ak.lab03.server.web.dto.TreeDto;
import ite.jp.ak.lab03.server.web.services.TreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tree")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @GetMapping("/{treeId}")
    public ResponseEntity<TreeDto> getById(@PathVariable UUID treeId) {
        return ResponseEntity.ok(treeService.getById(treeId));
    }

    @GetMapping
    public ResponseEntity<List<TreeDto>> getAll() {
        return ResponseEntity.ok(treeService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<TreeDto> create(@RequestBody TreeDto treeDto) {
        return ResponseEntity.ok(treeService.createDto(treeService.createAndSave(treeDto)));
    }

}
