package mrs.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mrs.domain.service.user.SignupUserService;


@Controller
public class SignupController {

	private final SignupUserService signupUserService;

	@Autowired
	public SignupController(SignupUserService signupUserService) {
		this.signupUserService = signupUserService;
	}

	@GetMapping("/signupForm")
	public String getSignupForm(SignupForm signupForm, Model model) {
		return "login/signupForm";
	}

	@PostMapping("/signupForm")
	public String postSignupForm(@ModelAttribute(value = "signupForm") @Validated SignupForm signupForm,
							  	 BindingResult bindingResult,
							  	 RedirectAttributes redirectAttributes,
							  	 Model model) {

       if (bindingResult.hasErrors()) {
       	return getSignupForm(signupForm, model);
       }

        try {
        	signupUserService.mailAuth(signupForm);
        	redirectAttributes.addAttribute("flash", "メール認証用のメールを送りました。メールボックスを確認して下さい。");
		} catch (Exception e) {
			model.addAttribute("error" , "エラーが発生しました。");
	       	return getSignupForm(signupForm, model);
		}
		return "redirect:login/loginForm";
	}

    @GetMapping("/validate")
	public String getMailAuth(@RequestParam("id") String uuid,
							  RedirectAttributes redirectAttributes) {
    	try {
			signupUserService.create(uuid);
			redirectAttributes.addAttribute("flash","認証されました。");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login/loginForm";
	}

}
