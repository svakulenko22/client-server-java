import config.DatabaseConfig;
import entity.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.MAX_PRIORITY;

public class Main {
    public static void main(String[] args) throws Exception {
        DatabaseConfig.connect();

        final ExecutorService threadPool = Executors.newFixedThreadPool(1);

        Runnable runnable = Main::makingProducts;
        threadPool.execute(runnable);

        threadPool.shutdown();
        threadPool.awaitTermination(MAX_PRIORITY, TimeUnit.HOURS);

        DatabaseConfig.close();

    }

    private static synchronized void makingProducts() {

        ProductService productService = new ProductServiceImpl();

        final Product product = new Product();
        product.setName("Гречка");
        product.setPrice(40.00);
        product.setCount(20);

        final Integer savedId = productService.save(product);

        final Product productById = productService.findById(savedId);
        System.out.println("Product by id: " + productById.toString());

        final List<Product> products = productService.findAll();
       // for (Product elem : products) {
         //   System.out.println(elem);
        //}
        products.forEach(System.out::println);

        productById.setPrice(40.01);
        productService.update(productById);

        productService.delete(productById.getId());
    }
}