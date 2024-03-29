package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.server.model.entities.Controller;
import ite.jp.ak.lab03.server.model.repositories.IControllerRepository;
import ite.jp.ak.lab03.server.web.dto.ControllerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ControllerService {

    private final IControllerRepository controllerRepository;

    public ControllerDto createDto(Controller controller) {
        ControllerDto controllerDto = new ControllerDto();
        controllerDto.setId(controller.getId());
        controllerDto.setUsername(controller.getUsername());
        controllerDto.setPesel(controller.getPesel());

        return controllerDto;
    }

    public ControllerDto getById(UUID id) {
        Controller controller = controllerRepository.findById(id).orElse(null);
        if (controller != null) {
            return createDto(controller);
        }
        return null;
    }

    public ControllerDto getByPesel(String pesel) {
        Controller controller = controllerRepository.findByPesel(pesel).orElse(null);
        if (controller != null) {
            return createDto(controller);
        }
        return null;
    }

    public ControllerDto getByUsername(String username) {
        Controller controller = controllerRepository.findByUsername(username).orElse(null);
        if (controller != null) {
            return createDto(controller);
        }
        return null;
    }

    public Controller create(ControllerDto controllerDto) {
        if (controllerDto == null) {
            return null;
        }
        Controller newController = new Controller();
        newController.setUsername(controllerDto.getUsername());
        newController.setPesel(controllerDto.getPesel());
        return newController;
    }

    public Controller createAndSave(ControllerDto controllerDto) {
        Controller newController = create(controllerDto);
        return controllerRepository.saveAndFlush(newController);
    }

    public List<ControllerDto> getAll() {
        return controllerRepository.findAll().stream().map(this::createDto).toList();
    }

}
