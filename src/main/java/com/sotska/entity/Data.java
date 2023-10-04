package com.sotska.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; //(1 mb)

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    public Data() {
    }

    public Data(String content, LocalDateTime modificationDate) {
        this.content = content;
        this.modificationDate = modificationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }
}
