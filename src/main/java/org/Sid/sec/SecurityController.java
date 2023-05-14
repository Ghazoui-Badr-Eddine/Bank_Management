package org.Sid.sec;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

	@RequestMapping(value="/login")
	public String login()
	{
		return "SeConnecter";
	}
	@RequestMapping(value="/403")
	public String accesDenid()
	{
		return "403";
	}
}
