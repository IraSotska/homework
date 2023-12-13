package com.sotska.service;

import com.sotska.entity.Data;
import com.sotska.repository.DataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Arrays;

import static java.time.LocalDateTime.now;

@Service
public class DataService {

    public static final int PAGE_SIZE = 50;
    public static final int DATA_SIZE = 1024 * 1024;
    public static final int ROW_COUNT = 500;
    private final DataRepository dataRepository;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Transactional
    public void updateModificationDate() {
        var pageRequest = PageRequest.of(0, PAGE_SIZE);
        Page<Data> dataPage = dataRepository.findAll(pageRequest);

        while (!dataPage.isEmpty()) {
            pageRequest = pageRequest.next();

            var content = dataPage.getContent();
            content.forEach(data -> data.setModificationDate(now(ZoneId.systemDefault())));
            dataRepository.saveAll(content);
            content = null;
            dataPage = null;
            dataPage = dataRepository.findAll(pageRequest);
        }
    }

    // -Xmx256m
    public void createData() {
        char[] charArray = new char[DATA_SIZE];
        Arrays.fill(charArray, 'a');
        var data = String.copyValueOf(charArray);
        for (int i = 0; i < ROW_COUNT; i++) {
            dataRepository.save(new Data(data, now(ZoneId.systemDefault())));
        }
    }
}
