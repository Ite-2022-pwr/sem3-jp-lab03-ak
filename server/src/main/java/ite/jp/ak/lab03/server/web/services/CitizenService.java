package ite.jp.ak.lab03.server.web.services;

import ite.jp.ak.lab03.server.model.entities.Citizen;
import ite.jp.ak.lab03.server.model.repositories.ICitizenRepository;
import ite.jp.ak.lab03.server.web.dto.CitizenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CitizenService {

    private final ICitizenRepository citizenRepository;

    public CitizenDto createDto(Citizen citizen) {
        CitizenDto citizenDto = new CitizenDto();
        citizenDto.setId(citizen.getId());
        citizenDto.setUsername(citizen.getUsername());
        citizenDto.setPesel(citizen.getPesel());

        return citizenDto;
    }

    public CitizenDto getById(UUID id) {
        Citizen citizen = citizenRepository.findById(id).orElse(null);
        if (citizen != null) {
            return createDto(citizen);
        }
        return null;
    }

    public CitizenDto getByPesel(String pesel) {
        Citizen citizen = citizenRepository.findByPesel(pesel).orElse(null);
        if (citizen != null) {
            return createDto(citizen);
        }
        return null;
    }

    public CitizenDto getByUsername(String username) {
        Citizen citizen = citizenRepository.findByUsername(username).orElse(null);
        if (citizen != null) {
            return createDto(citizen);
        }
        return null;
    }

    public Citizen create(CitizenDto citizenDto) {
        if (citizenDto == null) {
            return null;
        }
        Citizen newCitizen = new Citizen();
        newCitizen.setUsername(citizenDto.getUsername());
        newCitizen.setPesel(citizenDto.getPesel());
        return newCitizen;
    }

    public Citizen createAndSave(CitizenDto citizenDto) {
        Citizen newCitizen = create(citizenDto);
        return citizenRepository.saveAndFlush(newCitizen);
    }
}
