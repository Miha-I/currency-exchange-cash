package ua.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(Model model, NoHandlerFoundException ex){
        model.addAttribute("message", ex.getMessage());
        return "forward:/error/pageNotFound";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(Model model, Exception ex){
        model.addAttribute("message", ex.getMessage());
        return "forward:/error/accessDenied";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handlerIllegalArgumentException(Model model, Exception ex) {
        model.addAttribute("massage", ex.getMessage());
        return "forward:/error/pageNotFound";
    }
}
