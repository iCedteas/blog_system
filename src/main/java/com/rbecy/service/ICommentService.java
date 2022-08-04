package com.rbecy.service;

import com.github.pagehelper.PageInfo;
import com.rbecy.model.domain.Comment;
import org.apache.ibatis.annotations.Delete;

/**
 * 文章评论业务处理接口
 */
public interface ICommentService {
    // 获取文章下的评论
    PageInfo<Comment> getComments(Integer aid, int page, int count);

    // 发表评论
    void pushComment(Comment comment);

    //　通过文章id删除评论信息
    void deleteCommentWithId(Integer aid);
}
