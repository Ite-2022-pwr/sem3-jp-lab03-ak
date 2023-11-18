package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.server.model.entities.Tree;
import ite.jp.ak.lab03.server.model.repositories.ITreeRepository;
import ite.jp.ak.lab03.server.web.dto.TreeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TreeService {

    private final ITreeRepository treeRepository;

    public TreeDto createDto(Tree tree) {
        TreeDto treeDto = new TreeDto();
        treeDto.setDiameter(tree.getDiameter());
        treeDto.setName(tree.getName());
        treeDto.setId(tree.getId());

        return treeDto;
    }

    public TreeDto getById(UUID id) {
        Tree tree = treeRepository.findById(id).orElse(null);
        if (tree != null) {
            return createDto(tree);
        }
        return null;
    }

    public List<TreeDto> getAll() {
        List<TreeDto> treeDtos = new ArrayList<>();
        for (Tree tree : treeRepository.findAll()) {
            treeDtos.add(createDto(tree));
        }
        return treeDtos;
    }

    public Tree create(TreeDto treeDto) {
        if (treeDto == null) {
            return null;
        }
        Tree newTree = new Tree();
        newTree.setDiameter(treeDto.getDiameter());
        newTree.setName(treeDto.getName());

        return newTree;
    }

    public Tree createAndSave(TreeDto treeDto) {
        Tree newTree = create(treeDto);
        return treeRepository.saveAndFlush(newTree);
    }
}
