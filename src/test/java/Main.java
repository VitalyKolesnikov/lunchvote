import org.example.lunchvote.model.Dish;
import org.example.lunchvote.repository.DataJpaUserRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");

        Dish dish = new Dish();
        dish.setId(15);
        dish.setName("Burger");
        dish.setPrice(150);

        System.out.println(dish);

        System.out.println(ctx.getBean(DataJpaUserRepository.class).getAll());
    }
}