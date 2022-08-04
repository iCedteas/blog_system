package com.rbecy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rbecy.dao.CommentMapper;
import com.rbecy.model.domain.Comment;
import com.rbecy.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements ICommentService {

    private final CommentMapper commentMapper;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper, RedisTemplate<Object, Object> redisTemplate) {
        this.commentMapper = commentMapper;
        this.redisTemplate = redisTemplate;
    }

    // 根据文章id分页查询评论
    @Override
    @Cacheable(cacheNames = "comment_list", unless = "#result.list==null")
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page, count);
        List<Comment> commentList = commentMapper.selectCommentWithPage(aid);


        return new PageInfo<>(commentList);
    }
    // 发表评论
    @Override
    public void pushComment(Comment comment) {
        commentMapper.pushComment(comment);
    }

    // 删除评论
    @Override
    public void deleteCommentWithId(Integer aid) {

    }
}
