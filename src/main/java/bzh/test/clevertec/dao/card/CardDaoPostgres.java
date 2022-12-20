package bzh.test.clevertec.dao.card;

import bzh.test.clevertec.utils.ConnectionSupplier;
import bzh.test.clevertec.enities.DiscountCard;
import bzh.test.clevertec.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class CardDaoPostgres implements CardDaoInterface {
    private static final Logger logger = LoggerFactory.getLogger(CardDaoPostgres.class);

    @Override
    public Optional<DiscountCard> getDiscountCard(int number) {
        try {
            Connection con = ConnectionSupplier.getConnection();
            try (PreparedStatement st = con.prepareStatement("SELECT * FROM discount_cards WHERE number = ?")) {
                st.setInt(1, number);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    int discount = rs.getInt("discount");
                    logger.debug(" get DiscountCard number={}, discount={}", number, discount);
                    return Optional.of(new DiscountCard(number, discount));
                } else {
                    logger.info("Discount-card with number {} hasn't been found", number);
                    return Optional.empty();
                }
            } catch (SQLException ex) {
                logger.error("Error of DB request DiscountCard with number {}", number);
                throw new DBException("DB Error", ex);
            }
        } catch (SQLException | DBException ex) {
            throw new DBException("DB Error", ex);
        }
    }
}
