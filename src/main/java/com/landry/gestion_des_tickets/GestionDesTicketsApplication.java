package com.landry.gestion_des_tickets;

import com.landry.gestion_des_tickets.tickets.dto.TicketDto;
import com.landry.gestion_des_tickets.tickets.enums.Statut;
import com.landry.gestion_des_tickets.tickets.services.TicketServices;
import com.landry.gestion_des_tickets.users.dao.UsersRepository;
import com.landry.gestion_des_tickets.users.dto.RegisterRequest;
import com.landry.gestion_des_tickets.users.enums.Roles;
import com.landry.gestion_des_tickets.users.models.Usr;
import com.landry.gestion_des_tickets.users.services.authServices.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class GestionDesTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDesTicketsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(

			AuthenticationServiceImpl authenticationServiceImpl,
			TicketServices ticketServices,
			UsersRepository usersRepository
	)
	{
		return args -> {
			var ticket1 = ticketServices.addTicket(TicketDto.builder()
					.title("Jeux Videos")
					.description("Ticket de Jeux Videos")
					.statut(Statut.EN_COURS)
					.build());

			var ticket2 = ticketServices.addTicket(TicketDto.builder()
					.title("Programmation")
					.description("Ticket de Programmation")
					.statut(Statut.TERMINER)
					.build());
			var ticket3 = ticketServices.addTicket(TicketDto.builder()
					.title("Bonus")
					.description("Ticket de Bonus")
					.statut(Statut.ANNULER)
					.build());

			var user1 = RegisterRequest.builder()
					.username("landry")
					.email("landry@gmail.com")
					.password("12345")
					.roles(Roles.ADMIN)
					.build();
			System.out.println("Administrator Token: "+ authenticationServiceImpl.register(user1).getAccessToken());

			var user2 = RegisterRequest.builder()
					.username("willy")
					.email("willy@gmail.com")
					.password("1234")
					.roles(Roles.USER)
					.build();
			System.out.println("Utilisateur Token: "+ authenticationServiceImpl.register(user2).getAccessToken());

			var user3 = RegisterRequest.builder()
					.username("placide")
					.email("placide@gmail.com")
					.password("1234")
					.roles(Roles.USER)
					.build();
			System.out.println("Utilisateur Token: "+ authenticationServiceImpl.register(user3).getAccessToken());

			var user4 = RegisterRequest.builder()
					.username("joel")
					.email("joel@gmail.com")
					.password("12345")
					.roles(Roles.ADMIN)
					.build();

			System.out.println("Administrator Token: "+ authenticationServiceImpl.register(user4).getAccessToken());


			Usr usr = usersRepository.findById(1L).orElseThrow(()-> new RuntimeException("User not found"));
			Usr usr2 = usersRepository.findById(2L).orElseThrow(()-> new RuntimeException("User not found"));
			Usr usr3 = usersRepository.findById(3L).orElseThrow(()-> new RuntimeException("User not found"));
			Usr usr4 = usersRepository.findById(4L).orElseThrow(()-> new RuntimeException("User not found"));
			ticketServices.assignTicketToUser(ticket2.getId(), usr.getId());
			ticketServices.assignTicketToUser(ticket1.getId(), usr2.getId());
			ticketServices.assignTicketToUser(ticket3.getId(), usr4.getId());
			ticketServices.assignTicketToUser(ticket3.getId(), usr3.getId());


		};


	}





}
