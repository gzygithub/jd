package cn.itcast.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.itcast.pojo.SearchResult;

public interface ProductDao {

	public SearchResult getProductList(SolrQuery query);
}
