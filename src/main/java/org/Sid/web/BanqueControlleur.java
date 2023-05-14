package org.Sid.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.Sid.dao.ClientRepository;
import org.Sid.dao.CompteRepository;
import org.Sid.entities.Clients;
import org.Sid.entities.Compte;
import org.Sid.service.IBanqueService;
import org.Sid.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BanqueControlleur {

	@Autowired
	IBanqueService ibanqueService;
	
	@Autowired
	IClientService iclientservice;
	
	@Autowired
	ClientRepository clientRepository;
	
	
	
	@RequestMapping("/")
	public String index()
	{
		return "SeConnecter";
	}
	@RequestMapping("/Acc")
	public String acceuil()
	{
		return "Acc";
	}
	
	@RequestMapping("/ConsulterClient")
	public String ConsulterClient(Model model,HttpSession session,
			@RequestParam(name="page", defaultValue="0")int page , 
			@RequestParam(name="size", defaultValue="5")int size)
	{
		Page<Clients> clients = iclientservice.listClients(page, size);
		model.addAttribute("clients",clients.getContent());
		int[] pages = new int[clients.getTotalPages()];
		model.addAttribute("pages", pages);
		return "ConsulterClient";
	}
	
	
	@RequestMapping("/ModifierClient")
	public String modifiercompte(Model model,long code)
	{
		
		Clients c =clientRepository.findById(code).get();
		model.addAttribute("client",c);
		return "ModifierClient";
	}
	
	@RequestMapping(value="/Modifier")
	public String modifier(long code, String email, String nom)
	{
		
		Clients c =clientRepository.findById(code).get();
		//System.out.println(c.getEmail());
		
		c.setEmail(email);
		c.setNom(nom);
		clientRepository.save(c);
		//model.addAttribute("client",c);
		return "redirect:/ModifierClient?code="+c.getCode();
		//return "ModifierClient";
	}
	
	@RequestMapping(value="/deleteClient")
	public String delete(long code)
	{
		clientRepository.deleteById(code);
		return "redirect:/ConsulterClient";
		
	}
	
	
	@RequestMapping(value="/ajouterClient")
	public String ajouter(Model model)
	{
		return "Ajouter";	
	}
	
	@RequestMapping(value="/ajouter", method=RequestMethod.POST)
	public String ajouter(Model model,RedirectAttributes rd,String email, String nom)
	{
		
		
		Clients cl = clientRepository.findByEmail(email);
		if( cl == null ) {
			Clients client = new Clients();
			
			client.setEmail(email);
			client.setNom(nom);
			clientRepository.save(client);
			rd.addFlashAttribute("success", " client bien ajouter");
			
		
		}else {
			rd.addFlashAttribute("error", " client deja exi");
		}
		
		return "redirect:/ajouterClient";
		
	}

	

	
	@RequestMapping("/ConsulterCompte")
	public String ConsulterCompte()
	{
		return "ConsulterCompte";
	}
	
	@RequestMapping("/consulterCompte")
	public String Consulter(Model model ,String codeCompte, HttpSession session ,
			@RequestParam(name="page", defaultValue="0")int page , 
			@RequestParam(name="size", defaultValue="5")int size)
	{
		model.addAttribute("codeCompte",codeCompte);
		try {
			Compte cp= ibanqueService.consulterCPTE(codeCompte);
			Page operations = ibanqueService.ListeOperation(codeCompte, page,size);
			model.addAttribute("operations",operations.getContent());
			int[] pages = new int[operations.getTotalPages()];
			model.addAttribute("pages", pages);
			model.addAttribute("compte",cp);
			model.addAttribute("currentpage",page);
			
			
			session.setAttribute("codeCompte", codeCompte);
			session.setAttribute("monCompte", cp);
			session.setAttribute("monOp", operations);
			session.setAttribute("pages", pages);
			session.setAttribute("currentpage", page);
			
			
		}
		catch(Exception e)
		{
			model.addAttribute("exception",e);
		}
		
		return "ConsulterCompte";
	}
	
	@RequestMapping("/Operations")
	public String operation()
	{
		return "DoOperation";
	}
	
	@RequestMapping("/Deconnecter")
	public String deconnecter( HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.invalidate();
		session = request.getSession(false);
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value="/SaveOperation",method=RequestMethod.POST)
	public String Saveoperation(Model model , String codeCompte ,String codeCompte2 , double montant , String typeOp)
	{
		try {
			if(typeOp.equals("vir"))
				ibanqueService.virement(codeCompte, codeCompte2, montant);
			else 
				if((typeOp.equals("vers")))
						ibanqueService.Verser(codeCompte, montant);
				else
					if(typeOp.equals("ret"))
						ibanqueService.retirer(codeCompte, montant);
			
		}
		catch( Exception e)
		{
			model.addAttribute("erreur" ,e);
			System.out.println(e.getMessage());
			return "redirect:/Operations?erreur="+e.getMessage();
		}
		
		
		return "redirect:/consulterCompte?codeCompte="+codeCompte;
	}
}
