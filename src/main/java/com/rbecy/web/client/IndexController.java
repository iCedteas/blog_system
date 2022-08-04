package com.rbecy.web.client;

import com.github.pagehelper.PageInfo;
import com.rbecy.model.ResponseData.ArticleDetailed;
import com.rbecy.model.domain.Article;
import com.rbecy.model.domain.Comment;
import com.rbecy.service.IArticleService;
import com.rbecy.service.ICommentService;
import com.rbecy.service.ISiteService;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/blog")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final IArticleService articleServiceImpl;
    private final ICommentService commentServiceImpl;
    private final ISiteService siteServiceImpl;

    public IndexController(IArticleService articleServiceImpl, ICommentService commentServiceImpl, ISiteService siteServiceImpl) {
        this.articleServiceImpl = articleServiceImpl;
        this.commentServiceImpl = commentServiceImpl;
        this.siteServiceImpl = siteServiceImpl;
    }

    // 博客首页
    @GetMapping(value = "/page/{p}")
    public PageInfo<Article> index(@PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "5") int count) {
        PageInfo<Article> article = articleServiceImpl.selectArticleWithPage(page, count);
        logger.info("分页获取文章信息：页码 " + page + "，条数 " + count);
        return article;
    }

    // 获取文章热度统计信息
    @GetMapping(value = "/heat")
    public List<Article> heatArticles() {
        return articleServiceImpl.getHeatArticles();
    }

    // 文章详情查询
    @GetMapping(value = "/article/{id}")
    public ArticleDetailed getArticleById(@PathVariable("id") Integer id, HttpServletRequest request) {
        Article article = articleServiceImpl.selectArticleWithId(id);
        ArticleDetailed articleDetailed = new ArticleDetailed();
        if (article != null) {
            // 查询封装评论相关数据
            PageInfo<Comment> commentPageInfo = getArticleComments(article, request);
            // 更新文章
            siteServiceImpl.updateStatistics(article);
            articleDetailed.setArticle(article);
            articleDetailed.setCommentPageInfo(commentPageInfo);
        }
        return articleDetailed;
    }

    // 查询文章的评论信息，并补充到文章详情里面
    private PageInfo<Comment> getArticleComments(Article article, HttpServletRequest request) {
        PageInfo<Comment> commentPageInfo = new PageInfo<>();
        if (article.getAllowComment()) {
            // cp表示评论页码,commentPage
            String cp = request.getParameter("cp");
            cp = StringUtils.isBlank(cp) ? "1" : cp;
            commentPageInfo = commentServiceImpl.getComments(article.getId(), Integer.parseInt(cp), 3);
        }
        return commentPageInfo;
    }
}
