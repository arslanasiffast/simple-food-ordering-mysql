# Simple Food Ordering System (Java Swing + Hibernate + MySQL)

A layered Java Swing desktop application demonstrating how **Hibernate ORM** replaces boilerplate JDBC without writing a single line of SQL, HQL, or JPQL.

## Architecture Overview
- `com.foodapp.presentation` — Java Swing UI (`MainFrame`, `ManagerFrame`, `CustomerFrame`)
- `com.foodapp.bll` — Business Logic Layer (`ProductService`, `OrderService`)
- `com.foodapp.dal` — Data Access Layer (`HibernateUtil`, `ProductDAO`, `OrderDAO` using `persist`, `merge`, `remove`, `get`, and `CriteriaQuery`)
- `com.foodapp.dto` — Data Transfer Objects (`ProductDTO`, `OrderDTO`)
- `com.foodapp.entity` — JPA Entities (`Product`, `FoodOrder`)

## 1. MySQL Configuration
1. Make sure your **MySQL Server** is running on `localhost:3306`.
2. Open `src/main/resources/hibernate.cfg.xml` and set your MySQL username and password:
   ```xml
   <property name="hibernate.connection.username">root</property>
   <property name="hibernate.connection.password">YOUR_MYSQL_PASSWORD</property>
   ```
3. You do **not** need to manually create the database or tables:
   - `createDatabaseIfNotExist=true` in the JDBC URL automatically creates `food_ordering_db`.
   - `hibernate.hbm2ddl.auto=update` automatically generates the `products` and `food_orders` tables.

## 2. How to Open & Build in IntelliJ IDEA
1. Extract this ZIP archive to your workspace folder.
2. Open **IntelliJ IDEA** -> click **File > Open...**
3. Select the extracted folder (or its `pom.xml` file) and click **OK** (choose **Open as Project** if prompted).
4. IntelliJ will automatically detect Maven and download `hibernate-core` and `mysql-connector-j`.
   *(If dependencies do not load immediately, open the **Maven** tool window on the right and click the **Reload All Maven Projects** icon.)*
5. To rebuild the project: click **Build > Rebuild Project** from the top menu.

## 3. Running the Application
1. Navigate to `src/main/java/com/foodapp/presentation/MainFrame.java`.
2. Right-click `MainFrame.java` -> **Run 'MainFrame.main()'**.
3. Open the **Manager Module** first to add food items, then open the **Customer Module** to place orders.
