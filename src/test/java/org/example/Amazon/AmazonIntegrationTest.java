package org.example.Amazon;

import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.RegularCost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AmazonIntegrationTest {

    private Database database;
    private ShoppingCartAdaptor cartAdaptor;
    private Amazon amazon;

    @BeforeEach
    void setUp() {
        database = new Database();
        database.resetDatabase();
        cartAdaptor = new ShoppingCartAdaptor(database);

        List<PriceRule> rules = List.of(
                new RegularCost(),
                new DeliveryPrice(),
                new ExtraCostForElectronics()
        );

        amazon = new Amazon(cartAdaptor, rules);
    }

    @AfterEach
    void tearDown() {
        database.close();
    }

    @Nested
    @DisplayName("specification-based")
    class SpecificationBasedTests {

        @Test
        void addToCart_shouldPersistItemInDatabase() {
            Item item = new Item(ItemType.OTHER, "Book", 2, 15.0);

            amazon.addToCart(item);

            List<Item> items = cartAdaptor.getItems();
            assertThat(items).hasSize(1);
            assertThat(items.get(0).getName()).isEqualTo("Book");
            assertThat(items.get(0).getType()).isEqualTo(ItemType.OTHER);
            assertThat(items.get(0).getQuantity()).isEqualTo(2);
            assertThat(items.get(0).getPricePerUnit()).isEqualTo(15.0);
        }

        @Test
        void calculate_shouldReturnRegularPlusDeliveryPlusElectronicFee() {
            amazon.addToCart(new Item(ItemType.ELECTRONIC, "Tablet", 1, 300.0));
            amazon.addToCart(new Item(ItemType.OTHER, "Cover", 2, 25.0));

            double result = amazon.calculate();

            assertThat(result).isEqualTo(362.5);
        }

        @Test
        void calculate_shouldReturnZero_whenCartIsEmpty() {
            double result = amazon.calculate();

            assertThat(result).isEqualTo(0.0);
        }
    }

    @Nested
    @DisplayName("structural-based")
    class StructuralBasedTests {

        @Test
        void getItems_shouldReturnAllPersistedItems() {
            cartAdaptor.add(new Item(ItemType.OTHER, "Pen", 1, 2.5));
            cartAdaptor.add(new Item(ItemType.ELECTRONIC, "Mouse", 1, 25.0));

            List<Item> items = cartAdaptor.getItems();

            assertThat(items).hasSize(2);
            assertThat(items)
                    .extracting(Item::getName)
                    .containsExactlyInAnyOrder("Pen", "Mouse");
        }

        @Test
        void numberOfItems_shouldReturnCorrectCount() {
            cartAdaptor.add(new Item(ItemType.OTHER, "Pen", 1, 2.5));
            cartAdaptor.add(new Item(ItemType.ELECTRONIC, "Mouse", 1, 25.0));

            int count = cartAdaptor.numberOfItems();

            assertThat(count).isEqualTo(2);
        }

        @Test
        void resetDatabase_shouldClearAllItems() {
            cartAdaptor.add(new Item(ItemType.OTHER, "Pen", 1, 2.5));
            database.resetDatabase();

            List<Item> items = cartAdaptor.getItems();

            assertThat(items).isEmpty();
        }
    }
}