package org.springframework.samples.petclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FitSpiritController {

	@RequestMapping("/index")
	public String indexHandler() {
		return "index";
	}
	
}
