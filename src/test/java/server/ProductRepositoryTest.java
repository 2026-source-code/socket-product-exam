package server;

import org.junit.jupiter.api.Test;

import java.util.List;

public class ProductRepositoryTest {

    ProductRepository repo = new ProductRepository();

    @Test
    public void deleteById_notfound_test(){
        // given
        int id = 999;

        // when
        int result = repo.deleteById(id);

        // eye
        System.out.println("result : "+result);
    }

    @Test
    public void findById_notfound_test(){
        // given
        int id = 999;

        // when
        Product product = repo.findById(id);

        // eye
        System.out.println(product);
    }

    @Test
    public void deleteById_test(){
        // given
        int id = 1;

        // when
        int result = repo.deleteById(id);

        // eye
        System.out.println("result : "+result);
    }

    @Test
    public void findAll_test(){
        // when
        List<Product> list = repo.findAll();

        // eye
        for (Product p : list){
            System.out.println(p);
        }
    }

    @Test
    public void findById_test(){
        // given
        int id = 1;

        // when
        Product product = repo.findById(id);

        // eye
        System.out.println(product);
    }

    @Test
    public void insert_pk_duplicate_test(){
        // given
        int id = 2;
        String name = "딸기";
        int price = 1000;
        int qty = 10;

        // when
        int result = repo.insert(id, name, price, qty);

        // eye
        System.out.println("result : "+result);

    }
}
