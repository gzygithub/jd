package cn.itcast.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.SearchResult;
import cn.itcast.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;
	public SearchResult getProductList(String queryString, String catalog_name,
			String price, Integer page, String sort, Integer rows) {
		SolrQuery query = new SolrQuery();
		if(queryString!=null&&!"".equals(queryString)){
			query.setQuery(queryString);
		}else{
			query.setQuery("*:*");
		}
		if(catalog_name!=null&&!"".equals(catalog_name)){
			query.addFilterQuery("product_catalog_name:"+catalog_name);
		}
		if(price!=null&&!"".equals(price)){
			String[] priceArr = price.split("-");
			query.addFilterQuery("product_price:["+priceArr[0]+" TO "+priceArr[1]+"]");
		}
		int startIndex = (page-1)*rows;
		query.setStart(startIndex);
		query.setRows(rows);
		if("1".equals(sort)){
			query.setSort("product_price", ORDER.asc);
		}else{
			query.setSort("product_price", ORDER.desc);
		}
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		query.set("df","product_keywords");
		SearchResult result = dao.getProductList(query);
		result.setCurPage(page);
		Integer recordCount = result.getRecordCount();
		int totalPage = recordCount/rows;
		if(recordCount%rows!=0){
			totalPage++;
		}
		result.setTotalPages(totalPage);
		return result;
	}

}
