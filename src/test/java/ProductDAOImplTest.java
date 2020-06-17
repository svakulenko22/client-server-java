import config.DatabaseConfig;
import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
import entity.Product;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDAOImplTest {

    private static ProductDAO productDAO;
    private static int productId;

    @BeforeClass
    public static void beforeClass() {
        DatabaseConfig.connect();
        productDAO = new ProductDAOImpl();
    }

    @Test
    public void aSave() {
        final Product product = new Product();
        product.setName("Кус кус");
        product.setPrice(80.0);
        product.setCount(12);
        final Integer savedInteger = productDAO.save(product);
        System.out.println(savedInteger);
        productId = savedInteger;
        Assert.assertNotNull(savedInteger);
    }

    @Test
    public void bFindById() {
        final Product product = productDAO.findById(productId);
        Assert.assertEquals(product.getName(), "Кус кус");
    }

    @Test
    public void cFindAll() {
        final List<Product> products = productDAO.findAll();
        Assert.assertTrue(products.size() > 0);
    }

    @Test
    public void dUpdate() {
        productDAO.update(new Product(productId, "Авокадо", 1, 19.80));

        final Product product = productDAO.findById(productId);
        Assert.assertEquals(product.getName(), "Авокадо");
    }

    @Test
    public void eDelete() {
        productDAO.delete(productId);
        final Product product = productDAO.findById(productId);
        Assert.assertNull(product);
    }

    @AfterClass
    public static void afterClass() {
        DatabaseConfig.close();
    }
}
