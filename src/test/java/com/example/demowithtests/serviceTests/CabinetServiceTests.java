package com.example.demowithtests.serviceTests;

import com.example.demowithtests.domain.Cabinet;
import com.example.demowithtests.repository.CabinetRepository;
import com.example.demowithtests.service.cabinetService.CabinetServiceBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Cabinet Service Tests")
public class CabinetServiceTests {

    @Mock
    private CabinetRepository repository;

    @InjectMocks
    private CabinetServiceBean service;

    private Cabinet cabinet;

    @BeforeEach
    void setUp() {
        cabinet = Cabinet.builder()
                .id(1)
                .name("Lviv")
                .isDeleted(Boolean.FALSE)
                .build();
    }

    @Test
    @DisplayName("add cabinet test")
    void addCabinetTest() {
        when(repository.save(any(Cabinet.class))).thenReturn(cabinet);

        var created = service.addCabinet(cabinet);

        assertThat(created.getName()).isSameAs(cabinet.getName());
        verify(repository).save(cabinet);
    }

    @Test
    @DisplayName("get cabinet test")
    void getCabinetTest() {
        when(repository.findById(cabinet.getId())).thenReturn(Optional.of(cabinet));

        var expected = service.getCabinet(cabinet.getId());

        assertThat(expected).isSameAs(cabinet);
        verify(repository).findById(cabinet.getId());
    }

    @Test
    @DisplayName("delete cabinet test")
    void deleteCabinetTest() {
        when(repository.findById(cabinet.getId())).thenReturn(Optional.of(cabinet));
        when(repository.save(any(Cabinet.class))).thenReturn(cabinet);

        assertEquals(cabinet.getIsDeleted(), Boolean.FALSE);

        service.removeCabinet(cabinet.getId());

        assertEquals(cabinet.getIsDeleted(), Boolean.TRUE);
        verify(repository).save(cabinet);
    }

}
