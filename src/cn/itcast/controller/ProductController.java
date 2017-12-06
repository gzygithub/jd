package cn.itcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.pojo.SearchResult;
import cn.itcast.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	@RequestMapping("/list")
	public String getProductList(String queryString, String catalog_name,String price, 
			@RequestParam(defaultValue="1")Integer page, 
			@RequestParam(defaultValue="1")String sort, 
			@RequestParam(defaultValue="60")Integer rows,Model model){
		SearchResult result = service.getProductList(queryString, catalog_name, price, page, sort, rows);
		model.addAttribute("result", result);
		model.addAttribute("queryString", queryString);
		model.addAttribute("catalog_name", catalog_name);
		model.addAttribute("price", price);
		model.addAttribute("sort", sort);
		return "product_list";
	}
}
