package com.finch.webfinch.controller;

import com.finch.webfinch.model.Cliente;
import com.finch.webfinch.service.ClienteService;
import com.finch.webfinch.validator.ClienteFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * Controller do {@link Cliente}, responsável por toda interação com o front.
 * 
 * @author guilherme.carvalho
 */
@Controller
public class ClienteController {

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    ClienteFormValidator clienteFormValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(clienteFormValidator);
    }

    private ClienteService clienteService;

    @Autowired
    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        logger.debug("index()");
        return "redirect:/clientes";
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public String list(Model model) {

        logger.debug("list()");
        try {
            model.addAttribute("clientes", clienteService.findAll());
        } catch (Exception e) {
            model.addAttribute("clientes", null);
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "API não está rodando!");
        }
        return "clientes/list";

    }

    @RequestMapping(value = "/clientes/add", method = RequestMethod.GET)
    public String add(Model model) {
        logger.debug("add()");
        Cliente cliente = new Cliente();
        model.addAttribute("clienteForm", cliente);
        model.addAttribute("create", true);
        return "clientes/form";
    }

    @RequestMapping(value = "/clientes/{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable("id") Long id, Model model, final RedirectAttributes redirectAttributes) {
        logger.debug("update() : {}", id);
        try {
            Cliente cliente = clienteService.findById(id);
            if (cliente == null) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Cliente não encontrado!");
                return "redirect:/clientes";
            }
            model.addAttribute("clienteForm", cliente);
            model.addAttribute("create", false);
            return "clientes/form";
        } catch (Exception e) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "API não está rodando!");
            return "clientes/list";
        }
    }

    @RequestMapping(value = "/clientes/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {
        logger.debug("delete() : {}", id);
        try {
            clienteService.remove(id);
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Cliente foi deletado!");
            return "redirect:/clientes";
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Cliente não encontrado!");
            }
            return "redirect:/clientes";
        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("css", "danger");
            redirectAttributes.addFlashAttribute("msg", "API não está rodando!");
            return "redirect:/clientes";
        }
    }

    @RequestMapping(value = "/clientes/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model, final RedirectAttributes redirectAttributes) {

        logger.debug("showUser() id: {}", id);

        try {
            Cliente cliente = clienteService.findById(id);
            if (cliente == null) {
                redirectAttributes.addFlashAttribute("css", "danger");
                redirectAttributes.addFlashAttribute("msg", "Cliente não encontrado");
                return "redirect:/clientes";
            }
            model.addAttribute("cliente", cliente);

            return "clientes/show";
        } catch (Exception e) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "API não está rodando!");
            return "clientes/list";
        }

    }

    @RequestMapping(value = "/clientes/new", method = RequestMethod.POST)
    public String create(@ModelAttribute("clienteForm") @Validated Cliente cliente,
            BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
        logger.debug("create() : {}", cliente);
        if (result.hasErrors()) {
            model.addAttribute("create", true);
            return "clientes/form";
        } else {

            try {
                if (clienteService.findById(cliente.getId()) != null) {
                    model.addAttribute("css", "danger");
                    model.addAttribute("msg", "ID já existente!");
                    model.addAttribute("create", true);
                    return "clientes/form";
                }

                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Cliente cadastrado com sucesso!");
                clienteService.create(cliente);

                return "redirect:/clientes/" + cliente.getId();
            } catch (Exception e) {
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "API não está rodando!");
                model.addAttribute("create", true);
                return "clientes/form";
            }
        }

    }

    @RequestMapping(value = "/clientes/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("clienteForm") @Validated Cliente cliente,
            BindingResult result, Model model, final RedirectAttributes redirectAttributes) {

        logger.debug("update() : {}", cliente);

        if (result.hasErrors()) {
            model.addAttribute("create", false);
            return "clientes/form";
        } else {
            try {
                if (clienteService.findById(cliente.getId()) == null) {
                    redirectAttributes.addFlashAttribute("css", "danger");
                    redirectAttributes.addFlashAttribute("msg", "ID não existente!");
                    return "redirect:/clientes";
                }
                redirectAttributes.addFlashAttribute("css", "success");
                redirectAttributes.addFlashAttribute("msg", "Cliente alterado com sucesso!");
                clienteService.update(cliente);
                
                return "redirect:/clientes/" + cliente.getId();
            } catch (Exception e) {
                model.addAttribute("css", "danger");
                model.addAttribute("msg", "API não está rodando!");
                model.addAttribute("create", false);
                return "clientes/form";
            }
        }

    }
}
