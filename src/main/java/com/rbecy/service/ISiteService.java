package com.rbecy.service;

import com.rbecy.model.domain.Article;
import com.rbecy.model.domain.Comment;
import com.rbecy.model.domain.Statistic;
import com.rbecy.model.domain.StatisticsCount;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 博客站点统计服务
 */
public interface ISiteService {
    // 最新收到的评论
    List<Comment> recentComments(int limit);

    // 最新发表的文章
    List<Article> recentArticle(int limit);

    // 获取后台统计数据t_statistic
    StatisticsCount getStatistics();

    // 更新某个文章的统计数据
    void updateStatistics(Article article);

    // 新增文章对应的统计信息
    void addStatistic(Article article);

    // 通过文章id更新评论量
    void updateArticleCommentsWithArticleId(Statistic statistic);

    // 根据文章id删除统计数据
    void deleteStatisticWithId(Integer articleId);
}
