package com.rbecy.model.ResponseData;

import com.github.pagehelper.PageInfo;
import com.rbecy.model.domain.Article;
import com.rbecy.model.domain.Comment;

public class ArticleDetailed {
    private Article article;
    private PageInfo<Comment> commentPageInfo;

    @Override
    public String toString() {
        return "ArticleDetailed{" +
                "article=" + article +
                ", commentPageInfo=" + commentPageInfo +
                '}';
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public PageInfo<Comment> getCommentPageInfo() {
        return commentPageInfo;
    }

    public void setCommentPageInfo(PageInfo<Comment> commentPageInfo) {
        this.commentPageInfo = commentPageInfo;
    }
}
