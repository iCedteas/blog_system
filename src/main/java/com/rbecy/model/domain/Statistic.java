package com.rbecy.model.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Statistic implements Serializable {
    private Integer id;
    private Integer articleId;
    private Integer hits;
    private Integer commentsNum;
}
