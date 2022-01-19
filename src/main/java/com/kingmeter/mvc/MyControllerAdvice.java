package com.kingmeter.mvc;


import com.kingmeter.common.KingMeterException;
import com.kingmeter.common.ResponseCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class MyControllerAdvice {

    /**
     * Applies to all annotation methods and initializes the data binder before it executes
     * @param binder
     */
    @InitBinder
    public void initWebBinder(WebDataBinder binder) {
    }

    /**
     * put value into model
     * @param model
     */
    @ModelAttribute
    public void addAttribute(Model model) {
        model.addAttribute("attribute", "The Attribute");
    }

    /**
     * catch Exception
     * @param e
     * @return json
     */
    @ResponseBody
    @ExceptionHandler({KingMeterException.class})
    public ResponseCode customExceptionHandler(KingMeterException e) {
        return ResponseCode.getEnum(e.getCode());
    }

}
