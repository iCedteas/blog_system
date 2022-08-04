package com.rbecy.service;


import com.github.pagehelper.PageInfo;
import com.rbecy.model.domain.Article;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface IArticleService {
    // 分页查询文章列表
    PageInfo<Article> selectArticleWithPage(Integer page, Integer count);

    // 统计热度排名前十的文章信息
    List<Article> getHeatArticles();

    // 根据文章id查询单个文章详细
    Article selectArticleWithId(Integer id);

    // 发表文章
    boolean publishArticle(Article article);

    // 通过id删除文章
    void deleteArticleWithId(int id);

    // 通过id更新文章
    Integer updateArticleWithId(Article article);
}
