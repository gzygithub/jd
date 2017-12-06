package cn.itcast.service;

import org.apache.solr.client.solrj.SolrQuery;

import cn.itcast.pojo.SearchResult;

public interface ProductService {

	public SearchResult getProductList(String queryString,String catalog_name,String price,Integer page,String sort,Integer rows);
}
