package br.com.cinemenu.cinemenuapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "CineMenu-API",
        version = "1.0",
        description = """
                ENGLISH:
                                
                Welcome to Cine Menu API, your gateway to the world of movies and TV series!
                                
                Cine Menu API is designed for cinema and TV series enthusiasts, providing a platform where you can create your customized account and curate lists of movies and series while accessing detailed information about your favorite media.
                                
                Key features of Cine Menu API:
                                
                - **Create Your Custom Account**: Register and create your unique account to unlock the full range of API features.
                                
                - **Curate Custom Lists**: Build and manage your own lists of movies and series, organizing your entertainment choices as you see fit.
                                
                - **Search and Explore**: Easily look up comprehensive information about movies and series, including synopses, cast, ratings, and more.
                                
                - **Share Your Lists**: Share your personalized lists with friends, family, or other Cine Menu API users.
                                
                - **Copy Lists from Other Users**: Discover intriguing lists created by other users and duplicate them to your own account.
                                
                Cine Menu API is your ultimate tool to explore and enjoy the world of entertainment. Create your account today and start customizing your cinema and TV series experience like never before!
                                
                                
                PT-BR:
                                
                Bem-vindo à Cine Menu API, o seu guia para o mundo do cinema e séries de TV!
                                
                A Cine Menu API foi criada para apaixonados por cinema e séries, oferecendo uma plataforma onde você pode criar sua conta personalizada e montar listas de filmes e séries, além de acessar informações detalhadas sobre suas mídias favoritas.\s
                                
                Principais recursos da Cine Menu API:
                               
                - **Crie Sua Conta Personalizada**: Registre-se e crie sua conta exclusiva para aproveitar todos os recursos da API.
                               
                - **Monte Listas Personalizadas**: Crie e gerencie suas próprias listas de filmes e séries, organizando suas escolhas de entretenimento da maneira que desejar.
                                
                - **Pesquise e Explore**: Pesquise facilmente informações detalhadas sobre filmes e séries, incluindo sinopse, elenco, classificações e muito mais.
                                
                - **Compartilhe Suas Listas**: Compartilhe suas listas personalizadas com amigos e familiares ou outros usuários da Cine Menu API.
                                
                - **Copie Listas de Outros Usuários**: Descubra listas interessantes criadas por outros usuários e copie-as para a sua própria conta.
                                
                A Cine Menu API é a sua ferramenta definitiva para explorar e desfrutar de um mundo de entretenimento. Crie sua conta hoje e comece a personalizar sua experiência de cinema e séries como nunca antes!
                                
                     Media Types = TV, MOVIE or PERSON
                     CineMenu Genres IDs: 
                            ACTION = 67
                            ADVENTURE = 89
                            ANIMATION = 13
                            COMEDY = 32
                            CRIME = 70
                            DOCUMENTARY = 72
                            DRAMA = 76
                            FAMILY = 46
                            FANTASY = 98
                            HISTORY = 57
                            HORROR = 66
                            MUSIC = 91
                            MYSTERY = 16
                            ROMANCE = 38
                            SCIENCE_FICTION = 78
                            THRILLER = 11        
                """,
        contact = @Contact(
                name = "Guilherme Roncari",
                email = "GuilhermeABRoncari@hotmail.com"
        )))
public class CineMenuApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CineMenuApiApplication.class, args);
    }
}
