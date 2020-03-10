package social.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import social.model.User;
import social.service.UserService;

import java.util.Locale;

@Component
public class UserValidator implements Validator
{
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public boolean supports(Class<?> aClass)
    {
        return User.class.equals(aClass);
    }

    /**
     * @Override
     * Validation when new user is adding
     */
    public void validate(Object target, Errors errors)
    {
        User user = (User) target;
        Locale locale = Locale.getDefault(); // TODO get from app context

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");

        String sizeMessage = messageSource.getMessage("validation.basic.username.size", new Object[]{""}, locale);

        if (user.getUsername().length() < 4 || user.getUsername().length() > 32)
            errors.rejectValue("username", sizeMessage);

        if (userService.findByName(user.getUsername()) != null)
            errors.rejectValue("username", "validation.basic.username.duplication");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");

        if (user.getPassword().length() < 4 || user.getPassword().length() > 32)
            errors.rejectValue("password", "validation.basic.password.size");

        if (!user.getPasswordConfirmed().equals(user.getPassword()))
            errors.rejectValue("password", "validation.basic.password.matching");
    }
}
