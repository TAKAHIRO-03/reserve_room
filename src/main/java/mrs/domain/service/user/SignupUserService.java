package mrs.domain.service.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import mrs.app.user.SignupForm;
import mrs.domain.model.RoleName;
import mrs.domain.model.TmpUser;
import mrs.domain.model.User;
import mrs.domain.repository.user.TmpUserRepository;
import mrs.domain.repository.user.UserRepository;


@Service
public class SignupUserService {

	private final UserRepository userRepository;
    private final MapperFactory mapperFactory;
    private final PasswordEncoder passwordEncoder;
    private final TmpUserRepository tmpUserRepository;
	private final MailSender mailSender;

	@Autowired
	public SignupUserService(UserRepository userRepository,
							 MapperFactory mapperFactory,
							 PasswordEncoder passwordEncoder,
							 TmpUserRepository tmpUserRepository,
							 MailSender mailSender) {
		this.userRepository = userRepository;
		this.mapperFactory = mapperFactory;
		this.passwordEncoder = passwordEncoder;
		this.tmpUserRepository = tmpUserRepository;
		this.mailSender = mailSender;
	}

	public void create(String uuid) throws Exception{
		TmpUser tmpUser = tmpUserRepository.findByUuidEquals(uuid).get();
        BoundMapperFacade<TmpUser, User> boundMapperFacade =
                mapperFactory.getMapperFacade(TmpUser.class, User.class);
        User user = boundMapperFacade.map(tmpUser);
		userRepository.save(user);
	}

	public void mailAuth(SignupForm signupForm){
		String uuid =  UUID.randomUUID().toString();
		if(!tmpUserRepository.findByUuidEquals(uuid).isPresent()) {
			TmpUser tmpUser = new TmpUser();
	        signupForm.setRoleName(RoleName.USER);
	        signupForm.setPassword(passwordEncoder.encode(signupForm.getPassword()));
	        BoundMapperFacade<SignupForm, TmpUser> boundMapperFacade =
	                mapperFactory.getMapperFacade(SignupForm.class, TmpUser.class);
	        tmpUser = boundMapperFacade.map(signupForm);
			tmpUser.setUuid(uuid);
			tmpUserRepository.save(tmpUser);
			sendMail(tmpUser);
		}else {
			throw new ExistsTmpUserException("既にメールアドレスが登録されています。");
		}
	}

	public void sendMail(TmpUser tmpUser) {
//        InetAddress ia = null;
//		try {
//			ia = InetAddress.getLocalHost();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}

//        String hostname = ia.getHostName();
        String hostname = "localhost:8080";
        String from = "takachanchan2012@gmail.com";
        String title = "ssp-engine アカウント確認のお願い";
        String content = tmpUser.getUserId() + "さん" + "\n" + "\n" + "以下のリンクにアクセスしてアカウントを認証してください" + "\n"
                +"http://" + hostname
                + "/validate"+ "?id=" + tmpUser.getUuid()
                + "\n"
                + "心当たりの無い方は、メールを削除して下さい。";

	    try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(from);
            msg.setTo(tmpUser.getMail());
            msg.setSubject(title);// タイトルの設定
            msg.setText(content); // 本文の設定
            mailSender.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
