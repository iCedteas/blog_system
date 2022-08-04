package com.rbecy.dao;

import com.rbecy.model.domain.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 分页展示某个文章的评论
    @Select("select * from t_comment where article_id=#{aid} order by id desc")
    List<Comment> selectCommentWithPage(Integer aid);

    //　后台查询最新几条评论
    @Select("select * from t_comment order by id desc")
    List<Comment> selectNewComment();

    // 发表评论
    @Insert("insert into t_comment (article_id,created,author,ip,content)" +
            "values (#{articleId},#{created},#{author},#{ip},#{content})")
    void pushComment(Comment comment);

    // 站点服务统计，统计评论数量
    @Select("select count(1) from t_comment")
    Integer countComment();

    //　通过文章id删除评论信息
    @Delete("delete from t_comment where article_id=#{aid}")
    void deleteCommentWithId(Integer aid);
}
