package com.rbecy.service.impl;

import com.github.pagehelper.PageHelper;
import com.rbecy.dao.ArticleMapper;
import com.rbecy.dao.CommentMapper;
import com.rbecy.dao.StatisticMapper;
import com.rbecy.model.domain.Article;
import com.rbecy.model.domain.Comment;
import com.rbecy.model.domain.Statistic;
import com.rbecy.model.domain.StatisticsCount;
import com.rbecy.service.ISiteService;
import com.rbecy.utils.MyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SiteServiceImpl implements ISiteService {

    private final StatisticMapper statisticMapper;  // 统计mp
    private final ArticleMapper articleMapper;  // 文章mp
    private final CommentMapper commentMapper;  // 评论mp

    public SiteServiceImpl(StatisticMapper statisticMapper, ArticleMapper articleMapper, CommentMapper commentMapper, MyUtils myUtils) {
        this.statisticMapper = statisticMapper;
        this.articleMapper = articleMapper;
        this.commentMapper = commentMapper;
    }

    // 最近的评论
    @Override
    public List<Comment> recentComments(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
        return commentMapper.selectNewComment();
    }

    // 最近的文章
    @Override
    public List<Article> recentArticle(int limit) {
        PageHelper.startPage(1, limit > 10 || limit < 1 ? 10 : limit);
        List<Article> articleList = articleMapper.selectArticleWithPage();
        // 封装文章统计数据
        MyUtils.encapsulatedArticleStatistics(articleList, statisticMapper);
        return articleList;
    }

    // 获取统计数量
    @Override
    public StatisticsCount getStatistics() {
        StatisticsCount statisticsCount = new StatisticsCount();
        statisticsCount.setArticles(articleMapper.countArticle());
        statisticsCount.setComments(commentMapper.countComment());
        return statisticsCount;
    }

    // 更新点击统计数据-点击
    @Override
    public void updateStatistics(Article article) {
        Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
        statistic.setHits(statistic.getHits() + 1);
        statisticMapper.updateArticleHitsWithArticleId(statistic);
    }

    // 新增统计数据
    @Override
    public void addStatistic(Article article) {
        statisticMapper.addStatistic(article);
    }

    @Override
    public void updateArticleCommentsWithArticleId(Statistic statistic) {

    }

    @Override
    public void deleteStatisticWithId(Integer articleId) {

    }
}
