package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.model.entities.Manager;
import ite.jp.ak.lab03.model.repositories.IManagerRepository;
import ite.jp.ak.lab03.server.web.dto.FeedbackDto;
import ite.jp.ak.lab03.server.web.dto.ManagerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ManagerService {

    private final IManagerRepository managerRepository;

    @Lazy
    private final FeedbackService feedbackService;

    public ManagerDto createDto(Manager manager) {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setId(manager.getId());
        managerDto.setUsername(manager.getUsername());
        managerDto.setPesel(manager.getPesel());
        return managerDto;
    }

    public ManagerDto getById(UUID id) {
        Manager manager = managerRepository.findById(id).orElse(null);
        if (manager != null) {
            return createDto(manager);
        }
        return null;
    }

    public ManagerDto getByPesel(String pesel) {
        Manager manager = managerRepository.findByPesel(pesel).orElse(null);
        if (manager != null) {
            return createDto(manager);
        }
        return null;
    }

    public ManagerDto getByUsername(String username) {
        Manager manager = managerRepository.findByUsername(username).orElse(null);
        if (manager != null) {
            return createDto(manager);
        }
        return null;
    }

    public Manager create(ManagerDto managerDto) {
        if (managerDto == null) {
            return null;
        }
        Manager newManager = new Manager();
        newManager.setUsername(managerDto.getUsername());
        newManager.setPesel(managerDto.getPesel());
        return newManager;
    }

    public Manager createAndSave(ManagerDto managerDto) {
        Manager newManager = create(managerDto);
        return managerRepository.saveAndFlush(newManager);
    }
}
