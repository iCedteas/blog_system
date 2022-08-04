package com.rbecy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rbecy.dao.ArticleMapper;
import com.rbecy.dao.StatisticMapper;
import com.rbecy.model.domain.Article;
import com.rbecy.model.domain.Statistic;
import com.rbecy.service.IArticleService;
import com.rbecy.utils.MyUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class ArticleServiceImpl implements IArticleService {

    private final ArticleMapper articleMapper;
    private final StatisticMapper statisticMapper;
    private final RedisTemplate<Object, Object> redisTemplate;

    public ArticleServiceImpl(ArticleMapper articleMapper, StatisticMapper statisticMapper, RedisTemplate<Object, Object> redisTemplate) {
        this.articleMapper = articleMapper;
        this.statisticMapper = statisticMapper;
        this.redisTemplate = redisTemplate;
    }

    // 分页查询文章列表
    @Override
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        // 先从redis缓存中查询数据
        List<Article> articleList = MyUtils.cast(redisTemplate.opsForValue().get("article_list" + page + count));
        if (CollectionUtils.isEmpty(articleList)) {
            // 缓存中没有进入数据库查询
            articleList = articleMapper.selectArticleWithPage();
            if (!CollectionUtils.isEmpty(articleList)) {
                // 封装文章统计数据
                MyUtils.encapsulatedArticleStatistics(articleList, statisticMapper);
                // 查询结果进行缓存
                redisTemplate.opsForValue().set("article_list" + page + count, articleList, 15, TimeUnit.MINUTES);
            }
        }
        return new PageInfo<>(articleList);
    }


    // 统计热度排名前十的文章信息
    @Override
    public List<Article> getHeatArticles() {
        List<Statistic> statisticList = statisticMapper.getStatistic();
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < statisticList.size(); i++) {
            Article article = articleMapper.selectArticleWithId(statisticList.get(i).getArticleId());
            article.setHits(statisticList.get(i).getHits());
            article.setCommentsNum(statisticList.get(i).getCommentsNum());
            articleList.add(article);
            if (i >= 9) {
                break;
            }
        }
        return articleList;
    }

    // 查询指定文章
    @Override
    public Article selectArticleWithId(Integer id) {
        // 查缓存
        Article article = MyUtils.cast(redisTemplate.opsForValue().get("article" + id));
        if (article == null) {
            // 缓存没有查数据库
            article = articleMapper.selectArticleWithId(id);
            if (article != null) {
                redisTemplate.opsForValue().set("article" + article.getId(), article, 30, TimeUnit.MINUTES);
            }
        }
        return article;
    }

    // 发表文章
    @Override
    public boolean publishArticle(Article article) {
        Integer integer = articleMapper.publishArticle(article);
        if (integer > 0) {
            // 数据库有数据进行缓存
            redisTemplate.opsForValue().set("article" + article.getId(), article, 30, TimeUnit.MINUTES);
        }
        return integer > 0;
    }

    // 通过id删除文章
    @Override
    public void deleteArticleWithId(int id) {

    }

    // 通过id跟新文章
    @Override
    public Integer updateArticleWithId(Article article) {
        return null;
    }
}
