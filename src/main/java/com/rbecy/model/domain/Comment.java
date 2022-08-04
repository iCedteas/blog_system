package com.rbecy.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {
    private Integer id;
    private Integer articleId;
    private Date created;
    private String ip;
    private String content;
    private String status;
    private String author;
}
