package jp.co.ssd.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.ssd.demo.domain.entity.User;
import jp.co.ssd.demo.service.UserService;
import jp.co.ssd.demo.web.common.RedisSessionManager;
import jp.co.ssd.demo.web.model.UserDetailForm;

@Controller
public class UserDetailController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisSessionManager session;
	
	@GetMapping("userDetail")
	public String init(@ModelAttribute UserDetailForm form) {
	    String action = session.get("action").toString();
	    String userSerialId = session.get("userSerialId").toString();
        if ("new".equals(action)) {
            return "userDetail";
        } else {
           
            User user = userService.findOne(Long.valueOf(userSerialId));
            form.setUserId(user.getUserId());
            form.setUserName(user.getUserName());
            form.setUserEmail(user.getUserMail());
            form.setUserSerialId(Long.valueOf(userSerialId));
        }
        
        return "userDetail";
	}
	
	@GetMapping("userDetail/{action}/{userSerialId}")
	public String init2(@PathVariable("action") String action,
            @PathVariable("userSerialId") String userSerialId, 
            @ModelAttribute UserDetailForm form) {
	    
	    if ("new".equals(action)) {
	        return "userDetail";
	    } else {
	       
	        User user = userService.findOne(Long.valueOf(userSerialId));
	        form.setUserId(user.getUserId());
	        form.setUserName(user.getUserName());
	        form.setUserEmail(user.getUserMail());
	        form.setUserSerialId(Long.valueOf(userSerialId));
	    }
	    
	    return "userDetail";
	}
	
	@PostMapping(value = "userDetail", params = {"save"}) 
	public String save(@Validated UserDetailForm form, BindingResult result) {
		if (result.hasErrors()) {
            return "userDetail";
        }  
		userService.save(form.getUserSerialId(), form.getUserId(), form.getUserName(), form.getUserEmail());
		return "redirect:userList";
	}
}
