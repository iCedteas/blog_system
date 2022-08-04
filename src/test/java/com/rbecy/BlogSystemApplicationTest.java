package com.rbecy;

import com.github.pagehelper.PageInfo;
import com.rbecy.dao.ArticleMapper;
import com.rbecy.model.domain.Article;
import com.rbecy.model.domain.Comment;
import com.rbecy.model.domain.Statistic;
import com.rbecy.service.ICommentService;
import com.rbecy.service.ISiteService;
import com.rbecy.service.impl.ArticleServiceImpl;
import com.rbecy.service.impl.CommentServiceImpl;
import com.sun.org.glassfish.external.statistics.impl.StatisticImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogSystemApplicationTest {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    // 测试新增文章
    @Test
    public void test2(){
        Article article = new Article();
        article.setCommentsNum(10);
        article.setHits(52);
        article.setCategories("计算机2");
        article.setAllowComment(true);
        article.setContent("后端工程content");
        article.setCreated(new Date());
        article.setModified(new Date());
        article.setTags("后端");
        article.setThumbnail("x0aa");
        article.setTitle("什么是后端");

        boolean b = articleService.publishArticle(article);
        siteService.addStatistic(article);
        System.out.println(b);
    }

    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private ISiteService siteService;
    @Autowired
    private ICommentService iCommentService;

    //　分页查询文章
    @Test
    public void test3(){
        PageInfo<Article> article = articleService.selectArticleWithPage(1, 5);
        List<Article> list = article.getList();
        if (CollectionUtils.isEmpty(list)){
            System.out.println("空list");
        }else {
            list.forEach(System.out::println);
        }

    }

    //　发表评论
    @Test
    public void test4(){
        Comment comment = new Comment();
        comment.setCreated(new Date());
        comment.setContent("aaaaaaaaaa");
        comment.setAuthor("fafsaf");
        comment.setIp("3456.78.9");
        comment.setArticleId(1);
        iCommentService.pushComment(comment);
    }

    @Test
    public void test5(){
        PageInfo<Comment> comments = iCommentService.getComments(1,1, 5);
        List<Comment> commentList = comments.getList();
        if (CollectionUtils.isEmpty(commentList)){
            System.out.println("空list");
        }else {
            commentList.forEach(System.out::println);
        }

    }
}

