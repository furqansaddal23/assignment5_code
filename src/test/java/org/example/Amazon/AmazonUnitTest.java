package org.example.Amazon;

import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.PriceRule;
import org.example.Amazon.Cost.RegularCost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AmazonUnitTest {

    @Nested
    @DisplayName("specification-based")
    class SpecificationBasedTests {

        @Test
        void regularCost_shouldSumPriceTimesQuantity() {
            RegularCost rule = new RegularCost();
            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "Book", 2, 10.0),
                    new Item(ItemType.ELECTRONIC, "Headphones", 1, 50.0)
            );

            double result = rule.priceToAggregate(cart);

            assertThat(result).isEqualTo(70.0);
        }

        @Test
        void extraCostForElectronics_shouldAdd750_whenElectronicExists() {
            ExtraCostForElectronics rule = new ExtraCostForElectronics();
            List<Item> cart = List.of(
                    new Item(ItemType.ELECTRONIC, "Laptop", 1, 1000.0)
            );

            double result = rule.priceToAggregate(cart);

            assertThat(result).isEqualTo(7.50);
        }

        @Test
        void extraCostForElectronics_shouldAddZero_whenNoElectronicExists() {
            ExtraCostForElectronics rule = new ExtraCostForElectronics();
            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "Book", 2, 15.0)
            );

            double result = rule.priceToAggregate(cart);

            assertThat(result).isEqualTo(0.0);
        }

        @Test
        void deliveryPrice_shouldBeZero_forEmptyCart() {
            DeliveryPrice rule = new DeliveryPrice();

            double result = rule.priceToAggregate(List.of());

            assertThat(result).isEqualTo(0.0);
        }

        @Test
        void deliveryPrice_shouldBeFive_forOneToThreeDistinctItems() {
            DeliveryPrice rule = new DeliveryPrice();
            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "A", 1, 1.0),
                    new Item(ItemType.OTHER, "B", 1, 1.0),
                    new Item(ItemType.OTHER, "C", 1, 1.0)
            );

            double result = rule.priceToAggregate(cart);

            assertThat(result).isEqualTo(5.0);
        }

        @Test
        void deliveryPrice_shouldBeTwelvePointFive_forFourToTenDistinctItems() {
            DeliveryPrice rule = new DeliveryPrice();
            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "A", 1, 1.0),
                    new Item(ItemType.OTHER, "B", 1, 1.0),
                    new Item(ItemType.OTHER, "C", 1, 1.0),
                    new Item(ItemType.OTHER, "D", 1, 1.0)
            );

            double result = rule.priceToAggregate(cart);

            assertThat(result).isEqualTo(12.5);
        }

        @Test
        void deliveryPrice_shouldBeTwenty_forMoreThanTenDistinctItems() {
            DeliveryPrice rule = new DeliveryPrice();
            List<Item> cart = List.of(
                    new Item(ItemType.OTHER, "1", 1, 1.0),
                    new Item(ItemType.OTHER, "2", 1, 1.0),
                    new Item(ItemType.OTHER, "3", 1, 1.0),
                    new Item(ItemType.OTHER, "4", 1, 1.0),
                    new Item(ItemType.OTHER, "5", 1, 1.0),
                    new Item(ItemType.OTHER, "6", 1, 1.0),
                    new Item(ItemType.OTHER, "7", 1, 1.0),
                    new Item(ItemType.OTHER, "8", 1, 1.0),
                    new Item(ItemType.OTHER, "9", 1, 1.0),
                    new Item(ItemType.OTHER, "10", 1, 1.0),
                    new Item(ItemType.OTHER, "11", 1, 1.0)
            );

            double result = rule.priceToAggregate(cart);

            assertThat(result).isEqualTo(20.0);
        }
    }

    @Nested
    @DisplayName("structural-based")
    class StructuralBasedTests {

        @Test
        void calculate_shouldSumAllRules_usingMockedCart() {
            ShoppingCart cart = mock(ShoppingCart.class);
            List<Item> items = List.of(
                    new Item(ItemType.ELECTRONIC, "Phone", 2, 100.0),
                    new Item(ItemType.OTHER, "Case", 1, 20.0)
            );
            when(cart.getItems()).thenReturn(items);

            List<PriceRule> rules = List.of(
                    new RegularCost(),
                    new DeliveryPrice(),
                    new ExtraCostForElectronics()
            );

            Amazon amazon = new Amazon(cart, rules);

            double result = amazon.calculate();

            assertThat(result).isEqualTo(232.5);
            verify(cart, times(3)).getItems();
        }

        @Test
        void addToCart_shouldDelegateToShoppingCart() {
            ShoppingCart cart = mock(ShoppingCart.class);
            Amazon amazon = new Amazon(cart, List.of());

            Item item = new Item(ItemType.OTHER, "Notebook", 3, 4.0);
            amazon.addToCart(item);

            verify(cart).add(item);
        }
    }
}