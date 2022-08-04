package com.rbecy.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Article implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private String categories;
    private String tags;
    private Boolean allowComment;
    private String thumbnail;

    private Integer hits;
    private Integer commentsNum;
}
