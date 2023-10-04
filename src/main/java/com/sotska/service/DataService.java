package com.sotska.service;

import com.sotska.entity.Data;
import com.sotska.repository.DataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DataService {

    public static final int PAGE_SIZE = 50;
    public static final int DATA_SIZE = 50000;
    public static final int ROW_COUNT = 500;
    private final DataRepository dataRepository;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Transactional
    public void updateModificationDate() {
        var pageRequest = PageRequest.of(0, PAGE_SIZE);
        var todayDate = LocalDateTime.now();

        Page<Data> dataPage;

        do {
            dataPage = dataRepository.findAll(pageRequest);
            List<Data> content = dataPage.getContent();
            for(Data data: content) {
                data.setModificationDate(todayDate);
            }
            dataRepository.saveAll(content);
            pageRequest = pageRequest.next();
        } while (!dataPage.isLast());
    }

    public void createData() {
        char[] charArray = new char[DATA_SIZE];
        Arrays.fill(charArray, 'a');
        var data = String.copyValueOf(charArray);

        for (int i = 0; i < ROW_COUNT; i++) {
            dataRepository.save(new Data(data, LocalDateTime.now()));
        }
    }

    private void updateDateForPage(Page<Data> dataPage, LocalDateTime todayDate) {
        var ids = dataPage.get().map(Data::getId).collect(toList());
        dataRepository.updateModificationDate(ids, todayDate);
    }
}
