package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {
	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	public FundamentosApplication(
			@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
			MyBean myBean,
			MyBeanWithDependency myBeanWithDependency,
			MyBeanWithProperties myBeanWithProperties,
			UserPojo userPojo,
			UserRepository userRepository
	) {
		this.componentDependency = componentDependency;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.myBean = myBean;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		// ejemplosAnteriores();
		SaveUsersInDb();
		getInformationJplFromUser();
	}

	private void getInformationJplFromUser() {
		LOGGER.info("Usuario con el metodo findByUserEmail: " +
				userRepository
					.findUserByEmail("japodaca@gmail.com")
						.orElseThrow(()->new RuntimeException("No se encontro el usuario"))
		);

		userRepository.listAndSort("usuario", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Usuario:" + user));
	}

	private void SaveUsersInDb() {
		User user1 = new User("Erubiel", "japodaca@gmail.com", LocalDate.of(1990, 9, 28));
		User user2 = new User("Julio", "jcesar@gmail.com", LocalDate.of(1991, 5, 24));
		User user3 = new User("Maritza", "maritza@gmail.com", LocalDate.of(1994, 6, 14));
		User user4 = new User("usuario1", "juan@gmail.com", LocalDate.of(1993, 7, 10));
		User user5 = new User("usuario2", "Paul@gmail.com", LocalDate.of(1992, 8, 16));
		User user6 = new User("usuario3", "ivan@gmail.com", LocalDate.of(1996, 9, 26));
		User user7 = new User("Lucifer", "lucifer@gmail.com", LocalDate.of(1990, 10, 11));
		User user8 = new User("Maria", "maria@gmail.com", LocalDate.of(1990, 11, 21));
		User user9 = new User("Roberta", "roberta@gmail.com", LocalDate.of(1990, 12, 1));
		User user10 = new User("Luis", "luis@gmail.com", LocalDate.of(1990, 1, 2));
		List<User> userList = Arrays.asList(
				user1, user2, user3,
				user4, user5, user6,
				user7, user8, user9,
				user10
		);
		userList
			.stream()
			.forEach(userRepository::save);
	}

	private void ejemplosAnteriores() {
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());
		try {
			int valor = 10 / 0;
			LOGGER.debug("El valor es: " + valor);
		} catch (Exception e) {
			LOGGER.error("Error al dividir 10 / 0" + e.getMessage());
		}
	}

}
