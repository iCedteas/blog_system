package com.rbecy.dao;

import com.rbecy.model.domain.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    // 根据id查询文章信息
    @Select("select * from t_article where id=#{id}")
    Article selectArticleWithId(Integer id);

    //　发表文章，同时使用@Options注解获取自动生成的主键id
    @Insert("insert into t_article (title,created,modified," +
            "tags,categories,allow_comment,thumbnail,content) " +
            "values (#{title},#{created},#{modified},#{tags}," +
            "#{categories},#{allowComment},#{thumbnail},#{content})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer publishArticle(Article article);

    // 文章发分页查询
    @Select("select * from t_article order by id desc")
    List<Article> selectArticleWithPage();

    // 通过id删除文章
    @Delete("delete from t_article where id=#{id}")
    void deleteArticleWithId(int id);

    // 站点服务统计，统计文章数量
    @Select("select count(1) from t_article")
    Integer countArticle();

    // 通过id更新文章
    Integer updateArticleWithId(Article article);
}
