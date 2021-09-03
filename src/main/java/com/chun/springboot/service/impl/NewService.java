package com.chun.springboot.service.impl;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.chun.springboot.api.output.Res;
import com.chun.springboot.converter.NewConverter;
import com.chun.springboot.entity.CategoryEntity;
import com.chun.springboot.entity.NewEntity;
import com.chun.springboot.repository.CategoryRepository;
import com.chun.springboot.repository.NewRepository;
import com.chun.springboot.service.INewService;
import com.chun.springboot.service.dto.NewDTO;
import com.chun.springboot.service.mapper.NewMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

@Service
public class NewService implements INewService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewRepository newRepository;

    @Autowired
    private NewConverter newConverter;

    @Autowired
    private NewMapper newMapper;

    @Override
    public NewDTO save(NewDTO newDTO) {
        CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
        NewEntity newEntity = newConverter.toEntity(newDTO);
        newEntity.setCategory(categoryEntity);
        newEntity = newRepository.save(newEntity);
        return newConverter.toDTO(newEntity);
    }

    @Override
    public NewDTO update(NewDTO newDTO) {
        NewEntity oldNewEntity = newRepository.findOneById(newDTO.getId());
        NewEntity newEntity = newConverter.toEntity(newDTO, oldNewEntity);
        CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
        newEntity.setCategory(categoryEntity);
        newEntity = newRepository.save(newEntity);
        return newConverter.toDTO(newEntity);
    }

    @Override
    public void delete(long[] ids) {
        for (long item : ids) {
            newRepository.deleteById(item);
        }
    }

    @Override
    public List<NewDTO> findAll(int page, int limit) {
        List<NewDTO> results = new ArrayList<>();
        Page<NewEntity> entities = newRepository.findAll(PageRequest.of(page, limit, Sort.by("id").ascending()));
        for (NewEntity entity : entities) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
        }
        return results;
    }

    @Override
    public int totalitems() {
        return (int) newRepository.count();
    }

    @Override
    public List<NewDTO> findAll() {
        List<NewDTO> results = new ArrayList<>();
        List<NewEntity> entities = newRepository.findAll(Sort.by("id").ascending());
        for (NewEntity entity : entities) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
        }
        return results;
    }

    @Override
    public List<NewDTO> findAllByCategoryCode(String categoryCode) {
        List<NewDTO> results = new ArrayList<>();
        List<NewEntity> entities = newRepository.findAllByCategoryCode(categoryCode);
        for (NewEntity entity : entities) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
        }
        return results;
    }

    @Override
    public List<NewDTO> findAllByCategoryCode(String categoryCode, int page, int limit) {
        List<NewDTO> results = new ArrayList<>();
        List<NewEntity> entities = newRepository.findAllByCategoryCode(categoryCode,
                PageRequest.of(page, limit, Sort.by("id").ascending()));
        for (NewEntity entity : entities) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
        }
        return results;
    }

    @Override
    public List<NewDTO> findAllByDescription(String descriptionString) {
        List<NewDTO> results = new ArrayList<>();
        List<NewEntity> entities = newRepository.findAllByDescriptionIgnoreCaseContaining(descriptionString);
        for (NewEntity entity : entities) {
            // if (entity.getDescription().contains(descriptionString)) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
            // }
        }
        return results;
    }

    @Override
    public List<NewDTO> findAllByDescription(String descriptionString, int page, int limit) {
        List<NewDTO> results = new ArrayList<>();
        // List<NewEntity> temps = new ArrayList<>();
        List<NewEntity> entities = newRepository.findAllByDescriptionIgnoreCaseContaining(descriptionString,
                PageRequest.of(page, limit, Sort.by("id").ascending()));
        // for (NewEntity entity : entities) {
        // if (entity.getDescription().contains(descriptionString)) {
        // temps.add(entity);
        // }
        // }
        // Page<NewEntity> pages = new PageImpl<NewEntity>(temps, PageRequest.of(page,
        // limit, Sort.by("id").ascending()), temps.size());
        for (NewEntity entity : entities) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
        }
        return results;
    }

    @Override
    public Page<NewDTO> getAllNews(Pageable pageable) {
        List<NewDTO> results = new ArrayList<>();

        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        }
        Page<NewEntity> getAllNews = newRepository.getAllBy(pageable);
        for (NewEntity entity : getAllNews) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
        }
        return new PageImpl<>(results, pageable, getAllNews.getTotalElements());
    }

    @Override
    public Page<NewDTO> getAllNewsByDescription(String descriptionString, Pageable pageable) {
        List<NewDTO> results = new ArrayList<>();
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        }
        Page<NewEntity> getAllNews = newRepository.getAllByDescriptionIgnoreCaseContaining(descriptionString, pageable);
        for (NewEntity entity : getAllNews) {
            NewDTO dto = newConverter.toDTO(entity);
            results.add(dto);
        }
        return new PageImpl<>(results, pageable, getAllNews.getTotalElements());
    }

    @Override
    public Page<NewDTO> searchNews(NewDTO news, Pageable pageable) {
        Page<NewDTO> mappedResult = null;
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        }
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnorePaths("");
        NewEntity newExample = newMapper.toNewEntity(news);
        Example<NewEntity> example = Example.of(newExample, matcher);

        Page<NewEntity> getAllNew = newRepository.findAll(example, pageable);
        mappedResult = getAllNew.map(newMapper::toNewDTO);
        return mappedResult;
    }

    @Override
    @JsonIgnore
    public BufferedImage getBarcode(String string) {
        BufferedImage result;
        Res r = new Res();
        try {
            result = generateCode128BarcodeImage(string);
            r.setA(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r.getA();
    }

    public static BufferedImage generateCode128BarcodeImage(String barcodeText) throws Exception {
        Barcode barcode = BarcodeFactory.createCode128(barcodeText);
        barcode.setBarHeight(100);
        barcode.setResolution(200);
        barcode.setFont(new Font("Monospaced", Font.BOLD, 20));
        // File outputfile = new File("saved.png");
        // File temp = new File("temp.png");
        // ImageIO.write(BarcodeImageHandler.getImage(barcode), "png", temp);
        // ImageIO.write(BarcodeImageHandler.getImage(barcode), "png", outputfile);
        BarcodeImageHandler.getImage(barcode);
        return BarcodeImageHandler.getImage(barcode);
    }

}
