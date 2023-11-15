package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.model.entities.Controller;
import ite.jp.ak.lab03.model.entities.Report;
import ite.jp.ak.lab03.model.repositories.IControllerRepository;
import ite.jp.ak.lab03.server.web.dto.ControllerDto;
import ite.jp.ak.lab03.server.web.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ControllerService {

    private final IControllerRepository controllerRepository;

    @Lazy
    private final ReportService reportService;

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

}
