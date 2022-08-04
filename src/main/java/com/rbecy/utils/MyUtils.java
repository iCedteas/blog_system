package com.rbecy.utils;

import com.rbecy.dao.StatisticMapper;
import com.rbecy.model.domain.Article;
import com.rbecy.model.domain.Statistic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUtils {
    /**
     * 封装文章统计数据
     *
     * @param articleList     封装的文章
     * @param statisticMapper 统计mapper
     */
    public static void encapsulatedArticleStatistics(List<Article> articleList, StatisticMapper statisticMapper) {
        for (Article article : articleList) {
            Statistic statistic = statisticMapper.selectStatisticWithArticleId(article.getId());
            article.setHits(statistic.getHits());
            article.setCommentsNum(statistic.getCommentsNum());
        }
    }

    /**
     * 强转对象类型
     *
     * @param obj 对象
     * @param <T> 　类型
     * @return 转换后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

}
