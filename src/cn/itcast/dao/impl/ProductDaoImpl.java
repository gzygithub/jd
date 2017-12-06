package cn.itcast.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.Product;
import cn.itcast.pojo.SearchResult;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SolrServer solrServer;
	
	public SearchResult getProductList(SolrQuery query){
		SearchResult searchResult = new SearchResult();
		List<Product> list = new ArrayList<Product>();
		try {
			QueryResponse response = solrServer.query(query);
			SolrDocumentList results = response.getResults();
			Long numFound = results.getNumFound();
			searchResult.setRecordCount(numFound.intValue());
			for (SolrDocument doc : results) {
				Product p = new Product();
				String id = (String) doc.getFieldValue("id");
				String product_name = (String) doc.getFieldValue("product_name");
				Float product_price = (Float) doc.getFieldValue("product_price");
				String product_picture = (String) doc.getFieldValue("product_picture");
				Map<String, Map<String, List<String>>> map = response.getHighlighting();
				Map<String, List<String>> map2 = map.get(id);
				List<String> list2 = map2.get("product_name");
				if(list2!=null&&list2.size()>0){
					
					product_name = list2.get(0);
				}
				p.setPid(id);
				p.setName(product_name);
				p.setPrice(product_price);
				p.setPicture(product_picture);
				list.add(p);
			}
			searchResult.setProductList(list);
		} catch (SolrServerException e) {
			
			e.printStackTrace();
		}
		
		return searchResult;
	}
}
