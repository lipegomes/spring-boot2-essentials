package dev.filipegomes.springboot2.controller;

import dev.filipegomes.springboot2.domain.Country;
import dev.filipegomes.springboot2.service.CountryService;
import dev.filipegomes.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("countries")
@Log4j2
@RequiredArgsConstructor
public class CountryController {
    private final DateUtil dateUtil;
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> list() {
        log.info(dateUtil.formatLocalDateTimeToDataBasStyle(LocalDateTime.now()));
        return new ResponseEntity<>(countryService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Country> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(countryService.findById(id));
    }
}