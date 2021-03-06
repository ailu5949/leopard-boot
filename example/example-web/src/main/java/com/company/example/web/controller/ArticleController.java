package com.company.example.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.example.article.Article;
import com.company.example.article.ArticleService;
import com.company.example.web.form.ArticleForm;
import com.company.example.web.vo.ArticleListVO;
import com.company.example.web.vo.ArticleVO;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.QueryBuilder;
import io.leopard.lang.Page;
import io.leopard.lang.datatype.TimeRange;
import io.leopard.lang.util.BeanUtil;

/**
 * 文章
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/article/")
public class ArticleController {

	@Autowired
	private ArticleService articleSerivce;

	@Autowired
	private Jdbc jdbc;

	/**
	 * 添加
	 * 
	 * @param sessUid 当前登录用户ID
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean add(long sessUid, ArticleForm form) {
		Article article = BeanUtil.convert(form, Article.class);
		article.setUid(sessUid);
		articleSerivce.add(article);
		return true;
	}

	/**
	 * 列表
	 * 
	 * @param uid 用户ID
	 * @param start 分页其实位置
	 * @param size 每页记录条数
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public Page<ArticleListVO> list(long uid, TimeRange range, int start, int size) {
		QueryBuilder builder = new QueryBuilder("article");
		builder.addLong("uid", uid);
		builder.range("posttime", range);
		builder.order("posttime");
		return builder.queryForPage(jdbc, ArticleListVO.class, start, size);
	}

	/**
	 * 详情
	 * 
	 * @param articleId 文章ID
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public ArticleVO get(String articleId) {
		Article article = articleSerivce.get(articleId);
		ArticleVO articleVO = BeanUtil.convert(article, ArticleVO.class);
		return articleVO;
	}

	/**
	 * 删除
	 * 
	 * @param sessUid 当前登录用户ID
	 * @param articleId 文章ID
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public boolean delete(long sessUid, String articleId) {
		return articleSerivce.delete(articleId, 0);
	}
}
