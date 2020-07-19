package jp.co.ssd.demo.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.ssd.demo.service.UserService;
import jp.co.ssd.demo.web.common.RedisSessionManager;
import jp.co.ssd.demo.web.model.UserListForm;

@Controller
public class UserListController {
	@Autowired
	private UserService userService;
	
	@Autowired 
	private RedisSessionManager session;
	
	@GetMapping("userList")
	public String init(@ModelAttribute UserListForm form) {
		form.setUserList(userService.findAll());
		
		return "userList";
	}
	
	@PostMapping(value = "userList", params = {"searchByName"})
	public String searchByName(@ModelAttribute UserListForm form) {
		if (StringUtils.isEmpty(form.getSearchUserName())) {
			form.setUserList(userService.findAll());
		} else {
			form.setUserList(userService.findByUserNameLike(form.getSearchUserName()));
			//form.setUserList(userService.findByUserNameQuery(form.getSearchName()));
		}
		return "userList";
	}

   @PostMapping(value = "userList", params = {"searchByMail"})
    public String searchByMail(@ModelAttribute UserListForm form) {
        if (StringUtils.isEmpty(form.getSearchUserEmail())) {
            form.setUserList(userService.findAll());
        } else {
            form.setUserList(userService.findByUserMailLike(form.getSearchUserEmail()));
            //form.setUserList(userService.findByUserNameQuery(form.getSearchName()));
        }
        return "userList";
    }
	@PostMapping(value = "userList", params = {"search"})
	public String search(@ModelAttribute UserListForm form) {
		form.setUserList(userService.findBySpecification(form.getSearchUserId(), form.getSearchUserName(), form.getSearchUserEmail()));
		return "userList";
	}

	@PostMapping(value = "userList", params = {"searchById"})
	public String searchById(@ModelAttribute UserListForm form) {
		form.setUserList(userService.findByUserId(form.getSearchUserId()));
		return "userList";
	}

	@PostMapping(value = "userList", params = {"delete"})
	public String deleteById(@ModelAttribute UserListForm form) {
		userService.deleteById(form.getSearchUserId());
		form.setUserList(userService.findAll());
		return "userList";
	}
	

	@PostMapping(value = "userList", params = {"new"})
	public String createNewUser() {
	    session.set("action", "new");
	    session.set("userSerialId", 0);
		//return "redirect:userDetail/new/0";
	    return "redirect:userDetail";
	}
	
	@PostMapping(value = "userList", params = {"update"})
    public String deleteById(@ModelAttribute UserListForm form, HttpServletRequest req) {
	    Long userSerialId = Long.valueOf(req.getParameter("update"));
	    session.set("action", "update");
        session.set("userSerialId", userSerialId);
	    
        //return "redirect:userDetail/update/" + String.valueOf(userSerialId);
        return "redirect:userDetail";
    }

   @PostMapping(value = "userList", params = {"delete2"})
    public String delete(@ModelAttribute UserListForm form, HttpServletRequest req) {
        Long userSerialId = Long.valueOf(req.getParameter("delete2"));
        
        userService.deleteBySerialId(userSerialId);
        form.setUserList(userService.findAll());
        return "userList";
    }

}
