package bzh.test.clevertec.dao.product;

import bzh.test.clevertec.utils.ConnectionSupplier;
import bzh.test.clevertec.enities.Product;
import bzh.test.clevertec.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductDaoPostgres implements ProductDaoInterface {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoPostgres.class);

    @Override
    public Optional<Product> getProductById(int id) {
        try {
            Connection con = ConnectionSupplier.getConnection();
            try (PreparedStatement st = con.prepareStatement("SELECT * FROM products WHERE id = ?")) {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    int discountType = rs.getInt("discountType");
                    logger.debug(" get Product id={}, name={}, price={}, discountType={}", id, name, price, discountType);
                    return Optional.of(new Product(id, name, price, discountType));
                } else {
                    logger.info("Product with id {} hasn't been found", id);
                    return Optional.empty();
                }
            } catch (SQLException ex) {
                logger.error("Error of DB request Product with id {}", id);
                throw new DBException("DB Error", ex);
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("DB Error", ex);
        }
    }
}