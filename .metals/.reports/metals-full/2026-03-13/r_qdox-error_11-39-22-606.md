error id: file://<WORKSPACE>/src/main/java/org/example/Amazon/ShoppingCartAdaptor.java
file://<WORKSPACE>/src/main/java/org/example/Amazon/ShoppingCartAdaptor.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[64,1]

error in qdox parser
file content:
```java
offset: 2951
uri: file://<WORKSPACE>/src/main/java/org/example/Amazon/ShoppingCartAdaptor.java
text:
```scala
package org.example.Amazon;

import org.example.Amazon.Cost.ItemType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Class responsible for querying and saving invoices in the database
public class ShoppingCartAdaptor implements ShoppingCart {

    private Database connection;  // Represents the database connection object

    // Constructor that initializes the database connection using dependency injection
    public ShoppingCartAdaptor(Database connection) {
        this.connection = connection;
    }

    @Override
    public void add(Item item) {
        connection.withSql(() -> {  // Executes SQL operations within the database connection
            try (var ps = connection.getConnection().prepareStatement("insert into shoppingcart (name, type, quantity, priceperunit) values (?,?,?,?)")) {  // Prepares the SQL query to insert a new invoice
                ps.setString(1, item.getName());  // Sets the customer name in the query
                ps.setString(2, item.getType().name());  // Sets the invoice value in the query
                ps.setInt(3, item.getQuantity());  // Sets the invoice value in the query
                ps.setDouble(4, item.getPricePerUnit());  // Sets the invoice value in the query
                ps.execute();  // Executes the insert query

                connection.getConnection().commit();  // Commits the transaction to make the changes permanent
            }
            return null;  // Returns null as this operation does not need to return any value
        });
    }

    @Override
    public List<Item> getItems() {
        return connection.withSql(() -> {  // Executes SQL operations within the database connection
            try (var ps = connection.getConnection().prepareStatement("select * from shoppingcart")) {  // Prepares the SQL query to select all invoices
                final var rs = ps.executeQuery();  // Executes the query and stores the result set

                List<Item> ShoppingCart = new ArrayList<>();  // Creates a list to store all retrieved invoices
                while (rs.next()) {  // Iterates through each row in the result set
                    ShoppingCart.add(new Item(ItemType.valueOf(rs.getString("type")),rs.getString("name"),
                            rs.getInt("quantity"),rs.getDouble("priceperunit")));  // Creates a new Invoice object and adds it to the list
                }

                return ShoppingCart;  //  Returns the list of all invoices
            }
        });
    }

   @Override
public int numberOfItems() {
    return connection.withSql(() -> {
        try (var ps = connection.getConnection().prepareStatement("select count(*) from shoppingcart");
             var rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    });
}
@@
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:560)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:691)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:688)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:630)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:628)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1313)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:688)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:940)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:691)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:500)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1575)
```
#### Short summary: 

QDox parse error in file://<WORKSPACE>/src/main/java/org/example/Amazon/ShoppingCartAdaptor.java