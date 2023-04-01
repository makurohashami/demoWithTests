package com.example.demowithtests.web.cabinetController;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.service.cabinetService.CabinetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CabinetControllerBean implements CabinetController {

    private final CabinetService cabinetService;

    @Override
    @PostMapping("/cabinets")
    @ResponseStatus(HttpStatus.CREATED)
    public Cabinet createCabinet(@RequestBody Cabinet cabinet) {
        return cabinetService.addCabinet(cabinet);
    }

    @Override
    @GetMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cabinet readCabinet(@PathVariable Integer id) {
        return cabinetService.getCabinet(id);
    }

    @Override
    @PutMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cabinet updateCabinet(@PathVariable Integer id, @RequestBody Cabinet cabinet) {
        return cabinetService.updateCabinet(id, cabinet);
    }

    @Override
    @DeleteMapping("/cabinets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCabinet(@PathVariable Integer id) {
        cabinetService.removeCabinet(id);
    }
}
