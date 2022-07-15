package com.insigniait.accessControl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/")
public class IndexController {
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public String interfazPrincipal() {
		return "index/index.html";
	}
}
