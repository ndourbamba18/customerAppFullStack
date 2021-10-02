package com.saraya.customer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.saraya.customer.entities.Customer;
import com.saraya.customer.service.CustomerService;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String homePage(Model model) {
		model.addAttribute("customers", customerService.selectAllCustomers());
		return "home";
	}
	
	@RequestMapping(path = "/about", method = RequestMethod.GET)
	public String aboutPage() {
		return "about";
	}
	
	@RequestMapping(path = "/contact", method = RequestMethod.GET)
	public String contactPage() {
		return "contact";
	}
	
	@RequestMapping(path = "/detail-customer/{id}", method = RequestMethod.GET)
	public String detailCustomerPage(@PathVariable("id") int id, Model model) {
		Customer customer = customerService.selectCustomerById(id);
		model.addAttribute("customer", customer);
		return "customerDetail";
	}
	
	@RequestMapping(path = "/form-customer", method = RequestMethod.GET)
	public String newCustomerForm(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "customerForm";
	}
	
	@RequestMapping(path = "/save-customer", method = RequestMethod.POST)
	public String saveNewCustomer(Model model, @Valid @ModelAttribute("customer")Customer customerData, BindingResult bindingResult) {
		try {
			if(bindingResult.hasErrors()) {
				return "customerForm";
			}
			if(customerData.getName()=="" || customerData.getEmail()=="" || customerData.getPhone()=="" || customerData.getAddress()=="") {
				return "customerForm";
			}
			
			Customer customer = new Customer(customerData.getId(), customerData.getName(), customerData.getEmail(),
					                                                   customerData.getPhone(), customerData.getAddress());
			model.addAttribute("customer", customer);
			customerService.insertCustomer(customer);
			return "redirect:/";
		} catch (Exception e) {
			return "customerForm";
		}
	}
	
	@RequestMapping(path = "/delete-customer/{id}", method = RequestMethod.GET)
	public String deleteCustomerById(@PathVariable("id") int id) throws Exception{
		customerService.deleteCustomer(id);
		return "redirect:/";
	}
	
	 // UPDATE CUSTOMER FORM BY ID
	@RequestMapping(path = "/update-customer/{id}", method = RequestMethod.GET)
    public String updateCustomerForm(Model model, @PathVariable("id")int id){
        Customer customer = customerService.selectCustomerById(id);
        model.addAttribute("customer", customer);
        return "updateCustomerForm";
    }
	
	 // UPDATE CUSTOMER BY ID
    @PostMapping(path = "/edit-customer/{id}")
    public String updateCustomerById(@PathVariable("id") int id, Model model, @Valid @ModelAttribute("customer") Customer customerData, BindingResult bindingResult) throws Exception {
    	if(bindingResult.hasFieldErrors()) {
    		return "updateCustomerForm";
    	}
    	
    	Customer customer = customerService.selectCustomerById(id);
    	customer.setName(customerData.getName());
    	customer.setEmail(customerData.getEmail());
    	customer.setPhone(customerData.getPhone());
    	customer.setAddress(customerData.getAddress());
    	customerService.updateCustomer(customer);
    	return "redirect:/";
    }

}
