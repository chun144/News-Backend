package com.chun.springboot.service;

import java.util.List;
import java.awt.image.BufferedImage;

import com.chun.springboot.service.dto.NewDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INewService {
    NewDTO save(NewDTO newDTO);
    NewDTO update(NewDTO newDTO);
    void delete(long[] ids);
    List<NewDTO> findAll(int page, int limit);
    List<NewDTO> findAll();
    List<NewDTO> findAllByCategoryCode(String categoryCode);
    List<NewDTO> findAllByCategoryCode(String categoryCode, int page, int limit);
    List<NewDTO> findAllByDescription(String descriptionString);
    List<NewDTO> findAllByDescription(String descriptionString, int page, int limit);
    int totalitems();
    Page<NewDTO> getAllNews(Pageable pageable);  
    Page<NewDTO> getAllNewsByDescription(String descriptionString, Pageable pageable);
    Page<NewDTO> searchNews(NewDTO news, Pageable pageable);

    BufferedImage getBarcode(String string) throws Exception;
}