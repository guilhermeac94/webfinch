package com.finch.webfinch.validator;

import com.finch.webfinch.model.Cliente;
import com.finch.webfinch.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validador do formulário de cadastro de cliente.
 * 
 * @author guilherme.carvalho
 */
@Component
public class ClienteFormValidator implements Validator {

    @Autowired
    @Qualifier("emailValidator")
    EmailValidator emailValidator;

    @Autowired
    ClienteService clienteService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Cliente cliente = (Cliente) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "NotEmpty.clienteForm.id");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.clienteForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.clienteForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", "NotEmpty.clienteForm.sex");

        if (!emailValidator.valid(cliente.getEmail())) {
            errors.rejectValue("email", "Pattern.clienteForm.email");
        }       
    }

}
