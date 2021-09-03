package com.chun.springboot.api;

import com.chun.springboot.api.output.NewOutput;
import com.chun.springboot.service.dto.NewDTO;
import com.chun.springboot.service.impl.NewService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class NewAPI {

    @Autowired
    private NewService newService;

    @PreAuthorize("hasRole('ROLE_MEMBER')") 
    @PostMapping(value = "/new")
    public NewDTO createNew(@RequestBody NewDTO model) {
        return newService.save(model);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')") 
    @PutMapping(value = "/new/{id}")
    public NewDTO updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return newService.update(model);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER')") 
    @DeleteMapping(value = "/new")
    public void delete(@RequestBody long[] ids) {
        newService.delete(ids);
    }

    @GetMapping(value = "/new")
    public NewOutput showNews(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        NewOutput result = new NewOutput();
        if (page != null && limit != null) {
            result.setPage(page);
            result.setTotalPages((int) Math.ceil((double) (newService.totalitems()) / limit));
            result.setResults(newService.findAll(page - 1, limit));
        } else {
            result.setPage(1);
            result.setResults(newService.findAll());
            result.setTotalPages(1);
        }
        return result;
    }

    @GetMapping(value = "/new/category")
    public NewOutput showNewsByCategory(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "category") String category) {
        NewOutput result = new NewOutput();
        if (page != null && limit != null) {
            result.setPage(page);
            result.setTotalPages(
                    (int) Math.ceil((double) ((newService.findAllByCategoryCode(category)).size()) / limit));
            result.setResults(newService.findAllByCategoryCode(category, page - 1, limit));
        } else {
            result.setPage(1);
            result.setResults(newService.findAllByCategoryCode(category));
            result.setTotalPages(1);
        }
        return result;
    }

    @GetMapping(value = "/new/description")
    public NewOutput showNewsByDescription(@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit, 
            @RequestParam(value = "str") String str) {
        NewOutput result = new NewOutput();
        if (page != null && limit != null) {
            result.setPage(page);
            result.setTotalPages((int) Math.ceil((double) ((newService.findAllByDescription(str)).size()) / limit));
            result.setResults(newService.findAllByDescription(str, page - 1, limit));
        } else {
            result.setPage(1);
            result.setResults(newService.findAllByDescription(str));
            result.setTotalPages(1);
        }
        return result;
    }

    @GetMapping("/new/all")
    public HttpEntity<? extends Object> getAllNews(Pageable page) {
        return new ResponseEntity<>(newService.getAllNews(page), HttpStatus.OK);
    }

    @GetMapping("/new/des")
    public HttpEntity<? extends Object> getAllNewsByDescription(@RequestParam(value = "str") String str, Pageable page) {
        return new ResponseEntity<>(newService.getAllNewsByDescription(str, page), HttpStatus.OK);
    }

    @GetMapping("/new/search")
    public HttpEntity<? extends Object> searchNews(NewDTO dto, Pageable page) {
        return new ResponseEntity<>(newService.searchNews(dto, page), HttpStatus.OK);
    }

    @JsonIgnore
    @GetMapping(value = "/new/barcode",  produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<BufferedImage> getBarcode(@RequestParam(value = "str") String str) {
        return new ResponseEntity<>(newService.getBarcode(str), HttpStatus.OK);
    }
}
