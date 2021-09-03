package com.chun.springboot.api.output;

import java.util.ArrayList;
import java.util.List;

import com.chun.springboot.service.dto.NewDTO;

public class NewOutput {
    private int page;
    private int totalPages;
    List<NewDTO> results = new ArrayList<>();
    
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<NewDTO> getResults() {
        return results;
    }
    public void setResults(List<NewDTO> results) {
        this.results = results;
    }

    
}
